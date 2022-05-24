package com.sber.practika.models.requests.support.confirmCode;

import lombok.Data;

@Data
public class SupportRequestConfirmCodeAndPhone {
    private Long phone;
    private Integer confirmCode;
}
