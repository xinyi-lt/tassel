package com.zuoquan.lt.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by liutao on 2017/7/12.
 */
public class SecurityHash {

    public static final int DEFAULT_SALT_BYTE_SIZE = 10;
    public static final String DEFAULT_HASH_ALGORITHM = "MD5";

    public static String generateSalt(){
        return generateSalt(DEFAULT_SALT_BYTE_SIZE);
    }


    public static String generateSalt(int saltByteSize){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[saltByteSize];
        random.nextBytes(salt);
        return convertByteToHex(salt);
    }

    public static String getMD5Hex(final String inputString) throws NoSuchAlgorithmException {
        return getHashHex(DEFAULT_HASH_ALGORITHM, inputString);
    }

    public static String getMD5HexWithSalt(final String inputString, String salt) throws NoSuchAlgorithmException {
        String strWithSalt = inputString + salt;
        return getHashHex(DEFAULT_HASH_ALGORITHM, strWithSalt);
    }

    public static String getHashWithSalt(String algorithm, String inputString, String salt) throws NoSuchAlgorithmException {
        String strWithSalt = inputString + salt;
        return getHashHex(algorithm, strWithSalt);
    }

    public static String getHashHex(String algorithm, String inputString) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(inputString.getBytes());

        byte[] digest = md.digest();

        return convertByteToHex(digest);
    }



    private static String convertByteToHex(byte[] byteData) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }



}
