package com.example.shatabdi;

public class ModelDealers {
    int id;
    String city,area,dealer,dealer_name,phone,date;

    public ModelDealers(int id, String city, String area, String dealer, String dealer_name, String phone, String date) {
        this.id = id;
        this.city = city;
        this.area = area;
        this.dealer = dealer;
        this.dealer_name = dealer_name;
        this.phone = phone;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ModelDealers(String city) {
        this.city = city;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getDealer_name() {
        return dealer_name;
    }

    public void setDealer_name(String dealer_name) {
        this.dealer_name = dealer_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public ModelDealers() {
    }
}
