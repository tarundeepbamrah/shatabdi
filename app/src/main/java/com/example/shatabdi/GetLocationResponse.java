package com.example.shatabdi;

import java.util.List;

public class GetLocationResponse {
        String status;
        String message;
        List<ModelLocation> data;

        public GetLocationResponse(String status, String message, List<ModelLocation> data) {
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

        public List<ModelLocation> getData() {
            return data;
        }

        public void setData(List<ModelLocation> data) {
            this.data = data;
        }

}
