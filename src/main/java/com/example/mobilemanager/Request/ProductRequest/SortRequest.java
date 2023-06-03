package com.example.mobilemanager.Request.ProductRequest;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class SortRequest {
    private int pageNumber;
    private int pageSize;
    private int key;
}
