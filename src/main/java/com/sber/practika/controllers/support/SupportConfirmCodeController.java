package com.sber.practika.controllers.support;

import com.sber.practika.models.requests.support.SupportRequestConfirmCodeAndBankCard;
import com.sber.practika.models.requests.support.SupportRequestConfirmCodeAndPhone;
import com.sber.practika.models.requests.support.SupportRequestConfirmCodeAndUsername;
import com.sber.practika.service.emailService.EmailSenderService;
import com.sber.practika.service.emailService.emailException.BaseEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RequiredArgsConstructor
@RestController
@RequestMapping("confirm_code")
public class SupportConfirmCodeController {
    private final EmailSenderService emailSenderService;

    //Код подтверждения приходит
    @PostMapping(value = "username")
    public HashMap<String, String> isEqualsConfirmCodeAndUsername(@RequestBody SupportRequestConfirmCodeAndUsername request) {
        if(emailSenderService.isTrueConfirmCodeByUsername(request.getUsername(), request.getConfirmCode()))
            return new HashMap<String, String>(){{put("status", "ok");}};
        return new HashMap<String, String>(){{put("status", "error");}};
    }

    @PostMapping(value = "phone")
    public HashMap<String, String> isEqualsConfirmCodeAndPhone(@RequestBody SupportRequestConfirmCodeAndPhone request) {
        if(emailSenderService.isTrueConfirmCodeByPhone(request.getPhone(), request.getConfirmCode()))
            return new HashMap<String, String>(){{put("status", "ok");}};
        return new HashMap<String, String>(){{put("status", "error");}};
    }

    @PostMapping(value = "bankCard")
    public HashMap<String, String> isEqualsConfirmCodeAndBankCard(@RequestBody SupportRequestConfirmCodeAndBankCard request) {
        if(emailSenderService.isTrueConfirmCodeByBankCard(request.getBankCard(), request.getConfirmCode()))
            return new HashMap<String, String>(){{put("status", "ok");}};
        return new HashMap<String, String>(){{put("status", "error");}};
    }

    //Новый пароль
}
