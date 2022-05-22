package com.sber.practika.service.transferService;

import com.sber.practika.entity.BankCard;
import com.sber.practika.entity.Users;
import com.sber.practika.repo.BankCardRepository;
import com.sber.practika.repo.UsersRepository;
import com.sber.practika.service.transferService.transferException.NegativeTransferValueException;
import com.sber.practika.service.transferService.transferException.bankCardException.BankCardNotFoundException;
import com.sber.practika.service.transferService.transferException.bankCardException.BankCardsEqualsException;
import com.sber.practika.service.transferService.transferException.bankNumberException.BankNumberNotFoundException;
import com.sber.practika.service.transferService.transferException.bankNumberException.BankNumbersEqualsException;
import com.sber.practika.service.transferService.util.RegistrationTransferTransaction;
import com.sber.practika.service.transferService.util.Searcher;
import com.sber.practika.service.transferService.util.TransferComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final UsersRepository usersRepository;
    private final Searcher searcher;
    private final TransferComponent transferComponent;
    private final RegistrationTransferTransaction registrationTransferTransaction;

    public void bankCardToBankNumber(BigInteger bankCard, String bankNumber, BigInteger value) {
        isPositiveValue(value);

        UUID uuidTransaction = registrationTransferTransaction.registration(null, bankNumber,
                bankCard, null, value);

        BankCard card = searcher.searchBankCard(bankCard, uuidTransaction);
        Users user = searcher.searchBankNumber(bankNumber, uuidTransaction);

        transferComponent.transferBankCardToBankNumber(card, user, value, uuidTransaction);
    }
    public void bankCardToBankCard(BigInteger bankCard1, BigInteger bankCard2, BigInteger value) {
        isPositiveValue(value);
        if(bankCard1.equals(bankCard2))
            throw new BankCardsEqualsException("Номера карт одинаковые");

        UUID uuidTransaction = registrationTransferTransaction.registration(null, null,
                bankCard1, bankCard2, value);

        BankCard card1 = searcher.searchBankCard(bankCard1, uuidTransaction);
        BankCard card2 = searcher.searchBankCard(bankCard2, uuidTransaction);

        transferComponent.transferBankCardToBankCard(card1, card2, value, uuidTransaction);
    }
    public void bankCardToPhone(BigInteger bankCard, BigInteger phone, BigInteger value) {
        isPositiveValue(value);

        Users user = searcher.searchPhone(phone);

        UUID uuidTransaction = registrationTransferTransaction.registration(null, user.getBankNumber(),
                bankCard, null, value);

        BankCard card = searcher.searchBankCard(bankCard, uuidTransaction);

        transferComponent.transferBankCardToBankNumber(card, user, value, uuidTransaction);
    }


    public void bankNumberToBankNumber(String bankNumber1, String bankNumber2, BigInteger value) {
        isPositiveValue(value);

        if(bankNumber1.equals(bankNumber2))
            throw new BankCardsEqualsException("Банковские счета одинаковые");

        UUID uuidTransaction = registrationTransferTransaction
                .registration(bankNumber1, bankNumber2, null, null, value);

        Users user1 = searcher.searchBankNumber(bankNumber1, uuidTransaction);
        Users user2 = searcher.searchBankNumber(bankNumber2, uuidTransaction);

        transferComponent.transferBankNumberToBankNumber(user1, user2, value, uuidTransaction);
    }
    public void bankNumberToBankCard(String bankNumber, BigInteger bankCard, BigInteger value) {
        isPositiveValue(value);

        UUID uuidTransaction = registrationTransferTransaction.registration(bankNumber, null,
                null, bankCard, value);

        Users user = searcher.searchBankNumber(bankNumber, uuidTransaction);
        BankCard card = searcher.searchBankCard(bankCard, uuidTransaction);

        transferComponent.transferBankNumberToBankCard(user, card, value, uuidTransaction);
    }
    public void bankNumberToPhone(String bankNumber, BigInteger phone, BigInteger value) {
        isPositiveValue(value);

        Users user1 = usersRepository.findById(bankNumber)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт: " + bankNumber + " не найден"));

        if(user1.getPhone().equals(phone))
            throw new BankNumbersEqualsException("Одинаковые банковские счета");

        Users user2 = usersRepository.findByPhone(phone)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт по номеру телефона: " + phone + " не найден"));

        UUID uuidTransaction = registrationTransferTransaction
                .registration(user1.getBankNumber(), user2.getBankNumber(), null, null, value);

        transferComponent.transferBankNumberToBankNumber(user1, user2, value, uuidTransaction);
    }


    public void phoneToBankNumber(BigInteger phone, String bankNumber, BigInteger value) {
        isPositiveValue(value);

        Users user1 = usersRepository.findByPhone(phone)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт по номеру телефона: " + phone + " не найден"));

        if(user1.getBankNumber().equals(bankNumber))
            throw new BankNumbersEqualsException("Одинаковые банковские счёта");

        UUID uuidTransaction = registrationTransferTransaction
                .registration(user1.getBankNumber(), bankNumber, null, null, value);

        Users user2 = usersRepository.findById(bankNumber)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт: " + bankNumber + " не найден"));

        transferComponent.transferBankNumberToBankNumber(user1, user2, value, uuidTransaction);
    }
    public void phoneToBankCard(BigInteger phone, BigInteger bankCard, BigInteger value) {
        isPositiveValue(value);

        Users user = searcher.searchPhone(phone);

        UUID uuidTransaction = registrationTransferTransaction.registration(null, user.getBankNumber(),
                bankCard, null, value);

        BankCard card = searcher.searchBankCard(bankCard, uuidTransaction);

        transferComponent.transferBankCardToBankNumber(card, user, value, uuidTransaction);
    }
    public void phoneToPhone(BigInteger phone1, BigInteger phone2, BigInteger value) {
        isPositiveValue(value);

        if(phone1.equals(phone2))
            throw new BankNumbersEqualsException("Одинаковые банковские счета");

        Users user1 = searcher.searchPhone(phone1);
        Users user2 = searcher.searchPhone(phone2);

        UUID uuidTransaction = registrationTransferTransaction
                .registration(user1.getBankNumber(), user2.getBankNumber(), null, null, value);

        transferComponent.transferBankNumberToBankNumber(user1, user2, value, uuidTransaction);
    }

    public static void isPositiveValue(BigInteger value) {
        if(value.signum() != 1) {
            throw new NegativeTransferValueException("Сумма перевода должна быть положительной");
        }
    }

}
