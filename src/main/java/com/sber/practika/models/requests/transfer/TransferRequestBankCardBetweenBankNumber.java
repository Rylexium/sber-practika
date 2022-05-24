package com.sber.practika.models.requests.transfer;

import lombok.Data;

@Data
public class TransferRequestBankCardBetweenBankNumber {
    private Long bankCard;
    private String bankNumber;
    private Long value;
}
