package com.iexec.common.utils;

import org.web3j.utils.Numeric;

import javax.xml.bind.DatatypeConverter;

public class BytesUtils {

    // "0x0000000000000000000000000000000000000000"
    public final static String EMPTY_ADDRESS = BytesUtils.bytesToString(new byte[20]);

    //"0x0000000000000000000000000000000000000000000000000000000000000000"
    public final static String EMPTY_HEXASTRING_64 = BytesUtils.bytesToString(new byte[32]);
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

    public static boolean isBytes32(byte[] bytes){
        return bytes != null && bytes.length == 32;
    }

    // this adds zeros to the left of the hex string to make it bytes32
    public static byte[] stringToBytes32(String hexString) {
        byte[] stringBytes = stringToBytes(hexString);
        if (isBytes32(stringBytes)) return stringBytes;

        String cleanString = Numeric.cleanHexPrefix(hexString);
        String padded = padRight(cleanString, 64 - cleanString.length());
        return Numeric.hexStringToByteArray(padded);
    }

    public static String padRight(String s, int n) {
        if (n <= 0) return s;
        String zeros = new String(new char[n]).replace('\0', '0');
        return s + zeros;
    }
}