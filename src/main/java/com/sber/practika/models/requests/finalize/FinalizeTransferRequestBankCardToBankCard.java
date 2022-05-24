package com.sber.practika.models.requests.finalize;

import lombok.Data;

import java.util.UUID;

@Data
public class FinalizeTransferRequestBankCardToBankCard {
    private UUID uuidTransaction;
    private Long bankCard1;
    private Long bankCard2;
    private Long value;
}
