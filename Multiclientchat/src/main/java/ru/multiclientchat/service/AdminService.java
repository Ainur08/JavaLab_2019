package ru.multiclientchat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.multiclientchat.ChatClient;
import ru.multiclientchat.controller.MenuController;
import ru.multiclientchat.model.Payload;
import ru.multiclientchat.net.Session;

import java.util.LinkedHashMap;
import java.util.Scanner;

public class AdminService {
    private ChatClient client;
    private Session session;

    public AdminService(ChatClient client, Session session) {
        this.client = client;
        this.session = session;
    }

    public void addProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Add Product ===");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Price: ");
        Integer price = scanner.nextInt();
        ObjectMapper objectMapper = new ObjectMapper();
        Payload payload = new Payload();
        payload.setHeader("Command");
        LinkedHashMap<String, String> commandPayload = new LinkedHashMap<>();
        commandPayload.put("command", "set product");
        commandPayload.put("name", name);
        commandPayload.put("price", String.valueOf(price));
        payload.setPayload(commandPayload);
        try {
            String jsonProduct = objectMapper.writeValueAsString(payload);
            client.sendMessage(jsonProduct);
            new MenuController(client).openUserMenu(session);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    public void deleteProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Delete Product ===");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        ObjectMapper objectMapper = new ObjectMapper();
        Payload payload = new Payload();
        payload.setHeader("Command");
        LinkedHashMap<String, String> commandPayload = new LinkedHashMap<>();
        commandPayload.put("command", "delete product");
        commandPayload.put("name", name);
        payload.setPayload(commandPayload);
        try {
            String jsonProduct = objectMapper.writeValueAsString(payload);
            client.sendMessage(jsonProduct);
            new MenuController(client).openUserMenu(session);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }
}
