package com.example.shoppingapplication.model;

public class Item {

    private String name;
    private String imageUrl;
    private int id;

    public Item() {
    }

    public Item(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
