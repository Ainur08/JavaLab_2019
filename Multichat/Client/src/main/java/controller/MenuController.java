package controller;

import client.ChatClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;
import net.Session;
import service.AdminService;
import service.UserService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class MenuController {
    private ChatClient client;
    private User user;

    public MenuController(ChatClient client) {
        this.client = client;
    }

    public MenuController() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void openWelcomeMenu() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("=== MultiChat ===");
            System.out.println("Select number");
            System.out.println("1. Login");
            int button = scanner.nextInt();
            if (button == 1) {
                openLoginPage();
                break;
            } else {
                System.err.println("Wrong button");
            }
        }
    }

    public void openLoginPage() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Login ===");
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        // авторизация с помощью json
        AuthData authData = new AuthData();
        authData.setLogin(login);
        authData.setPassword(password);
        Payload payload = new Payload("Login", authData);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonValue = objectMapper.writeValueAsString(payload);
            // перехватит payload контроллер на стороне сервака
            client.sendMessage(jsonValue);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void openUserMenu(Session session) {
        new HelperOfMenu(session).start();
    }

    private class HelperOfMenu extends Thread {
        private Session session;

        public HelperOfMenu(Session session) {
            this.session = session;
        }

        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("=== User menu ===");
            System.out.println("Hello, " + session.getRole());
            // варианты действий
            System.out.println("Select number");
            System.out.println("1. Open the chat");
            System.out.println("2. Show message archive");
            if (session.getRole().equals("admin")) {
                System.out.println("3. Add product");
                System.out.println("4. Delete product");
            } else {
                System.out.println("3. Buy product");
                System.out.println("4. My Orders");
            }
            System.out.println("5. List products");
            // выбор действия
            AdminService adminService = new AdminService(client, session);
            UserService userService = new UserService(client, session, user);
            int button = scanner.nextInt();
            if (button == 1) {
                openChat();
            } else if (button == 2) {
                openArchive();
            } else if (button == 3) {
                if (session.getRole().equals("admin")) {
                    adminService.addProduct();
                } else {
                    userService.buyProduct();
                }
            } else if (button == 4) {
                if (session.getRole().equals("admin")) {
                    adminService.deleteProduct();
                } else {
                    userService.viewOrders();
                }
            } else if (button == 5) {
                userService.viewProducts();
            } else if (button == 0) {
//                logout();
            } else {
                System.err.println("Wrong button");
            }
        }
    }

    public void openChat() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String messageText = scanner.nextLine();
            //TODO with userID
            Message message = new Message(String.valueOf(1), new SimpleDateFormat("HH:mm:ss").format(new Date()), messageText);
            Payload payload = new Payload("Message", message);
            ObjectMapper mapper = new ObjectMapper();
            try {
                String jsonValue = mapper.writeValueAsString(payload);
                client.sendMessage(jsonValue); // перехватит payload контроллер на стороне сервака
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public void openArchive() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select page and size");
        System.out.print("Page: ");
        int page = scanner.nextInt();
        System.out.print("Size: ");
        int size = scanner.nextInt();
        Archive archive = new Archive("get messages", String.valueOf(page), String.valueOf(size));
        Payload payload = new Payload("Command", archive);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonValue = mapper.writeValueAsString(payload);
            client.sendMessage(jsonValue); // перехватит payload контроллер на стороне сервака
            System.out.println("Select number");
            System.out.println("1. Want to open a chat?");
            System.out.println("2. Show message archive again?");
            int button = scanner.nextInt();
            if (button == 1) {
                openChat();
            }
            if (button == 2) {
                openArchive();
            } else {
                System.err.println("Wrong button");
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

//    public void logout() {
//        Scanner scanner = new Scanner(System.in);
//        Payload payload = new Payload();
//        payload.setHeader("Logout");
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            String jsonObject = mapper.writeValueAsString(payload);
//            client.sendMessage(jsonObject);
//            System.out.println("1. Want to login?");
//            int button = scanner.nextInt();
//            if (button == 1) {
//                openLoginPage();
//            } else {
//                System.err.println("Wrong button");
//            }
//        } catch (Exception e) {
//            throw new IllegalStateException(e);
//        }
//    }
}

