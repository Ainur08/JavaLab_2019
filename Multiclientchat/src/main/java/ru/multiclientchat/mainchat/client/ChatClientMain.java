package ru.multiclientchat.mainchat.client;

import ru.multiclientchat.client.ChatClient;
import ru.multiclientchat.config.ConnectionConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class ChatClientMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        setDbProperty();
        ChatClient chatClient = new ChatClient();
        chatClient.startConnection(args[0], Integer.parseInt(args[1]));
    }

    public static void setDbProperty() {
        FileInputStream fileInputStream;
        Properties properties = new Properties();
        try {
            fileInputStream = new FileInputStream("src/main/resources/db.properties");
            properties.load(fileInputStream);

            String dbName = properties.getProperty("db.name");
            String dbUrl = properties.getProperty("db.url");
            String dbId = properties.getProperty("db.id");
            String dbPassword = properties.getProperty("db.password");

            ConnectionConfig.setInstance(dbName, dbUrl, dbId, dbPassword);
            System.out.println(ConnectionConfig.getInstance());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
