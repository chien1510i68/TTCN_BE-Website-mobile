//package com.example.mobilemanager.Controller;
//
//import com.example.mobilemanager.Constant.ErrorCodeDefs;
//import com.example.mobilemanager.Model.DTO.BillDTO;
//import com.example.mobilemanager.Request.BillRequest.BillRequest;
//import com.example.mobilemanager.Response.ResponseError.BaseResponse;
//import com.example.mobilemanager.Response.ResponseSuccess.ItemResponse;
//import com.example.mobilemanager.Service.Bill.BillService;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("bill/")
//public class BillController {
//    private final BillService billService;
//    private final ModelMapper mapper;
//
//    @Autowired
//    public BillController(BillService billService, ModelMapper mapper) {
//        this.billService = billService;
//        this.mapper = mapper;
//    }
//
//
//
//
//    @PostMapping("create")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
//
//    public ResponseEntity<?> createBill( @RequestBody BillRequest request) throws Exception {
//        try {
//            BillDTO bill = billService.createBillDTO(request);
//
//            ItemResponse response = new ItemResponse();
//            response.setSuccess(true);
////            response.setData(bill);
//            response.setData(bill);
//            return ResponseEntity.ok(response);
//        }catch (Exception ex){
//            BaseResponse response = new BaseResponse();
//            response.setSuccess(false);
//            response.setFailed(ErrorCodeDefs.SERVER_ERROR , ex.getMessage());
//            return ResponseEntity.ok(response);
//        }
//    }
//}
