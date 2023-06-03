package com.example.mobilemanager.Repository.Supplier;

import com.example.mobilemanager.Entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier,Long> , JpaSpecificationExecutor<Supplier> {
    List<Supplier> findAllByOrderByImportDateDesc();
    Optional<Supplier> findBySupplierPhoneNumber(String phone);
}
