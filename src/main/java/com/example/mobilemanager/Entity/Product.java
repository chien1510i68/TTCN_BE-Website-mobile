package com.example.mobilemanager.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "productId")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId ;

    @Column
    private float price ;

    @Column
    private String image;

    @Column(name = "product_name")
    private  String productName;

    @Column(name = "memory_stick")
    private int memoryStick;

    @Column
    private int camera;

    @Column
    private Integer memory;



    @Column(name = "operating_system")
    private String operatingSystem;

    @Column
    private String battery;

    @Column
    private String manufacturer ;

    @Column
    private String size;

    @Column
    private String color;

    @Column
    private long quantityInStore = 0;

    @Column(name = "warranty_period")
    private String warrantyPeriod;

//    @Column(name = "promotion_id")
//    private long promotionID;

    private int percenPromotion ;


    @Column
    private long sold = 0 ;


    @Column(name ="sale_price")
    private Float salePrice ;

//   @OneToMany(mappedBy = "orderProduct" , cascade = CascadeType.ALL)
//    List<OrderItem> listOrderItem = new ArrayList<>();
//
//
//   @OneToMany(mappedBy = "orderProduct" , cascade = CascadeType.ALL)
    @OneToMany
    @JoinTable(name = "order_products",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "fk_product_id")}
    )
   private Set<OrderItem> orderItems;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_store")
    private Store store;


    // cmt 
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;




}
