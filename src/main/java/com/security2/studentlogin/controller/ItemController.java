package com.security2.studentlogin.controller;

import com.security2.studentlogin.dto.UserDTO;
import com.security2.studentlogin.model.Item;
import com.security2.studentlogin.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class ItemController {
    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/cart")
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "cart";
    }
}
