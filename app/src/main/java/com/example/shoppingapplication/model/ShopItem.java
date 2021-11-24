package com.example.shoppingapplication.model;

public class ShopItem {
    private int id;
    private String name;
    private String deliveryTime;
    private String imageUrl;
    private String location;
    private String desc;
    private String categories;
    private double ratingScore;
    private double lat;
    private double lon;


    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public double getRatingScore() {
        return ratingScore;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
