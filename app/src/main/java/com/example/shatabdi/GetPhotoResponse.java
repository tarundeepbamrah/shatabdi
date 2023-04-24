package com.example.shatabdi;

import java.util.List;

public class GetPhotoResponse {
    String status;
    String message;
    List<ModelPhoto> data;

    public GetPhotoResponse(String status, String message, List<ModelPhoto> data) {
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

    public List<ModelPhoto> getData() {
        return data;
    }

    public void setData(List<ModelPhoto> data) {
        this.data = data;
    }
}
