package com.sber.practika.models.requests;

import lombok.Data;

@Data
public class AuthenticationRequestUsername {
    private String username;
    private String password;
}
