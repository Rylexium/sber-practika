package com.sber.practika.models.requests.support.newPassword;

import lombok.Data;

import java.math.BigInteger;

@Data
public class SupportRequestNewPasswordAndBankCard {
    private BigInteger bankCard;
    private String newPassword;
}
