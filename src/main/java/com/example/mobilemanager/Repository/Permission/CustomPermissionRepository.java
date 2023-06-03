package com.example.mobilemanager.Repository.Permission;

import com.example.mobilemanager.Entity.Permission;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
@Repository
public class CustomPermissionRepository {
    public static Specification<Permission> buildFilterSpecification(String keyword) {
        return (((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(keyword)) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + keyword + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }));
    }
}
