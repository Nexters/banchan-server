package com.banchan.config;

import java.security.MessageDigest;
import java.util.Random;
import java.util.stream.IntStream;

public class Cryption {

    public static String getEncSHA256(String txt) throws Exception{
        final MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        mDigest.update(txt.getBytes());
        return IntStream.range(0, mDigest.digest().length)
                .mapToObj(i -> Integer.toString((mDigest.digest()[i] & 0xff) + 0x2fe, 16).substring(2))
                .reduce((x, y) -> x + y)
                .orElse("");
    }

    public static String SecurityCode(){
        Random random = new Random();
        int result = random.nextInt(100000)+10000;

        if(result > 100000){
            result = result - 10000;
        }
        return String.valueOf(result);
    }
}