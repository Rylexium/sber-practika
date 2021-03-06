package com.sber.practika.security;

import com.sber.practika.entity.Users;
import com.sber.practika.repo.UsersRepository;
import com.sber.practika.security.jwt.JwtUserFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;


@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String param) throws UsernameNotFoundException {
        Users user;
        try { //проверяем по username
            user = usersRepository.findByUsername(param).orElse(null);
            if (user == null)
                throw new UsernameNotFoundException("User with username: " + param + " not found");
        }catch(Exception e) {
            if (param.length() == 11) { // поиск по телефону
                user = usersRepository.findByPhone(Long.valueOf(param)).orElse(null);
                if (user == null)
                    throw new UsernameNotFoundException("User with phone: " + param + " not found");
            }
            else if(param.length() == 16) { // поиск по банковской карте
                user = usersRepository.findByCardNumber(Long.valueOf(param)).orElse(null);
                if (user == null)
                    throw new UsernameNotFoundException("User with bankCard: " + param + " not found");
            }
            else throw new UsernameNotFoundException("Invalid param : " + param);
        }

        return JwtUserFactory.create(user);
    }
}

