package com.example.mobilemanager.Service.AuthService;

import com.example.mobilemanager.Request.AuthRequest.RegisterRequest;
import com.example.mobilemanager.Response.ResponseSuccess.LogginResponse;

public interface AuthenService {
    LogginResponse authenticateUser(String username, String password);

    void registerUser(RegisterRequest request);
}
