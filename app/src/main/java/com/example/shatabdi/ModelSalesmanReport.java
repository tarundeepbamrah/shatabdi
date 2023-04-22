package com.example.shatabdi;

public class ModelSalesmanReport {
    String salesman_name,salesman_phone;

    public ModelSalesmanReport(String salesman_name, String salesman_phone) {
        this.salesman_name = salesman_name;
        this.salesman_phone = salesman_phone;
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
