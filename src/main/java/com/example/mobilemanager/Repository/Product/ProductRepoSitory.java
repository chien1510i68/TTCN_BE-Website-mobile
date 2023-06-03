package com.example.mobilemanager.Repository.Product;

import com.example.mobilemanager.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepoSitory extends JpaRepository<Product, Long> , JpaSpecificationExecutor<Product> {
    List<Product> findAllByOrderByProductIdAsc();

    List<Product> findAllByOrderByQuantityInStoreDesc();

    @Query("SELECT p.manufacturer, SUM(p.sold) FROM Product p GROUP BY p.manufacturer")
    List<Object[]> findManufacturerAndSoldCount();


}
