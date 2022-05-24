package com.sber.practika.models.requests.support.confirmCode;

import lombok.Data;

@Data
public class SupportRequestConfirmCodeAndBankCard {
    private Long bankCard;
    private Integer confirmCode;
}
