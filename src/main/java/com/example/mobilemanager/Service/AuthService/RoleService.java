package com.example.mobilemanager.Service.AuthService;

import com.example.mobilemanager.Entity.Role;
import com.example.mobilemanager.Model.DTO.RoleDTO;
import com.example.mobilemanager.Request.RoleRequest.CreateRoleRequest;
import com.example.mobilemanager.Request.RoleRequest.GetListRoleRequest;
import com.example.mobilemanager.Request.RoleRequest.UpdateRoleRequest;
import org.springframework.data.domain.Page;

public interface RoleService {
    RoleDTO createRole(CreateRoleRequest createRoleRequest);

    RoleDTO updateRole(UpdateRoleRequest updateRoleRequest);

    Page<Role> getAllRoles(GetListRoleRequest request);

    void deleteRole(Integer id);
}
