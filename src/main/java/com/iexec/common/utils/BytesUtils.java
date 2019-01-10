package com.iexec.common.utils;

import org.web3j.utils.Numeric;

public class BytesUtils {

    private BytesUtils() {
        throw new UnsupportedOperationException();
    }

    public final static String EMPTY_ADDRESS = BytesUtils.bytesToString(new byte[20]); //"0x0000000000000000000000000000000000000000"
    public final static String EMPTY_HEXASTRING_64 = BytesUtils.bytesToString(new byte[32]); //"0x0000000000000000000000000000000000000000000000000000000000000000"

    public static String bytesToString(byte[] bytes) {
        return Numeric.toHexString(bytes);
    }

    public static byte[] stringToBytes(String hexaString) {
        return Numeric.hexStringToByteArray(hexaString);
    }

}