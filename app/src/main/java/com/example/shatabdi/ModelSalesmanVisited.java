package com.example.shatabdi;

public class ModelSalesmanVisited {
    String salesman_name,salesman_phone,date;

    public ModelSalesmanVisited(String salesman_name, String salesman_phone,String date) {
        this.salesman_name = salesman_name;
        this.salesman_phone = salesman_phone;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSalesman_name() {
        return salesman_name;
    }

    public void setSalesman_name(String salesman_name) {
        this.salesman_name = salesman_name;
    }

    public String getSalesman_phone() {
        return salesman_phone;
    }

    public void setSalesman_phone(String salesman_phone) {
        this.salesman_phone = salesman_phone;
    }

}
