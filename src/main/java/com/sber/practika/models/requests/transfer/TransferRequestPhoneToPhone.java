package com.sber.practika.models.requests.transfer;

import lombok.Data;

import java.math.BigInteger;

@Data
public class TransferRequestPhoneToPhone {
    private BigInteger phone1;
    private BigInteger phone2;
    private BigInteger value;
}
