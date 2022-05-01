package com.sber.practika.service.transferService.transferException;

public class BankNumberNotFoundException extends TransferBaseException {
    public BankNumberNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public BankNumberNotFoundException(String msg) {
        super(msg);
    }
}
