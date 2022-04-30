package com.sber.practika.models.requests.authentication;

import lombok.Data;

import java.math.BigInteger;

@Data
public class AuthenticationRequestPhone {
    private BigInteger phone;
    private String password;
}