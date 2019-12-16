package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.MenuController;
import model.Payload;
import model.User;
import net.Session;
import service.TokenClientService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatClient {
    private User user;
    private ChatClient chatClient;
    private Socket clientSocket;
    private MenuController menuController;
    private PrintWriter writer; // поток чтения в сокет
    private BufferedReader reader; // поток чтения из сокета

    public void startConnection(String ip, int port) {
        try {
            chatClient = this;
            clientSocket = new Socket(ip, port);
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            menuController = new MenuController(this);
            this.user = menuController.getUser();
            new ReadMessage().start();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private class ReadMessage extends Thread {
        @Override
        public void run() {
            try {
                String message;
                while (true) {
                    message = reader.readLine();
                    if (message != null) {
                        // TODO вынести в отдельный класс
                        if (message.contains("data")) {
                            ObjectMapper objectMapper = new ObjectMapper();
                            try {
                                Payload payloadOfLoginStatus = objectMapper.readValue(message, Payload.class);
                                HashMap<String, ArrayList> map = (HashMap<String, ArrayList>) payloadOfLoginStatus.getPayload();

                                ArrayList arrayList = map.get("messages");
                                for (int i = 0; i < arrayList.size(); i++) {
                                    System.out.println(arrayList.get(i));
                                }
                            } catch (Exception e) {
                                throw new IllegalStateException(e);
                            }
                        } else if (message.contains("token")) {
                            ObjectMapper objectMapper = new ObjectMapper();
                            Payload payloadOfLoginStatus = objectMapper.readValue(message, Payload.class);
                            HashMap<String, String> map = (HashMap<String, String>) payloadOfLoginStatus.getPayload();

                            Session session = new TokenClientService().saveToken(map.get("token"));
                            new MenuController(chatClient).openUserMenu(session);
                        } else if (message.contains("products")) {
                            ObjectMapper objectMapper = new ObjectMapper();
                            try {
                                Payload payloadOfLoginStatus = objectMapper.readValue(message, Payload.class);
                                HashMap<String, ArrayList> map = (HashMap<String, ArrayList>) payloadOfLoginStatus.getPayload();

                                ArrayList arrayList = map.get("products");
                                for (int i = 0; i < arrayList.size(); i++) {
                                    System.out.println(arrayList.get(i));
                                }
                            } catch (Exception e) {
                                throw new IllegalStateException(e);
                            }
                        } else if (message.contains("orders")) {
                            ObjectMapper objectMapper = new ObjectMapper();
                            try {
                                Payload payloadOfLoginStatus = objectMapper.readValue(message, Payload.class);
                                HashMap<String, ArrayList> map = (HashMap<String, ArrayList>) payloadOfLoginStatus.getPayload();

                                ArrayList arrayList = map.get("orders");
                                for (int i = 0; i < arrayList.size(); i++) {
                                    System.out.println(arrayList.get(i));
                                }
                            } catch (Exception e) {
                                throw new IllegalStateException(e);
                            }
                        } else if (message.contains("message")) {
                            ObjectMapper objectMapper = new ObjectMapper();
                            Payload payloadOfLoginStatus = objectMapper.readValue(message, Payload.class);
                            HashMap<String, String> map = (HashMap<String, String>) payloadOfLoginStatus.getPayload();
                            System.out.println(map.get("dateTime") + " <" + "Ainur" + ">: " + map.get("message"));
                        }
                    }
                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public MenuController getMenuController() {
        return menuController;
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    public void sendMessage(String message) {
        writer.println(message);
    }
}

