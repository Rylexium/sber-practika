package com.sber.practika.models.requests.support.newPassword;

import lombok.Data;

@Data
public class SupportRequestNewPasswordAndUsername {
    private String username;
    private String newPassword;
}
