package com.example.shatabdi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GetStringResponse {
    String status;
    String message;
    List<ModelCity> data;

    public GetStringResponse(String status, String message, List<ModelCity> data) {
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

    public List<ModelCity> getData() {
        return data;
    }

    public void setData(List<ModelCity> data) {
        this.data = data;
    }
}
