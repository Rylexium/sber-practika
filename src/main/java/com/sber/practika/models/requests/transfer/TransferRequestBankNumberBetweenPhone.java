package com.sber.practika.models.requests.transfer;

import lombok.Data;

import java.math.BigInteger;

@Data
public class TransferRequestBankNumberBetweenPhone {
    private String bankNumber;
    private BigInteger phone;
    private BigInteger value;
}
