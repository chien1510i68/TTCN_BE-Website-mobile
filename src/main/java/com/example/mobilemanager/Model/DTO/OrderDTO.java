package com.example.mobilemanager.Model.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrderDTO {
    private Integer quantity;

    private Long orderId;

    private String addr;

    private String phoneNumber;

    private Float totalValueOrder;

    private String email ;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date orderCreationDate;

    private String name ;

//    private String infor;

    private int statusId;


    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date startDateWarranty ;

//    private String listOrderItem;

    List<OrderItemDTO> orderItems = new ArrayList<>();

//    List<Long , Long>

}
