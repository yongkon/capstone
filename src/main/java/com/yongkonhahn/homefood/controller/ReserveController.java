package com.yongkonhahn.homefood.controller;

import com.yongkonhahn.homefood.dto.OrderDTO;
import com.yongkonhahn.homefood.dto.ReserveDTO;
import com.yongkonhahn.homefood.impl.UserServiceImpl;
import com.yongkonhahn.homefood.repository.UserRepository;
import com.yongkonhahn.homefood.service.UserService;
import com.yongkonhahn.homefood.model.Reserve;
import com.yongkonhahn.homefood.repository.ReserveRepository;
import com.yongkonhahn.homefood.service.ReserveService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class ReserveController {
    @Autowired
    ReserveService reserveService;

    @GetMapping("/book")
    public String showReservationForm(Model model) {
        // create a model object to store form data
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = "";
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            role = authority.getAuthority(); // 권한(롤) 가져오기
        }

        if ("ROLE_ADMIN".equals(role)) {
            //findAll Implementation
            //List<Reserve> reservations = reserveRepository.findAll();
            //Get the Order History
            try {
                Connection conn = DriverManager.getConnection(UserServiceImpl.DB_URL, UserServiceImpl.DB_USERNAME, UserServiceImpl.DB_PASSWORD);
                // SQL query to retrieve order history
                String sql = "Select id, date, email, name, people, request " +
                            "from reserve " +
                            "order by date ";

                // Initialize the PreparedStatement
                Statement statment = conn.prepareStatement(sql);

                // Execute the query and get the result set
                ResultSet rs = statment.executeQuery(sql);

                // Process the result set and add orders to the model
                List<ReserveDTO> allReservations = new ArrayList<>();
                while (rs.next()) {
                    ReserveDTO reserveDTO = new ReserveDTO();
                    reserveDTO.setId(rs.getInt("id"));
                    reserveDTO.setDate(rs.getTimestamp("date"));
                    reserveDTO.setEmail(rs.getString("email"));
                    reserveDTO.setName(rs.getString("name"));
                    reserveDTO.setPeople(rs.getInt("people"));
                    reserveDTO.setRequest(rs.getString("request"));
                    allReservations.add(reserveDTO);
                }
                model.addAttribute("allReservations", allReservations);

                //Valid reservation
                sql = "Select id, date, email, name, people, request " +
                      "from reserve " +
                      "where date >= now() " +
                      "order by date ";

                // Initialize the PreparedStatement
                statment = conn.prepareStatement(sql);

                // Execute the query and get the result set
                rs = statment.executeQuery(sql);
                // Process the result set and add orders to the model
                List<ReserveDTO> validReservations = new ArrayList<>();
                while (rs.next()) {
                    ReserveDTO reserveDTO = new ReserveDTO();
                    reserveDTO.setId(rs.getInt("id"));
                    reserveDTO.setDate(rs.getTimestamp("date"));
                    reserveDTO.setEmail(rs.getString("email"));
                    reserveDTO.setName(rs.getString("name"));
                    reserveDTO.setPeople(rs.getInt("people"));
                    reserveDTO.setRequest(rs.getString("request"));
                    validReservations.add(reserveDTO);
                }
                model.addAttribute("validReservations", validReservations);

                // Close the prepared statement and result set
                rs.close();
                statment.close();

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            model.addAttribute("reservations", Collections.emptyList());
        }

        model.addAttribute("reserve", new ReserveDTO());
        model.addAttribute("loginUser", role);

        return "/book";
    }

    @PostMapping("/book/save")
    public String reservation(@Valid @ModelAttribute("reserve") ReserveDTO reserveDTO, BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("reserve", reserveDTO);
            return "/book";
        }
        //Save a reservation
        reserveService.saveReservation(reserveDTO);

        return "redirect:/book?success";
    }
}
