package com.sber.practika.controllers.transfer;

import com.sber.practika.models.requests.transfer.TransferRequestBankCardBetweenBankCard;
import com.sber.practika.models.requests.transfer.TransferRequestBankCardBetweenBankNumber;
import com.sber.practika.models.requests.transfer.TransferRequestBankCardBetweenPhone;
import com.sber.practika.service.transferService.transferException.TransferBaseException;
import com.sber.practika.service.transferService.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class TransferBankCardController {
    private final TransferService transferService;

    @PostMapping(value = "/bankCard_to_bankNumber")     // с карты на банк.счёт
    public Object bankCardToBankNumber(@RequestBody TransferRequestBankCardBetweenBankNumber request) {
        return wrapper(() -> transferService.bankCardToBankNumber(
                request.getBankCard(),
                request.getBankNumber(),
                request.getValue()));
    }

    @PostMapping(value = "/bankCard_to_bankCard")    // с карты на карту
    public Object bankCardToBankCard(@RequestBody TransferRequestBankCardBetweenBankCard request) {
        return wrapper(() -> transferService.bankCardToBankCard(
                request.getBankCard1(),
                request.getBankCard2(),
                request.getValue()));
    }

    @PostMapping(value = "/bankCard_to_phone")    // с карты на карту
    public Object bankCardToPhone(@RequestBody TransferRequestBankCardBetweenPhone request) {
        return wrapper(() -> transferService.bankCardToPhone(
                request.getBankCard(),
                request.getPhone(),
                request.getValue()));
    }



    public static HashMap<String, String> wrapper(Runnable runnable){
        try {
            runnable.run();
            return new HashMap<String, String>() {
                {
                    put("status", "ok");
                    put("message", "successful");
                }
            };
        } catch (TransferBaseException e){
            return new HashMap<String, String>() {
                {
                    put("status", "error");
                    put("message", e.getMessage());
                }
            };
        }
    }

}
