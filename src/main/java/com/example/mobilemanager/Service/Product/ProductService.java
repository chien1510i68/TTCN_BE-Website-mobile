package com.example.mobilemanager.Service.Product;

import com.example.mobilemanager.Entity.Product;
import com.example.mobilemanager.Model.DTO.ProductDTO;
import com.example.mobilemanager.Request.ProductRequest.*;
import com.example.mobilemanager.Response.ResponseSuccess.StatisticalPhoneResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    ProductDTO addMobile(ProductRequest productRequest);
    ProductDTO getByID(long id) throws Exception;
    List<ProductDTO> getAllMobile();
    ProductDTO updateMobile(ProductRequest productRequest) throws Exception;
    ProductDTO deleteByID(Long id) throws Exception;
    List<ProductDTO> deleteList(List<Long> ids);
    Page<ProductDTO> getAllProduct(int pageNumber , int pageSize);

    Page<Product> filterListProduct(FilterProductRequest request);

    Page<ProductDTO> sortProduct(SortRequest request);

    List<ProductDTO> getProductByListId(List<Long> ids);

    List<StatisticalPhoneResponse> statisticalPhoneByManufacturer(StatisticalPhoneReq req);

    List<StatisticalPhoneResponse> InventoryLowQuantity(StatisticalPhoneReq req);





}
