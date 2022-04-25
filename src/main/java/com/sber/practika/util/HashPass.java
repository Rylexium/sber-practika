package com.sber.practika.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HashPass {
    public static String getHashSha256(String password, String salt1, String salt2){
        return sha256(sha256(password + salt1) + salt2);
    }
    public static String sha256(final String base) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            final StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                final String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    public static String generateSalt() {
        String alfavit = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ<,.>/?;:]}[{-=+*!1@2#3$4%5^6&78(9)0";
        StringBuilder res = new StringBuilder();
        for(int i=0; i<32; i++)
            res.append(alfavit.charAt( (int) (Math.random() * alfavit.length()) ));
        return res.toString();
    }
}