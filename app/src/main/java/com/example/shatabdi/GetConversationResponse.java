package com.example.shatabdi;

import java.util.List;

public class GetConversationResponse {
        String status;
        String message;
        List<ModelConversation> data;

        public GetConversationResponse(String status, String message, List<ModelConversation> data) {
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

        public List<ModelConversation> getData() {
            return data;
        }

        public void setData(List<ModelConversation> data) {
            this.data = data;
        }
}
