package com.sber.practika.models.requests.support.confirmCode;

import lombok.Data;

@Data
public class SupportRequestConfirmCodeAndUsername {
    private String username;
    private Integer confirmCode;
}
