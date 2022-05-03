package com.sber.practika.service.transferService.transferException.bankCardException;

import com.sber.practika.service.transferService.transferException.TransferBaseException;

public class BankCardDeletedException extends TransferBaseException {
    public BankCardDeletedException(String msg, Throwable t) {
        super(msg, t);
    }

    public BankCardDeletedException(String msg) {
        super(msg);
    }
}
