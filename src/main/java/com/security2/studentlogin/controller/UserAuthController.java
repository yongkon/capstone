package com.security2.studentlogin.controller;

import java.util.List;
import java.util.Map;

import com.security2.studentlogin.dto.UserDTO;
import com.security2.studentlogin.impl.JwtServiceImpl;
import com.security2.studentlogin.model.User;
import com.security2.studentlogin.repository.UserRepository;
import com.security2.studentlogin.service.JwtService;
import com.security2.studentlogin.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class UserAuthController {
    private UserService userService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    public UserAuthController(UserService userService) {
        this.userService = userService;
    }
    // handler method to handle the home (index.html is home) page request

    @GetMapping("/index")
    public String home() {
        return "index";
    }
    // handler method handles the login request

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    // handler method to handle the user registration form request



    //JWT Token -임시 추가 (hyk)
    //ResponseEntity에서 String으로 변경
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Map<String, String> params,
                                HttpServletResponse res) {
        User user = userRepository.findByEmailAndPassword(params.get("email"), params.get("password"));

        if (user != null) {
            long id = user.getId();
            String token = jwtService.getToken("id", id);

            Cookie  cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");

            res.addCookie(cookie);

            return ResponseEntity.ok().build();
           // return "redirect:/index"; // 로그인 성공 시 홈 페이지로 리다이렉트
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/login/check")
    public ResponseEntity check(@CookieValue(value = "token", required = false) String token) {
        Claims claims = jwtService.getClaims(token);

        if (claims != null) {
            int id = Integer.parseInt(claims.get("id").toString());
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }





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
        System.out.println("***********************");
        System.out.println( userDto.getEmail());
        System.out.println("***********************");

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
        return "users";
    }

    @GetMapping("/book")
    public String book() {
        return "book";
    }

    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

}
