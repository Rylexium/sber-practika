package com.sber.practika.controllers.transaction;

import com.sber.practika.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping(value = "/bankNumber")     // с карты на банк.счёт
    public Object bankCardToBankNumber(@RequestParam("id") String bankNumber) {
        return new HashMap<String, Object>() {
            {
                put("transactions", transactionService.allTransactionsByBankNumber(bankNumber));
            }
        };
    }

    @GetMapping(value = "/bankCard")     // с карты на банк.счёт
    public Object bankCardToBankNumber(@RequestParam("id") BigInteger bankCard) {
        return new HashMap<String, Object>() {
            {
                put("transactions", transactionService.allTransactionsByBankCard(bankCard));
            }
        };
    }

}
