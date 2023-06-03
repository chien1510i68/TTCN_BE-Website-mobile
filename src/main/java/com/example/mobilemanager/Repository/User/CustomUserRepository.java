package com.example.mobilemanager.Repository.User;

import com.example.mobilemanager.Entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
@Repository
public class CustomUserRepository {
    public static Specification<User> buildFilterSpecification(String keyword , Long id ){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            //always not list superadmin for api
            predicates.add(criteriaBuilder.equal(root.get("isSuperAdmin"), false));
            if (StringUtils.hasText(keyword)) {
                predicates.add(criteriaBuilder.like(root.get("userName"), "%" + keyword + "%"));
            }
            if(id != null ){
                predicates.add(criteriaBuilder.equal(root.get("id"), id));

            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        });
    }
}
