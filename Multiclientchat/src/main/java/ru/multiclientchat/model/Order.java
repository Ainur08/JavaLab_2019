package ru.multiclientchat.model;

public class Order {
    private Integer id;
    private Integer userId;
    private String name;
    private Integer price;

    public Order(Integer userId, String name, Integer price) {
        this.userId = userId;
        this.name = name;
        this.price = price;
    }

    public Order(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
