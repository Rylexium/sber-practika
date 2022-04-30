package com.sber.practika.models.requests.authentication;

import lombok.Data;

@Data
public class AuthenticationRequestUsername {
    private String username;
    private String password;
}
