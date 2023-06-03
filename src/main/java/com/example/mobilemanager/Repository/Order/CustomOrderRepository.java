package com.example.mobilemanager.Repository.Order;

import com.example.mobilemanager.Entity.Order;
import com.example.mobilemanager.Entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class CustomOrderRepository {
    public static Specification<Order> filterOrderByCondition(String phoneNumber, String email) {
        return ((root, query, criteriaBuilder) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(phoneNumber)) {
                predicates.add(criteriaBuilder.like(root.get("phoneNumber"), "%" + phoneNumber + "%"));
            }
            if (StringUtils.hasText(email)) {
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + email + "%"));
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        });
    }
}
