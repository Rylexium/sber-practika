package com.sber.practika.service.transfer;

import com.sber.practika.entity.BankCard;
import com.sber.practika.entity.Users;
import com.sber.practika.repo.UsersRepository;
import com.sber.practika.service.transfer.transferException.NegativeTransferValueException;
import com.sber.practika.service.transfer.transferException.bankCardException.BankCardsEqualsException;
import com.sber.practika.service.transfer.transferException.bankNumberException.BankNumberNotFoundException;
import com.sber.practika.service.transfer.transferException.bankNumberException.BankNumbersEqualsException;
import com.sber.practika.service.transfer.util.RegistrationTransferTransaction;
import com.sber.practika.service.transfer.util.Searcher;
import com.sber.practika.service.transfer.util.TransferComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final UsersRepository usersRepository;
    private final Searcher searcher;
    private final TransferComponent transferComponent;
    private final RegistrationTransferTransaction registrationTransferTransaction;

    public void bankCardToBankNumber(Long senderBankCardId, String recipientBankNumberId, Long value) {
        isPositiveValue(value);

        UUID uuidTransaction = registrationTransferTransaction.registration(null, recipientBankNumberId,
                senderBankCardId, null, value);

        BankCard senderBankCard = searcher.searchBankCard(senderBankCardId, uuidTransaction);
        Users recipientBankNumber = searcher.searchBankNumber(recipientBankNumberId, uuidTransaction);

        transferComponent.transferBankCardToBankNumber(senderBankCard, recipientBankNumber, value, uuidTransaction);
    }
    public void bankCardToBankCard(Long senderBankCardId, Long recipientBankCardId, Long value) {
        isPositiveValue(value);
        if(senderBankCardId.equals(recipientBankCardId))
            throw new BankCardsEqualsException("Номера карт одинаковые");

        UUID uuidTransaction = registrationTransferTransaction.registration(null, null,
                senderBankCardId, recipientBankCardId, value);

        BankCard senderBankCard = searcher.searchBankCard(senderBankCardId, uuidTransaction);
        BankCard recipientBankCard = searcher.searchBankCard(recipientBankCardId, uuidTransaction);

        transferComponent.transferBankCardToBankCard(senderBankCard, recipientBankCard, value, uuidTransaction);
    }
    public void bankCardToPhone(Long senderBankCardId, Long phone, Long value) {
        isPositiveValue(value);

        Users recipientBankNumber = searcher.searchPhone(phone);

        UUID uuidTransaction = registrationTransferTransaction.registration(null, recipientBankNumber.getBankNumber(),
                senderBankCardId, null, value);

        BankCard senderBankCard = searcher.searchBankCard(senderBankCardId, uuidTransaction);

        transferComponent.transferBankCardToBankNumber(senderBankCard, recipientBankNumber, value, uuidTransaction);
    }


    public void bankNumberToBankNumber(String senderBankNumberId, String recipientBankNumberId, Long value) {
        isPositiveValue(value);

        if(senderBankNumberId.equals(recipientBankNumberId))
            throw new BankCardsEqualsException("Банковские счета одинаковые");

        UUID uuidTransaction = registrationTransferTransaction
                .registration(senderBankNumberId, recipientBankNumberId, null, null, value);

        Users senderBankNumber = searcher.searchBankNumber(senderBankNumberId, uuidTransaction);
        Users recipientBankNumber = searcher.searchBankNumber(recipientBankNumberId, uuidTransaction);

        transferComponent.transferBankNumberToBankNumber(senderBankNumber, recipientBankNumber, value, uuidTransaction);
    }
    public void bankNumberToBankCard(String senderBankNumberId, Long recipientBankCardId, Long value) {
        isPositiveValue(value);

        UUID uuidTransaction = registrationTransferTransaction.registration(senderBankNumberId, null,
                null, recipientBankCardId, value);

        Users senderBankNumber = searcher.searchBankNumber(senderBankNumberId, uuidTransaction);
        BankCard recipientBankCard = searcher.searchBankCard(recipientBankCardId, uuidTransaction);

        transferComponent.transferBankNumberToBankCard(senderBankNumber, recipientBankCard, value, uuidTransaction);
    }
    public void bankNumberToPhone(String senderBankNumberId, Long phone, Long value) {
        isPositiveValue(value);

        Users senderBankNumber = usersRepository.findById(senderBankNumberId)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт: " + senderBankNumberId + " не найден"));

        if(senderBankNumber.getPhone().equals(phone))
            throw new BankNumbersEqualsException("Одинаковые банковские счета");

        Users recipientBankNumber = usersRepository.findByPhone(phone)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт по номеру телефона: " + phone + " не найден"));

        UUID uuidTransaction = registrationTransferTransaction
                .registration(senderBankNumber.getBankNumber(), recipientBankNumber.getBankNumber(), null, null, value);

        transferComponent.transferBankNumberToBankNumber(senderBankNumber, recipientBankNumber, value, uuidTransaction);
    }


    public void phoneToBankNumber(Long phone, String recipientBankNumberId, Long value) {
        isPositiveValue(value);

        Users senderBankNumber = usersRepository.findByPhone(phone)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт по номеру телефона: " + phone + " не найден"));

        if(senderBankNumber.getBankNumber().equals(recipientBankNumberId))
            throw new BankNumbersEqualsException("Одинаковые банковские счёта");

        UUID uuidTransaction = registrationTransferTransaction
                .registration(senderBankNumber.getBankNumber(), recipientBankNumberId, null, null, value);

        Users recipientBankNumber = usersRepository.findById(recipientBankNumberId)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт: " + recipientBankNumberId + " не найден"));

        transferComponent.transferBankNumberToBankNumber(senderBankNumber, recipientBankNumber, value, uuidTransaction);
    }
    public void phoneToBankCard(Long phone, Long recipientBankCardId, Long value) {
        isPositiveValue(value);

        Users senderBankNumber = searcher.searchPhone(phone);

        UUID uuidTransaction = registrationTransferTransaction.registration(senderBankNumber.getBankNumber(), null,
                null, recipientBankCardId, value);

        BankCard recipientBankCard = searcher.searchBankCard(recipientBankCardId, uuidTransaction);

        transferComponent.transferBankNumberToBankCard(senderBankNumber, recipientBankCard, value, uuidTransaction);
    }
    public void phoneToPhone(Long phone1, Long phone2, Long value) {
        isPositiveValue(value);

        if(phone1.equals(phone2))
            throw new BankNumbersEqualsException("Одинаковые банковские счета");

        Users senderBankNumber = searcher.searchPhone(phone1);
        Users recipientBankNumber = searcher.searchPhone(phone2);

        UUID uuidTransaction = registrationTransferTransaction
                .registration(senderBankNumber.getBankNumber(), recipientBankNumber.getBankNumber(), null, null, value);

        transferComponent.transferBankNumberToBankNumber(senderBankNumber, recipientBankNumber, value, uuidTransaction);
    }

    public static void isPositiveValue(Long value) {
        if(value <= 0) {
            throw new NegativeTransferValueException("Сумма перевода должна быть положительной");
        }
    }

}
