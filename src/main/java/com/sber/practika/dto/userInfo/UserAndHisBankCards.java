package com.sber.practika.dto.userInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAndHisBankCards {
    private String bankNumber;
    private String username;

    private String family;
    private String name;
    private String patronymic;

    private String email;
    private String address;
    private BigInteger phone;
    private String dateOfBirthday;

    private BigInteger balanceBank;
    private List<MinInfoBankCard> cardList;
}
