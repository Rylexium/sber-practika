package com.sber.practika.service.transfer.transferException.bankCardException;

import com.sber.practika.service.transfer.transferException.TransferBaseException;

public class BankCardsEqualsException extends TransferBaseException {
    public BankCardsEqualsException(String msg, Throwable t) {
        super(msg, t);
    }

    public BankCardsEqualsException(String msg) {
        super(msg);
    }
}
