package ru.contextservlet.dto;

public class Product {
    private Long userId;
    private String name;
    private Integer price;

    public Product(Long userId, String name, Integer price) {
        this.userId = userId;
        this.name = name;
        this.price = price;
    }

    public Product(){

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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
