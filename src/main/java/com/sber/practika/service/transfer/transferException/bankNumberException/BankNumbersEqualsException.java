package com.sber.practika.service.transfer.transferException.bankNumberException;

import com.sber.practika.service.transfer.transferException.TransferBaseException;

public class BankNumbersEqualsException extends TransferBaseException {
    public BankNumbersEqualsException(String msg, Throwable t) {
        super(msg, t);
    }

    public BankNumbersEqualsException(String msg) {
        super(msg);
    }
}
