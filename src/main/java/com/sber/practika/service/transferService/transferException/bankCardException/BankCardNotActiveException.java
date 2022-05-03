package com.sber.practika.service.transferService.transferException.bankCardException;

import com.sber.practika.service.transferService.transferException.TransferBaseException;

public class BankCardNotActiveException extends TransferBaseException {
    public BankCardNotActiveException(String msg, Throwable t) {
        super(msg, t);
    }

    public BankCardNotActiveException(String msg) {
        super(msg);
    }
}
