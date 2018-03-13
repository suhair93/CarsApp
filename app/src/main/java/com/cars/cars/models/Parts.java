package com.cars.cars.models;

/**
 * Created by locall on 3/11/2018.
 */

public class Parts {
    private String name;
    private String model;
    private String price;
    private String details;
    private String userid;
    private String number;
    private String imageURL;
    public Parts(){}

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUserid() {
        return userid;
    }

    public String getPrice() {
        return price;
    }

    public String getModel() {
        return model;
    }

    public String getDetails() {
        return details;
    }

    public String getName() {
        return name;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setName(String name) {
        this.name = name;
    }
}
