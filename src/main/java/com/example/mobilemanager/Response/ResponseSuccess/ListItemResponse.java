package com.example.mobilemanager.Response.ResponseSuccess;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class ListItemResponse<T> {
    private boolean success = true;
    private ListProd<T> data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
    public static class ListProd<T>  {
        private long total;
        private List<T> listItem;
    }
    public void setResult(long total, List<T> items) {
        data = new ListProd<>();
        data.setListItem(items);
        data.setTotal(total);
    }
}
