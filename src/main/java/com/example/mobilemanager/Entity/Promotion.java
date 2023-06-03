package com.example.mobilemanager.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "promotion")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "promotionID")

@Builder
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id")
    private Long promotionID;

    @Column(name = "promotion_percentage")
    private float promotionPercentage;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "minimum_purchase_amount")
    private float minimumPurchaseAmount;


//    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL)
//    private List<OrderItem> orderItems;

    @Column(name = "list_product")
    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL)
    private List<Product> products;
}
