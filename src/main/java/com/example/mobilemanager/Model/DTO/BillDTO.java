package com.example.mobilemanager.Model.DTO;

import com.example.mobilemanager.Entity.BillItem;
import com.example.mobilemanager.Entity.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillDTO {
    private int totalProductQuantity ;

    private float totalValueProducts;

    private int statusBill;

    private Date billCreateDate ;

    private Supplier supplier;

    List<BillItemDTO> billItemList = new ArrayList<>();
}
