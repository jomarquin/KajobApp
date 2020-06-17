package com.kajoba.app.Entities;

public class Product {

    private String id;
    private String name;
    private String category;
    private String description;
    private Integer quantity;
    private Integer cost;
    private Integer price;

    public Product() {
    }

    public Product(String id, String name, String category, String description, Integer quantity, Integer cost, Integer price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.quantity = quantity;
        this.cost = cost;
        this.price = price;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Integer getCost() {
        return cost;
    }
    public void setCost(Integer cost) {
        this.cost = cost;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
}
