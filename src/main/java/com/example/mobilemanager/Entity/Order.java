package com.example.mobilemanager.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "order_product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "addr")
    private String addr;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "total_value_order")
    private Float totalValueOrder;

    @Column(name = "order_creation_date")
    private Date orderCreationDate;

    @Column(name = "status_id")
    private int statusId;

    @Column(name = "name")
    private String name;
    @Column(name = "start_date_warranty")
    private Date startDateWarranty;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<OrderItem> orderItems = new ArrayList<>();



}
