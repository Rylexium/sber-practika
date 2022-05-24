package com.sber.practika.controllers.transfer;

import com.sber.practika.models.requests.transfer.*;
import com.sber.practika.service.transfer.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import static com.sber.practika.controllers.transfer.TransferBankCardController.wrapper;

@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class TransferPhoneController {
    private final TransferService transferService;

    @PostMapping(value = "/phone_to_bankNumber")     // с карты на банк.счёт
    public HashMap<String, String> phoneToBankNumber(@RequestBody TransferRequestBankNumberBetweenPhone request) {
        return wrapper(() -> transferService.phoneToBankNumber(
                request.getPhone(),
                request.getBankNumber(),
                request.getValue()));
    }

    @PostMapping(value = "/phone_to_bankCard")    // с карты на карту
    public HashMap<String, String> phoneToBankCard(@RequestBody TransferRequestBankCardBetweenPhone request) {
        return wrapper(() -> transferService.phoneToBankCard(
                request.getPhone(),
                request.getBankCard(),
                request.getValue()));
    }

    @PostMapping(value = "/phone_to_phone")    // с карты на карту
    public HashMap<String, String> phoneToPhone(@RequestBody TransferRequestPhoneBetweenPhone request) {
        return wrapper(() -> transferService.phoneToPhone(
                request.getPhone1(),
                request.getPhone2(),
                request.getValue()));
    }
}
