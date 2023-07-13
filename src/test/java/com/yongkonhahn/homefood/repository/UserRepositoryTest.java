package com.yongkonhahn.homefood.repository;

import com.yongkonhahn.homefood.dto.UserDTO;
import com.yongkonhahn.homefood.model.Role;
import com.yongkonhahn.homefood.model.User;
import com.yongkonhahn.homefood.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmail() {
        // Create test data
        User user1 = new User();
        user1.setName("John");
        user1.setEmail("john@example.com");
        user1.setPassword("password");

        User user2 = new User();
        user2.setName("Alice");
        user2.setEmail("alice@example.com");
        user2.setPassword("password");

        userRepository.saveAll(List.of(user1, user2));

        // Test the findByEmail method
        User foundUser = userRepository.findByEmail("john@example.com");

        // Verify the result
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(user1.getName(), foundUser.getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"john@example.com", "alice@example.com"})
    public void testFindByEmail_Parametrized(String email) {
        // Test the findByEmail method with different email values
        User foundUser = userRepository.findByEmail(email);

        // Verify the result
        Assertions.assertNotNull(foundUser);
    }

    @Test
    public void testFindUserDTOByEmail() {
        // Create test data
        User user = new User();
        user.setName("John");
        user.setEmail("john@example.com");
        user.setPassword("password");

        userRepository.save(user);

        // Test the findUserDTOByEmail method
        UserDTO foundUserDTO = userRepository.findUserDTOByEmail("john@example.com");

        // Verify the result
        Assertions.assertNotNull(foundUserDTO);
        Assertions.assertEquals(user.getName(), foundUserDTO.getFirstName());
        Assertions.assertEquals(user.getEmail(), foundUserDTO.getEmail());
    }

    @Test
    public void testFindByEmailAndPassword() {
        // Create test data
        User user = new User();
        user.setName("John");
        user.setEmail("john@example.com");
        user.setPassword("password");

        userRepository.save(user);

        // Test the findByEmailAndPassword method
        User foundUser = userRepository.findByEmailAndPassword("john@example.com", "password");

        // Verify the result
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(user.getName(), foundUser.getName());
    }
}