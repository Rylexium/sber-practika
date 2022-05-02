package com.sber.practika.service.transferService.transferException.bankNumberException;

import com.sber.practika.service.transferService.transferException.TransferBaseException;

public class BankNumberDeleted extends TransferBaseException {
    public BankNumberDeleted(String msg, Throwable t) {
        super(msg, t);
    }

    public BankNumberDeleted(String msg) {
        super(msg);
    }
}
