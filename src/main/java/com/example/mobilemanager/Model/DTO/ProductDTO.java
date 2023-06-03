package com.example.mobilemanager.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductDTO {
    private long productId ;
    private float price ;
    private String image;
    private  String productName;
    private long quantityInStore ;
    private int memoryStick;
    private int camera;
    private int memory;
    private String operatingSystem;
    private String battery;
    private String manufacturer ;
    private String size;
    private String color;
    private String warrantyPeriod;
    private long promotionID;
    private Float salePrice ;
    private Integer percenPromotion;
}
