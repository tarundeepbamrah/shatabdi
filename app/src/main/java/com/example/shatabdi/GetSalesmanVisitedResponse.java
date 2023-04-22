package com.example.shatabdi;

import java.util.List;

public class GetSalesmanVisitedResponse {
    String status;
    String message;
    List<ModelSalesmanVisited> data;

    public GetSalesmanVisitedResponse(String status, String message, List<ModelSalesmanVisited> data) {
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

    public List<ModelSalesmanVisited> getData() {
        return data;
    }

    public void setData(List<ModelSalesmanVisited> data) {
        this.data = data;
    }
}
