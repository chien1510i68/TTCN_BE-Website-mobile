package com.example.mobilemanager.Controller;

import com.example.mobilemanager.Constant.ErrorCodeDefs;
import com.example.mobilemanager.Entity.Product;
import com.example.mobilemanager.Model.DTO.ProductDTO;
import com.example.mobilemanager.Request.ProductRequest.*;
import com.example.mobilemanager.Response.ResponseError.BaseResponse;
import com.example.mobilemanager.Response.ResponseSuccess.ListItemResponse;
import com.example.mobilemanager.Response.ResponseSuccess.ProductResponse;
import com.example.mobilemanager.Response.ResponseSuccess.SoldResponse;
import com.example.mobilemanager.Response.ResponseSuccess.StatisticalPhoneResponse;
import com.example.mobilemanager.Service.Product.ProductServiceIplm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("prod/")
@Transactional
public class ProductController {
    private ProductServiceIplm productService;

    private ModelMapper mapper;

    @Autowired
    public ProductController(ProductServiceIplm productService, ModelMapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    //    Thêm sản phẩm :
    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('ADMIN' ,'MOD')")

    public ResponseEntity<?> addProd(@Valid @RequestBody ProductRequest productRequest) {
        ProductDTO productDTO = productService.addMobile(productRequest);
        ProductResponse productResponse = ProductResponse.builder()
                .success(true)
                .data(productDTO).build();
        return ResponseEntity.ok(productResponse);
    }

    //    Tìm kiếm sản phẩm theo ID :
    @GetMapping("client/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN' , 'USER' , 'MOD')")
//
    public ResponseEntity<?> getByID(@PathVariable  Long id) throws Exception {

        try {
            ProductDTO productDTO = productService.getByID(id);
            ProductResponse productResponse = ProductResponse.builder()
                    .success(true)
                    .data(productDTO).build();
            return ResponseEntity.ok(productResponse);
//                return ResponseEntity.ok(prodDTO);
        } catch (Exception ex) {
            BaseResponse response = new BaseResponse();
            response.setFailed(ErrorCodeDefs.SERVER_ERROR, ex.getMessage());
            return ResponseEntity.ok(response);
        }


    }

    @PostMapping("")
//    @PreAuthorize("hasAnyAuthority('ADMIN' ,'MOD' , 'USER')")
    public ResponseEntity<?> getAllProd(@RequestBody PaginationRequest rq) {
        List<ProductDTO> dtoList = productService.getAllMobile();
        ListItemResponse listItemResponse = new ListItemResponse();
        listItemResponse.setSuccess(true);
        listItemResponse.setResult(dtoList.size(), dtoList);
        return ResponseEntity.ok(listItemResponse);
    }

    @PutMapping("update/")
    @PreAuthorize("hasAnyAuthority('ADMIN' ,'MOD')")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductRequest productRequest) {
        try {
            ProductDTO productDTO = productService.updateMobile(productRequest);
            ProductResponse productResponse = ProductResponse.builder().success(true).data(productDTO).build();
            return ResponseEntity.ok(productResponse);
        } catch (Exception ex) {
            BaseResponse baseResponse = new BaseResponse();
            BaseResponse response = new BaseResponse();
            response.setFailed(ErrorCodeDefs.SERVER_ERROR, ex.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("del/")
    @PreAuthorize("hasAnyAuthority('ADMIN' ,'MOD')")

    public ResponseEntity<?> deleteByID(@Valid @RequestBody DeleteRequest req) throws Exception {
        try {
            ProductDTO productDTO = productService.deleteByID(req.getProductId());
            ProductResponse productResponse = ProductResponse.builder().success(true).data(productDTO).build();
            return ResponseEntity.ok(productResponse);
        } catch (Exception ex) {
            BaseResponse baseResponse = new BaseResponse();
            BaseResponse response = new BaseResponse();
            response.setFailed(ErrorCodeDefs.SERVER_ERROR, ex.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("del/all")
    @PreAuthorize("hasAnyAuthority('ADMIN' ,'MOD')")

    public ResponseEntity<?> deleteAll(@RequestBody List<Long> ids) {
        List<ProductDTO> dtoList = productService.deleteList(ids);
        ListItemResponse listItemResponse = new ListItemResponse();
        listItemResponse.setSuccess(true);
        listItemResponse.setResult(dtoList.size(), dtoList);
        return ResponseEntity.ok(listItemResponse);
    }


    @PostMapping("all")
    @PreAuthorize("hasAnyAuthority('ADMIN' , 'USER' , 'MOD')")

    public ResponseEntity<?> getAllProduct(@RequestBody PaginationRequest request) {
        Page<ProductDTO> prodDTOS = productService.getAllProduct(request.getPageNumber(), request.getPageSize());
        ListItemResponse listItemResponse = new ListItemResponse();
        listItemResponse.setSuccess(true);
        listItemResponse.setResult(prodDTOS.getTotalElements(), prodDTOS.getContent());
        return ResponseEntity.ok(listItemResponse);
    }

    @PostMapping("client/filter/")
//    @PreAuthorize("hasAnyAuthority('ADMIN' ,'MOD' , 'USER')")

    public ResponseEntity<?> getProductByCondition(@Valid @RequestBody FilterProductRequest request) {
        Page<Product> products = productService.filterListProduct(request);


        List<ProductDTO> dtoList = products.getContent().stream()
                .map(i -> mapper.map(i, ProductDTO.class))
                .collect(Collectors.toList());
//        return ResponseEntity.ok(products);
        if (products.getTotalElements() == 0) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setSuccess(false);
            baseResponse.setFailed(ErrorCodeDefs.SERVER_ERROR, "No products were found");
            return ResponseEntity.ok(baseResponse);
        } else {
            ListItemResponse response = new ListItemResponse();
            response.setSuccess(true);
            response.setResult(products.getTotalElements(), dtoList);
            return ResponseEntity.ok(response);

        }

    }


    @PostMapping("sort/")
    @PreAuthorize("hasAnyAuthority('ADMIN' ,'MOD' , 'USER')")
    public ResponseEntity<?> sortProduct(@Valid @RequestBody SortRequest request){
        Page<ProductDTO> productDTOS = productService.sortProduct(request);
        ListItemResponse response = new ListItemResponse();
        response.setResult(productDTOS.getSize(), productDTOS.getContent());
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }

    @PostMapping("findproductIds")
    @PreAuthorize("hasAnyAuthority('ADMIN' ,'USER')")
    public ResponseEntity<?> getProductByListProductId (@RequestBody List<Long> ids){
        List<ProductDTO> dtoList = productService.getProductByListId(ids);
        ListItemResponse response = new ListItemResponse();
        response.setResult(dtoList.size() , dtoList);
        return  ResponseEntity.ok(response);
    }



    @PostMapping("statisticalphonebymanufacturer")
    @PreAuthorize("hasAnyAuthority('ADMIN' ,'USER')")
    public ResponseEntity<?> statisticalManufacturer (@RequestBody StatisticalPhoneReq req){
//        List<ProductDTO> dtoList = productService.statisticalPhoneByManufacturer(req);


        List<StatisticalPhoneResponse> dtoList = productService.statisticalPhoneByManufacturer(req);
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("inventoryLowQuantity")
    @PreAuthorize("hasAnyAuthority('ADMIN' ,'USER')")
    public ResponseEntity<?> inventoryLowQuantity (@RequestBody StatisticalPhoneReq req){
        List<StatisticalPhoneResponse> phoneResponses = productService.InventoryLowQuantity(req);
        return ResponseEntity.ok(phoneResponses);
    }

    @GetMapping("statisticalSold")
    @PreAuthorize("hasAnyAuthority('ADMIN' ,'USER')")
    public ResponseEntity<?> statisticalSoldByManufacturer(){
      List<SoldResponse> stringLongMap = productService.statisticalSold();
        return ResponseEntity.ok(stringLongMap);
    }



}
