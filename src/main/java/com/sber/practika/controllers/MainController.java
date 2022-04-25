package com.sber.practika.controllers;

import com.sber.practika.models.AuthenticationRequest;
import com.sber.practika.security.jwt.JwtTokenProvider;
import com.sber.practika.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class MainController {
    private final AuthorizationService authorizationComponent;
    private final JwtTokenProvider jwtTokenProvider;

    @RequestMapping("/hello")
    public Object authorization() {
        return new HashMap<String, String>() {{put("status", "Hello World!");}};
    }

    @RequestMapping(value = "/authentication", method = RequestMethod.POST)
    public Object createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            UserDetails user = authorizationComponent.login( authenticationRequest.getUsername(),
                                                             authenticationRequest.getPassword());
            if (user == null) throw new UsernameNotFoundException("User with username: " + authenticationRequest.getUsername() + " not found");

            return ResponseEntity.ok(new HashMap<String, String>() {
                {
                    put("status", "ok");
                    put("username", authenticationRequest.getUsername());
                    put("token", jwtTokenProvider.createToken(authenticationRequest.getUsername()));
                }
            });
        } catch (AuthenticationException e) {
            return new HashMap<String, String>() {{put("status", "Invalid username or password");}};
        }
    }
}
