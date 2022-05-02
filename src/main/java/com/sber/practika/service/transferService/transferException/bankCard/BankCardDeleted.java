package com.sber.practika.service.transferService.transferException.bankCard;

import com.sber.practika.service.transferService.transferException.TransferBaseException;

public class BankCardDeleted extends TransferBaseException {
    public BankCardDeleted(String msg, Throwable t) {
        super(msg, t);
    }

    public BankCardDeleted(String msg) {
        super(msg);
    }
}
