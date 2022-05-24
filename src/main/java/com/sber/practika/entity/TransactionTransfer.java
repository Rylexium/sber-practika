package com.sber.practika.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class TransactionTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    private String senderBankNumber;
    private String recipientBankNumber;
    private Long senderBankCard;
    private Long recipientBankCard;
    private Long value;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private Integer statusTransaction;

    public TransactionTransfer(String senderBankNumber, String recipientBankNumber,
                               Long senderBankCard, Long recipientBankCard,
                               Long value, Integer statusTransaction) {
        this.senderBankNumber = senderBankNumber;
        this.recipientBankNumber = recipientBankNumber;
        this.senderBankCard = senderBankCard;
        this.recipientBankCard = recipientBankCard;
        this.value = value;
        this.statusTransaction = statusTransaction;
    }

    @PrePersist
    @PreUpdate
    private void onCreateOrUpdate() {
        date = new Date();
    }
    public TransactionTransfer() {
    }
}
