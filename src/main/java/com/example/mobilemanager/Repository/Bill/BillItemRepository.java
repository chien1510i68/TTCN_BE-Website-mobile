package com.example.mobilemanager.Repository.Bill;

import com.example.mobilemanager.Entity.BillItem;
import com.example.mobilemanager.Entity.BillProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillItemRepository extends JpaRepository<BillItem , Long> {
}
