package com.sber.practika.controllers.finalize;

import com.sber.practika.models.requests.finalize.FinalizeTransferRequestBankCardBetweenBankNumber;
import com.sber.practika.models.requests.finalize.FinalizeTransferRequestBankCardToBankCard;
import com.sber.practika.service.finalize.FinalizeTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sber.practika.controllers.transfer.TransferBankCardController.wrapper;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/finalize")
public class FinalizeTransferBankCardController {
    private final FinalizeTransferService finalizeTransferService;

    @PostMapping(value = "/bankCard_to_bankNumber")     // с карты на банк.счёт
    public Object bankCardToBankNumber(@RequestBody FinalizeTransferRequestBankCardBetweenBankNumber request) {
        return wrapper(() -> finalizeTransferService.bankCardToBankNumber(
                request.getBankCard(),
                request.getBankNumber(),
                request.getValue(),
                request.getUuidTransaction()));
    }

    @PostMapping(value = "/bankCard_to_bankCard")    // с карты на карту
    public Object bankCardToBankCard(@RequestBody FinalizeTransferRequestBankCardToBankCard request) {
        return wrapper(() -> finalizeTransferService.bankCardToBankCard(
                request.getBankCard1(),
                request.getBankCard2(),
                request.getValue(),
                request.getUuidTransaction()));
    }
}
