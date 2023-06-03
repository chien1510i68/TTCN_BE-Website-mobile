package com.example.mobilemanager.Model.Mapper;

import com.example.mobilemanager.Entity.Product;
import com.example.mobilemanager.Model.DTO.ProductDTO;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Builder
public class MapToDTO {
    public ProductDTO convertProdToDTO(Product pe){
        ProductDTO productDTO = ProductDTO.builder()
//                .pdtoid(pe.getProductId())
                .price(pe.getPrice())
                .image(pe.getImage())
                .productName(pe.getProductName())
                .memoryStick(pe.getMemoryStick())
                .camera(pe.getCamera())
                .memory(pe.getMemory())
                .operatingSystem(pe.getOperatingSystem())
                .battery(pe.getBattery())
                .size(pe.getSize())
                .color(pe.getColor())
                .warrantyPeriod(pe.getWarrantyPeriod())
//                .promotionID(pe.getPromotionID())
                .build();
        return productDTO;

    }
}
