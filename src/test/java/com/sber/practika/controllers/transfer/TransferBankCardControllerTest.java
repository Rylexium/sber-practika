package com.sber.practika.controllers.transfer;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.sber.practika.util.ConnectToUrl.getJWT;
import static com.sber.practika.util.ConnectToUrl.transfer;
import static org.junit.jupiter.api.Assertions.*;

class TransferBankCardControllerTest {

    @Test
    void testBankCardToBankNumber() throws JSONException, IOException {
        System.out.println("\ntestBankCardToBankNumber\n===========================================");

            //{
            //    "bankNumber" : "18649937255896693161",
            //    "bankCard" : 5234234343124321,
            //    "value" : 100
            //}

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
                            .put("value", "-100")), "Отрицательное число");
            Assert.isTrue(!transfer(getJWT(),
                        "bankNumber_to_bankCard",
                        new JSONObject()
                            .put("bankNumber", "18649937255896693161")
                            .put("bankCard", "8787314829830131")
                            .put("value", "10000000")), "Нехватка средств на карте");
            Assert.isTrue(!transfer(getJWT(),
                        "bankNumber_to_bankCard",
                        new JSONObject()
                            .put("bankNumber", "18649937255896693161")
                            .put("bankCard", "4752154192152587")
                            .put("value", "1")), "Срок карты");
            Assert.isTrue(!transfer(getJWT(),
                        "bankNumber_to_bankCard",
                        new JSONObject()
                            .put("bankNumber", "18649937255896693161")
                            .put("bankCard", "1234123412341234")
                            .put("value", "1")), "Не наход карты");
            Assert.isTrue(!transfer(getJWT(),
                        "bankNumber_to_bankCard",
                        new JSONObject()
                            .put("bankNumber", "18649937255896693162")
                            .put("bankCard", "5234234343124321")
                            .put("value", "1")), "Не наход счёта");
            Assert.isTrue(!transfer(getJWT(),
                    "bankNumber_to_bankCard",
                            new JSONObject()
                                .put("bankNumber", "18649937255896693161")
                                .put("bankCard", "7722702991638932")
                                .put("value", "1")), "Статус карты");
            Assert.isTrue(!transfer(getJWT(),
                        "bankNumber_to_bankCard",
                        new JSONObject()
                            .put("bankNumber", "18649937255896693169")
                            .put("bankCard", "5234234343124321")
                            .put("value", "1")), "Статус счёта");
    }

    @Test
    void testBankCardToBankCard() throws JSONException, IOException {
        System.out.println("\ntestBankCardToBankCard\n===========================================");

        //{
        //    "bankCard1" : 1457345448336542,
        //    "bankCard2" : 7302903045234380,
        //    "value" : 1
        //}
        Assert.isTrue(transfer(getJWT(),
                "bankCard_to_bankCard",
                new JSONObject()
                        .put("bankCard1", "1457345448336542")
                        .put("bankCard2", "7302903045234380")
                        .put("value", "1")), "Успешный перевод");
        Assert.isTrue(!transfer(getJWT(),
                "bankCard_to_bankCard",
                new JSONObject()
                        .put("bankCard1", "1457345448336542")
                        .put("bankCard2", "7302903045234380")
                        .put("value", "-10")), "Отрицательное число");
        Assert.isTrue(!transfer(getJWT(),
                "bankCard_to_bankCard",
                new JSONObject()
                        .put("bankCard1", "1457345448336541")
                        .put("bankCard2", "7302903045234380")
                        .put("value", "1")), "Не наход банковской карты1");
        Assert.isTrue(!transfer(getJWT(),
                "bankCard_to_bankCard",
                new JSONObject()
                        .put("bankCard1", "1457345448336542")
                        .put("bankCard2", "7302903045234381")
                        .put("value", "1")), "Не наход банковской карты2");
        Assert.isTrue(!transfer(getJWT(),
                "bankCard_to_bankCard",
                new JSONObject()
                        .put("bankCard1", "1457345448336542")
                        .put("bankCard2", "1457345448336542")
                        .put("value", "1")), "Одинаковые карты");
    }

    @Test
    void testBankCardToPhone() throws JSONException, IOException {
        System.out.println("\ntestBankCardToPhone\n===========================================");
        Assert.isTrue(transfer(getJWT(),
                "bankCard_to_phone",
                new JSONObject()
                        .put("bankCard", "1457345448336542")
                        .put("phone", "89371727345")
                        .put("value", "1")), "Успешный перевод");
        Assert.isTrue(!transfer(getJWT(),
                "bankCard_to_phone",
                new JSONObject()
                        .put("bankCard", "1457345448336542")
                        .put("phone", "89371727345")
                        .put("value", "-1")), "Отрицательное число");
        Assert.isTrue(!transfer(getJWT(),
                "bankCard_to_phone",
                new JSONObject()
                        .put("bankCard", "1457345448336541")
                        .put("phone", "89371727345")
                        .put("value", "1")), "Не наход банковской карты");
        Assert.isTrue(!transfer(getJWT(),
                "bankCard_to_phone",
                new JSONObject()
                        .put("bankCard", "1457345448336542")
                        .put("phone", "89371727346")
                        .put("value", "1")), "Не наход банковский счёт по телефону");

    }
}