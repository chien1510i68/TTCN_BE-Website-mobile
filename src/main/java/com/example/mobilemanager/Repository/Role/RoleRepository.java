package com.example.mobilemanager.Repository.Role;

import com.example.mobilemanager.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role , Integer>,JpaSpecificationExecutor<Role>  {
}
