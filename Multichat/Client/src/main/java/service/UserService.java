package service;

import client.ChatClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.MenuController;
import model.Payload;
import model.User;
import net.Session;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class UserService {
    private ChatClient client;
    private Session session;
    private User user;

    public UserService(ChatClient client, Session session, User user) {
        this.client = client;
        this.session = session;
        this.user = user;
    }

    public void viewProducts() {
        ObjectMapper objectMapper = new ObjectMapper();
        Payload payload = new Payload();
        payload.setHeader("Command");
        LinkedHashMap<String, String> commandPayload = new LinkedHashMap<>();
        commandPayload.put("command", "get products");
        payload.setPayload(commandPayload);
        try {
            String jsonProduct = objectMapper.writeValueAsString(payload);
            client.sendMessage(jsonProduct);
            new MenuController(client).openUserMenu(session);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    //TODO with userID
    public void viewOrders() {
        ObjectMapper objectMapper = new ObjectMapper();
        Payload payload = new Payload();
        payload.setHeader("Command");
        LinkedHashMap<String, String> commandPayload = new LinkedHashMap<>();
        commandPayload.put("command", "get orders");
        commandPayload.put("userId", "2");
        payload.setPayload(commandPayload);
        try {
            String jsonProduct = objectMapper.writeValueAsString(payload);
            client.sendMessage(jsonProduct);
            new MenuController(client).openUserMenu(session);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    //TODO with userID
    public void buyProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Buy Product ===");
        ObjectMapper objectMapper = new ObjectMapper();
        Payload payload = new Payload();
        System.out.print("Name of product: ");
        String name = scanner.nextLine();
        payload.setHeader("Command");
        HashMap<String, String> commandPayload = new HashMap<>();
        commandPayload.put("command", "buy product");
        commandPayload.put("name", name);
        commandPayload.put("userId", "2");
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
