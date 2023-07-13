package com.yongkonhahn.homefood.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    @Column(name = "user_id")
    int userId;

    @Column(name = "item_id")
    int itemId;

    @Column(length = 50)
    Date orderDate;
}