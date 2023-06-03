package com.example.mobilemanager.Model.DTO;

import com.example.mobilemanager.Entity.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionDTO {
    private Long promotionID;

    private float promotionPercentage;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date startDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date endDate;

    private float minimumPurchaseAmount;

    private List<Product> products;

//    private PromotionProduct promotion_productEntity;
}
