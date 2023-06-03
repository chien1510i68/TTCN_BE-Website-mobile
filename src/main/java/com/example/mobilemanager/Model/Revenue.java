package com.example.mobilemanager.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Revenue {
//    private int year;
    private int month;
//    private BigDecimal totalValue;
    private Float value;
}




