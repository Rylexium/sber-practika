package com.sber.practika.models.requests.transfer;

import lombok.Data;


@Data
public class TransferRequestBankCardBetweenBankCard {
    private Long bankCard1;
    private Long bankCard2;
    private Long value;
}
