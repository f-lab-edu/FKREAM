package com.flab.fkream.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Util {

    public static final String ENCRYPTION_TYPE ="SHA-256";

    public static String encrypt(String context){

        String SHA = null;

        try{
            MessageDigest messageDigest = MessageDigest.getInstance(ENCRYPTION_TYPE);
            messageDigest.update(context.getBytes());
            return bytesToHex(messageDigest.digest());

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("암호화 에러",e);
        }
    }

    private static String bytesToHex(byte[] bytes){
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x",b));
        }
        return builder.toString();
    }
}
