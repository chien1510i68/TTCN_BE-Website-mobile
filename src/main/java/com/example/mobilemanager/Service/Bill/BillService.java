package com.example.mobilemanager.Service.Bill;

import com.example.mobilemanager.Entity.Bill;
import com.example.mobilemanager.Model.DTO.BillDTO;
import com.example.mobilemanager.Request.BillRequest.BillRequest;
import org.springframework.stereotype.Component;
@Component
public interface BillService {
    Bill createBill(BillRequest request) throws Exception;
    BillDTO createBillDTO(BillRequest request) throws Exception;
}
