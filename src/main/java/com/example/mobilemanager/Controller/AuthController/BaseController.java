package com.example.mobilemanager.Controller.AuthController;

import com.example.mobilemanager.Response.ResponseSuccess.ItemResponse;
import com.example.mobilemanager.Response.ResponseSuccess.ListItemResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class BaseController {

    protected  <T> ResponseEntity<?> buildItemResponse(T data) {
        ItemResponse<T> response = new ItemResponse<>();
        response.setData(data);
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }

    protected <T> ResponseEntity<?> buildListItemResponse(List<T> data, long total) {
        ListItemResponse<T> response = new ListItemResponse<>();
        response.setResult(total , data);
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }
}
