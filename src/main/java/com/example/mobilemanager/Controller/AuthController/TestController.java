package com.example.mobilemanager.Controller.AuthController;

import com.example.mobilemanager.Response.ResponseSuccess.ItemResponse;
import com.example.mobilemanager.Response.ResponseSuccess.TestResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
    public ItemResponse allAccess() {
        TestResponse response = new TestResponse("Public Content.");
        return new ItemResponse(true, response);
    }
//    @GetMapping("/user")
//    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
//    public String userAccess() {
//        return "User Content.";
//    }

    @GetMapping("/mod")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ItemResponse moderatorAccess() {
        TestResponse response = new TestResponse("Moderator Board.");
        return new ItemResponse<>(true,  response);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ItemResponse adminAccess() {
        TestResponse response = new TestResponse("Admin Board.");
        return new ItemResponse<>(true, response);
    }

    @GetMapping("/products/list")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ItemResponse productList() {
        TestResponse response = new TestResponse("Admin product list.");
        return new ItemResponse<>(true, response);
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ItemResponse userAcess() {
        TestResponse response = new TestResponse("User Board.");
        return new ItemResponse<>(true, response);
    }
}