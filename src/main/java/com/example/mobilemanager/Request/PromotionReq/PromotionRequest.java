package com.example.mobilemanager.Request.PromotionReq;

import com.example.mobilemanager.validation.BirthDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PromotionRequest {
    @NotNull(message = "promotionPercentage is not null")
    private int promotionPercentage;
    @NotNull(message = "minimumPurchaseAmount is not null")
    private float minimumPurchaseAmount;

    @NotBlank(message = "startDate is not null")
    @BirthDate
    private String startDate;

    @NotBlank(message = "endtDate is not null")
    @BirthDate
    private String endDate;


    private Long promotionID ;

    private List<Long> ids ;


//    private PromotionProduct promotion_productEntity;
}