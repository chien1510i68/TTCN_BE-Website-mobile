package com.example.mobilemanager.Controller;

import com.example.mobilemanager.Constant.DateTimeConstant;
import com.example.mobilemanager.Constant.ErrorCodeDefs;
import com.example.mobilemanager.Entity.Supplier;
import com.example.mobilemanager.Model.DTO.SupplierDTO;
import com.example.mobilemanager.Request.ProductRequest.PaginationRequest;
import com.example.mobilemanager.Request.Supplier.FilterSupplierRequest;
import com.example.mobilemanager.Request.Supplier.SupplierRequest;
import com.example.mobilemanager.Response.ResponseError.BaseResponse;
import com.example.mobilemanager.Response.ResponseSuccess.ItemResponse;
import com.example.mobilemanager.Response.ResponseSuccess.ListItemResponse;
import com.example.mobilemanager.Service.Supplier.SupplierService;
import com.example.mobilemanager.utils.MyUntil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @Transactional
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN' ,'MOD')")

    public ResponseEntity<?> createSupplier(@Valid @RequestBody SupplierRequest request) throws ParseException {
        try {
            SupplierDTO supplierDTO = supplierService.createSupplier(request);
            ItemResponse response = ItemResponse.builder()
                    .success(true)
                    .data(supplierDTO)
                    .build();

            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setFailed(ErrorCodeDefs.SERVER_ERROR, ex.getMessage());
            return ResponseEntity.ok(baseResponse);
        }

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN' ,'MOD')")
    public ResponseEntity<?> getSupplierById(@PathVariable(required = false) Long id) throws Exception {
        try {
            SupplierDTO supplierDTO = supplierService.getSupplierById(id);
            ItemResponse response = ItemResponse.builder()
                    .success(true)
                    .data(supplierDTO)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            BaseResponse response = new BaseResponse();
            response.setFailed(ErrorCodeDefs.SERVER_ERROR, ex.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN' ,'MOD')")

    public ResponseEntity<?> deleteSupplier(@PathVariable Long id) throws Exception {
        try {
            SupplierDTO supplierDTO = supplierService.deleteSupplier(id);
            ItemResponse response = ItemResponse.builder()
                    .success(true)
                    .data(supplierDTO)
                    .build();
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            BaseResponse response = new BaseResponse();
            response.setFailed(ErrorCodeDefs.SERVER_ERROR, ex.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN' ,'MOD')")

    public ResponseEntity<?> updateSupplier(@Valid @RequestBody SupplierRequest request) throws Exception {
        try {
            SupplierDTO supplierDTO = supplierService.updateSupplier(request, request.getSupplierId());
            ItemResponse response = ItemResponse.builder()
                    .success(true)
                    .data(supplierDTO).build();
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            BaseResponse response = new BaseResponse();
            response.setFailed(ErrorCodeDefs.SERVER_ERROR, ex.getMessage());
            return ResponseEntity.ok(response);

        }

    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN' ,'MOD')")
    public ResponseEntity<?> getAllSupplier(@RequestBody PaginationRequest request){
        Page<SupplierDTO> supplierDTOS = supplierService.getAllSupplier(request.getPageNumber(), request.getPageSize());
        ListItemResponse listItemResponse = new ListItemResponse();
        listItemResponse.setSuccess(true);
        listItemResponse.setResult(supplierDTOS.getSize() , supplierDTOS.getContent());
        return ResponseEntity.ok(listItemResponse);
    }

    @PostMapping("/filter")
    @PreAuthorize("hasAnyAuthority('ADMIN' ,'MOD')")

    public ResponseEntity<?> getAllBycondition(@Valid @RequestBody FilterSupplierRequest request) throws ParseException {
        ModelMapper mapper = new ModelMapper();
        Page<Supplier> supplierPage = supplierService.getSupplierByCondition(
                request.getPage(), request.getSize(),
                StringUtils.hasText(request.getDateFrom()) ?MyUntil.convertDateFromString(request.getDateFrom(), DateTimeConstant.DATE_FORMAT):null ,
                StringUtils.hasText(request.getDateTo()) ?MyUntil.convertDateFromString(request.getDateTo(), DateTimeConstant.DATE_FORMAT):null
                );
        if(supplierPage.getTotalElements() == 0){
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setSuccess(false);
            baseResponse.setFailed(ErrorCodeDefs.SERVER_ERROR , "No supplier found !");
            return ResponseEntity.ok(baseResponse);

        }

        List<SupplierDTO> supplierList = supplierPage.getContent().stream()
                .map(i->mapper.map(i , SupplierDTO.class))
                .collect(Collectors.toList());
        ListItemResponse response = new ListItemResponse();
        response.setSuccess(true);
        response.setResult(supplierList.size(), supplierList);
        return ResponseEntity.ok(response);


    }
    @GetMapping("/sort/{page}/{size}")
    @PreAuthorize("hasAnyAuthority('ADMIN' ,'MOD' )")
    public ResponseEntity<?> sortSupplier(@PathVariable int page , @PathVariable int size){
//        Page<SupplierDTO> supplierDTOS = supplierService.getSupplierImportDateDESC(page, size);
        Page<SupplierDTO> supplierDTOS = supplierService.suppliersSort(page, size);

        ListItemResponse response = new ListItemResponse();
        response.setSuccess(true);
        response.setResult(supplierDTOS.getTotalElements(), supplierDTOS.getContent());
        return ResponseEntity.ok(response);
    }





}
