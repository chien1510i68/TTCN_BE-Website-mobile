package com.example.mobilemanager.Service.Supplier;

import com.example.mobilemanager.Entity.Supplier;
import com.example.mobilemanager.Model.DTO.SupplierDTO;
import com.example.mobilemanager.Request.Supplier.SupplierRequest;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.Date;

public interface SupplierService {
    SupplierDTO createSupplier(SupplierRequest sp) throws ParseException;
    SupplierDTO getSupplierById(Long id) throws Exception;
    SupplierDTO deleteSupplier(Long id) throws Exception;
    SupplierDTO updateSupplier(SupplierRequest sp , Long id) throws Exception;

    Page<SupplierDTO> getAllSupplier(int page , int size);
    Page<Supplier> getSupplierByCondition(Integer page , Integer size , Date dateFrom , Date dateTo);

    Page<SupplierDTO> getSupplierImportDateDESC(int page , int size);
    Page<SupplierDTO> suppliersSort(int page , int size);
}
