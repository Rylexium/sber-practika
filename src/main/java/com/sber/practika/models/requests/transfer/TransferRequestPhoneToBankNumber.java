package com.sber.practika.models.requests.transfer;

import lombok.Data;

import java.math.BigInteger;

@Data
public class TransferRequestPhoneToBankNumber {
    private BigInteger phone;
    private String bankNumber;
    private BigInteger value;
}
