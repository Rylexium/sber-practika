package com.sber.practika.entity;

import com.sber.practika.models.Status;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;

@Data
@Entity
public class BankCard {
    @Id
    private BigInteger id;
    private BigInteger phoneUser;
    private String date;
    private String name;
    private BigInteger balance;
    private Status enabled;
}
