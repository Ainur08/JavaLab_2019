package ru.multiclientchat.mainchat.client;

import ru.multiclientchat.ChatClient;
import ru.multiclientchat.controller.MenuController;

import java.util.Scanner;

import static ru.multiclientchat.config.ConnectionConfig.setDbProperty;

public class p2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        setDbProperty();
        ChatClient chatClient = new ChatClient();
        chatClient.startConnection(args[0], Integer.parseInt(args[1]));
        MenuController menuController = new MenuController(chatClient);
        menuController.openWelcomeMenu();
        chatClient.setMenuController(menuController);
    }
}
