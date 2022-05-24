package com.sber.practika.service.transfer.util;

import com.sber.practika.entity.BankCard;
import com.sber.practika.entity.Users;
import com.sber.practika.repo.BankCardRepository;
import com.sber.practika.repo.UsersRepository;
import com.sber.practika.service.transfer.transferException.bankCardException.BankCardNotFoundException;
import com.sber.practika.service.transfer.transferException.bankNumberException.BankNumberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Searcher {
    private final UsersRepository usersRepository;
    private final BankCardRepository bankCardRepository;
    private final RegistrationTransferTransaction registrationTransferTransaction;

    public BankCard searchBankCard(Long bankCard, UUID uuidTransaction) {
        return bankCardRepository.findById(bankCard)
                .orElseThrow(() -> {
                    registrationTransferTransaction.cannotBePerformedDueCard(uuidTransaction);
                    return new BankCardNotFoundException("Карта: " +
                            TransferComponent.beautifulInputBankCard(bankCard.toString()) + " не найдена");
                });
    }

    public Users searchBankNumber(String bankNumber, UUID uuidTransaction) {
         return usersRepository.findById(bankNumber)
                .orElseThrow(() -> {
                    registrationTransferTransaction.cannotBePerformedDueUser(uuidTransaction);
                    return new BankNumberNotFoundException("Банковский счёт: " + bankNumber + " не найден");
                });
    }

    public Users searchPhone(Long phone) {
        return usersRepository.findByPhone(phone)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт по номеру телефона: " + phone + " не найден"));
    }
}
