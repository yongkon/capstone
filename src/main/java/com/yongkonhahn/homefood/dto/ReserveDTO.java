package com.yongkonhahn.homefood.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReserveDTO {
    private int id;

    private String name;

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    private Date date;

    private int people;

    private String request;
}