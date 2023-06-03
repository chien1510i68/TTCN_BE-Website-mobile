package com.example.mobilemanager.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "supplier")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "supplier_addr")
    private String supplierAddr ;

    @Column(name = "supplier_phoneNumber" )
    private String supplierPhoneNumber;

    @Column(name = "supplier_email")
    private String supplierEmail ;


    @Column(name = "supplier_website")
    private String supplierWebsite;


    @Column(name = "Bank_account_number")
    private Long supplierAccountNumber ;


    @Column(name = "bank")
    private String bank ;


    @Column(name = "bank_account_name")
    private String bankAccountName;


    @Column(name = "import_date")
    private Date importDate;

    @OneToOne(mappedBy = "supplier" )
    private Bill bill;


}
