package com.sber.practika.models.requests.finalize;

import lombok.Data;

import java.math.BigInteger;
import java.util.UUID;

@Data
public class FinalizeTransferRequestBankNumberToBankNumber {
    private UUID uuidTransaction;
    private String bankNumber1;
    private String bankNumber2;
    private BigInteger value;
}
