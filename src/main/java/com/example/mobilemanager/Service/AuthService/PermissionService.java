package com.example.mobilemanager.Service.AuthService;

import com.example.mobilemanager.Entity.Permission;
import com.example.mobilemanager.Request.PermissionRequest.GetListPermissionRequest;
import com.example.mobilemanager.Request.PermissionRequest.PermissionRequest;
import org.springframework.data.domain.Page;

public interface PermissionService {
    Page<Permission> getAllPermission(GetListPermissionRequest request);

    Permission createPermission(PermissionRequest request);

    Permission deletePermission(PermissionRequest request) throws Exception;
}
