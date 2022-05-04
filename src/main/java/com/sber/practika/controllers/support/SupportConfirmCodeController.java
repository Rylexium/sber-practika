package com.sber.practika.controllers.support;

import com.sber.practika.models.requests.support.confirmCode.SupportRequestConfirmCodeAndBankCard;
import com.sber.practika.models.requests.support.confirmCode.SupportRequestConfirmCodeAndPhone;
import com.sber.practika.models.requests.support.confirmCode.SupportRequestConfirmCodeAndUsername;
import com.sber.practika.security.jwt.JwtTokenProvider;
import com.sber.practika.service.recoveryPasswordService.RecoveryPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RequiredArgsConstructor
@RestController
@RequestMapping("confirm_code")
public class SupportConfirmCodeController {
    private final RecoveryPasswordService recoveryPasswordService;
    private final JwtTokenProvider jwtTokenProvider;

    //Код подтверждения приходит
    @PostMapping(value = "username")
    public HashMap<String, String> isEqualsConfirmCodeAndUsername(@RequestBody SupportRequestConfirmCodeAndUsername request) {
        if(recoveryPasswordService.isTrueConfirmCodeByUsername(request.getUsername(), request.getConfirmCode()))
            return new HashMap<String, String>(){
                {
                    put("status", "ok");
                    put("token", jwtTokenProvider.createToken(request.getUsername().toString()));
                }
            };
        return error();
    }

    @PostMapping(value = "phone")
    public HashMap<String, String> isEqualsConfirmCodeAndPhone(@RequestBody SupportRequestConfirmCodeAndPhone request) {
        if(recoveryPasswordService.isTrueConfirmCodeByPhone(request.getPhone(), request.getConfirmCode()))
            return new HashMap<String, String>(){
                {
                    put("status", "ok");
                    put("token", jwtTokenProvider.createToken(request.getPhone().toString()));
                }
            };
        return error();
    }

    @PostMapping(value = "bankCard")
    public HashMap<String, String> isEqualsConfirmCodeAndBankCard(@RequestBody SupportRequestConfirmCodeAndBankCard request) {
        if(recoveryPasswordService.isTrueConfirmCodeByBankCard(request.getBankCard(), request.getConfirmCode()))
            return new HashMap<String, String>(){
                {
                    put("status", "ok");
                    put("token", jwtTokenProvider.createToken(request.getBankCard().toString()));
                }
            };
        return error();
    }

    private HashMap<String, String> error() { return new HashMap<String, String>(){{put("status", "error");}}; }
}
