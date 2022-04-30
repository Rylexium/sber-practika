package com.sber.practika.models.requests.transfer;

import lombok.Data;

import java.math.BigInteger;

@Data
public class TransferRequestBankCardToBankNumber {
    private BigInteger bankCard;
    private String bankNumber;
    private BigInteger value;
}
