package com.sber.practika.service.transferService.transferException.bankCardException;

import com.sber.practika.service.transferService.transferException.TransferBaseException;

public class BankCardNotActive extends TransferBaseException {
    public BankCardNotActive(String msg, Throwable t) {
        super(msg, t);
    }

    public BankCardNotActive(String msg) {
        super(msg);
    }
}
