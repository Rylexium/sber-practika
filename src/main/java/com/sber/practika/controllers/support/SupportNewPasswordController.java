package com.sber.practika.controllers.support;

import com.sber.practika.models.requests.support.newPassword.SupportRequestNewPasswordAndBankCard;
import com.sber.practika.models.requests.support.newPassword.SupportRequestNewPasswordAndPhone;
import com.sber.practika.models.requests.support.newPassword.SupportRequestNewPasswordAndUsername;
import com.sber.practika.service.recoveryPasswordService.RecoveryPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("support/new_password")
public class SupportNewPasswordController {
    private final RecoveryPasswordService recoveryPasswordService;

    @PostMapping(value = "username")
    public HashMap<String, String> rememberPasswordByUsername(@RequestBody SupportRequestNewPasswordAndUsername request){
        recoveryPasswordService.newPasswordByUsername(request.getUsername(), request.getNewPassword());
        return ok();
    }

    @PostMapping(value = "phone")
    public HashMap<String, String> rememberPasswordByPhone(@RequestBody SupportRequestNewPasswordAndPhone request){
        recoveryPasswordService.newPasswordByPhone(request.getPhone(), request.getNewPassword());
        return ok();
    }

    @PostMapping(value = "bankCard")
    public HashMap<String, String> rememberPasswordByBankCard(@RequestBody SupportRequestNewPasswordAndBankCard request){
        recoveryPasswordService.newPasswordByBankCard(request.getBankCard(), request.getNewPassword());
        return ok();
    }

    private HashMap<String, String> ok() {
        return new HashMap<String, String>(){
            {
                put("status", "ok");
                put("message", "Пароль успешно изменён");
            }
        };
    }
}
