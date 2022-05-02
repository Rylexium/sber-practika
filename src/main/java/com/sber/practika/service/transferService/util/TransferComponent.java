package com.sber.practika.service.transferService.util;

import com.sber.practika.entity.BankCard;
import com.sber.practika.entity.Users;
import com.sber.practika.models.Status;
import com.sber.practika.repo.BankCardRepository;
import com.sber.practika.repo.UsersRepository;
import com.sber.practika.service.transferService.transferException.InsufficientFundsException;
import com.sber.practika.service.transferService.transferException.bankCardException.BankCardExpiredDate;
import com.sber.practika.service.transferService.transferException.bankNumberException.BankNumberDeleted;
import com.sber.practika.service.transferService.transferException.bankNumberException.BankNumberNotActive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class TransferComponent {
    private final UsersRepository usersRepository;
    private final BankCardRepository bankCardRepository;

    private void checkStatusUser(Users user){
        if(user.getEnabled() == Status.DELETED)
            throw new BankNumberDeleted("Банковского счёта : " + user.getBankNumber() + " не существует");
        else if(user.getEnabled() == Status.NOT_ACTIVE)
            throw new BankNumberNotActive("Банковский счёт : " + user.getBankNumber() + " не активирован");
    }

    public static String beautifulInputBankCard(String idCard) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < idCard.length(); i++){
            res.append(idCard.charAt(i));
            if( (i+1) % 4 == 0) res.append(" ");
        }
        return res.toString();
    }
    private void isPossibleWork(BankCard card){
        checkStatusCard(card);
        isValidData(card);
    }
    private void checkStatusCard(BankCard card) {
        if(card.getEnabled() == Status.DELETED)
            throw new BankNumberDeleted("Карта : " + beautifulInputBankCard(card.getId().toString()) + "не существует");
        else if(card.getEnabled() == Status.NOT_ACTIVE)
            throw new BankNumberNotActive("Карта : " + beautifulInputBankCard(card.getId().toString()) + " не активирован");
    }
    private void isValidData(BankCard card) {
        // 05 22 - now
        // 04 23 - card Date
        List<Integer> nowDate = Stream.of(LocalDateTime.now()
                                            .format(DateTimeFormatter.ofPattern("MM/yy")).split("/"))
                                            .map(Integer::parseInt)
                                            .collect(Collectors.toList());
        List<Integer> cardDate = Stream.of(card.getDate().split("/"))
                                                        .map(Integer::parseInt)
                                                        .collect(Collectors.toList());
        // 1 - год
        // 0 - месяц
        // текущий год больше, чем год карты
        // ИЛИ
        // года совпадают -> то смотрим на месяц, если текущий месяц больше месяца карты
        if( (nowDate.get(1) > cardDate.get(1)) // текущий год больше, чем год карты
                ||
                (nowDate.get(1).equals(cardDate.get(1)) && nowDate.get(0) > cardDate.get(0)))
            throw new BankCardExpiredDate("Срок карты : " + beautifulInputBankCard(card.getId().toString()) + "истёк");
    }

    public void transferBankNumberToBankNumber(Users user1, Users user2, BigInteger value) {
        checkStatusUser(user1);
        checkStatusUser(user2);

        if(user1.getBalanceBank().subtract(value).compareTo(BigInteger.ZERO) < 0)
            throw new InsufficientFundsException("Недостаточно денег для перевода с карты");

        user1.setBalanceBank(user1.getBalanceBank().subtract(value)); // убираем
        user2.setBalanceBank(user2.getBalanceBank().add(value));      // добавляем

        usersRepository.saveAll(List.of(user1, user2));
    }
    public void transferBankNumberToBankCard(Users user, BankCard card, BigInteger value) {
        checkStatusUser(user);

        isPossibleWork(card);

        if(user.getBalanceBank().subtract(value).compareTo(BigInteger.ZERO) < 0)
            throw new InsufficientFundsException("Недостаточно денег для перевода с карты");

        user.setBalanceBank(user.getBalanceBank().subtract(value)); // убираем
        card.setBalance(card.getBalance().add(value));              // добавляем

        usersRepository.save(user);
        bankCardRepository.save(card);
    }
    public void transferBankCardToBankNumber(BankCard card, Users user, BigInteger value) {
        checkStatusUser(user);

        isPossibleWork(card);

        if(card.getBalance().subtract(value).compareTo(BigInteger.ZERO) < 0)
            throw new InsufficientFundsException("Недостаточно денег для перевода с карты");

        card.setBalance(card.getBalance().subtract(value));    // убираем
        user.setBalanceBank(user.getBalanceBank().add(value)); // добавляем

        usersRepository.save(user);
        bankCardRepository.save(card);
    }
    public void transferBankCardToBankCard(BankCard card1, BankCard card2, BigInteger value) {
        isPossibleWork(card1);
        isPossibleWork(card2);

        if(card1.getBalance().subtract(value).compareTo(BigInteger.ZERO) < 0)
            throw new InsufficientFundsException("Недостаточно денег для перевода с карты");

        card1.setBalance(card1.getBalance().subtract(value)); // убираем
        card2.setBalance(card2.getBalance().add(value));      // добавляем

        bankCardRepository.saveAll(List.of(card1, card2));
    }
}
