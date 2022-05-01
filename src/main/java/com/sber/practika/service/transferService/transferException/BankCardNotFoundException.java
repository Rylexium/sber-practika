package com.sber.practika.service.transferService.transferException;

public class BankCardNotFoundException extends TransferBaseException {
    public BankCardNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public BankCardNotFoundException(String msg) {
        super(msg);
    }
}
