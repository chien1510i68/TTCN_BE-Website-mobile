package com.example.mobilemanager.Repository.Supplier;

import com.example.mobilemanager.Entity.Supplier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class CustomSupplierRepository {


    public static Specification<Supplier> filterSupplierByCondition(Date dateFrom, Date dateTo) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicate = new ArrayList<>();
            if (dateFrom != null && dateTo != null) {
                predicate.add(criteriaBuilder.between(root.get("importDate"), dateFrom, dateTo));
            }


            return criteriaBuilder.and(new Predicate[0]);
        });

    }
}
