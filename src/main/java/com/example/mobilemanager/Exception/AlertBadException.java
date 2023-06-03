package com.example.mobilemanager.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AlertBadException extends RuntimeException {
    private String msg;
    private Integer code;
}
