package com.yongkonhahn.homefood.controller;

import com.yongkonhahn.homefood.model.Item;
import com.yongkonhahn.homefood.repository.ItemRepository;
import com.yongkonhahn.homefood.security.CustomUserDetailsService;
import com.yongkonhahn.homefood.service.UserService;
import com.yongkonhahn.homefood.model.Cart;
import com.yongkonhahn.homefood.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CartController {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    private UserService userService;

    //Save selected item to a cart
    @PostMapping("/cart/items/{itemId}")
    public ResponseEntity pushCartItem(
            @PathVariable("itemId") int itemId,
            @CookieValue(value = "token", required = false) String token)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Get a user id from current user
        int userId = userService.findUserByEmail(username).getId();
        // Get Cart information
        Cart cart = cartRepository.findByUserIdAndItemId(userId, itemId);
        // Save a new Cart
        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            newCart.setItemId(itemId);
            cartRepository.save(newCart);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Delete selected item from a cart
    @DeleteMapping("/cart/items/{itemId}")
    public ResponseEntity removeCartItem(
            @PathVariable("itemId") int itemId,
            @CookieValue(value = "token", required = false) String token
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Get a user id from current user
        int userId = userService.findUserByEmail(username).getId();
        // Get a Cart information of current user
        Cart cart = cartRepository.findByUserIdAndItemId(userId, itemId);
        // Delete selected item from cart
        cartRepository.delete(cart);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
