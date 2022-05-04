package com.sber.practika.service.emailService;

import com.sber.practika.entity.Users;
import com.sber.practika.repo.BankCardRepository;
import com.sber.practika.repo.UsersRepository;
import com.sber.practika.service.emailService.emailException.BaseEmailException;
import com.sber.practika.service.emailService.emailException.EmailNotFoundException;
import com.sber.practika.service.emailService.emailException.UserNotFoundException;
import com.sber.practika.service.emailService.util.EmailSenderComponent;
import com.sber.practika.service.transferService.util.TransferComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@RequiredArgsConstructor
@Service
public class EmailSenderService {
    private final EmailSenderComponent mailSender;
    private final UsersRepository usersRepository;

    public void findByUsernameAndSend(String username) throws BaseEmailException {
        sendEmail(usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Пользователя с таким username : " + username + " нет")));
    }

    public void findByPhoneAndSend(BigInteger phone) throws BaseEmailException {
        sendEmail(usersRepository.findByPhone(phone)
                .orElseThrow(() -> new UserNotFoundException("Пользователя с таким phone : " + phone + " нет")));
    }

    public void findByBankCardAndSend(BigInteger bankCard) throws BaseEmailException {
        sendEmail(usersRepository.findByCardNumber(bankCard)
                .orElseThrow(() -> new UserNotFoundException("Пользователя с такой банковской картой : "
                        + TransferComponent.beautifulInputBankCard(bankCard.toString()) + " нет")));
    }

    private void sendEmail(Users user) {
        if(user.getEmail() == null)
            throw new EmailNotFoundException("Электронный почты у пользователя " + user.getBankNumber() + " нет");

        StringBuilder code = new StringBuilder("");
        for(int i = 0; i<4; ++i)
            code.append((int)Math.floor(Math.random()*10));

        user.setConfirmCode(Integer.parseInt(code.toString()));
        usersRepository.save(user);

        new Thread(()-> mailSender.sendSimpleEmail(user.getEmail(),
                "Код подтверждения: " + code,
                "Восстановление"))
                .start();
    }
}