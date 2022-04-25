package com.sber.practika.security.jwt;

import com.sber.practika.entity.Users;
import com.sber.practika.models.Status;


public final class JwtUserFactory {

    public static JwtUser create(Users user) {
        return new JwtUser(
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getFamily(),
                user.getSalt1(), user.getSalt2(),
                user.getPatronymic(),
                user.getEmail(),
                user.getPhone(),
                user.getEnabled().equals(Status.ACTIVE)
        );
    }
}