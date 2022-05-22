package com.sber.practika.service.transfer.transferException.bankNumberException;

import com.sber.practika.service.transfer.transferException.TransferBaseException;

public class BankNumberNotActiveException extends TransferBaseException {
    public BankNumberNotActiveException(String msg, Throwable t) {
        super(msg, t);
    }

    public BankNumberNotActiveException(String msg) {
        super(msg);
    }
}
