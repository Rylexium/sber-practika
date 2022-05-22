package com.sber.practika.models.requests.finalize;

import lombok.Data;

import java.math.BigInteger;
import java.util.UUID;

@Data
public class FinalizeTransferRequestBankCardBetweenBankNumber {
    private UUID uuidTransaction;
    private BigInteger bankCard;
    private String bankNumber;
    private BigInteger value;
}
