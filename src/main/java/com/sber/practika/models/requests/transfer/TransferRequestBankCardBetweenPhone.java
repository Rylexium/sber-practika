package com.sber.practika.models.requests.transfer;

import lombok.Data;

@Data
public class TransferRequestBankCardBetweenPhone {
    private Long bankCard;
    private Long phone;
    private Long value;
}
