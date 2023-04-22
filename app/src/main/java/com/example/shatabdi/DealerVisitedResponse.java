package com.example.shatabdi;

import java.util.List;

public class DealerVisitedResponse {
    String status;
    String message;
    List<ModelDealerVisited> data;

    public DealerVisitedResponse(String status, String message, List<ModelDealerVisited> data) {
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

    public List<ModelDealerVisited> getData() {
        return data;
    }

    public void setData(List<ModelDealerVisited> data) {
        this.data = data;
    }
}
