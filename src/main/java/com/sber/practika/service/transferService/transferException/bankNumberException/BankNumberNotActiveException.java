package com.sber.practika.service.transferService.transferException.bankNumberException;

import com.sber.practika.service.transferService.transferException.TransferBaseException;

public class BankNumberNotActiveException extends TransferBaseException {
    public BankNumberNotActiveException(String msg, Throwable t) {
        super(msg, t);
    }

    public BankNumberNotActiveException(String msg) {
        super(msg);
    }
}
