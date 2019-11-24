package ru.multiclientchat.net;

public class Session {
    private Integer id;
    private String role;
    private static Session instance;

    public Session() {

    }

    public Session(Integer id, String role) {
        this.id = id;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static Session getInstance(String role, Integer id) {
        if (instance == null) {
            instance = new Session(id, role);
        }
        return instance;
    }

    public static Session getInstance() {
        return instance;
    }

    public void setInstance(Session instance) {
        this.instance = instance;
    }
}
