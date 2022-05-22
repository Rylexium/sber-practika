package com.sber.practika.service.email.emailException;

public class EmailNotFoundException extends BaseEmailException {
    public EmailNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public EmailNotFoundException(String msg) { super(msg); }
}
