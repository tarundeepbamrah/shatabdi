package com.example.shatabdi;

import java.util.List;

public class GetResponse2 {
    String status;
    String message;
    List<ModelDealers> data;

    public GetResponse2(String status, String message, List<ModelDealers> data) {
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

    public List<ModelDealers> getData() {
        return data;
    }

    public void setData(List<ModelDealers> data) {
        this.data = data;
    }
}
