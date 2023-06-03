package com.example.mobilemanager.Request.OrderRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {
        private  Long product_id;
        @NotNull(message = "orderCreationDate is not null")
        private String orderCreationDate;


}
