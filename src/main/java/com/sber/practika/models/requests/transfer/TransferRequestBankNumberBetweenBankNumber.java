package com.sber.practika.models.requests.transfer;

import lombok.Data;

import java.math.BigInteger;

@Data
public class TransferRequestBankNumberBetweenBankNumber {
    private String bankNumber1;
    private String bankNumber2;
    private BigInteger value;
}
