package com.sber.practika.service.transferService.transferException;

import org.springframework.security.core.AuthenticationException;

public class TransferBaseException extends AuthenticationException {
    public TransferBaseException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public TransferBaseException(String msg) {
        super(msg);
    }
}
