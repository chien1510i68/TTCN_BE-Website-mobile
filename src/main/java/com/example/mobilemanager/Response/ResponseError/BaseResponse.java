package com.example.mobilemanager.Response.ResponseError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse {
    private boolean success = false;
    private Error error;
    public void setFailed(int code, String message) {
        error = new Error(code,message, null);
        success = false;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Error {
        private int status;
        private String message;
        private List<DetailError> detailErrorList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DetailError {
        private String id;
        private String message;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ErrorDetail {
        private String message;
        private String id;
    }
}
