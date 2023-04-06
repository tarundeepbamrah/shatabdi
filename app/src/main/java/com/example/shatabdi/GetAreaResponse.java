package com.example.shatabdi;

import java.util.List;

public class GetAreaResponse {
    String status;
    String message;
    List<ModelArea> data;

    public GetAreaResponse(String status, String message, List<ModelArea> data) {
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

    public List<ModelArea> getData() {
        return data;
    }

    public void setData(List<ModelArea> data) {
        this.data = data;
    }
}
