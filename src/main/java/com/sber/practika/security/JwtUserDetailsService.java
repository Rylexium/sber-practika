package com.sber.practika.security;

import com.sber.practika.entity.Users;
import com.sber.practika.repo.UsersRepository;
import com.sber.practika.security.jwt.JwtUserFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String bankNumber) throws UsernameNotFoundException {
        Users user = usersRepository.findById(bankNumber).orElse(null);

        if (user == null)
            throw new UsernameNotFoundException("User with username: " + bankNumber + " not found");


        return JwtUserFactory.create(user);
    }
}

