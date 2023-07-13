package com.yongkonhahn.homefood.controller;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import com.yongkonhahn.homefood.model.Item;
import com.yongkonhahn.homefood.model.Order;
import com.yongkonhahn.homefood.model.User;
import com.yongkonhahn.homefood.repository.CartRepository;
import com.yongkonhahn.homefood.repository.ItemRepository;
import com.yongkonhahn.homefood.repository.OrderRepository;
import com.yongkonhahn.homefood.security.CustomUserDetailsService;
import com.yongkonhahn.homefood.service.UserService;
import com.yongkonhahn.homefood.model.Cart;
import com.yongkonhahn.homefood.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/order")
    public String order(Model model) {
        // Get a user id from current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Get a UserDetailsService by current user's email
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // If fail to retrieve user information
        if (userDetails == null) {
            return "redirect:/login";
        }
        // Current user's information
        User user = userService.findUserByEmail(username);

        // Login User Information
        model.addAttribute("loginUser", user.getName());

        // Order Information
        List<Cart> orders = cartRepository.findByUserId(user.getId());
        List<Integer> itemIds = orders.stream().map(Cart::getItemId).toList();
        List<Item> items = itemRepository.findByIdIn(itemIds);

        // Calculate total price
        double totalPrice = 0;
        for (Item item : items) {
            totalPrice = BigDecimal.valueOf(totalPrice)
                    .add(BigDecimal.valueOf(item.getPrice()))
                    .doubleValue();
        }

        BigDecimal totalPriceBigDecimal = BigDecimal.valueOf(totalPrice);

        model.addAttribute("items", items);
        model.addAttribute("totalPrice", totalPriceBigDecimal);

        return "order";
    }

    @PostMapping("/order/save")
    @Transactional
    public String orderSave(Model model) {

        // Get UserId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findUserByEmail(username);

        // Get Cart and CartItem by UserId
        List<Cart> orders = cartRepository.findByUserId(user.getId());
        List<Integer> itemIds = orders.stream().map(Cart::getItemId).toList();
        List<Item> items = itemRepository.findByIdIn(itemIds);


        //Delete Cart
        cartRepository.deleteAll();

        // New Order to save
        Order newOrder = new Order();

        //Set User Information
        newOrder.setUser(user);

        //Set Items
        for (Item item : items) {
            OrderItem orderItem = new OrderItem();
            // order item setting
            orderItem.setItem(item);
            orderItem.setQuantity(1);

            newOrder.addOrderItem(orderItem);
        }
        //Save Order and OrderItems
        orderRepository.save(newOrder);

        return "redirect:/order?success";
    }
}
