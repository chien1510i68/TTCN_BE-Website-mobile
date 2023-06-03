package com.example.mobilemanager.Controller.AuthController;


import com.example.mobilemanager.Constant.ErrorCodeDefs;
import com.example.mobilemanager.Request.AuthRequest.LoginRequest;
import com.example.mobilemanager.Request.AuthRequest.RegisterRequest;
import com.example.mobilemanager.Response.ResponseError.BaseResponse;
import com.example.mobilemanager.Response.ResponseSuccess.ItemResponse;
import com.example.mobilemanager.Response.ResponseSuccess.LogginResponse;
import com.example.mobilemanager.Service.AuthService.AuthenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthenService authenService;

    @Autowired
    public AuthController(AuthenService authenService) {
        this.authenService = authenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        LogginResponse jwtResponse = authenService.authenticateUser(loginRequest.getUserName(), loginRequest.getPassword());
        ItemResponse<LogginResponse> response = new ItemResponse<>();
        response.setData(jwtResponse);
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        BaseResponse response = new BaseResponse();
        try {
            authenService.registerUser(signUpRequest);
            response.setSuccess(true);
        } catch (Exception ex) {
            response.setFailed(ErrorCodeDefs.SERVER_ERROR, ex.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}
