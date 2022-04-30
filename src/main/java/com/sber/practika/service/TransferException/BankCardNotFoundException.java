package com.sber.practika.service.TransferException;

public class BankCardNotFoundException extends TransferBaseException {
    public BankCardNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public BankCardNotFoundException(String msg) {
        super(msg);
    }
}
