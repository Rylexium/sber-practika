package com.sber.practika.controllers.transfer;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.io.IOException;

import static com.sber.practika.util.ConnectToUrl.getJWT;
import static com.sber.practika.util.ConnectToUrl.transfer;
import static org.junit.jupiter.api.Assertions.*;

class TransferBankNumberControllerTest {

    @Test
    void testBankNumberToBankNumber() throws JSONException, IOException {
        System.out.println("\ntestBankNumberToBankNumber\n===========================================");
        Assert.isTrue(transfer(getJWT(),
                "bankNumber_to_bankNumber",
                new JSONObject()
                        .put("bankNumber1", "18649937255896693161")
                        .put("bankNumber2", "60602215678377095740")
                        .put("value", "1")),"Успешный перевод");
        Assert.isTrue(!transfer(getJWT(),
                "bankNumber_to_bankNumber",
                new JSONObject()
                        .put("bankNumber1", "18649937255896693161")
                        .put("bankNumber2", "52462862984750424247")
                        .put("value", "-100")),"Отрицательное число");
        Assert.isTrue(!transfer(getJWT(),
                "bankNumber_to_bankNumber",
                new JSONObject()
                        .put("bankNumber1", "88914563117452057787")
                        .put("bankNumber2", "30101810400000000225")
                        .put("value", "10000000000")),"Недостаточно средств");
        Assert.isTrue(!transfer(getJWT(),
                "bankNumber_to_bankNumber",
                new JSONObject()
                        .put("bankNumber1", "18649937255896693162")
                        .put("bankNumber2", "52462862984750424247")
                        .put("value", "1")),"Не наход банковского счёта 1");
        Assert.isTrue(!transfer(getJWT(),
                "bankNumber_to_bankNumber",
                new JSONObject()
                        .put("bankNumber1", "18649937255896693161")
                        .put("bankNumber2", "52462862984750424248")
                        .put("value", "1")),"Не наход банковского счёта 2");
        Assert.isTrue(!transfer(getJWT(),
                "bankNumber_to_bankNumber",
                new JSONObject()
                        .put("bankNumber1", "69813821829651181008")
                        .put("bankNumber2", "18649937255896693161")
                        .put("value", "1")),"Статус банковского счёта 1");
        Assert.isTrue(!transfer(getJWT(),
                "bankNumber_to_bankNumber",
                new JSONObject()
                        .put("bankNumber1", "18649937255896693161")
                        .put("bankNumber2", "69813821829651181008")
                        .put("value", "1")),"Статус банковского счёта 2");
        Assert.isTrue(!transfer(getJWT(),
                "bankNumber_to_bankNumber",
                new JSONObject()
                        .put("bankNumber1", "18649937255896693161")
                        .put("bankNumber2", "18649937255896693161")
                        .put("value", "1")),"Одинаковые счета");
    }

    @Test
    void testBankNumberToBankCard() throws JSONException, IOException {
        System.out.println("\ntestBankNumberToBankCard\n===========================================");
        Assert.isTrue(transfer(getJWT(),
                "bankNumber_to_bankCard",
                new JSONObject()
                        .put("bankNumber", "18649937255896693161")
                        .put("bankCard", "5234234343124321")
                        .put("value", "1")),"Успешный перевод");
        Assert.isTrue(!transfer(getJWT(),
                "bankNumber_to_bankCard",
                new JSONObject()
                        .put("bankNumber", "18649937255896693161")
                        .put("bankCard", "5234234343124321")
                        .put("value", "1000000000")),"Недостаточно средств");
        Assert.isTrue(!transfer(getJWT(),
                "bankNumber_to_bankCard",
                new JSONObject()
                        .put("bankNumber", "18649937255896693161")
                        .put("bankCard", "5234234343124321")
                        .put("value", "-1")),"Отрицательная сумма");
        Assert.isTrue(!transfer(getJWT(),
                "bankNumber_to_bankCard",
                new JSONObject()
                        .put("bankNumber", "18649937255896693161")
                        .put("bankCard", "1234123412341234")
                        .put("value", "5")),"Банковской карты нет");
        Assert.isTrue(!transfer(getJWT(),
                "bankNumber_to_bankCard",
                new JSONObject()
                        .put("bankNumber", "18649937255896693160")
                        .put("bankCard", "1234123412341234")
                        .put("value", "5")),"Банковского счёта нет");
    }

    @Test
    void testBankNumberToPhone() throws JSONException, IOException {
        System.out.println("\ntestBankNumberToPhone\n===========================================");
        Assert.isTrue(transfer(getJWT(),
                "bankNumber_to_phone",
                new JSONObject()
                        .put("bankNumber", "18649937255896693161")
                        .put("phone", "85952161586")
                        .put("value", "1")),"Успешный перевод");
        Assert.isTrue(!transfer(getJWT(),
                "bankNumber_to_phone",
                new JSONObject()
                        .put("bankNumber", "60602215678377095740")
                        .put("phone", "85952161586")
                        .put("value", "1")),"Одинаковый банковский счёт");
    }
}