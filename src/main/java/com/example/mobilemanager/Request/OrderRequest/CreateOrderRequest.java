package com.example.mobilemanager.Request.OrderRequest;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
@Component
@Data
public class CreateOrderRequest {



//    @NotNull(message = "Email is not null")
    @Pattern(regexp="\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}", message="Email không hợp lệ")
    private String email ;

    @NotNull(message = "addr is not null")
    private String addr ;

    @NotBlank(message = "phonenumber is not null")
    @Pattern(regexp="\\d{10}", message="Số điện thoại phải là 10 số ")
    private String phoneNumber;

    private int statusId;

//    private List<Long> listIdProduct;

    private List<OrderItemReq>  itemReqList;

    private Integer quantity;

    private Long orderId;

    private String name;



}
