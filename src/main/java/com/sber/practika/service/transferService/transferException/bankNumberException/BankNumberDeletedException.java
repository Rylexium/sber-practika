package com.sber.practika.service.transferService.transferException.bankNumberException;

import com.sber.practika.service.transferService.transferException.TransferBaseException;

public class BankNumberDeletedException extends TransferBaseException {
    public BankNumberDeletedException(String msg, Throwable t) {
        super(msg, t);
    }

    public BankNumberDeletedException(String msg) {
        super(msg);
    }
}
