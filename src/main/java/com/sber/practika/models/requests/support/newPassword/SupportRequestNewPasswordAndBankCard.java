package com.sber.practika.models.requests.support.newPassword;

import lombok.Data;

@Data
public class SupportRequestNewPasswordAndBankCard {
    private Long bankCard;
    private String newPassword;
}
