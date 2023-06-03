package com.example.mobilemanager.Request.AuthRequest;

import lombok.Data;

@Data

public class RegisterRequest {
    private String userName;
    private String password;
    private String phoneNumber ;
    private String email;
    private String address;
}
