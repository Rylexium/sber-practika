package com.sber.practika.service.transferService.transferException.bankCardException;

import com.sber.practika.service.transferService.transferException.TransferBaseException;

public class BankCardExpiredDate extends TransferBaseException {
    public BankCardExpiredDate(String msg, Throwable t) {
        super(msg, t);
    }

    public BankCardExpiredDate(String msg) {
        super(msg);
    }
}
