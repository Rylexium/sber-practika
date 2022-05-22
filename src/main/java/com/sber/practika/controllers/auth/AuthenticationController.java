package com.sber.practika.controllers.auth;

import com.sber.practika.models.requests.authentication.AuthenticationRequestBankCard;
import com.sber.practika.models.requests.authentication.AuthenticationRequestPhone;
import com.sber.practika.models.requests.authentication.AuthenticationRequestUsername;
import com.sber.practika.security.jwt.JwtTokenProvider;
import com.sber.practika.service.authorization.AuthorizationService;
import com.sber.practika.service.userInfoService.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class AuthenticationController {
    private final UserInfoService userInfoService;
    private final AuthorizationService authorizationComponent;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.token.expired}")
    private Long expired; //отсылаем клиенту, чтобы знал сколько действует

    @RequestMapping("/hello")
    public Object authorization() {
        return new HashMap<String, String>() {
            {
                put("status", "Hello World!");
            }
        };
    }

    @PostMapping(value = "/authentication/phone")
    public Object authenticationByPhone(@RequestBody AuthenticationRequestPhone authenticationRequest) {
        try {
            UserDetails user = authorizationComponent.logInByPhone( authenticationRequest.getPhone(),
                                                             authenticationRequest.getPassword());
            if (user == null)
                throw new UsernameNotFoundException("User with phone: " + authenticationRequest.getPhone() + " not found");

            return ResponseEntity.ok(new HashMap<String, Object>() {
                {
                    put("status", "ok");
                    put("token", jwtTokenProvider.createToken(authenticationRequest.getPhone().toString()));
                    put("body", userInfoService.selectInfoByPhone(authenticationRequest.getPhone()));
                    put("expired", expired);
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return error();
        }
    }

    @PostMapping(value = "/authentication/username")
    public Object authenticationByUsername(@RequestBody AuthenticationRequestUsername authenticationRequest) {
        try {
            UserDetails user = authorizationComponent.logInByUsername(  authenticationRequest.getUsername(),
                                                                        authenticationRequest.getPassword());

            if (user == null)
                throw new UsernameNotFoundException("User with username: " + authenticationRequest.getUsername() + " not found");

            return ResponseEntity.ok(new HashMap<String, Object>() {
                {
                    put("status", "ok");
                    put("token", jwtTokenProvider.createToken(authenticationRequest.getUsername()));
                    put("body", userInfoService.selectInfoByUsername(authenticationRequest.getUsername()));
                    put("expired", expired);
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return error();
        }
    }

    @PostMapping(value = "/authentication/bankCard")
    public Object authenticationByBankCard(@RequestBody AuthenticationRequestBankCard authenticationRequest) {
        try {
            UserDetails user = authorizationComponent.logInByBankCard(  authenticationRequest.getBankCard(),
                    authenticationRequest.getPassword());

            if (user == null)
                throw new UsernameNotFoundException("User with bankCard: " + authenticationRequest.getBankCard() + " not found");

            return ResponseEntity.ok(new HashMap<String, Object>() {
                {
                    put("status", "ok");
                    put("token", jwtTokenProvider.createToken(String.valueOf(authenticationRequest.getBankCard())));
                    put("body", userInfoService.selectInfoByBankCard(authenticationRequest.getBankCard()));
                    put("expired", expired);
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return error();
        }
    }

    private HashMap<String, String> error() { return new HashMap<String, String>() {{ put("status", "Invalid date or password");}}; }
}
