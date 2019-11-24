package ru.multiclientchat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.sun.org.apache.xpath.internal.operations.Or;
import ru.multiclientchat.model.Message;
import ru.multiclientchat.model.Payload;
import ru.multiclientchat.model.Product;
import ru.multiclientchat.model.User;
import ru.multiclientchat.server.MultiClientServer;
import ru.multiclientchat.service.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class PayloadController {

    private Socket clientSocket;
    private PrintWriter out;
    private User user;
    private List<MultiClientServer.ClientHandler> clients;
    private MultiClientServer.ClientHandler client;

    public PayloadController(Socket clientSocket, List<MultiClientServer.ClientHandler> clients, MultiClientServer.ClientHandler client) {
        try {
            this.clientSocket = clientSocket;
            this.clients = clients;
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.client = client;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void handleRequest(String jsonRequest) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Payload payload = objectMapper.readValue(jsonRequest, Payload.class);
            String header = payload.getHeader();
            switch (header) {
                case "Login": {
                    doLogin(payload);
                }
                break;
                case "Logout": {
                    doLogout(payload);
                }
                break;
                case "Message": {
                    doMessage(payload);
                }
                break;
                case "Command": {
                    LinkedHashMap<String, String> command = (LinkedHashMap) payload.getPayload();
                    switch (command.get("command")) {
                        case "get messages": {
                            doPagination(payload);
                        }
                        break;
                        case "set product": {
                            ProductService productsService = new ProductService();
                            productsService.addProduct(jsonRequest);
                        }
                        break;
                        case "get products": {
                            ProductService productsService = new ProductService();
                            out.println(productsService.getProducts());
                        }
                        break;
                        case "delete product": {
                            ProductService productsService = new ProductService();
                            productsService.deleteProduct(jsonRequest);
                        }
                        break;
                        case "buy product": {
                            OrderService orderService = new OrderService();
                            orderService.addProduct(jsonRequest);
                        }
                        break;
                        case "get orders":{
                            OrderService orderService = new OrderService();
                            out.println(orderService.getOrders(Integer.valueOf(command.get("userId"))));
                        }
                    }
                }
                break;
            }
        } catch (IOException | JOSEException e) {
            throw new IllegalStateException(e);
        }
    }

    public void doLogin(Payload payload) throws JOSEException {
        LoginService loginService = new LoginService();
        this.user = loginService.login((LinkedHashMap<String, String>) payload.getPayload());

        // отправка токена пользователю
        if (user != null) {
            TokenService tokenService = new TokenService();
            Payload auth = new Payload();
            ObjectMapper mapper = new ObjectMapper();

            String token = tokenService.getToken(user.getId(), user.getRole());
            auth.setHeader("token");
            auth.setPayload(token);
            try {
                out.println(mapper.writeValueAsString(auth));
            } catch (JsonProcessingException e) {
                throw new IllegalStateException(e);
            }
        } else {
            out.println("Wrong password or login");
        }
    }

    public void doLogout(Payload payload) {
        clients.remove(client);
        System.out.println(user.getName() + " left the chat");
    }

    public void doMessage(Payload payload) {
        MessageService messageService = new MessageService();
        messageService.sendMessage((LinkedHashMap) payload.getPayload(), clients, user);
    }

    public void doPagination(Payload payload) {
        CommandService commandService = new CommandService();
        ObjectMapper mapper = new ObjectMapper();
        HashMap hashMap = new HashMap();
        LinkedHashMap<Integer, Integer> command = (LinkedHashMap) payload.getPayload();
        try {
            LinkedList messages = commandService.getMessages(command.get("page"), command.get("size"));
            hashMap.put("data", messages);
            String jsonObject = mapper.writeValueAsString(hashMap);
            out.println(jsonObject);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }
}

