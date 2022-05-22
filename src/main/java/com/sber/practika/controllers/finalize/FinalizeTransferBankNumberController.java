package com.sber.practika.controllers.finalize;

import com.sber.practika.models.requests.finalize.FinalizeTransferRequestBankCardBetweenBankNumber;
import com.sber.practika.models.requests.finalize.FinalizeTransferRequestBankNumberToBankNumber;
import com.sber.practika.models.requests.transfer.TransferRequestBankCardBetweenBankNumber;
import com.sber.practika.models.requests.transfer.TransferRequestBankNumberBetweenBankNumber;
import com.sber.practika.service.finalizeTransferService.FinalizeTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sber.practika.controllers.transfer.TransferBankCardController.wrapper;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/finalize")
public class FinalizeTransferBankNumberController {
    private final FinalizeTransferService finalizeTransferService;

    @PostMapping(value = "/bankNumber_to_bankNumber")    // с банк.счёта на карту
    public Object bankNumberToBankNumber(@RequestBody FinalizeTransferRequestBankNumberToBankNumber request) {
        return wrapper(() -> finalizeTransferService.bankNumberToBankNumber(
                request.getBankNumber1(),
                request.getBankNumber2(),
                request.getValue(),
                request.getUuidTransaction()));
    }

    @PostMapping(value = "/bankNumber_to_bankCard")    // с банк.счёта на банк.счёт
    public Object bankNumberToBankCard(@RequestBody FinalizeTransferRequestBankCardBetweenBankNumber request) {
        return wrapper(() -> finalizeTransferService.bankNumberToBankCard(
                request.getBankNumber(),
                request.getBankCard(),
                request.getValue(),
                request.getUuidTransaction()));
    }
}
