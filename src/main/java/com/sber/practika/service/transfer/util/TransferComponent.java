package com.sber.practika.service.transfer.util;

import com.sber.practika.entity.BankCard;
import com.sber.practika.entity.Users;
import com.sber.practika.entity.statuses.Status;
import com.sber.practika.repo.BankCardRepository;
import com.sber.practika.repo.UsersRepository;
import com.sber.practika.service.transfer.transferException.InsufficientFundsException;
import com.sber.practika.service.transfer.transferException.bankCardException.BankCardExpiredDateException;
import com.sber.practika.service.transfer.transferException.bankNumberException.BankNumberDeletedException;
import com.sber.practika.service.transfer.transferException.bankNumberException.BankNumberNotActiveException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

    private void checkStatusUser(Users bankNumber, UUID uuidTransaction){
        if(bankNumber.getEnabled() == Status.DELETED) {
            registrationTransferTransaction.cannotBePerformedDueUser(uuidTransaction);
            throw new BankNumberDeletedException("Банковский счёт : " + bankNumber.getBankNumber() + " удален");
        }
        else if(bankNumber.getEnabled() == Status.NOT_ACTIVE) {
            registrationTransferTransaction.cannotBePerformedDueUser(uuidTransaction);
            throw new BankNumberNotActiveException("Банковский счёт : " + bankNumber.getBankNumber() + " не активен");
        }
    }

    public static String beautifulInputBankCard(String bankCardId) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < bankCardId.length(); i++){
            res.append(bankCardId.charAt(i));
            if( (i+1) % 4 == 0) res.append(" ");
        }
        return res.toString();
    }
    private void isPossibleWork(BankCard bankCard, UUID uuidTransaction){
        checkStatusCard(bankCard, uuidTransaction);
        isValidData(bankCard, uuidTransaction);
    }
    private void checkStatusCard(BankCard bankCard, UUID uuidTransaction) {
        if(bankCard.getEnabled() == Status.DELETED) {
            registrationTransferTransaction.cannotBePerformedDueCard(uuidTransaction);
            throw new BankNumberDeletedException("Карта : " + beautifulInputBankCard(bankCard.getId().toString()) + "не существует");
        }
        else if(bankCard.getEnabled() == Status.NOT_ACTIVE) {
            registrationTransferTransaction.cannotBePerformedDueCard(uuidTransaction);
            throw new BankNumberNotActiveException("Карта : " + beautifulInputBankCard(bankCard.getId().toString()) + " не активирован");
        }
    }
    private void isValidData(BankCard bankCard, UUID uuidTransaction) {
        // 05 22 - now
        // 04 23 - card Date
        List<Integer> nowDate = Stream.of(LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("MM/yy")).split("/"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<Integer> cardDate = Stream.of(bankCard.getDate().split("/"))
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
            throw new BankCardExpiredDateException("Срок карты : " + beautifulInputBankCard(bankCard.getId().toString()) + "истёк");
        }
    }
    private void isHaveMoney(Long balance, Long value, UUID uuidTransaction) {
        if(balance - value < 0) {
            registrationTransferTransaction.noMoney(uuidTransaction);
            throw new InsufficientFundsException("Недостаточно денег для перевода");
        }
    }

    public void transferBankNumberToBankNumber(Users senderBankNumber, Users recipientBankNumber, Long value, UUID uuidTransaction) {
        checkStatusUser(senderBankNumber, uuidTransaction);
        checkStatusUser(recipientBankNumber, uuidTransaction);

        isHaveMoney(senderBankNumber.getBalanceBank(), value, uuidTransaction);

        senderBankNumber.setBalanceBank(senderBankNumber.getBalanceBank() - value); // убираем
        recipientBankNumber.setBalanceBank(recipientBankNumber.getBalanceBank() + value);      // добавляем

        usersRepository.save(senderBankNumber);
        usersRepository.save(recipientBankNumber);

        registrationTransferTransaction.access(uuidTransaction);
    }
    public void transferBankNumberToBankCard(Users senderBankNumber, BankCard recipientBankCard, Long value, UUID uuidTransaction) {
        checkStatusUser(senderBankNumber, uuidTransaction);

        isPossibleWork(recipientBankCard, uuidTransaction);

        isHaveMoney(senderBankNumber.getBalanceBank(), value, uuidTransaction);

        senderBankNumber.setBalanceBank(senderBankNumber.getBalanceBank() - value); // убираем
        recipientBankCard.setBalance(recipientBankCard.getBalance() + value);              // добавляем

        usersRepository.save(senderBankNumber);
        bankCardRepository.save(recipientBankCard);

        registrationTransferTransaction.access(uuidTransaction);
    }
    public void transferBankCardToBankNumber(BankCard senderBankCard, Users recipientBankNumber, Long value, UUID uuidTransaction) {
        checkStatusUser(recipientBankNumber, uuidTransaction);

        isPossibleWork(senderBankCard, uuidTransaction);

        isHaveMoney(senderBankCard.getBalance(), value, uuidTransaction);

        senderBankCard.setBalance(senderBankCard.getBalance() - value);    // убираем
        recipientBankNumber.setBalanceBank(recipientBankNumber.getBalanceBank() + value); // добавляем

        usersRepository.save(recipientBankNumber);
        bankCardRepository.save(senderBankCard);

        registrationTransferTransaction.access(uuidTransaction);
    }
    public void transferBankCardToBankCard(BankCard senderBankCard, BankCard recipientBankCard, Long value, UUID uuidTransaction) {
        isPossibleWork(senderBankCard, uuidTransaction);
        isPossibleWork(recipientBankCard, uuidTransaction);

        isHaveMoney(senderBankCard.getBalance(), value, uuidTransaction);

        senderBankCard.setBalance(senderBankCard.getBalance() - value); // убираем
        recipientBankCard.setBalance(recipientBankCard.getBalance() + value);      // добавляем

        bankCardRepository.save(senderBankCard);
        bankCardRepository.save(recipientBankCard);

        registrationTransferTransaction.access(uuidTransaction);
    }
}
