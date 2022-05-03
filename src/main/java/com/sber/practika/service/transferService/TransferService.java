package com.sber.practika.service.transferService;

import com.sber.practika.entity.BankCard;
import com.sber.practika.entity.Users;
import com.sber.practika.repo.BankCardRepository;
import com.sber.practika.repo.UsersRepository;
import com.sber.practika.service.transferService.transferException.NegativeTransferValue;
import com.sber.practika.service.transferService.transferException.bankCardException.BankCardNotFoundException;
import com.sber.practika.service.transferService.transferException.bankNumberException.BankNumberNotFoundException;
import com.sber.practika.service.transferService.util.TransferComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final UsersRepository usersRepository;
    private final BankCardRepository bankCardRepository;
    private final TransferComponent transferBetween;

    public void bankCardToBankNumber(BigInteger bankCard, String bankNumber, BigInteger value) {
        isPositiveValue(value);

        BankCard card = bankCardRepository.findById(bankCard)
                .orElseThrow(() -> new BankCardNotFoundException("Карта: " + TransferComponent.beautifulInputBankCard(bankCard.toString()) + " не найдена"));

        Users user = usersRepository.findById(bankNumber)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт: " + bankNumber + " не найден"));

        transferBetween.transferBankCardToBankNumber(card, user, value);
    }
    public void bankCardToBankCard(BigInteger bankCard1, BigInteger bankCard2, BigInteger value) {
        isPositiveValue(value);

        BankCard card1 = bankCardRepository.findById(bankCard1)
                .orElseThrow(() -> new BankCardNotFoundException("Карта: " + TransferComponent.beautifulInputBankCard(bankCard1.toString()) + " не найдена"));

        BankCard card2 = bankCardRepository.findById(bankCard2)
                .orElseThrow(() -> new BankCardNotFoundException("Карта: " + TransferComponent.beautifulInputBankCard(bankCard2.toString()) + " не найдена"));

        transferBetween.transferBankCardToBankCard(card1, card2, value);
    }
    public void bankCardToPhone(BigInteger bankCard, BigInteger phone, BigInteger value) {
        isPositiveValue(value);

        BankCard card = bankCardRepository.findById(bankCard)
                .orElseThrow(() -> new BankCardNotFoundException("Карта: " + TransferComponent.beautifulInputBankCard(bankCard.toString()) + " не найдена"));

        Users user = usersRepository.findByPhone(phone)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт по номеру телефона: " + phone + " не найден"));

        transferBetween.transferBankCardToBankNumber(card, user, value);
    }


    public void bankNumberToBankNumber(String bankNumber1, String bankNumber2, BigInteger value) {
        isPositiveValue(value);

        Users user1 = usersRepository.findById(bankNumber1)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт: " + bankNumber1 + " не найден"));

        Users user2 = usersRepository.findById(bankNumber2)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт: " + bankNumber2 + " не найден"));

        transferBetween.transferBankNumberToBankNumber(user1, user2, value);
    }
    public void bankNumberToBankCard(String bankNumber, BigInteger bankCard, BigInteger value) {
        isPositiveValue(value);

        Users user = usersRepository.findById(bankNumber)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт: " + bankNumber + " не найден"));

        BankCard card = bankCardRepository.findById(bankCard)
                .orElseThrow(() -> new BankCardNotFoundException("Карта: " + TransferComponent.beautifulInputBankCard(bankCard.toString()) + " не найдена"));

        transferBetween.transferBankNumberToBankCard(user, card, value);
    }
    public void bankNumberToPhone(String bankNumber, BigInteger phone, BigInteger value) {
        isPositiveValue(value);

        Users user1 = usersRepository.findById(bankNumber)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт: " + bankNumber + " не найден"));

        Users user2 = usersRepository.findByPhone(phone)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт по номеру телефона: " + phone + " не найден"));

        transferBetween.transferBankNumberToBankNumber(user1, user2, value);
    }


    public void phoneToBankNumber(BigInteger phone, String bankNumber, BigInteger value) {
        isPositiveValue(value);

        Users user1 = usersRepository.findByPhone(phone)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт по номеру телефона: " + phone + " не найден"));

        Users user2 = usersRepository.findById(bankNumber)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт: " + bankNumber + " не найден"));

        transferBetween.transferBankNumberToBankNumber(user1, user2, value);
    }
    public void phoneToBankCard(BigInteger phone, BigInteger bankCard, BigInteger value) {
        isPositiveValue(value);

        Users user = usersRepository.findByPhone(phone)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт по номеру телефона: " + phone + " не найден"));

        BankCard card = bankCardRepository.findById(bankCard)
                .orElseThrow(() -> new BankCardNotFoundException("Карта: " + TransferComponent.beautifulInputBankCard(bankCard.toString()) + " не найдена"));

        transferBetween.transferBankCardToBankNumber(card, user, value);
    }
    public void phoneToPhone(BigInteger phone1, BigInteger phone2, BigInteger value) {
        isPositiveValue(value);

        Users user1 = usersRepository.findByPhone(phone1)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт по номеру телефона: " + phone1 + " не найден"));

        Users user2 = usersRepository.findByPhone(phone2)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт по номеру телефона: " + phone2 + " не найден"));

        transferBetween.transferBankNumberToBankNumber(user1, user2, value);
    }

    public static void isPositiveValue(BigInteger value) {
        if(value.signum() != 1)
            throw new NegativeTransferValue("Сумма перевода должна быть положительной");
    }

}
