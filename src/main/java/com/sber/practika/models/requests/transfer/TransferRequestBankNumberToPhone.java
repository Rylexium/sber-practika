package com.sber.practika.models.requests.transfer;

import lombok.Data;

import java.math.BigInteger;

@Data
public class TransferRequestBankNumberToPhone {
    private String bankNumber;
    private BigInteger phone;
    private BigInteger value;
}
