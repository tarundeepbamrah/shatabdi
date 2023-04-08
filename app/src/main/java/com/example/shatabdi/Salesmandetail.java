package com.example.shatabdi;

public class Salesmandetail {
    String name;
    String phone;
    String role;

    public Salesmandetail(String name, String phone, String role) {
        this.name = name;
        this.phone = phone;
        this.role = role;
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
}
