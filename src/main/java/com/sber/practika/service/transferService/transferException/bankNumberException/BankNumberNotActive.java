package com.sber.practika.service.transferService.transferException.bankNumberException;

import com.sber.practika.service.transferService.transferException.TransferBaseException;

public class BankNumberNotActive extends TransferBaseException {
    public BankNumberNotActive(String msg, Throwable t) {
        super(msg, t);
    }

    public BankNumberNotActive(String msg) {
        super(msg);
    }
}
