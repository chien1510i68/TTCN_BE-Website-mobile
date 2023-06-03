package com.example.mobilemanager.Service.Promotion;

import com.example.mobilemanager.Entity.Promotion;
import com.example.mobilemanager.Model.DTO.PromotionDTO;
import com.example.mobilemanager.Request.ProductRequest.PaginationRequest;
import com.example.mobilemanager.Request.PromotionReq.PromotionRequest;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;

public interface PromotionService {
     Promotion createPromotion(PromotionRequest request) throws ParseException;

     Page<PromotionDTO> getAllPromotion(PaginationRequest request);

     PromotionDTO updatePromotion (PromotionRequest request) throws ParseException;

     boolean deletePromotion (Long id);

     List<PromotionDTO> listPromotion(List<Long> ids);

     PromotionDTO getPrommotionById (Long id);

}
