package com.sber.practika.controllers.transfer;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.sber.practika.util.ConnectToUrl.getJWT;
import static com.sber.practika.util.ConnectToUrl.transfer;
import static org.junit.jupiter.api.Assertions.*;

class TransferBankCardControllerTest {

    @Test
    void testBankCardToBankNumber() {
        System.out.println("===========================================");
        try {
            List<JSONObject> data = new ArrayList<>();

            //{
            //    "bankNumber" : "18649937255896693161",
            //    "bankCard" : 5234234343124321,
            //    "value" : 100
            //}

            data.add(new JSONObject()
                    .put("bankNumber", "18649937255896693161") //true
                    .put("bankCard", "5234234343124321")
                    .put("value", "100"));
            data.add(new JSONObject()
                    .put("bankNumber", "18649937255896693161") //true
                    .put("bankCard", "5234234343124321")
                    .put("value", "-100"));
            data.add(new JSONObject()
                    .put("bankNumber", "18649937255896693161") //true
                    .put("bankCard", "5234234343124321")
                    .put("value", "100"));
            data.add(new JSONObject()
                    .put("bankNumber", "18649937255896693161") //true
                    .put("bankCard", "5234234343124321")
                    .put("value", "100"));
            System.out.println("JWT : " + getJWT());
            for(int i=0; i<data.size(); i++){
                transfer(getJWT(),"bankNumber_to_bankCard", data.get(i));
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testBankCardToBankCard() {
        System.out.println("===========================================");
    }

    @Test
    void testBankCardToPhone() {
        System.out.println("===========================================");
    }
}