package ru.multiclientchat.mainchat.server;

import ru.multiclientchat.config.ConnectionConfig;
import ru.multiclientchat.server.MultiClientServer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ChatServerMain {
    public static void main(String[] args) {
        MultiClientServer multiClientServer =
                new MultiClientServer();
        multiClientServer.start(Integer.parseInt(args[0]));

    }

    public static void setDbProperty() {
        FileInputStream fileInputStream;
        Properties properties = new Properties();
        try {
            fileInputStream = new FileInputStream("db.properties");
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
