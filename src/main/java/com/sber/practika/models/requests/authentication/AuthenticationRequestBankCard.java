package com.sber.practika.models.requests.authentication;

import lombok.Data;

import java.math.BigInteger;

@Data
public class AuthenticationRequestBankCard {
    private Long bankCard;
    private String password;
}
