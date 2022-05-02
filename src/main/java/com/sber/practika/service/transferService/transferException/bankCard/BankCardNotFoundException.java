package com.sber.practika.service.transferService.transferException.bankCard;

import com.sber.practika.service.transferService.transferException.TransferBaseException;

public class BankCardNotFoundException extends TransferBaseException {
    public BankCardNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public BankCardNotFoundException(String msg) {
        super(msg);
    }
}
