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
import java.util.Arrays;
import java.util.regex.Pattern;

public class BytesUtils {

    // "0x0000000000000000000000000000000000000000"
    public final static String EMPTY_ADDRESS = BytesUtils.bytesToString(new byte[20]);

    private static final int BYTES_32_SIZE = 32;
    //"0x0000000000000000000000000000000000000000000000000000000000000000"
    public final static String EMPTY_HEX_STRING_32 = BytesUtils.bytesToString(new byte[BYTES_32_SIZE]);
    @Deprecated
    public final static String EMPTY_HEXASTRING_64 = BytesUtils.bytesToString(new byte[BYTES_32_SIZE]);
    private static final String HEX_REGEX = "\\p{XDigit}+$";
    private static final Pattern HEX_PATTERN = Pattern.compile("(?i)^(0x)?" + HEX_REGEX);
    private static final Pattern HEX_WITH_PREFIX_PATTERN = Pattern.compile("^0x" + HEX_REGEX);

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

    public static boolean isHexStringWithPrefix(String hexString) {
        return hexString != null
                && HEX_WITH_PREFIX_PATTERN.matcher(hexString).matches();
    }

    public static boolean isHexString(String hexString) {
        return isHexaString(hexString);
    }

    @Deprecated
    public static boolean isHexaString(String hexString) {
        return hexString != null
                && HEX_PATTERN.matcher(hexString).matches();
    }

    /**
     * Validates hexadecimal string input and verifies its length.
     * Hexadecimal string input must contain 0x prefix.
     *
     * @param hexString        hexadecimal input
     * @param expectedByteSize expected byte size
     * @return true if
     */
    public static boolean isHexStringWithPrefixAndProperBytesSize(String hexString,
                                                                  int expectedByteSize) {
        return expectedByteSize > 0
                && isHexStringWithPrefix(hexString)
                && Numeric.hexStringToByteArray(hexString).length == expectedByteSize;
    }

    public static boolean isNonZeroedHexStringWithPrefixAndProperBytesSize(String hexString,
                                                                           int expectedByteSize) {
        return isHexStringWithPrefixAndProperBytesSize(hexString, expectedByteSize)
                && !Arrays.equals(Numeric.hexStringToByteArray(hexString),
                new byte[expectedByteSize]);
    }

    public static boolean isBytes32(String hexString) {
        return isHexStringWithPrefixAndProperBytesSize(hexString, BYTES_32_SIZE);
    }

    public static boolean isNonZeroedBytes32(String hexString) {
        return isNonZeroedHexStringWithPrefixAndProperBytesSize(hexString, BYTES_32_SIZE);
    }

    @Deprecated
    public static boolean isBytes32(byte[] bytes){
        return bytes != null && bytes.length == BYTES_32_SIZE;
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