package com.sber.practika.controllers;

import io.jsonwebtoken.lang.Assert;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.sber.practika.util.ConnectToUrl.authentication;

class AuthenticationControllerTest {
    @Test
    void testAuthenticationByPhone() {
        System.out.println("===========================================");
        try {
            List<JSONObject> data = new ArrayList<>();

            data.add(new JSONObject()
                    .put("phone", "85068407596") //true
                    .put("password", "95a81899484bec3b5a087bf9649996eba4856a5c673358b0a0e2e93715662dfc"));
            data.add(new JSONObject()
                    .put("phone", "85068407595") //false
                    .put("password", "95a81899484bec3b5a087bf9649996eba4856a5c673358b0a0e2e93715662dfc"));
            data.add(new JSONObject()
                    .put("phone", "89371727345")
                    .put("password", "95a81899484bec3b5a087bf9649996eba4856a5c673358b0a0e2e93715662dfc"));
            data.add(new JSONObject()
                    .put("phone", "89371727345")
                    .put("password", "95a81899484bec3b5a087bf9649996eba4856a5c673358b0a0e2e93715662dfa"));

            for(int i=0; i < data.size(); i++) {
                try {
                    System.out.println("test " + (i+1) + " in authenticationByUsernameTest");
                    if (i % 2 == 0)
                        Assert.isTrue(authentication("phone", data.get(i)));
                    else
                        Assert.isTrue(!authentication("phone", data.get(i)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAuthenticationByUsername() {
        System.out.println("===========================================");
        try {
            List<JSONObject> data = new ArrayList<>();

            data.add(new JSONObject()
                    .put("username", "123") //true
                    .put("password", "95a81899484bec3b5a087bf9649996eba4856a5c673358b0a0e2e93715662dfc"));
            data.add(new JSONObject()
                    .put("username", "1234") //false
                    .put("password", "95a81899484bec3b5a087bf9649996eba4856a5c673358b0a0e2e93715662dfc"));
            data.add(new JSONObject()
                    .put("username", "salam")
                    .put("password", "95a81899484bec3b5a087bf9649996eba4856a5c673358b0a0e2e93715662dfc"));
            data.add(new JSONObject()
                    .put("username", "rylexium")
                    .put("password", "95a81899484bec3b5a087bf9649996eba4856a5c673358b0a0e2e93715662dfa"));

            for(int i=0; i < data.size(); i++) {
                try {
                    System.out.println("test " + (i+1) + " in authenticationByUsernameTest");
                    if (i % 2 == 0)
                        Assert.isTrue(authentication("username", data.get(i)));
                    else
                        Assert.isTrue(!authentication("username", data.get(i)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAuthenticationByBankCard() {
        System.out.println("===========================================");
        try {
            List<JSONObject> data = new ArrayList<>();

            data.add(new JSONObject()
                    .put("bankCard", "7954449398567304") //true
                    .put("password", "95a81899484bec3b5a087bf9649996eba4856a5c673358b0a0e2e93715662dfc"));
            data.add(new JSONObject()
                    .put("bankCard", "7954449398567305") //false
                    .put("password", "95a81899484bec3b5a087bf9649996eba4856a5c673358b0a0e2e93715662dfc"));
            data.add(new JSONObject()
                    .put("bankCard", "8978598074672600")
                    .put("password", "95a81899484bec3b5a087bf9649996eba4856a5c673358b0a0e2e93715662dfc"));
            data.add(new JSONObject()
                    .put("bankCard", "8978598074672600")
                    .put("password", "95a81899484bec3b5a087bf9649996eba4856a5c673358b0a0e2e93715662dfa"));

            for (int i = 0; i < data.size(); i++) {
                try {
                    System.out.println("test " + (i + 1) + " in authenticationByBankCardTest");
                    if (i % 2 == 0)
                        Assert.isTrue(authentication("bankCard", data.get(i)));
                    else
                        Assert.isTrue(!authentication("bankCard", data.get(i)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}