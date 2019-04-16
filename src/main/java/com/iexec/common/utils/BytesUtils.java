package com.iexec.common.utils;

import org.web3j.utils.Numeric;

import javax.xml.bind.DatatypeConverter;

public class BytesUtils {

    public final static String EMPTY_ADDRESS = BytesUtils.bytesToString(new byte[20]); //"0x0000000000000000000000000000000000000000"
    public final static String EMPTY_HEXASTRING_64 = BytesUtils.bytesToString(new byte[32]); //"0x0000000000000000000000000000000000000000000000000000000000000000"
    private BytesUtils() {
        throw new UnsupportedOperationException();
    }

    public static String bytesToString(byte[] bytes) {
        return Numeric.toHexString(bytes);
    }

    public static byte[] stringToBytes(String hexaString) {
        return Numeric.hexStringToByteArray(hexaString);
    }

    public static String hexStringToAscii(String hexString) {
        return new String(DatatypeConverter.parseHexBinary(Numeric.cleanHexPrefix(hexString)));
    }

    public static boolean isHexaString(String hexaString) {
        return Numeric.cleanHexPrefix(hexaString).matches("\\p{XDigit}+"); // \\p{XDigit} matches any hexadecimal character
    }

}