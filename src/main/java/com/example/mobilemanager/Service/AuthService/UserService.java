package com.example.mobilemanager.Service.AuthService;

import com.example.mobilemanager.Entity.User;
import com.example.mobilemanager.Model.DTO.UserDTO;
import com.example.mobilemanager.Request.UserRequest.CreateUserRequest;
import com.example.mobilemanager.Request.UserRequest.GetListUserRequest;
import com.example.mobilemanager.Request.UserRequest.UpdateUserRequest;
import org.springframework.data.domain.Page;

public interface UserService {
    UserDTO createUser(CreateUserRequest request);

    UserDTO updateUser(UpdateUserRequest request);

    void deleteUser(Long id);


    Page<User> getAllUsers(GetListUserRequest request);


    UserDTO findUserById(Long id);
}
