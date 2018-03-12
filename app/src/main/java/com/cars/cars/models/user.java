package com.cars.cars.models;

/**
 * Created by locall on 2/14/2018.
 */

public class user {
    private String name;
    private String password;
    private String typeUser;
    private String phone;
    private String city;
    private String location;
    private String street;
    private String zip;
    private String email;


    public user(){}

    public user(String username, String password, String typeUser){
        this.name=username;
        this.password=password;
        this.typeUser=typeUser;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public String getPhone() {
        return phone;
    }

    public String getStreet() {
        return street;
    }

    public String getZip() {
        return zip;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPassword() {
        return password;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public String getName() {
        return name;
    }



    public void setPassword(String password) {
        this.password = password;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    public void setName(String username) {
        this.name = username;
    }
}
