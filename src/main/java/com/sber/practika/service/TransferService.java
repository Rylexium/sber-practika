package com.sber.practika.service;

import com.sber.practika.entity.BankCard;
import com.sber.practika.entity.Users;
import com.sber.practika.repo.BankCardRepository;
import com.sber.practika.repo.UsersRepository;
import com.sber.practika.service.TransferException.BankCardNotFoundException;
import com.sber.practika.service.TransferException.BankNumberNotFoundException;
import com.sber.practika.service.TransferException.InsufficientFundsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final UsersRepository usersRepository;
    private final BankCardRepository bankCardRepository;

    public void bankCardToBankNumber(BigInteger bankCard, String bankNumber, BigInteger value) throws UsernameNotFoundException {
        BankCard card = bankCardRepository.findById(bankCard)
                .orElseThrow(() -> new BankCardNotFoundException("Карта: " + bankCard + " не найдена"));

        Users user = usersRepository.findById(bankNumber)
                .orElseThrow(() -> new BankNumberNotFoundException("Банковский счёт: " + bankNumber + " не найден"));

        if(card.getBalance().subtract(value).compareTo(BigInteger.ZERO) < 0)
            throw new InsufficientFundsException("Недостаточно денег для перевода с карты");

        card.setBalance(card.getBalance().subtract(value));
        user.setBalanceBank(user.getBalanceBank().add(value));

        usersRepository.save(user);
        bankCardRepository.save(card);
    }

    public void bankCardToBankCard(BigInteger bankCard1, BigInteger bankCard2, BigInteger value) {

    }

    public void bankNumberToBankNumber(String bankNumber1, String bankNumber2, BigInteger value) {

    }

    public void bankNumberToBankCard(String bankNumber, BigInteger bankCard, BigInteger value) {

    }
}
