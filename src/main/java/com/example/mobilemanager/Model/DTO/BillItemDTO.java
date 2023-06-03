package com.example.mobilemanager.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class BillItemDTO {
//    private Long id ;

    private int quantity;

    private float totalPrice;

    private ProductDTO product;
}
