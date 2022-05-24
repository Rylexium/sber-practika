package com.sber.practika.dto.userInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MinInfoBankCard {
    private Long id;
    private String date;
    private String name;
    private Long balance;
}
