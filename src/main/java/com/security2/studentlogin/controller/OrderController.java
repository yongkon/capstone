package com.security2.studentlogin.controller;

import com.security2.studentlogin.impl.JwtServiceImpl;
import com.security2.studentlogin.model.Item;
import com.security2.studentlogin.model.Order;
import com.security2.studentlogin.repository.ItemRepository;
import com.security2.studentlogin.repository.OrderRepository;
import com.security2.studentlogin.service.JwtService;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
public class OrderController {
    @Autowired
    JwtService jwtService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/cart/items")
    public ResponseEntity getOrderItems(@CookieValue(value = "token", required = false) String token) {

        if(!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        long userId = jwtService.getId(token);
        List<Order> orders = orderRepository.findByUserId(userId);
        List<Integer> itemIds = orders.stream().map(Order::getItemId).toList();
        List<Item> items = itemRepository.findByIdIn(itemIds);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }
    @PostMapping("/cart/items/{itemId}")
    public ResponseEntity pushOrderItem(
            @PathVariable("itemId") int itemId,
            @CookieValue(value = "token", required = false) String token
    ) {

        if(!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        long userId = jwtService.getId(token);
        Order order = orderRepository.findByUserIdAndItemId(userId, itemId);

        if (order == null) {
            Order newOrder = new Order();
            newOrder.setUserId(userId);
            newOrder.setItemId(itemId);
            orderRepository.save(newOrder);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
