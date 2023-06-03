package com.example.mobilemanager.Repository.Product;

import com.example.mobilemanager.Entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class CustomProductRepository {
    public static Specification<Product> filterProductByCondition(Float priceFrom, Float priceTo,
                                                                  String manufacturer,
                                                                  String color,
                                                                  Integer memory,
                                                                  Long productId ,

                                                                  String productName
    ) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (priceFrom != null && priceTo != null) {
                predicates.add(criteriaBuilder.between(root.get("price"), priceFrom, priceTo));
            }
            if (StringUtils.hasText(manufacturer)) {
                predicates.add(criteriaBuilder.like(root.get("manufacturer"), "%" + manufacturer + "%"));
            }
            if (StringUtils.hasText(color)) {
                predicates.add(criteriaBuilder.like(root.get("color"), "%" + color + "%"));
            }
            if (memory != null) {
                predicates.add(criteriaBuilder.equal(root.get("memory"), memory));
//                predicates.add(criteriaBuilder.like(root.get("memory"), "%" + memory + "%"));
            }
            if (productId != null) {
                predicates.add(criteriaBuilder.equal(root.get("productId"), productId));
            }
            if (productName != null) {
                predicates.add(criteriaBuilder.like(root.get("productName"), "%" + productName + "%"));
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        });
    }


}
