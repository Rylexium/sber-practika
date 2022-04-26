package com.sber.practika.service;

import com.sber.practika.entity.Users;
import com.sber.practika.repo.UsersRepository;
import com.sber.practika.security.jwt.JwtUserFactory;
import com.sber.practika.util.HashPass;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final UsersRepository usersRepository;
    public UserDetails login(BigInteger phone, String password) throws UsernameNotFoundException {
        Users user = usersRepository.findById(phone).orElse(null);

        if (user == null)
            throw new UsernameNotFoundException("User with username: " + phone + " not found");

        if(user.getPassword().equals(HashPass.getHashSha256(password, user.getSalt1(), user.getSalt2())))
            return JwtUserFactory.create(user);
        else
            throw new UsernameNotFoundException("Invalid login or password");
    }
}
