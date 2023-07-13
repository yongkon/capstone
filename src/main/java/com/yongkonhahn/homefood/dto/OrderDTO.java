package com.yongkonhahn.homefood.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderDTO {
    private int id;

    private Date date;

    private int itemId;

    private String imgPath;

    private String price;

    private String name;
}

