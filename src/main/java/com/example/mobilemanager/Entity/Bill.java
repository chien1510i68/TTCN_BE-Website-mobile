package com.example.mobilemanager.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Long billId;

    @Column(name = "total_product_quantity")
    private int totalProductQuantity ;

    @Column(name = "total_value_products")
    private float totalValueProducts;


    @Column(name = "status_bill")
    private int statusBill;

    @Column(name = "bill_creation_date")
    private Date billCreateDate ;

    @OneToOne
    @JoinColumn(name = "fk_supplier" , referencedColumnName = "supplier_id")
    private Supplier supplier;


    @OneToMany(mappedBy = "billItem" , fetch = FetchType.EAGER)
    List<BillItem> billItemList = new ArrayList<>();


//   @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "bill_item",
//            joinColumns = @JoinColumn(name = "bill_id" ),
//            inverseJoinColumns = @JoinColumn(name = "fk_product_id" , referencedColumnName = "product_id"))
//    private List<Product> listProduct = new ArrayList<>() ;


}
