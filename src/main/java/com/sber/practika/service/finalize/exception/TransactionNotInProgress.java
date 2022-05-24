package com.sber.practika.service.finalize.exception;

import com.sber.practika.service.transfer.transferException.TransferBaseException;

public class TransactionNotInProgress extends TransferBaseException {
    public TransactionNotInProgress(String msg, Throwable t) {
        super(msg, t);
    }

    public TransactionNotInProgress(String msg) {
        super(msg);
    }
}