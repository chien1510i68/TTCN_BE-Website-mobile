package com.example.mobilemanager.Request.OrderRequest;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UpdateOrderRequest {

    @NotNull(message = "Address is not null")
    @Pattern(regexp="\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}", message="Invalid email format")
    private String addr ;

    @NotBlank(message = "phonenumber is not null")
    @Pattern(regexp="\\d{10}", message="Phone number must be 10 digits")
    private String phoneNumber;

    private int statusId;

    private Integer quantity;

    private Long orderId;
}
