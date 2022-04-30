package com.sber.practika.service.TransferException;

public class InsufficientFundsException extends TransferBaseException {
    public InsufficientFundsException(String msg, Throwable t) {
        super(msg, t);
    }

    public InsufficientFundsException(String msg) {
        super(msg);
    }
}
