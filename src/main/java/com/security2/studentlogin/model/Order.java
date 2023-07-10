package com.security2.studentlogin.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    /*
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
*/
    /*
    @OneToMany(mappedBy = "order")
    public List<OrderItem> items = new ArrayList<>();
    */

    @Column(name = "user_id")
    long userId;

    @Column(name = "item_id")
    int itemId;

    @Column(length = 50)
    Date orderDate;
}