package com.example.shatabdi;

import java.util.List;

public class GetResponse {
    String status;
    String message;
    List<ModelDealer> data;

    public GetResponse(String status, String message, List<ModelDealer> data) {
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

    public List<ModelDealer> getData() {
        return data;
    }

    public void setData(List<ModelDealer> data) {
        this.data = data;
    }
}
