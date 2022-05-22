package com.sber.practika.controllers.transfer;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.io.IOException;

import static com.sber.practika.util.ConnectToUrl.getJWT;
import static com.sber.practika.util.ConnectToUrl.transfer;
import static org.junit.jupiter.api.Assertions.*;

class TransferPhoneControllerTest {

    @Test
    void testPhoneToBankNumber() throws JSONException, IOException {
        Assert.isTrue(transfer(getJWT(),
                "phone_to_bankNumber",
                new JSONObject()
                        .put("bankNumber", "18649937255896693161")
                        .put("phone", "85952161586")
                        .put("value", "1")),"Успешный перевод");
        Assert.isTrue(!transfer(getJWT(),
                "phone_to_bankNumber",
                new JSONObject()
                        .put("bankNumber", "18649937255896693161")
                        .put("phone", "85952161586")
                        .put("value", "-10")),"Отрицательный перевод");
        Assert.isTrue(!transfer(getJWT(),
                "phone_to_bankNumber",
                new JSONObject()
                        .put("bankNumber", "18649937255896693161")
                        .put("phone", "85952161586")
                        .put("value", "1000000")),"Недостаточно средств");
        Assert.isTrue(!transfer(getJWT(),
                "phone_to_bankNumber",
                new JSONObject()
                        .put("bankNumber", "60602215678377095740")
                        .put("phone", "85952161586")
                        .put("value", "1")),"Одинаковый банковский счёт");
    }

    @Test
    void testPhoneToBankCard() throws JSONException, IOException {
        Assert.isTrue(transfer(getJWT(),
                "phone_to_bankCard",
                new JSONObject()
                        .put("phone", "89371727345")
                        .put("bankCard", "5234234343124321")
                        .put("value", "1")),"Успешный перевод");
        Assert.isTrue(!transfer(getJWT(),
                "phone_to_bankCard",
                new JSONObject()
                        .put("phone", "89371727345")
                        .put("bankCard", "5234234343124321")
                        .put("value", "-100")),"Отрицательный перевод");
        Assert.isTrue(!transfer(getJWT(),
                "phone_to_bankCard",
                new JSONObject()
                        .put("phone", "89371727345")
                        .put("bankCard", "5234234343124321")
                        .put("value", "1000000")),"Недостаточно средств");
        Assert.isTrue(!transfer(getJWT(),
                "phone_to_bankCard",
                new JSONObject()
                        .put("phone", "89371727346")
                        .put("bankCard", "5234234343124321")
                        .put("value", "1")),"Нет такого банковского счёта по телефону");
        Assert.isTrue(!transfer(getJWT(),
                "phone_to_bankCard",
                new JSONObject()
                        .put("phone", "89371727345")
                        .put("bankCard", "1234123412341234")
                        .put("value", "1")),"Нет такой банковской карты");
    }

    @Test
    void testPhoneToPhone() throws JSONException, IOException {
        Assert.isTrue(transfer(getJWT(),
                "phone_to_phone",
                new JSONObject()
                        .put("phone1", "89371727345")
                        .put("phone2", "84404318694")
                        .put("value", "1")),"Успешный перевод");
        Assert.isTrue(!transfer(getJWT(),
                "phone_to_phone",
                new JSONObject()
                        .put("phone1", "89371727345")
                        .put("phone2", "84404318694")
                        .put("value", "-100")),"Отрицательный перевод");
        Assert.isTrue(!transfer(getJWT(),
                "phone_to_phone",
                new JSONObject()
                        .put("phone1", "89371727345")
                        .put("phone2", "84404318694")
                        .put("value", "1000000")),"Недостаточно средств");
        Assert.isTrue(!transfer(getJWT(),
                "phone_to_phone",
                new JSONObject()
                        .put("phone1", "84404318694")
                        .put("phone2", "84404318694")
                        .put("value", "1")),"Одинаковые телефоны");
    }
}