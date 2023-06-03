package com.example.mobilemanager.Request.ProductRequest;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class FindProductByID {
    private long id ;
}
