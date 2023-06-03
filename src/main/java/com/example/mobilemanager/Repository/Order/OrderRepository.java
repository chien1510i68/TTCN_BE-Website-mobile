package com.example.mobilemanager.Repository.Order;

import com.example.mobilemanager.Entity.Order;
import com.example.mobilemanager.Model.Revenue;
import com.example.mobilemanager.Request.OrderRequest.RevenueRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order , Long> {
    Order findAllByOrderCreationDate(int year);

//    Order findAllByPhoneNumber(String phoneNumber);

    List<Order> findAllByOrderByOrderIdAsc();

    List<Order> findAllByOrderByOrderCreationDateDesc();
    Order findAllByOrderId(Long id);
    List<Order> findAllByEmail(String email);
    List<Order> findAllByOrderCreationDateBetween(Date start , Date end);

    List<Order> findAllByPhoneNumber(String phoneNumber);

    @Query("SELECT FUNCTION('MONTH', o.orderCreationDate), FUNCTION('YEAR', o.orderCreationDate), SUM(o.totalValueOrder) " +
            "FROM Order o " +
            "WHERE o.orderCreationDate BETWEEN :startDate AND :endDate " +
            "GROUP BY FUNCTION('YEAR', o.orderCreationDate), FUNCTION('MONTH', o.orderCreationDate)")
    List<Revenue> getOrderRevenueByMonthAndYear(Date startDate, Date endDate);


}
