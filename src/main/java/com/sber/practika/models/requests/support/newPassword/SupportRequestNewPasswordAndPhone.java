package com.sber.practika.models.requests.support.newPassword;

import lombok.Data;

@Data
public class SupportRequestNewPasswordAndPhone {
    private Long phone;
    private String newPassword;
}
