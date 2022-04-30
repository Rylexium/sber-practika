package com.sber.practika.models.requests.transfer;

import lombok.Data;

import java.math.BigInteger;

@Data
public class TransferRequestBankNumberToBankCard {
    private String bankNumber;
    private BigInteger bankCard;
    private BigInteger value;
}
