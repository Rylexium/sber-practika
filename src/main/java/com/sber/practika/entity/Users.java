package com.sber.practika.entity;

import com.sber.practika.models.Status;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;

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
    private BigInteger phone;
    private String dateOfBirthday;
    private Status enabled;

    private BigInteger balanceBank;
    private BigInteger mainCardNumber;
}