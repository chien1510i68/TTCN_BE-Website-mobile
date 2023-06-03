package com.example.mobilemanager.Repository.Bill;

import com.example.mobilemanager.Entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill , Long> {
}
