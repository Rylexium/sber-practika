package com.sber.practika.service.transfer.transferException;

public class InsufficientFundsException extends TransferBaseException {
    public InsufficientFundsException(String msg, Throwable t) {
        super(msg, t);
    }

    public InsufficientFundsException(String msg) {
        super(msg);
    }
}
