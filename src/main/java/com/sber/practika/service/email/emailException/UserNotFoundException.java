package com.sber.practika.service.email.emailException;

public class UserNotFoundException extends BaseEmailException {
    public UserNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserNotFoundException(String msg) {
        super(msg);
    }
}
