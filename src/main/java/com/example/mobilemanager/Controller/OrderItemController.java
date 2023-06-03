package com.example.mobilemanager.Controller;

import com.example.mobilemanager.Constant.ErrorCodeDefs;
import com.example.mobilemanager.Model.DTO.OrderItemDTO;
import com.example.mobilemanager.Response.ResponseError.BaseResponse;
import com.example.mobilemanager.Response.ResponseSuccess.ItemResponse;
import com.example.mobilemanager.Service.OrderItem.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderitem")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("{id}")
    public ResponseEntity<?> getOrderItemByID(@PathVariable Long id) throws Exception {
        try {
            OrderItemDTO itemDTO = orderItemService.getOrderItem(id);
            ItemResponse response = ItemResponse.builder().success(true)
                    .data(itemDTO).build();
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setFailed(ErrorCodeDefs.SERVER_ERROR, ex.getMessage());
            return ResponseEntity.ok(baseResponse);
        }
    }
}
