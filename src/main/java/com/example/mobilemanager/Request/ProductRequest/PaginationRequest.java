package com.example.mobilemanager.Request.ProductRequest;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class PaginationRequest {
    private int pageNumber;
    private int pageSize;

    private int key ;
}
