package com.example.mobilemanager.Repository.Permission;

import com.example.mobilemanager.Entity.Permission;
import com.example.mobilemanager.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission , String> , JpaSpecificationExecutor<Permission> {
}
