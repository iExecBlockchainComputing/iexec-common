/*
 * Copyright 2020 IEXEC BLOCKCHAIN TECH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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