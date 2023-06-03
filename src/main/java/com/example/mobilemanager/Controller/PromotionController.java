package com.example.mobilemanager.Controller;

import com.example.mobilemanager.Entity.Promotion;
import com.example.mobilemanager.Model.DTO.PromotionDTO;
import com.example.mobilemanager.Request.ProductRequest.PaginationRequest;
import com.example.mobilemanager.Request.PromotionReq.GetPromotionReq;
import com.example.mobilemanager.Request.PromotionReq.PromotionRequest;
import com.example.mobilemanager.Response.ResponseError.BaseResponse;
import com.example.mobilemanager.Response.ResponseSuccess.ListItemResponse;
import com.example.mobilemanager.Service.Promotion.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/promotion/")
@PreAuthorize("hasAnyAuthority('ADMIN')")

public class PromotionController {
    @Autowired
    private PromotionService promotionService;


    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('MOD', 'ADMIN')")
    public ResponseEntity<?> createPromotion(@Valid @RequestBody PromotionRequest request) throws ParseException {
        Promotion dto = promotionService.createPromotion(request);
        List<Promotion> dtoList = new ArrayList<>();
        
        dtoList.add(dto);
        ListItemResponse response = new ListItemResponse();
        response.setResult(1 , dtoList);
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('MOD', 'ADMIN')")
    public ResponseEntity<?> getAllPromotion(@RequestBody PaginationRequest request){
        Page<PromotionDTO> promotionDTOPage = promotionService.getAllPromotion(request);
        ListItemResponse response = new ListItemResponse();
        response.setSuccess(true);
        response.setResult(promotionDTOPage.getTotalElements(), promotionDTOPage.getContent());
        return ResponseEntity.ok(response);
    }

    @PutMapping("update")
    @PreAuthorize("hasAnyAuthority('MOD', 'ADMIN')")
    public ResponseEntity<?> updatePromotion(@RequestBody PromotionRequest request) throws ParseException {
        PromotionDTO promotion = promotionService.updatePromotion(request);
        List<PromotionDTO> promotionDTOS = new ArrayList<>();
        promotionDTOS.add(promotion);
        ListItemResponse response = new ListItemResponse();
        response.setSuccess(true);
        response.setResult(1 , promotionDTOS);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/del/{promotionID}")
    @PreAuthorize("hasAnyAuthority('MOD', 'ADMIN')")
    public  ResponseEntity<?> deletePromotion (@PathVariable("promotionID") Long promotionID ){
        boolean bl = promotionService.deletePromotion(promotionID);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setSuccess(true);
        return ResponseEntity.ok(baseResponse);
    }
    @PostMapping("/getIds")
    @PreAuthorize("hasAnyAuthority('MOD','USER', 'ADMIN')")
    public ResponseEntity<?> getListPromotion (@RequestBody List<Long> ids){
        List<PromotionDTO> promotionDTOS = promotionService.listPromotion(ids);
        return ResponseEntity.ok(promotionDTOS);
    }

    @PostMapping("/findId")
    @PreAuthorize("hasAnyAuthority('MOD','USER', 'ADMIN')")
    public ResponseEntity<?> getPromotionById (@RequestBody GetPromotionReq rq){

        PromotionDTO promotion = promotionService.getPrommotionById(rq.getId());
        List<PromotionDTO> promotionDTOS = new ArrayList<>();
        promotionDTOS.add(promotion);
        ListItemResponse response = new ListItemResponse();

        response.setSuccess(true);
        response.setResult(1 ,promotionDTOS);
        return ResponseEntity.ok(promotion);
    }



}
















