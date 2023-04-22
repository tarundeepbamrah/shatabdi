package com.example.shatabdi;

import java.util.List;

public class GetSalesmanResponse {
    String status;
    String message;
    List<ModelSalesmanReport> data;

    public GetSalesmanResponse(String status, String message, List<ModelSalesmanReport> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ModelSalesmanReport> getData() {
        return data;
    }

    public void setData(List<ModelSalesmanReport> data) {
        this.data = data;
    }
}
