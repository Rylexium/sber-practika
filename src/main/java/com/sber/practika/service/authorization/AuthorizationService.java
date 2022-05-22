package com.sber.practika.service.authorization;

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

    public UserDetails logInByPhone(BigInteger phone, String password) throws UsernameNotFoundException {
        Users user = usersRepository.findByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException("User with phone: " + phone + " not found"));

        if(user.getPassword().equals(HashPass.getHashSha256(password, user.getSalt1(), user.getSalt2())))
            return JwtUserFactory.create(user);
        else
            throw new UsernameNotFoundException("Invalid phone or password");
    }

    public UserDetails logInByUsername(String username, String password) throws UsernameNotFoundException {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));

        if(user.getPassword().equals(HashPass.getHashSha256(password, user.getSalt1(), user.getSalt2())))
            return JwtUserFactory.create(user);
        else
            throw new UsernameNotFoundException("Invalid username or password");
    }

    public UserDetails logInByBankCard(BigInteger bankCard, String password) throws UsernameNotFoundException {
        Users user = usersRepository.findByCardNumber(bankCard)
                .orElseThrow(() -> new UsernameNotFoundException("User with bankCard: " + bankCard + " not found"));

        if(user.getPassword().equals(HashPass.getHashSha256(password, user.getSalt1(), user.getSalt2())))
            return JwtUserFactory.create(user);
        else
            throw new UsernameNotFoundException("Invalid bankCard or password");
    }
}
