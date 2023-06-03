package com.example.mobilemanager.Request.ProductRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private long productId ;
    @Min(value = 1 ,message = "Price must be greater than 1")
    private float price;

    @NotBlank(message = "image is not empty")
    private String image;

    @NotBlank(message = "product name is not empty")
    private String productName;

    @NotNull
    @Min(value = 1 ,message = "memoryStick must be greater than 1")

    private int memoryStick;

    @NotNull
    @Min(value = 1 ,message = "number camera must be greater than 1")
    private int camera;

    @NotNull
    @Min(value = 1 , message = "memory must be greater than 1")
    private int memory;

    @NotBlank(message = "operatingSystem is not null ")
    private String operatingSystem;

    @NotBlank(message = "battery is not null ")
    private String battery;

    @NotBlank(message = "manufacturer is not null ")
    private String manufacturer ;


    @NotBlank(message = "size is not null ")
    private String size;

    @NotBlank(message = "color is not null ")
    private String color;

    @NotBlank(message = "warrantyPeriod is not null ")
    private String warrantyPeriod;

//    @NotNull
    @NotNull(message = "quantity is not null")
    private long quantityInStore;

    private Long promotionID;
}
