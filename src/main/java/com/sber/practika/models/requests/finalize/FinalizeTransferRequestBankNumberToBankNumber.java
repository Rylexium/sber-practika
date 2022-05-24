package com.sber.practika.models.requests.finalize;

import lombok.Data;

import java.util.UUID;

@Data
public class FinalizeTransferRequestBankNumberToBankNumber {
    private UUID uuidTransaction;
    private String bankNumber1;
    private String bankNumber2;
    private Long value;
}
