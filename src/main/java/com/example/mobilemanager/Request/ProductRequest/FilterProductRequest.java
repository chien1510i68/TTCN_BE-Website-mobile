package com.example.mobilemanager.Request.ProductRequest;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Data
@Component
public class FilterProductRequest {
    private Float priceFrom;
    private  Float priceTo;
    private String manufacturer;
    private String color;
    private Integer memory;
    @NotNull(message = "Page is not null")
    private Integer page ;
    @NotNull(message = "Page size is not null")
    private Integer size ;
    private Long productId ;
    private int keySort;
    private String productName;

}
