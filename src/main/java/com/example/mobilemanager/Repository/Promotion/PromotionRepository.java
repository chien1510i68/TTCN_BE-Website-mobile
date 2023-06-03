package com.example.mobilemanager.Repository.Promotion;

import com.example.mobilemanager.Entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion,Long> {
    List<Promotion> findAll();

    Promotion findAllByPromotionID(Long id );


}
