package com.sber.practika.models.requests.transfer;

import lombok.Data;

@Data
public class TransferRequestBankNumberBetweenPhone {
    private String bankNumber;
    private Long phone;
    private Long value;
}
