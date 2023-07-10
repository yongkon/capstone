package com.security2.studentlogin.repository;

import com.security2.studentlogin.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(Long userId);
    Order findByUserIdAndItemId(Long userId, int itemId);
}
