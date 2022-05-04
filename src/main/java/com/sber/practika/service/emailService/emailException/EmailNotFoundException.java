package com.sber.practika.service.emailService.emailException;

public class EmailNotFoundException extends BaseEmailException {
    public EmailNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public EmailNotFoundException(String msg) { super(msg); }
}
