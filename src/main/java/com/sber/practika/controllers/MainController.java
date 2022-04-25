package com.sber.practika.controllers;

import com.sber.practika.models.AuthenticationRequest;
import com.sber.practika.models.AuthenticationResponse;
import com.sber.practika.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class MainController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtTokenUtil;

    @RequestMapping("/hello")
    public Object authorization() {
        return new HashMap<String, String>() {{put("status", "Hello World!");}};
    }

    @RequestMapping(value = "/authentication", method = RequestMethod.POST)
    public Object createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        //здесь 2 раза ходит в базу
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername()); //тут

            if(userDetails == null) throw new BadCredentialsException("User not found");

            authenticationManager.authenticate( //и тут
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            userDetails.getPassword())
            );
        } catch (BadCredentialsException e){
            return new HashMap<String, String>() {{put("status", "Incorrect username or password");}};
        }
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
