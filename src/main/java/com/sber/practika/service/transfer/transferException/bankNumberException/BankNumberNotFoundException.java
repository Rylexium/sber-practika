package com.sber.practika.service.transfer.transferException.bankNumberException;

import com.sber.practika.service.transfer.transferException.TransferBaseException;

public class BankNumberNotFoundException extends TransferBaseException {
    public BankNumberNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public BankNumberNotFoundException(String msg) {
        super(msg);
    }
}
