package com.sber.practika.service;

import com.sber.practika.repo.UsersRepository;
import com.sber.practika.util.HashPass;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
    private final UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.sber.practika.entity.Users user = usersRepository.findUsersByUsername(username);
        if(user == null) return null;
        return new User(user.getUsername(),
                HashPass.getHashSha256(user.getPassword(), user.getSalt1(), user.getSalt2()),
                new ArrayList<>());
    }
}

