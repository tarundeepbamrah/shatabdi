package com.example.shatabdi;

public class Salesmandetail {
    String name;
    String phone;
    String role;
    String UserId;

    public Salesmandetail(String name, String phone, String role,String UserId) {
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.UserId = UserId;
    }

    public Salesmandetail() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
