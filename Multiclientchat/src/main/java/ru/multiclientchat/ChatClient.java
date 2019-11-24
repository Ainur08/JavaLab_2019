package ru.multiclientchat;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.multiclientchat.config.ConnectionConfig;
import ru.multiclientchat.controller.MenuController;
import ru.multiclientchat.model.Archive;
import ru.multiclientchat.model.Message;
import ru.multiclientchat.model.Payload;
import ru.multiclientchat.model.User;
import ru.multiclientchat.net.Session;
import ru.multiclientchat.service.TokenClientService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

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
                        if (message.contains("data")) {
                            ObjectMapper objectMapper = new ObjectMapper();
                            try {
                                HashMap hashMap = objectMapper.readValue(message, HashMap.class);
                                ArrayList arrayList = (ArrayList) hashMap.get("data");
                                for (int i = 0; i < arrayList.size(); i++) {
                                    System.out.println(arrayList.get(i));
                                }
                            } catch (Exception e) {
                                throw new IllegalStateException(e);
                            }
                        } else if (message.contains("token")) {
                            Session session = new TokenClientService().saveToken(message);
                            new MenuController(chatClient).openUserMenu(session);
                        } else if (message.contains("products")) {
                            ObjectMapper objectMapper = new ObjectMapper();
                            try {
                                HashMap hashMap = objectMapper.readValue(message, HashMap.class);
                                ArrayList arrayList = (ArrayList) hashMap.get("products");
                                for (int i = 0; i < arrayList.size(); i++) {
                                    System.out.println(arrayList.get(i));
                                }
                            } catch (Exception e) {
                                throw new IllegalStateException(e);
                            }
                        } else if (message.contains("orders")) {
                            ObjectMapper objectMapper = new ObjectMapper();
                            try {
                                HashMap hashMap = objectMapper.readValue(message, HashMap.class);
                                ArrayList arrayList = (ArrayList) hashMap.get("orders");
                                for (int i = 0; i < arrayList.size(); i++) {
                                    System.out.println(arrayList.get(i));
                                }
                            } catch (Exception e) {
                                throw new IllegalStateException(e);
                            }
                        } else {
                            System.out.println(message);
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

