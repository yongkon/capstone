package com.yongkonhahn.homefood.repository;
import com.yongkonhahn.homefood.dto.ReserveDTO;
import com.yongkonhahn.homefood.impl.UserServiceImpl;
import com.yongkonhahn.homefood.model.Role;
import com.yongkonhahn.homefood.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    UserServiceImpl userServiceImpl;

    @Test
    public void testFindByName() throws SQLException {
        //Delete the Test
        try {
            Connection conn = DriverManager.getConnection(UserServiceImpl.DB_URL, UserServiceImpl.DB_USERNAME, UserServiceImpl.DB_PASSWORD);
            // SQL query to retrieve order history
            String sql = "Delete from roles where name = 'ROLE_TEST' ";

            // Initialize the PreparedStatement
            Statement statment = conn.prepareStatement(sql);

            // Execute the query and get the result set
            statment.executeQuery(sql);

            // Close the prepared statement and result set
            statment.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create test data
        Role role1 = new Role();
        role1.setName("ROLE_TEST");

        roleRepository.saveAll(List.of(role1));

        // Test the findByName method
        Role foundRole = roleRepository.findByName("ROLE_TEST");

        // Verify the result
        Assertions.assertNotNull(foundRole);
        Assertions.assertEquals(role1.getName(), foundRole.getName());
    }
}