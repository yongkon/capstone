package com.yongkonhahn.homefood.repository;

import com.yongkonhahn.homefood.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByUserId(int userId);

    Cart findByUserIdAndItemId(int userId, int itemId);

    void deleteByUserId(int userId);

    void deleteAll();
}