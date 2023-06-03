package com.example.mobilemanager.Repository.Order;

import com.example.mobilemanager.Entity.Order;
import com.example.mobilemanager.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

//    List<OrderItem> findAllByOrder();
    List<OrderItem> findAllByOrder(Order order);
}
