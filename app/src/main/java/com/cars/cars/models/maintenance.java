package com.cars.cars.models;

/**
 * Created by locall on 3/11/2018.
 */

public class maintenance {

    private String details;
    private String nameCompany;
    private String companyid;
    private String userid;
    private String name;
    private  String phone;
    private String city;
    private String street;


    public maintenance(){}

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getStreet() {
        return street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCompanyid() {
        return companyid;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public String getDetails() {
        return details;
    }



    public String getUserid() {
        return userid;
    }

    public void setDetails(String details) {
        this.details = details;
    }



    public void setUserid(String userid) {
        this.userid = userid;
    }
}
