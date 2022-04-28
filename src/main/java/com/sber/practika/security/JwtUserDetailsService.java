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
        try {
            user = usersRepository.findByUsername(param).orElse(null);
            if (user == null)
                throw new UsernameNotFoundException("User with username: " + param + " not found");
        }catch(Exception e) {
            if(param.length() != 11) throw new UsernameNotFoundException("Invalid param : " + param);
            user = usersRepository.findByPhone(new BigInteger(param)).orElse(null);
            if (user == null)
                throw new UsernameNotFoundException("User with phone: " + param + " not found");
        }

        return JwtUserFactory.create(user);
    }
}

