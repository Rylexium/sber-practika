package com.sber.practika.service.transfer.transferException.bankCardException;

import com.sber.practika.service.transfer.transferException.TransferBaseException;

public class BankCardExpiredDateException extends TransferBaseException {
    public BankCardExpiredDateException(String msg, Throwable t) {
        super(msg, t);
    }

    public BankCardExpiredDateException(String msg) {
        super(msg);
    }
}
