package com.yongkonhahn.homefood.service;

import com.yongkonhahn.homefood.dto.UserDTO;
import com.yongkonhahn.homefood.model.Role;
import com.yongkonhahn.homefood.model.User;
import com.yongkonhahn.homefood.repository.UserRepository;
import com.yongkonhahn.homefood.service.UserService;
import com.yongkonhahn.homefood.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUser() {
        // Create test data
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setEmail("john@example.com");
        userDTO.setPassword("password");

        // Test the saveUser method
        userService.saveUser(userDTO);

        // Verify that the userRepository's save method is called once
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testFindUserByEmail() {
        // Create test data
        User user = new User();
        user.setName("John");
        user.setEmail("john@example.com");
        user.setPassword("password");

        when(userRepository.findByEmail("john@example.com")).thenReturn(user);

        // Test the findUserByEmail method
        User foundUser = userService.findUserByEmail("john@example.com");

        // Verify the result
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(user.getName(), foundUser.getName());
    }

    @Test
    public void testFindUserDtoByEmail() {
        // Create test data
        User user = new User();
        user.setName("John");
        user.setEmail("john@example.com");
        user.setPassword("password");

        when(userRepository.findByEmail("john@example.com")).thenReturn(user);

        // Test the findUserDtoByEmail method
        UserDTO foundUserDTO = userService.findUserDtoByEmail("john@example.com");

        // Verify the result
        Assertions.assertNotNull(foundUserDTO);
        Assertions.assertEquals(user.getName(), foundUserDTO.getFirstName());
        Assertions.assertEquals(user.getEmail(), foundUserDTO.getEmail());
    }

    @Test
    public void testFindAllUsers() {
        // Create test data
        User user1 = new User();
        user1.setName("John");
        user1.setEmail("john@example.com");
        user1.setPassword("password");

        User user2 = new User();
        user2.setName("Alice");
        user2.setEmail("alice@example.com");
        user2.setPassword("password");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        // Test the findAllUsers method
        List<UserDTO> foundUserDTOs = userService.findAllUsers();

        // Verify the result
        Assertions.assertEquals(users.size(), foundUserDTOs.size());
        // Add more assertions to verify the contents of the foundUserDTOs list
    }
}