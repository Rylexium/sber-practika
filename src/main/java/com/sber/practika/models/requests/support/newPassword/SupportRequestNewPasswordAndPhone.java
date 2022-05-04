package com.sber.practika.models.requests.support.newPassword;

import lombok.Data;

import java.math.BigInteger;

@Data
public class SupportRequestNewPasswordAndPhone {
    private BigInteger phone;
    private String newPassword;
}
