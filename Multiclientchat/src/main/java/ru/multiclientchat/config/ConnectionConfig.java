package ru.multiclientchat.config;

import java.sql.Connection;
import java.sql.DriverManager;

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
}
