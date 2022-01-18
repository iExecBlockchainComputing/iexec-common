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

import org.junit.jupiter.api.Test;

import static com.iexec.common.utils.BytesUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class BytesUtilsTests {

    private String hexaString = "0x00916a7d68e0ed8714fde137ed60de0e586e75467ae6ca0b090950f772ca9ac8";
    private byte[] bytes = new byte[]{0, -111, 106, 125, 104, -32, -19, -121, 20, -3, -31, 55, -19, 96, -34, 14, 88, 110,
            117, 70, 122, -26, -54, 11, 9, 9, 80, -9, 114, -54, -102, -56};

    @Test
    void shouldBeValid() {
        assertEquals(hexaString, bytesToString(bytes));
        assertArrayEquals(stringToBytes(hexaString), bytes);

        assertEquals(bytesToString(stringToBytes(hexaString)), hexaString);
        assertArrayEquals(stringToBytes(bytesToString(bytes)), bytes);
    }

    @Test
    void shouldBeHexStringWithPrefix() {
        assertTrue(isHexStringWithPrefix("0xabc123"));
    }

    @Test
    void shouldNotBeHexStringWithPrefixSinceNoPrefix() {
        assertFalse(isHexStringWithPrefix("abc123"));
    }

    @Test
    void shouldNotBeHexStringWithPrefixSinceNotHex() {
        assertFalse(isHexStringWithPrefix("0xabc123defg"));
    }

    @Test
    void shouldNotBeHexStringWithPrefixSinceMissing() {
        assertFalse(isHexStringWithPrefix("0x"));
    }

    @Test
    void shouldNotBeHexStringWithPrefixSinceEmpty() {
        assertFalse(isHexStringWithPrefix(""));
    }

    @Test
    void shouldNotBeHexStringWithPrefixSinceNull() {
        assertFalse(isHexStringWithPrefix(null));
    }


    @Test
    void shouldBeHexString() {
        assertTrue(isHexString("0xabc123"));
    }

    @Test
    void shouldBeHexStringWithoutPrefix() {
        assertTrue(isHexString("abc123"));
    }

    @Test
    void shouldNotBeHexStringSinceNotHexa() {
        assertFalse(isHexString("0xabc123defg"));
    }

    @Test
    void shouldNotBeHexStringSinceEmptyWithPrefix() {
        assertFalse(isHexString("0x"));
    }

    @Test
    void shouldNotBeHexStringSinceEmpty() {
        assertFalse(isHexString(""));
    }

    @Test
    void shouldNotBeHexStringSinceNull() {
        assertFalse(isHexString(null));
    }

    // isHexStringWithProperBytesSize(..) tests

    @Test
    void shouldBeHexStringWithProperByteSize() {
        assertTrue(isHexStringWithPrefixAndProperBytesSize("0xabc123", 3));
    }

    @Test
    void shouldBeHexStringWithProperByteSizeEvenWithZeroedArray() {
        assertTrue(isHexStringWithPrefixAndProperBytesSize("0x000000", 3));
    }

    @Test
    void shouldNotBeHexStringWithProperByteSizeSinceExpectedSizeTooSmall() {
        assertFalse(isHexStringWithPrefixAndProperBytesSize("0xabc123", 0));
    }

    @Test
    void shouldNotBeHexStringWithProperByteSizeSinceEmptyInput() {
        assertFalse(isHexStringWithPrefixAndProperBytesSize("", 3));
    }

    @Test
    void shouldNotBeHexStringWithProperByteSizeSinceNoPrefix() {
        assertFalse(isHexStringWithPrefixAndProperBytesSize("abc123", 3));
    }

    @Test
    void shouldNotBeHexStringWithProperByteSizeSinceWrongSize() {
        assertFalse(isHexStringWithPrefixAndProperBytesSize("0xabc123", 2));
    }

    // isNonZeroedHexStringWithProperBytesSize(..) tests

    @Test
    void shouldBeNonZeroedHexStringWithProperBytesSize() {
        assertTrue(isNonZeroedHexStringWithPrefixAndProperBytesSize("0xabc123", 3));
    }

    @Test
    void shouldNotBeNonZeroedHexStringWithProperBytesSizeSinceZeroed() {
        assertFalse(isNonZeroedHexStringWithPrefixAndProperBytesSize("0x000000", 3));
    }

    @Test
    void shouldConvertHexStringToBytes32() {
        byte[] bytes32 = hexStringToBytes32(hexaString);
        assertEquals(32, bytes32.length);
        assertArrayEquals(this.bytes, bytes32);
        assertEquals(hexaString, bytesToString(bytes32));
    }

    @Test
    void shouldConvertShortHexStringToFullBytes32() {
        String shortHexString = "0x916a7d68e0ed8714fde137ed60de0e586e75467ae6ca0b090950f772ca9ac8"; //short version for 0x0091[...]c8
        byte[] bytes32 = hexStringToBytes32(shortHexString);
        assertEquals(32, bytes32.length);
        assertArrayEquals(this.bytes, bytes32);
        assertEquals(hexaString, bytesToString(bytes32)); //0x91[...]c8 becomes 0x0091[...]c8
    }

    @Test
    void shouldThrowOnConvertHexStringToBytes32SinceNullInput() {
        assertThrows(IllegalArgumentException.class, () -> hexStringToBytes32(null));
    }

    @Test
    void shouldThrowOnConvertHexStringToBytes32SinceTooLong() {
        String longHexString = "0x10010000000000000000000000000000000000000000000000000000000000001"; //2 + 1 + 64
        assertThrows(IllegalArgumentException.class, () -> hexStringToBytes32(longHexString));
    }

}
