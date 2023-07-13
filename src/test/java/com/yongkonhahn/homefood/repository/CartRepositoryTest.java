package com.yongkonhahn.homefood.repository;

import com.yongkonhahn.homefood.model.Cart;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@SpringBootTest
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Test
    public void testFindByUserId() {
        // Create test data
        // UserId 1 : Admin
        // ItemID 5 : New York Style Pizza, 18.99
        // ItemID 6 : Philly Cheesesteak Sandwich 19.99
        int userId = 1;
        Cart cart1 = new Cart();
        cart1.setUserId(userId);
        cart1.setItemId(5);
        cartRepository.save(cart1);

        Cart cart2 = new Cart();
        cart2.setUserId(userId);
        cart2.setItemId(6);
        cart2.setOrderDate(new Date());
        cartRepository.save(cart2);

        // Perform the search
        List<Cart> carts = cartRepository.findByUserId(userId);

        // Verify the result
        Assertions.assertEquals(2, carts.size());
    }

    @Test
    public void testFindByUserIdAndItemId() {
        // Create test data
        int userId = 1;
        int itemId = 10;
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setItemId(itemId);
        cartRepository.save(cart);

        // Perform the search
        Cart foundCart = cartRepository.findByUserIdAndItemId(userId, itemId);

        // Verify the result
        Assertions.assertNotNull(foundCart);
        Assertions.assertEquals(userId, foundCart.getUserId());
        Assertions.assertEquals(itemId, foundCart.getItemId());
    }

    @Test
    public void testDeleteByUserId() {
        // Create test data
        int userId = 1;
        Cart cart1 = new Cart();
        cart1.setUserId(userId);
        cart1.setItemId(10);
        cartRepository.deleteByUserId(userId);

        // Perform the deletion
        cartRepository.deleteByUserId(userId);

        // Verify the result
        List<Cart> carts = cartRepository.findByUserId(userId);
        Assertions.assertEquals(0, carts.size());
    }

    @Test
    public void testDeleteAll() {
        // Create test data
        Cart cart1 = new Cart();
        cart1.setUserId(1);
        cart1.setItemId(6);
        cartRepository.save(cart1);

        Cart cart2 = new Cart();
        cart2.setUserId(1);
        cart2.setItemId(6);
        cartRepository.save(cart2);

        // Perform the deletion
        cartRepository.deleteAll();

        // Verify the result
        List<Cart> carts = cartRepository.findAll();
        Assertions.assertEquals(0, carts.size());
    }
}