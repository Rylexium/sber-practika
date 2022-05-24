package com.sber.practika.models.requests.transfer;

import lombok.Data;

@Data
public class TransferRequestBankNumberBetweenBankNumber {
    private String bankNumber1;
    private String bankNumber2;
    private Long value;
}
