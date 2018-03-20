package com.cars.cars.models;

/**
 * Created by locall on 3/19/2018.
 */

public class saeling_request {




    private String customer_id;
    private String company_id;
    private String type_service;
    private String model_service;
    private String typeview;
    private String name_customer;


    public saeling_request(){}


    public void setName_customer(String name_customer) {
        this.name_customer = name_customer;
    }

    public String getName_customer() {
        return name_customer;
    }

    public String getType_service() {
        return type_service;
    }

    public String getCompany_id() {
        return company_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getModel_service() {
        return model_service;
    }

    public String getTypeview() {
        return typeview;
    }

    public void setType_service(String type_service) {
        this.type_service = type_service;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public void setModel_service(String model_service) {
        this.model_service = model_service;
    }

    public void setTypeview(String typeview) {
        this.typeview = typeview;
    }
}