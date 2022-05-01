package com.sber.practika.models.requests.transfer;

import lombok.Data;

import java.math.BigInteger;

@Data
public class TransferRequestPhoneToBankCard {
    private BigInteger phone;
    private BigInteger bankCard;
    private BigInteger value;
}
