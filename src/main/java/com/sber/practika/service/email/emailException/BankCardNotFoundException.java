package com.sber.practika.service.email.emailException;

public class BankCardNotFoundException extends BaseEmailException {
    public BankCardNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BankCardNotFoundException(String msg) {
        super(msg);
    }
}
