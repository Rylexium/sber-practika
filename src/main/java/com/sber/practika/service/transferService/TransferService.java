package com.sber.practika.service.transferService;

import com.sber.practika.entity.BankCard;
import com.sber.practika.entity.Users;
import com.sber.practika.repo.BankCardRepository;
import com.sber.practika.repo.UsersRepository;
import com.sber.practika.service.transferService.transferException.BankCardNotFoundException;
import com.sber.practika.service.transferService.transferException.BankNumberNotFoundException;
import com.sber.practika.service.transferService.transferException.InsufficientFundsException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final UsersRepository usersRepository;
    private final BankCardRepository bankCardRepository;

    @Async
    public void bankCardToBankNumber(BigInteger bankCard, String bankNumber, BigInteger value) {
        BankCard card = bankCardRepository.findById(bankCard)
                .orElseThrow(() -> new BankCardNotFoundException("Карта: " + bankCard + " не найдена"));

        Users user = usersRepository.findById(bankNumber)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт: " + bankNumber + " не найден"));

        transferBankCardToBankNumber(value, card, user);
    }

    @Async
    public void bankCardToBankCard(BigInteger bankCard1, BigInteger bankCard2, BigInteger value) {
        BankCard card1 = bankCardRepository.findById(bankCard1)
                .orElseThrow(() -> new BankCardNotFoundException("Карта: " + bankCard1 + " не найдена"));

        BankCard card2 = bankCardRepository.findById(bankCard2)
                .orElseThrow(() -> new BankCardNotFoundException("Карта: " + bankCard2 + " не найдена"));

        if(card1.getBalance().subtract(value).compareTo(BigInteger.ZERO) < 0)
            throw new InsufficientFundsException("Недостаточно денег для перевода с карты");

        card1.setBalance(card1.getBalance().subtract(value)); // убираем
        card2.setBalance(card2.getBalance().add(value));      // добавляем

        bankCardRepository.saveAll(List.of(card1, card2));
    }

    @Async
    public void bankCardToPhone(BigInteger bankCard, BigInteger phone, BigInteger value) {
        BankCard card = bankCardRepository.findById(bankCard)
                .orElseThrow(() -> new BankCardNotFoundException("Карта: " + bankCard + " не найдена"));

        Users user = usersRepository.findByPhone(phone)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт по номеру телефона: " + phone + " не найден"));

        transferBankCardToBankNumber(value, card, user);
    }


    @Async
    public void bankNumberToBankNumber(String bankNumber1, String bankNumber2, BigInteger value) {
        Users user1 = usersRepository.findById(bankNumber1)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт: " + bankNumber1 + " не найден"));

        Users user2 = usersRepository.findById(bankNumber2)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт: " + bankNumber2 + " не найден"));

        transferBankNumberToBankNumber(user1, user2, value);
    }

    @Async
    public void bankNumberToBankCard(String bankNumber, BigInteger bankCard, BigInteger value) {
        Users user = usersRepository.findById(bankNumber)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт: " + bankNumber + " не найден"));

        transferBankCardToBankNumber(bankCard, value, user);
    }

    @Async
    public void bankNumberToPhone(String bankNumber, BigInteger phone, BigInteger value) {
        Users user1 = usersRepository.findById(bankNumber)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт: " + bankNumber + " не найден"));

        Users user2 = usersRepository.findByPhone(phone)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт по номеру телефона: " + phone + " не найден"));

        transferBankNumberToBankNumber(user1, user2, value);
    }


    @Async
    public void phoneToBankNumber(BigInteger phone, String bankNumber, BigInteger value) {
        Users user1 = usersRepository.findByPhone(phone)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт по номеру телефона: " + phone + " не найден"));

        Users user2 = usersRepository.findById(bankNumber)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт: " + bankNumber + " не найден"));

        transferBankNumberToBankNumber(user1, user2, value);
    }

    @Async
    public void phoneToBankCard(BigInteger phone, BigInteger bankCard, BigInteger value) {
        Users user = usersRepository.findByPhone(phone)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт по номеру телефона: " + phone + " не найден"));

        transferBankCardToBankNumber(bankCard, value, user);
    }

    @Async
    public void phoneToPhone(BigInteger phone1, BigInteger phone2, BigInteger value) {
        Users user1 = usersRepository.findByPhone(phone1)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт по номеру телефона: " + phone1 + " не найден"));

        Users user2 = usersRepository.findByPhone(phone2)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт по номеру телефона: " + phone2 + " не найден"));

        transferBankNumberToBankNumber(user1, user2, value);
    }



    private void transferBankNumberToBankNumber(Users user1, Users user2, BigInteger value) {
        if(user1.getBalanceBank().subtract(value).compareTo(BigInteger.ZERO) < 0)
            throw new InsufficientFundsException("Недостаточно денег для перевода с карты");

        user1.setBalanceBank(user1.getBalanceBank().subtract(value)); // убираем
        user2.setBalanceBank(user2.getBalanceBank().add(value));      // добавляем

        usersRepository.saveAll(List.of(user1, user2));
    }

    private void transferBankCardToBankNumber(BigInteger bankCard, BigInteger value, Users user) {
        BankCard card = bankCardRepository.findById(bankCard)
                .orElseThrow(() -> new BankCardNotFoundException("Карта: " + bankCard + " не найдена"));

        if(user.getBalanceBank().subtract(value).compareTo(BigInteger.ZERO) < 0)
            throw new InsufficientFundsException("Недостаточно денег для перевода с карты");

        user.setBalanceBank(user.getBalanceBank().subtract(value)); // убираем
        card.setBalance(card.getBalance().add(value));              // добавляем

        usersRepository.save(user);
        bankCardRepository.save(card);
    }

    private void transferBankCardToBankNumber(BigInteger value, BankCard card, Users user) {
        if(card.getBalance().subtract(value).compareTo(BigInteger.ZERO) < 0)
            throw new InsufficientFundsException("Недостаточно денег для перевода с карты");

        card.setBalance(card.getBalance().subtract(value));    // убираем
        user.setBalanceBank(user.getBalanceBank().add(value)); // добавляем

        usersRepository.save(user);
        bankCardRepository.save(card);
    }

}
