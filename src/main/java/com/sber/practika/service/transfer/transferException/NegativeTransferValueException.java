package com.sber.practika.service.transfer.transferException;

public class NegativeTransferValueException extends TransferBaseException {
    public NegativeTransferValueException(String msg, Throwable t) {
        super(msg, t);
    }

    public NegativeTransferValueException(String msg) {
        super(msg);
    }
}
