package com.example.mobilemanager.Constant;

import org.springframework.stereotype.Component;

@Component
public class ErrorCodeDefs {
    public static final int OTHER = 1;
    public static final int VALIDATION_ERROR = 2;
    public static final int SERVER_ERROR = 500;
    public static final int BAD_REQUEST = 400;
    public static final int TOKEN_INVALID = 404;


    public static final String getErrMsg(int code) {
        switch (code) {
            case OTHER:
                return "Các lỗi khác";
            case VALIDATION_ERROR:
                return "Tham số không hợp lệ";
            case SERVER_ERROR:
                return "Lỗi hệ thống";
            case BAD_REQUEST:
                return "Bad request";
            default:
                return "Không xác định";
        }
    }
}
