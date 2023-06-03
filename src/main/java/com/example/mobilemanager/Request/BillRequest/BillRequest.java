package com.example.mobilemanager.Request.BillRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class BillRequest {
    private List<Long> listProductId;
    private String phoneNumberSupplier;
    private int statusId;
    List<BillReq> billReqs;

}
