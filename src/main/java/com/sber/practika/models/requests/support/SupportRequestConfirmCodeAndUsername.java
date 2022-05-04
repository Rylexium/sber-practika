package com.sber.practika.models.requests.support;

import lombok.Data;

@Data
public class SupportRequestConfirmCodeAndUsername {
    private String username;
    private Integer confirmCode;
}
