package com.sber.practika.models.requests.finalize;

import lombok.Data;

import java.util.UUID;

@Data
public class FinalizeTransferRequestBankCardBetweenBankNumber {
    private UUID uuidTransaction;
    private Long bankCard;
    private String bankNumber;
    private Long value;

}
