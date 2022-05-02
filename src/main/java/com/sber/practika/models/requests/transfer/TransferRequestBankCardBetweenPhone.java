package com.sber.practika.models.requests.transfer;

import lombok.Data;

import java.math.BigInteger;

@Data
public class TransferRequestBankCardBetweenPhone {
    private BigInteger bankCard;
    private BigInteger phone;
    private BigInteger value;
}
