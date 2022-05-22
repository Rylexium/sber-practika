package com.sber.practika.service.userInfoService;

import com.sber.practika.dto.userInfo.MinInfoBankCard;
import com.sber.practika.dto.userInfo.UserAndHisBankCards;
import com.sber.practika.entity.Users;
import com.sber.practika.repo.BankCardRepository;
import com.sber.practika.repo.UsersRepository;
import com.sber.practika.service.email.emailException.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserInfoService {
    private final UsersRepository usersRepository;
    private final BankCardRepository bankCardRepository;

    public UserAndHisBankCards selectInfoByUsername(String username) {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Такого пользователя нет"));
        return getMinInfoUserAndHisBankCards(user);
    }

    public UserAndHisBankCards selectInfoByBankCard(BigInteger bankCard) {
        Users user = usersRepository.findByCardNumber(bankCard)
                .orElseThrow(() -> new UserNotFoundException("Такого пользователя нет"));
        return getMinInfoUserAndHisBankCards(user);
    }

    public UserAndHisBankCards selectInfoByPhone(BigInteger phone) {
        Users user = usersRepository.findByPhone(phone)
                .orElseThrow(() -> new UserNotFoundException("Такого пользователя нет"));
        return getMinInfoUserAndHisBankCards(user);
    }

    private UserAndHisBankCards getMinInfoUserAndHisBankCards(Users user) {
        List<MinInfoBankCard> bankCards = new ArrayList<>();
        bankCardRepository.findAllByBankNumber(user.getBankNumber()).forEach(item -> {
            bankCards.add(MinInfoBankCard.builder()
                    .id(item.getId())
                    .date(item.getDate())
                    .name(item.getName())
                    .balance(item.getBalance())
                    .build());
        });
        return UserAndHisBankCards.builder()
                .bankNumber(user.getBankNumber())
                .username(user.getUsername())
                .family(user.getFamily())
                .name(user.getName())
                .patronymic(user.getPatronymic())
                .email(user.getEmail())
                .address(user.getAddress())
                .phone(user.getPhone())
                .dateOfBirthday(user.getDateOfBirthday())
                .balanceBank(user.getBalanceBank())
                .cardList(bankCards)
                .build();
    }
}
