package com.sber.practika.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class TransactionTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    private String bankNumber1;
    private String bankNumber2;
    private BigInteger bankCard1;
    private BigInteger bankCard2;
    private BigInteger value;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private Integer statusTransaction;

    public TransactionTransfer(String bankNumber1, String bankNumber2,
                               BigInteger bankCard1, BigInteger bankCard2,
                               BigInteger value, Integer statusTransaction) {
        this.bankNumber1 = bankNumber1;
        this.bankNumber2 = bankNumber2;
        this.bankCard1 = bankCard1;
        this.bankCard2 = bankCard2;
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
