package com.sber.practika.models;

import lombok.Data;

@Data
public class AuthenticationRequestUsername {
    private String username;
    private String password;
}
