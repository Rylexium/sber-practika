package com.sber.practika.service.emailService.emailException;

import org.springframework.security.core.AuthenticationException;

public class BaseEmailException extends AuthenticationException {
    public BaseEmailException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BaseEmailException(String msg) {
        super(msg);
    }
}
