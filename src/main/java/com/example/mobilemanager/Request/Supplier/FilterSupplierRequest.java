package com.example.mobilemanager.Request.Supplier;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Data
@Component
public class FilterSupplierRequest {
    private String dateFrom;
    private String dateTo;
    @NotNull(message = "Page is not null")
    private Integer page ;
    @NotNull(message = "Size is not null")
    private Integer size ;
}
