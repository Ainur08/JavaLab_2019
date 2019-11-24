package ru.multiclientchat.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionConfig {
    private String driverName;
    private String dbUrl;
    private String id;
    private String pass;

    private static ConnectionConfig instance;

    public ConnectionConfig(String driverName, String dbUrl, String id, String pass) {
        this.driverName = driverName;
        this.dbUrl = dbUrl;
        this.id = id;
        this.pass = pass;
    }

    public static ConnectionConfig getInstance() {
        return instance;
    }

    public static void setInstance(String driverName, String dbUrl, String id, String pass) {
        instance = new ConnectionConfig(driverName, dbUrl, id, pass);
    }

    public Connection getConnection() {
        try {
            Class.forName(driverName);
            return DriverManager.getConnection(dbUrl, id, pass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
