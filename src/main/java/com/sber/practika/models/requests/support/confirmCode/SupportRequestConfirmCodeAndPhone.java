package com.sber.practika.models.requests.support.confirmCode;

import lombok.Data;

import java.math.BigInteger;
@Data
public class SupportRequestConfirmCodeAndPhone {
    private BigInteger phone;
    private Integer confirmCode;
}
