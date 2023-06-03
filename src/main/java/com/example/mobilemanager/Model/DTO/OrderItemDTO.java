package com.example.mobilemanager.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private long productId;
    private int quantityProduct;





}
