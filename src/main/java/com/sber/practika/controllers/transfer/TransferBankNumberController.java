package com.sber.practika.controllers.transfer;

import com.sber.practika.models.requests.transfer.TransferRequestBankNumberToBankCard;
import com.sber.practika.models.requests.transfer.TransferRequestBankNumberToBankNumber;
import com.sber.practika.models.requests.transfer.TransferRequestBankNumberToPhone;
import com.sber.practika.service.transferService.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sber.practika.controllers.transfer.TransferBankCardController.wrapper;

@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class TransferBankNumberController {
    private final TransferService transferService;

    @PostMapping(value = "/bankNumber_to_bankNumber")    // с банк.счёта на карту
    public Object bankNumberToBankNumber(@RequestBody TransferRequestBankNumberToBankNumber request) {
        return wrapper(() -> transferService.bankNumberToBankNumber(
                request.getBankNumber1(),
                request.getBankNumber2(),
                request.getValue()));
    }

    @PostMapping(value = "/bankNumber_to_bankCard")    // с банк.счёта на банк.счёт
    public Object bankNumberToBankCard(@RequestBody TransferRequestBankNumberToBankCard request) {
        return wrapper(() -> transferService.bankNumberToBankCard(
                request.getBankNumber(),
                request.getBankCard(),
                request.getValue()));
    }

    @PostMapping(value = "/bankNumber_to_phone")    // с банк.счёта на банк.счёт по телефону
    public Object bankNumberToPhone(@RequestBody TransferRequestBankNumberToPhone request) {
        return wrapper(() -> transferService.bankNumberToPhone(
                request.getBankNumber(),
                request.getPhone(),
                request.getValue()));
    }
}
