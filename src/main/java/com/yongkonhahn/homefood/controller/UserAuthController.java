package com.yongkonhahn.homefood.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yongkonhahn.homefood.dto.OrderDTO;
import com.yongkonhahn.homefood.dto.UserDTO;
import com.yongkonhahn.homefood.impl.UserServiceImpl;
import com.yongkonhahn.homefood.model.Item;
import com.yongkonhahn.homefood.model.User;
import com.yongkonhahn.homefood.repository.CartRepository;
import com.yongkonhahn.homefood.repository.ItemRepository;
import com.yongkonhahn.homefood.repository.OrderRepository;
import com.yongkonhahn.homefood.repository.UserRepository;
import com.yongkonhahn.homefood.security.CustomUserDetailsService;
import com.yongkonhahn.homefood.service.UserService;
import com.yongkonhahn.homefood.model.Cart;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
public class UserAuthController {

    UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    public UserAuthController(UserService userService) {
        this.userService = userService;
    }
    // handler method to handle the home (index.html is home) page request

    // Main page
    @GetMapping("/index")
    public String home() {
        return "index";
    }
    // handler method handles the login request

    @GetMapping("/login")
    public String login(Model model) {
        // Get User name
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Get User Information
        // Login User Information
        User user = new User();
        if (username != null && !username.equals("anonymousUser")) {
            user = userService.findUserByEmail(username);
            if (user == null) {
                return "login";
            }
        }

        // Login User Information
        model.addAttribute("loginUser", user.getName());
        return "login";
    }
    // New User Register
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create a model object to store form data
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle user registration from submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDTO userDto, BindingResult result,
                               Model model) {

        User existingUser = userService.findUserByEmail(userDto.getEmail());
        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null, "There is already an account registered with the same email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }
        userService.saveUser(userDto);
        return "redirect:/register?success";
    }


    // handler method is used to handle a list of users
    @GetMapping("/users")
    public String users(Model model) {
        List<UserDTO> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "/users";
    }

    // Menu page
    @GetMapping("/menu")
    public String menu(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        model.addAttribute("username", username);

        return "menu";
    }
    // Contact Page
    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    // Cart Page
    @GetMapping("/cart")
    public String cart(Model model, HttpSession session) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // get UserDetailsService from current user's email.
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            return "redirect:/login";
        }

        User user = userService.findUserByEmail(username);

        // Login User Information
        model.addAttribute("loginUser", user.getName());

        // Cart Information
        List<Cart> orders = cartRepository.findByUserId(user.getId());
        List<Integer> itemIds = orders.stream().map(Cart::getItemId).toList();
        List<Item> items = itemRepository.findByIdIn(itemIds);

        double totalPrice = 0;
        for (Item item : items) {
            totalPrice = BigDecimal.valueOf(totalPrice)
                    .add(BigDecimal.valueOf(item.getPrice()))
                    .doubleValue();
        }

        BigDecimal totalPriceBigDecimal = BigDecimal.valueOf(totalPrice);

        model.addAttribute("items", items);
        model.addAttribute("totalPrice", totalPriceBigDecimal);

        //Get the Order History
        try {
            Connection conn = DriverManager.getConnection(UserServiceImpl.DB_URL, UserServiceImpl.DB_USERNAME, UserServiceImpl.DB_PASSWORD);
            // SQL query to retrieve order history
            String sql = " select a.id, a.date, b.item_id, c.img_path, c.price, c.name  "
                    + " from  orders a, order_items b, items c "
                    + " where a.user_id = " + String.valueOf(user.getId())
                    + " and   a.id = b.order_id "
                    + " and   b.item_id = c.id "
                    + " order by a.id desc";

            Statement statment = conn.prepareStatement(sql);


            // Execute the query and get the result set
            ResultSet rs = statment.executeQuery(sql);

            // Process the result set and add orders to the model
            List<OrderDTO> historyOrders = new ArrayList<>();
            while (rs.next()) {
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setId(rs.getInt("id"));
                orderDTO.setDate(rs.getTimestamp("date"));
                orderDTO.setItemId(rs.getInt("item_id"));
                orderDTO.setImgPath(rs.getString("img_path"));
                orderDTO.setPrice(String.format("$%.2f", rs.getDouble("price")));
                orderDTO.setName(rs.getString("name"));
                historyOrders.add(orderDTO);
            }
            model.addAttribute("historyOrders", historyOrders);

            // Close the prepared statement and result set
            rs.close();
            statment.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return "cart";
    }

    // User login page
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> params) {
        // Login logic
        User user = userRepository.findByEmailAndPassword(params.get("email"), params.get("email"));

        // redirect : success? or fail?
        if (user != null) {
            // successfully login
            return "redirect:/cart";
        } else {
            // login failed
            return "redirect:/login";
        }
    }

    //User logout page
    @PostMapping("/logout")
    public String logout() {
        // after logout, move to the login
        return "redirect:/login";
    }
}
