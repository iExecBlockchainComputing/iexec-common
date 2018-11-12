package com.iexec.common.utils;

import javax.xml.bind.DatatypeConverter;

public class BytesUtils {

    private BytesUtils(){
        throw new UnsupportedOperationException();
    }

    public static String bytesToString(byte[] bytes){
        return DatatypeConverter.printHexBinary(bytes);
    }

    public static byte[] stringToBytes(String hexaString){
        return DatatypeConverter.parseHexBinary(hexaString);
    }

}