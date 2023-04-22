package com.example.shatabdi;

import java.util.List;

public class GetAttendanceResponse {
        String status;
        String message;
        List<ModelAttendance> data;

        public GetAttendanceResponse(String status, String message, List<ModelAttendance> data) {
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

        public List<ModelAttendance> getData() {
            return data;
        }

        public void setData(List<ModelAttendance> data) {
            this.data = data;
        }

}
