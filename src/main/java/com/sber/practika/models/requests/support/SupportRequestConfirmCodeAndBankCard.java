package com.sber.practika.models.requests.support;

import lombok.Data;

import java.math.BigInteger;
@Data
public class SupportRequestConfirmCodeAndBankCard {
    private BigInteger bankCard;
    private Integer confirmCode;
}
