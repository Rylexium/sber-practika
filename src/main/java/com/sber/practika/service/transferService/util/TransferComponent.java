package com.sber.practika.service.transferService.util;

import com.sber.practika.entity.BankCard;
import com.sber.practika.entity.TransactionTransfer;
import com.sber.practika.entity.Users;
import com.sber.practika.models.Status;
import com.sber.practika.models.StatusTransaction;
import com.sber.practika.repo.BankCardRepository;
import com.sber.practika.repo.TransactionTransferRepository;
import com.sber.practika.repo.UsersRepository;
import com.sber.practika.service.transferService.transferException.InsufficientFundsException;
import com.sber.practika.service.transferService.transferException.bankCardException.BankCardExpiredDateException;
import com.sber.practika.service.transferService.transferException.bankNumberException.BankNumberDeletedException;
import com.sber.practika.service.transferService.transferException.bankNumberException.BankNumberNotActiveException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class TransferComponent {
    private final UsersRepository usersRepository;
    private final BankCardRepository bankCardRepository;
    private final RegistrationTransferTransaction registrationTransferTransaction;

    private void checkStatusUser(Users user, UUID uuidTransaction){
        if(user.getEnabled() == Status.DELETED) {
            registrationTransferTransaction.cannotBePerformedDueUser(uuidTransaction);
            throw new BankNumberDeletedException("Банковский счёт : " + user.getBankNumber() + " удален");
        }
        else if(user.getEnabled() == Status.NOT_ACTIVE) {
            registrationTransferTransaction.cannotBePerformedDueUser(uuidTransaction);
            throw new BankNumberNotActiveException("Банковский счёт : " + user.getBankNumber() + " не активен");
        }
    }

    public static String beautifulInputBankCard(String idCard) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < idCard.length(); i++){
            res.append(idCard.charAt(i));
            if( (i+1) % 4 == 0) res.append(" ");
        }
        return res.toString();
    }
    private void isPossibleWork(BankCard card, UUID uuidTransaction){
        checkStatusCard(card, uuidTransaction);
        isValidData(card, uuidTransaction);
    }
    private void checkStatusCard(BankCard card, UUID uuidTransaction) {
        if(card.getEnabled() == Status.DELETED) {
            registrationTransferTransaction.cannotBePerformedDueCard(uuidTransaction);
            throw new BankNumberDeletedException("Карта : " + beautifulInputBankCard(card.getId().toString()) + "не существует");
        }
        else if(card.getEnabled() == Status.NOT_ACTIVE) {
            registrationTransferTransaction.cannotBePerformedDueCard(uuidTransaction);
            throw new BankNumberNotActiveException("Карта : " + beautifulInputBankCard(card.getId().toString()) + " не активирован");
        }
    }
    private void isValidData(BankCard card, UUID uuidTransaction) {
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
        if ((nowDate.get(1) > cardDate.get(1)) // текущий год больше, чем год карты
                ||
                (nowDate.get(1).equals(cardDate.get(1)) && nowDate.get(0) > cardDate.get(0))) {
            registrationTransferTransaction.cannotBePerformedDueCard(uuidTransaction);
            throw new BankCardExpiredDateException("Срок карты : " + beautifulInputBankCard(card.getId().toString()) + "истёк");
        }
    }
    private void isHaveMoney(BigInteger balance, BigInteger value, UUID uuidTransaction) {
        if(balance.subtract(value).compareTo(BigInteger.ZERO) < 0) {
            registrationTransferTransaction.noMoney(uuidTransaction);
            throw new InsufficientFundsException("Недостаточно денег для перевода");
        }
    }

    public void transferBankNumberToBankNumber(Users user1, Users user2, BigInteger value, UUID uuidTransaction) {
        checkStatusUser(user1, uuidTransaction);
        checkStatusUser(user2, uuidTransaction);

        isHaveMoney(user1.getBalanceBank(), value, uuidTransaction);

        user1.setBalanceBank(user1.getBalanceBank().subtract(value)); // убираем
        user2.setBalanceBank(user2.getBalanceBank().add(value));      // добавляем

        usersRepository.save(user1);
        usersRepository.save(user2);

        registrationTransferTransaction.access(uuidTransaction);
    }
    public void transferBankNumberToBankCard(Users user, BankCard card, BigInteger value, UUID uuidTransaction) {
        checkStatusUser(user, uuidTransaction);

        isPossibleWork(card, uuidTransaction);

        isHaveMoney(user.getBalanceBank(), value, uuidTransaction);

        user.setBalanceBank(user.getBalanceBank().subtract(value)); // убираем
        card.setBalance(card.getBalance().add(value));              // добавляем

        usersRepository.save(user);
        bankCardRepository.save(card);

        registrationTransferTransaction.access(uuidTransaction);
    }
    public void transferBankCardToBankNumber(BankCard card, Users user, BigInteger value, UUID uuidTransaction) {
        checkStatusUser(user, uuidTransaction);

        isPossibleWork(card, uuidTransaction);

        isHaveMoney(card.getBalance(), value, uuidTransaction);

        card.setBalance(card.getBalance().subtract(value));    // убираем
        user.setBalanceBank(user.getBalanceBank().add(value)); // добавляем

        usersRepository.save(user);
        bankCardRepository.save(card);

        registrationTransferTransaction.access(uuidTransaction);
    }
    public void transferBankCardToBankCard(BankCard card1, BankCard card2, BigInteger value, UUID uuidTransaction) {
        isPossibleWork(card1, uuidTransaction);
        isPossibleWork(card2, uuidTransaction);

        isHaveMoney(card1.getBalance(), value, uuidTransaction);

        card1.setBalance(card1.getBalance().subtract(value)); // убираем
        card2.setBalance(card2.getBalance().add(value));      // добавляем

        bankCardRepository.save(card1);
        bankCardRepository.save(card2);

        registrationTransferTransaction.access(uuidTransaction);
    }
}
