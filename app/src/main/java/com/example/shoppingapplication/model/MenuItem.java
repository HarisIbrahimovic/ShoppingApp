package com.example.shoppingapplication.model;

public class MenuItem {

    private int id;
    private int shopId;
    private String name;
    private String desc;
    private String imageUrl;
    private double price;

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getShopId() {
        return shopId;
    }

    public MenuItem(int id, int shopId, String name, String desc, String imageUrl, double price) {
        this.id = id;
        this.shopId = shopId;
        this.name = name;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public MenuItem(){

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getPrice() {
        return price;
    }
}
