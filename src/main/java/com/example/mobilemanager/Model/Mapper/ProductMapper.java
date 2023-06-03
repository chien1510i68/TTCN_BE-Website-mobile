package com.example.mobilemanager.Model.Mapper;

import com.example.mobilemanager.Entity.Product;
import com.example.mobilemanager.Model.DTO.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    ProductDTO toProdentityToProductDTO(Product product);

}
