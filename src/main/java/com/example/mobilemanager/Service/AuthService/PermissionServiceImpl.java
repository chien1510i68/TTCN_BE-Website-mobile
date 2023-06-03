package com.example.mobilemanager.Service.AuthService;

import com.example.mobilemanager.Entity.Permission;
import com.example.mobilemanager.Repository.Permission.CustomPermissionRepository;
import com.example.mobilemanager.Repository.Permission.PermissionRepository;
import com.example.mobilemanager.Request.PermissionRequest.GetListPermissionRequest;
import com.example.mobilemanager.Request.PermissionRequest.PermissionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Page<Permission> getAllPermission(GetListPermissionRequest request) {
        Specification<Permission> specification = CustomPermissionRepository.buildFilterSpecification(request.getKeyword());
        return permissionRepository.findAll(specification, PageRequest.of(request.getStart(), request.getLimit()));
    }

    @Override
    public Permission createPermission(PermissionRequest request) {
         Permission p =  Permission.builder().id(request.getId())
                .name(request.getName()).build();
        permissionRepository.save(p);
            return p ;
    }



    @Override
    public Permission deletePermission(PermissionRequest request) throws Exception {
        Optional<Permission> permission = permissionRepository.findById(request.getId());
        if(permission.isPresent()){
            permissionRepository.deleteById(request.getId());
            return permission.get();
        }else {
            throw new Exception("Khong tim thay id permission");
        }
    }
}
