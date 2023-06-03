package com.example.mobilemanager.Request.RoleRequest;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
@Data
public class UpdateRoleRequest {

    @NotNull(message = "Id not null")
    private Integer id ;
    private String name ;
    private List<String> permissionIds;
}
