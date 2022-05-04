package com.sber.practika.controllers.support;


import com.sber.practika.service.emailService.EmailSenderService;
import com.sber.practika.service.emailService.emailException.BaseEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("support/password")
public class SupportPasswordController {

    private final EmailSenderService emailSenderService;

    @GetMapping(value = "username")
    public HashMap<String, String> rememberPasswordByUsername(@RequestParam("username") String username){
        return wrapper(() -> emailSenderService.findByUsernameAndSend(username));
    }

    @GetMapping(value = "phone")
    public HashMap<String, String> rememberPasswordByPhone(@RequestParam("phone") BigInteger phone){
        return wrapper(() -> emailSenderService.findByPhoneAndSend(phone));
    }

    @GetMapping(value = "bankCard")
    public HashMap<String, String> rememberPasswordByBankCard(@RequestParam("bankCard") BigInteger bankCard){
        return wrapper(() -> emailSenderService.findByBankCardAndSend(bankCard));
    }

    private HashMap<String, String> wrapper(Runnable runnable){
        try {
            runnable.run();
        } catch (BaseEmailException e) {
            e.printStackTrace();
            return new HashMap<String, String>(){
                {
                    put("status", "error");
                    put("message", e.getMessage());
                }
            };
        }
        return new HashMap<String, String>(){
            {
                put("status", "ok");
                put("message", "Код подтверждения был отправлен Вам на электронную почту");
            }
        };
    }
}
