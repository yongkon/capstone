package com.yongkonhahn.homefood.repository;

import com.yongkonhahn.homefood.impl.UserServiceImpl;
import com.yongkonhahn.homefood.model.Item;
import com.yongkonhahn.homefood.model.Order;
import com.yongkonhahn.homefood.model.OrderItem;
import com.yongkonhahn.homefood.model.User;
import com.yongkonhahn.homefood.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testFindByUserId() {
        //Delete the Test data
        //orders : userId, date
        //order_items : quntity(1), itemId, order_id
        try {
            Connection conn = DriverManager.getConnection(UserServiceImpl.DB_URL, UserServiceImpl.DB_USERNAME, UserServiceImpl.DB_PASSWORD);
            // Delete Orders Table : userId, date
            String sql = "Delete from orders where user_id = 999 ";
            // Initialize the PreparedStatement
            Statement statment = conn.prepareStatement(sql);
            // Execute the query and get the result set
            statment.executeQuery(sql);

            // Delete order_items Table : userId, date
            sql = "Delete from order_items " +
                  "where a.order_id = (Select order_id from orders where user_id = 999 ) ";
            // Initialize the PreparedStatement
            statment = conn.prepareStatement(sql);
            // Execute the query and get the result set
            statment.executeQuery(sql);

            // Close the prepared statement and result set
            statment.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create test data
        User user = new User();
        user.setId(999);
        user.setName("John");

        Order order1 = new Order();
        order1.setUser(user);

        Item item1 = new Item();
        item1.setId(10);
        Item item2 = new Item();
        item1.setId(11);

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setOrder(order1);
        orderItem1.setItem(item1);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setOrder(order1);
        orderItem2.setItem(item2);
        // Set other properties of orderItem1

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);

        order1.setOrderItems(orderItems);

        // Set other properties of order2

        orderRepository.saveAll(List.of(order1));

        // Test the findByUserId method
        List<Order> foundOrders = orderRepository.findByUserId(user.getId());

        // Verify the results
        Assertions.assertEquals(2, foundOrders.size());
        Assertions.assertEquals(user, foundOrders.get(0).getUser());
        Assertions.assertEquals(user, foundOrders.get(1).getUser());
    }
}