package com.sber.practika.models.requests.authentication;

import lombok.Data;

import java.math.BigInteger;

@Data
public class AuthenticationRequestBankCard {
    private BigInteger bankCard;
    private String password;
}
