package com.example.mobilemanager.Controller;

import com.example.mobilemanager.Constant.DateTimeConstant;
import com.example.mobilemanager.Constant.ErrorCodeDefs;
import com.example.mobilemanager.Entity.Order;
import com.example.mobilemanager.Model.DTO.OrderDTO;
import com.example.mobilemanager.Model.Revenue;
import com.example.mobilemanager.Request.OrderRequest.*;
import com.example.mobilemanager.Request.ProductRequest.PaginationRequest;
import com.example.mobilemanager.Response.ResponseError.BaseResponse;
import com.example.mobilemanager.Response.ResponseSuccess.ItemResponse;
import com.example.mobilemanager.Response.ResponseSuccess.ListItemResponse;
import com.example.mobilemanager.Service.Order.OrderServiceImpl;
import com.example.mobilemanager.utils.MyUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("order/")
public class OrderController {
    private final OrderServiceImpl orderServiceImpl;

    @Autowired
    public OrderController(OrderServiceImpl orderServiceImpl) {
        this.orderServiceImpl = orderServiceImpl;
    }


    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN','MOD')")
    public ResponseEntity<?> getAllOrder(@RequestBody PaginationRequest request) {
        Page<OrderDTO> orderDTOS = orderServiceImpl.getAllOrder(request);
        ListItemResponse response = new ListItemResponse();
        response.setSuccess(true);
        response.setResult(orderDTOS.getTotalElements(), orderDTOS.getContent());
        return ResponseEntity.ok(response);
    }

    @PutMapping("update")
    @PreAuthorize("hasAnyAuthority('MOD' ,'ADMIN')")
    public ResponseEntity<?> updateOrder(@RequestBody UpdateOrderRequest request) {
        OrderDTO orderDTO = orderServiceImpl.updateOrder(request);
        BaseResponse response = new BaseResponse();
        response.setSuccess(true);
        response.setError(null);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MOD')")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long id) {
        BaseResponse response = new BaseResponse();
        response.setSuccess(orderServiceImpl.deleteOrder(id));
        response.setError(null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("getId")
    @PreAuthorize("hasAnyAuthority('ADMIN','MOD')")
    public ResponseEntity<?> getOrderById(@RequestBody Long id) {
        OrderDTO orderDTO = orderServiceImpl.findOrderById(id);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        orderDTOS.add(orderDTO);
        ListItemResponse response = new ListItemResponse();
        response.setSuccess(true);
        response.setResult(1, orderDTOS);
        return ResponseEntity.ok(response);
    }


    @PostMapping("create/")
    @PreAuthorize("hasAnyAuthority( 'USER' )")

    public ResponseEntity<?> test(@Valid @RequestBody CreateOrderRequest request) throws Exception {
        try {
            OrderDTO order = orderServiceImpl.createOrder(request);
            ItemResponse response = new ItemResponse();
            response.setSuccess(true);
            response.setData(order);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            BaseResponse response = new BaseResponse();
            response.setSuccess(false);
            response.setFailed(ErrorCodeDefs.SERVER_ERROR, ex.getMessage());
            return ResponseEntity.ok(response);
        }
    }


    @GetMapping("revenue/")
    @PreAuthorize("hasAnyAuthority('ADMIN','MOD')")
    public ResponseEntity<?> getRevenue() {

        List<Revenue> revenueList = orderServiceImpl.getRevenue();
        ListItemResponse response = new ListItemResponse();
        response.setSuccess(true);
        response.setResult(revenueList.size(), revenueList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("revenues/")
    @PreAuthorize("hasAnyAuthority('ADMIN','MOD')")
    public ResponseEntity<?> getRevenueByCondition(@RequestBody RevenueRequest request) throws ParseException {
        try {
            Date start = MyUntil.convertDateFromString(request.getStart(), DateTimeConstant.DATE_FORMAT);
            Date end = MyUntil.convertDateFromString(request.getEnd(), DateTimeConstant.DATE_FORMAT);
            List<Revenue> revenueList = orderServiceImpl.getRevenueByCondition(start, end);
            return ResponseEntity.ok(revenueList);
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }

    }

    @PostMapping("revenuequantity/")
    @PreAuthorize("hasAnyAuthority('ADMIN','MOD')")
    public ResponseEntity<?> getRevenueByQuantity(@RequestBody RevenueRequest request) throws ParseException {
        try {
            Date start = MyUntil.convertDateFromString(request.getStart(), DateTimeConstant.DATE_FORMAT);
            Date end = MyUntil.convertDateFromString(request.getEnd(), DateTimeConstant.DATE_FORMAT);
            List<Revenue> revenueList = orderServiceImpl.revennueByQuantity(start, end);
            ListItemResponse response = new ListItemResponse();
            response.setSuccess(true);
            response.setResult(revenueList.size(), revenueList);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }

    }

    @PostMapping("revenuequantityupdate/")
    @PreAuthorize("hasAnyAuthority('ADMIN','MOD')")

    public ResponseEntity<?> getOrderRevenueByMonthAndYear(@RequestBody RevenueRequest request) throws ParseException {
        try {
            Date start = MyUntil.convertDateFromString(request.getStart(), DateTimeConstant.DATE_FORMAT);
            Date end = MyUntil.convertDateFromString(request.getEnd(), DateTimeConstant.DATE_FORMAT);
            List<Revenue> revenueList = orderServiceImpl.getOrderRevenueByMonthAndYear(start, end);
            ListItemResponse response = new ListItemResponse();
            response.setSuccess(true);
            response.setResult(revenueList.size(), revenueList);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }

    }


    @PostMapping("getByEmail")
    @PreAuthorize("hasAnyAuthority('ADMIN','MOD' ,'USER') ")
    public ResponseEntity getOrderByEmail(@RequestBody GetEmailReq req) {
        List<OrderDTO> orders = orderServiceImpl.getListOrderByEmail(req.getEmail());
        ListItemResponse response = new ListItemResponse();
        response.setSuccess(true);
        response.setResult(orders.size(), orders);
        return ResponseEntity.ok(response);
    }

    @PostMapping("ordersByPhone")
    public ResponseEntity<?> getListProductByPhoneNumber(@RequestBody GetPhoneReq req) {
        List<OrderDTO> dtoList = orderServiceImpl.getOrderByPhoneNumber(req.getPhoneNumber());
        ListItemResponse response = new ListItemResponse();
        response.setSuccess(true);
        response.setResult(dtoList.size(), dtoList);
        return ResponseEntity.ok(response);
    }


}
