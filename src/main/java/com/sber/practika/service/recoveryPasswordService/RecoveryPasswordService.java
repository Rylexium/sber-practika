package com.sber.practika.service.recoveryPasswordService;

import com.sber.practika.entity.Users;
import com.sber.practika.repo.UsersRepository;
import com.sber.practika.service.emailService.emailException.UserNotFoundException;
import com.sber.practika.service.transferService.util.TransferComponent;
import com.sber.practika.util.HashPass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@RequiredArgsConstructor
@Service
public class RecoveryPasswordService {
    private final UsersRepository usersRepository;

    public boolean isTrueConfirmCodeByUsername(String username, Integer code) {
        Users user =  usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Пользователя с таким username : " + username + " нет"));
        if(user.getConfirmCode() != null && user.getConfirmCode().equals(code)){
            user.setConfirmCode(null);
            usersRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean isTrueConfirmCodeByPhone(BigInteger phone, Integer code) {
        Users user = usersRepository.findByPhone(phone)
                .orElseThrow(() -> new UserNotFoundException("Пользователя с таким phone : " + phone + " нет"));
        if(user.getConfirmCode() != null && user.getConfirmCode().equals(code)){
            user.setConfirmCode(null);
            usersRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean isTrueConfirmCodeByBankCard(BigInteger bankCard, Integer code) {
        Users user = usersRepository.findByCardNumber(bankCard)
                .orElseThrow(() -> new UserNotFoundException("Пользователя с такой банковской картой : "
                        + TransferComponent.beautifulInputBankCard(bankCard.toString()) + " нет"));
        if(user.getConfirmCode() != null && user.getConfirmCode().equals(code)){
            user.setConfirmCode(null);
            usersRepository.save(user);
            return true;
        }
        return false;
    }


    public void newPasswordByUsername(String username, String newPassword) {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Такого пользователя нет"));
        user.setPassword(HashPass.getHashSha256(newPassword, user.getSalt1(), user.getSalt2()));
        usersRepository.save(user);
    }

    public void newPasswordByPhone(BigInteger phone, String newPassword) {
        Users user = usersRepository.findByPhone(phone)
                .orElseThrow(() -> new UserNotFoundException("Такого пользователя нет"));
        user.setPassword(HashPass.getHashSha256(newPassword, user.getSalt1(), user.getSalt2()));
        usersRepository.save(user);
    }

    public void newPasswordByBankCard(BigInteger bankCard, String newPassword) {
        Users user = usersRepository.findByCardNumber(bankCard)
                .orElseThrow(() -> new UserNotFoundException("Такого пользователя нет"));
        user.setPassword(HashPass.getHashSha256(newPassword, user.getSalt1(), user.getSalt2()));
        usersRepository.save(user);
    }

}
