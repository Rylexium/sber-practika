package com.sber.practika.service.transferService.transferException;

public class NegativeTransferValue extends TransferBaseException {
    public NegativeTransferValue(String msg, Throwable t) {
        super(msg, t);
    }

    public NegativeTransferValue(String msg) {
        super(msg);
    }
}
