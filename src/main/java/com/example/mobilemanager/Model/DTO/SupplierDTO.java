package com.example.mobilemanager.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierDTO {
    private String supplierName;

    private String supplierAddr;

    private Long supplierPhoneNumber;

    private String supplierEmail;

    private String supplierWebsite;

    private Long supplierAccountNumber;

    private String bank;

    private String bankAccountName;

    private Long supplierId;
}
