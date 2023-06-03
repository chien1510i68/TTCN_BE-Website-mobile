package com.example.mobilemanager.Response.ResponseSuccess;

import com.example.mobilemanager.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogginResponse {
    private String jwt;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
}
