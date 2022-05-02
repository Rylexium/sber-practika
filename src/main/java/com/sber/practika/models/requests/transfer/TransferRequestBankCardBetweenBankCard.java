package com.sber.practika.models.requests.transfer;

import lombok.Data;

import java.math.BigInteger;

@Data
public class TransferRequestBankCardBetweenBankCard {
    private BigInteger bankCard1;
    private BigInteger bankCard2;
    private BigInteger value;
}