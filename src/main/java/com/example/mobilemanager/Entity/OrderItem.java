package com.example.mobilemanager.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "order_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;


    @Column(name = "quantity_product_item")
    private int quantityProductItem;

    @Column(name = "total_value_order_item")
    private float totalValueOrderItem;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_product_id")
    private Product orderProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_promotion_id")
    private Promotion promotion;

}
