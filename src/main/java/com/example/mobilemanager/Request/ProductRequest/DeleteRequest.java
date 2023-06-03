package com.example.mobilemanager.Request.ProductRequest;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Data
@Component
public class DeleteRequest {
    @NotNull(message = "id không được để trống ")
    private Long productId;
}
