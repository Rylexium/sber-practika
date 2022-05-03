package com.sber.practika.service.transferService.transferException;

public class NegativeTransferValueException extends TransferBaseException {
    public NegativeTransferValueException(String msg, Throwable t) {
        super(msg, t);
    }

    public NegativeTransferValueException(String msg) {
        super(msg);
    }
}
