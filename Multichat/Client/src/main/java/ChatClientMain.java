import client.ChatClient;
import controller.MenuController;

import java.util.Scanner;

public class ChatClientMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ChatClient chatClient = new ChatClient();
        chatClient.startConnection(args[0], Integer.parseInt(args[1]));
        MenuController menuController = new MenuController(chatClient);
        menuController.openWelcomeMenu();
        chatClient.setMenuController(menuController);
    }
}
