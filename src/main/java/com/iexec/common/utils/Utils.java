package com.iexec.common.utils;

import java.math.BigInteger;

public class Utils {
    private Utils(){
        throw new UnsupportedOperationException();
    }

    public static String bytesToString(byte[] bytes){
        return new BigInteger(bytes).toString(16);
    }
}