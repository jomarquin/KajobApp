package com.kajoba.app.Entities;

import java.io.Serializable;

public class Partner implements Serializable {

    private Integer id;
    private String name;
    private String identification;
    private String phone;
    private String email;
    private String location;
    private String birthDate;

    public Partner() {
    }

    public Partner(Integer id, String name, String identification, String phone, String email, String location, String birthDate) {
        this.id = id;
        this.name = name;
        this.identification = identification;
        this.phone = phone;
        this.email = email;
        this.location = location;
        this.birthDate = birthDate;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIdentification() {
        return identification;
    }
    public void setIdentification(String identification) {
        this.identification = identification;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
