package com.sber.practika.models;

import lombok.Data;

@Data //auto generat getters and setters
public class AuthenticationRequest {
    private String username;
    private String password;
}