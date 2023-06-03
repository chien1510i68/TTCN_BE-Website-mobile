package com.example.mobilemanager.Response.ResponseSuccess;

import com.example.mobilemanager.Response.ResponseError.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class ItemResponse<T>  {
    private boolean success = true;
    private T data;

    public ItemResponse(T data) {

        this.data = data;
    }
}
