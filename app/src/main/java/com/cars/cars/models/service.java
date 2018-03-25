package com.cars.cars.models;

/**
 * Created by locall on 3/11/2018.
 */

public class service {
    private String name_or_type;
    private String model;
    private String price;
    private String details;
    private String userid;
    private String number;
    private String type_service;
    private String typeView;
    private String imageURL;
    private String company;
    private String city;
    private String company_street;
    public service(){}

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getType_service() {
        return type_service;
    }

    public void setType_service(String type_service) {
        this.type_service = type_service;
    }


    public String getTypeView() {
        return typeView;
    }

    public String getName_or_type() {
        return name_or_type;
    }

    public void setTypeView(String typeView) {
        this.typeView = typeView;
    }

    public void setName_or_type(String name_or_type) {
        this.name_or_type = name_or_type;
    }

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


}
