package com.cars.cars.models;

/**
 * Created by locall on 3/11/2018.
 */

public class Car {
    private String type;
    private String model;
    private String price;
    private String details;
    private String typeView;
    private String userid;
    private String imageURL;
    public Car(){}

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDetails() {
        return details;
    }

    public String getModel() {
        return model;
    }

    public String getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getTypeView() {
        return typeView;
    }

    public String getUserid() {
        return userid;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTypeView(String typeView) {
        this.typeView = typeView;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
