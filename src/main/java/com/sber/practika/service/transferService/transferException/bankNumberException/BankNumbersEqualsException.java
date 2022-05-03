package com.sber.practika.service.transferService.transferException.bankNumberException;

import com.sber.practika.service.transferService.transferException.TransferBaseException;

public class BankNumbersEqualsException extends TransferBaseException {
    public BankNumbersEqualsException(String msg, Throwable t) {
        super(msg, t);
    }

    public BankNumbersEqualsException(String msg) {
        super(msg);
    }
}
