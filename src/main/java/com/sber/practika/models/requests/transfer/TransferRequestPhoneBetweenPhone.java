package com.sber.practika.models.requests.transfer;

import lombok.Data;

@Data
public class TransferRequestPhoneBetweenPhone {
    private Long phone1;
    private Long phone2;
    private Long value;
}
