package com.sber.practika.models.requests.finalize;

import lombok.Data;

import java.math.BigInteger;
import java.util.UUID;

@Data
public class FinalizeTransferRequestBankCardToBankCard {
    private UUID uuidTransaction;
    private BigInteger bankCard1;
    private BigInteger bankCard2;
    private BigInteger value;
}
