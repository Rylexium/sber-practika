package com.sber.practika.service.transfer.transferException.bankCardException;

import com.sber.practika.service.transfer.transferException.TransferBaseException;

public class BankCardNotActiveException extends TransferBaseException {
    public BankCardNotActiveException(String msg, Throwable t) {
        super(msg, t);
    }

    public BankCardNotActiveException(String msg) {
        super(msg);
    }
}
