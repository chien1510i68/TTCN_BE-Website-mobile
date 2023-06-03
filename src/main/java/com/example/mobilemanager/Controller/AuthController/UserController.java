package com.example.mobilemanager.Controller.AuthController;

import com.example.mobilemanager.Entity.User;
import com.example.mobilemanager.Model.DTO.UserDTO;
import com.example.mobilemanager.Request.UserRequest.CreateUserRequest;
import com.example.mobilemanager.Request.UserRequest.FindUserRequest;
import com.example.mobilemanager.Request.UserRequest.GetListUserRequest;
import com.example.mobilemanager.Request.UserRequest.UpdateUserRequest;
import com.example.mobilemanager.Response.ResponseError.BaseResponse;
import com.example.mobilemanager.Response.ResponseSuccess.ItemResponse;
import com.example.mobilemanager.Response.ResponseSuccess.ListItemResponse;
import com.example.mobilemanager.Service.AuthService.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController extends BaseController {
    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('CREATE_USER','ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody CreateUserRequest request) {
        UserDTO response = userService.createUser(request);
        return buildItemResponse(response);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('UPDATE_USER', 'ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateUserRequest request) {
        UserDTO response = userService.updateUser(request);
        return buildItemResponse(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_ROLE', 'ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        BaseResponse response = new BaseResponse();
        userService.deleteUser(id);
        response.setSuccess(true);
        return buildItemResponse(response);
    }

    @PostMapping("/list")
    public ResponseEntity<?> getList(@Valid @RequestBody GetListUserRequest request) {
        Page<User> page = userService.getAllUsers(request);
        List<UserDTO> response = page.getContent().stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
        return buildListItemResponse(response, page.getTotalElements());
    }
    @PostMapping("/find")
    public ResponseEntity<?> getUser(@RequestBody FindUserRequest request){
        List<UserDTO> dtoList = new ArrayList<>();
        dtoList.add(userService.findUserById(request.getId()));
//        UserDTO userDTO = userService.findUserById(request.getId());
        ListItemResponse baseResponse = new ListItemResponse();
        baseResponse.setSuccess(true);
        baseResponse.setResult(1, dtoList);
        return ResponseEntity.ok(baseResponse);
    }
}
