package com.example.mobilemanager.Controller.AuthController;

import com.example.mobilemanager.Entity.Permission;
import com.example.mobilemanager.Model.DTO.PermissionDTO;
import com.example.mobilemanager.Request.PermissionRequest.GetListPermissionRequest;
import com.example.mobilemanager.Request.PermissionRequest.PermissionRequest;
import com.example.mobilemanager.Response.ResponseSuccess.ItemResponse;
import com.example.mobilemanager.Service.AuthService.PermissionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController extends BaseController {
    private final PermissionService permissionService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }


    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createPermission(@RequestBody PermissionRequest permissionRequest){
        Permission permission = permissionService.createPermission(permissionRequest);
        ItemResponse response = new ItemResponse();
        response.setSuccess(true);
        response.setData(permission);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('ADMIN')")

    public ResponseEntity<?> getRoles(@Valid @RequestBody GetListPermissionRequest request) {
        Page<Permission> page = permissionService.getAllPermission(request);
        List<PermissionDTO> response = page.getContent().stream()
                .map(permission -> modelMapper.map(permission, PermissionDTO.class))
                .collect(Collectors.toList());
        return buildListItemResponse(response, page.getTotalElements());
    }

}
