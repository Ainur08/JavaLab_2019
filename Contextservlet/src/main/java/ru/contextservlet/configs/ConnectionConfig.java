package ru.contextservlet.configs;

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

    public ConnectionConfig() {
        this.driverName = "org.postgresql.Driver";
        this.dbUrl = "jdbc:postgresql://localhost:5432/context";
        this.id = "postgres";
        this.pass = "123456";
    }

    public static ConnectionConfig getInstance() {
        if (instance == null) {
            instance = new ConnectionConfig();
        }
        return instance;
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
