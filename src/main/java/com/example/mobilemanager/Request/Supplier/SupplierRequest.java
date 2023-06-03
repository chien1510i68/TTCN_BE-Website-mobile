package com.example.mobilemanager.Request.Supplier;

import com.example.mobilemanager.validation.BirthDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Component
@Data
@NoArgsConstructor
public class SupplierRequest {
    @NotNull(message =  "supplierName is not null")
    private String supplierName;

    @NotNull(message =  "supplierAddr is not null")
    private String supplierAddr ;

    @NotNull(message =  "supplierNumber is not null")
    @Pattern(regexp="\\d{10}", message="Phone number must be 10 digits")
    private String supplierPhoneNumber;

    @NotNull(message =  "supplierEmail is not null")
    @Pattern(regexp="\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}", message="Invalid email format")
    private String supplierEmail ;

    @NotNull(message =  "supplierWebsite is not null")
    private String supplierWebsite;

    @NotNull(message =  "supplierAccountNumber is not null")
    private Long supplierAccountNumber ;

    @NotNull(message =  "bank is not null")
    private String bank ;

    @NotNull(message =  "bankAccountName is not null")
    private String bankAccountName;


    private Long supplierId;
//    @NotNull(message = "importDate is not null ")
//    @BirthDate(message = "Date format is dd/MM/yyy")
//    private String importDate;
}
