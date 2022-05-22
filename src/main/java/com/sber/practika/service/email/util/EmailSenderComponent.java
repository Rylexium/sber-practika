package com.sber.practika.service.email.util;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSenderComponent {

    private final JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail,
                                String body,
                                String subject){
        SimpleMailMessage message = new SimpleMailMessage();


        message.setFrom("rylexium@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
    }
}
