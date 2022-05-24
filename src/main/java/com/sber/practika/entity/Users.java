package com.sber.practika.entity;

import com.sber.practika.entity.statuses.Status;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Users {
    @Id
    private String bankNumber;
    private String username;
    private String password;
    private String salt1;
    private String salt2;

    private String family;
    private String name;
    private String patronymic;

    private String email;
    private String address;
    private Long phone;
    private String dateOfBirthday;
    private Status enabled;

    private Long balanceBank;
    private Long mainCardNumber;
    private Integer confirmCode;
}