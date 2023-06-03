package com.example.mobilemanager.Service.Promotion;

import com.example.mobilemanager.Constant.DateTimeConstant;
import com.example.mobilemanager.Entity.Product;
import com.example.mobilemanager.Entity.Promotion;
import com.example.mobilemanager.Model.DTO.PromotionDTO;

import com.example.mobilemanager.Repository.Product.ProductRepoSitory;
import com.example.mobilemanager.Repository.Promotion.PromotionRepository;
import com.example.mobilemanager.Request.ProductRequest.PaginationRequest;
import com.example.mobilemanager.Request.PromotionReq.PromotionRequest;
import com.example.mobilemanager.utils.MyUntil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionServiceImpl implements PromotionService {
    private final PromotionRepository promotionRepository;
    private final ModelMapper mapper;

    private final ProductRepoSitory productRepoSitory;

    @Autowired
    public PromotionServiceImpl(PromotionRepository promotionRepository, ModelMapper mapper, ProductRepoSitory productRepoSitory) {
        this.promotionRepository = promotionRepository;
        this.mapper = mapper;
        this.productRepoSitory = productRepoSitory;
    }


    public Promotion createPromotion(PromotionRequest request) throws ParseException {
        List<Product> products = productRepoSitory.findAllById(request.getIds());
        Promotion promotion = Promotion.builder()
                .promotionPercentage(request.getPromotionPercentage())
                .startDate(MyUntil.convertDateFromString(request.getStartDate(), DateTimeConstant.DATE_FORMAT))
                .endDate(MyUntil.convertDateFromString(request.getEndDate(), DateTimeConstant.DATE_FORMAT))
                .minimumPurchaseAmount(request.getMinimumPurchaseAmount())
                .build();
        promotionRepository.save(promotion);

        for (Product product : products) {
            product.setPromotion(promotion);
            Float  salePrice = product.getPrice() - ((product.getPrice() * promotion.getPromotionPercentage())/100) ;
            product.setSalePrice(salePrice);
            product.setPercenPromotion((int)promotion.getPromotionPercentage());
            productRepoSitory.save(product);
        }
        promotion.setProducts(products);

//        return mapper.map(promotion, PromotionDTO.class);
        return promotion;
    }

    @Override
    public Page<PromotionDTO> getAllPromotion(PaginationRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        List<Promotion> dtoList = promotionRepository.findAll();
        List<PromotionDTO> promotionDTOS = dtoList.stream().map(i -> mapper.map(i, PromotionDTO.class)).collect(Collectors.toList());
        return new PageImpl<>(promotionDTOS, pageable, promotionDTOS.size());
    }

    @Override
    public PromotionDTO updatePromotion(PromotionRequest request) throws ParseException {
        Promotion promotion = promotionRepository.findAllByPromotionID(request.getPromotionID());
        promotion.setPromotionPercentage(request.getPromotionPercentage());
        Date endDateConvert = MyUntil.convertDateFromString(request.getEndDate(), DateTimeConstant.DATE_FORMAT);
        Date startDateConvert = MyUntil.convertDateFromString(request.getStartDate(), DateTimeConstant.DATE_FORMAT);

        promotion.setEndDate(endDateConvert);
        promotion.setStartDate(startDateConvert);
        promotion.setMinimumPurchaseAmount(request.getMinimumPurchaseAmount());
        List<Product> products = productRepoSitory.findAllById(request.getIds());
        for (Product product : products) {
            product.setPromotion(promotion);
            product.setPercenPromotion((int)promotion.getPromotionPercentage());
            product.setSalePrice(product.getPrice() - ((product.getPrice() * promotion.getPromotionPercentage()) / 100));
            productRepoSitory.save(product);
        }
        promotion.setProducts(products);

        promotionRepository.save(promotion);
        return mapper.map(promotion, PromotionDTO.class);
    }

    @Override
    public boolean deletePromotion(Long id) {
//        Promotion promotion = promotionRepository.findAllByPromotionID(id);
        promotionRepository.deleteById(id);
        return true;
    }

    @Override
    public List<PromotionDTO> listPromotion(List<Long> ids) {
        List<Promotion> promotions = promotionRepository.findAllById(ids);
        return promotions.stream().map(i -> mapper.map(i, PromotionDTO.class)).collect(Collectors.toList());
    }

    @Override
    public PromotionDTO getPrommotionById(Long id) {
        Promotion promotion = promotionRepository.findAllByPromotionID(id);
        return mapper.map(promotion, PromotionDTO.class);
    }


}



