package com.sber.practika.controllers;

import com.sber.practika.models.AuthenticationRequestPhone;
import com.sber.practika.models.AuthenticationRequestUsername;
import com.sber.practika.security.jwt.JwtTokenProvider;
import com.sber.practika.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class AuthenticationController {
    private final AuthorizationService authorizationComponent;
    private final JwtTokenProvider jwtTokenProvider;

    @RequestMapping("/hello")
    public Object authorization() {
        return new HashMap<String, String>() {
            {
                put("status", "Hello World!");
            }
        };
    }

    @RequestMapping(value = "/authentication/phone", method = RequestMethod.POST)
    public Object authenticationPhone(@RequestBody AuthenticationRequestPhone authenticationRequest) {
        try {
            UserDetails user = authorizationComponent.logInByPhone( authenticationRequest.getPhone(),
                                                             authenticationRequest.getPassword());
            if (user == null)
                throw new UsernameNotFoundException("User with username: " + authenticationRequest.getPhone() + " not found");

            return ResponseEntity.ok(new HashMap<String, String>() {
                {
                    put("status", "ok");
                    put("phone", authenticationRequest.getPhone().toString());
                    put("token", jwtTokenProvider.createToken(authenticationRequest.getPhone().toString()));
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new HashMap<String, String>() {{put("status", "Invalid username or password");}};
        }
    }

    @RequestMapping(value = "/authentication/username", method = RequestMethod.POST)
    public Object authenticationUsername(@RequestBody AuthenticationRequestUsername authenticationRequest) {
        try {
            UserDetails user = authorizationComponent.logInByUsername(  authenticationRequest.getUsername(),
                                                                        authenticationRequest.getPassword());

            if (user == null)
                throw new UsernameNotFoundException("User with username: " + authenticationRequest.getUsername() + " not found");

            return ResponseEntity.ok(new HashMap<String, String>() {
                {
                    put("status", "ok");
                    put("username", authenticationRequest.getUsername());
                    put("token", jwtTokenProvider.createToken(authenticationRequest.getUsername()));
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new HashMap<String, String>() {{put("status", "Invalid username or password");}};
        }
    }
}
