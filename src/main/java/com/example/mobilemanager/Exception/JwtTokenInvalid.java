package com.example.mobilemanager.Exception;

import lombok.Data;

@Data
public class JwtTokenInvalid extends RuntimeException{
    public JwtTokenInvalid(String message) {
        super(message);
    }
}
