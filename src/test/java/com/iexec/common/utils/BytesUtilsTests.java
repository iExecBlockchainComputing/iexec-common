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

public class BytesUtilsTests {

    private String hexaString = "0x00916a7d68e0ed8714fde137ed60de0e586e75467ae6ca0b090950f772ca9ac8";
    private byte[] bytes = new byte[]{0, -111, 106, 125, 104, -32, -19, -121, 20, -3, -31, 55, -19, 96, -34, 14, 88, 110,
            117, 70, 122, -26, -54, 11, 9, 9, 80, -9, 114, -54, -102, -56};

    @Test
    public void shouldBeValid() {
        assertEquals(hexaString, bytesToString(bytes));
        assertArrayEquals(stringToBytes(hexaString), bytes);

        assertEquals(bytesToString(stringToBytes(hexaString)), hexaString);
        assertArrayEquals(stringToBytes(bytesToString(bytes)), bytes);
    }

    @Test
    public void shouldBeHexStringWithPrefix() {
        assertTrue(isHexStringWithPrefix("0xabc123"));
    }

    @Test
    public void shouldNotBeHexStringWithPrefixSinceNoPrefix() {
        assertFalse(isHexStringWithPrefix("abc123"));
    }

    @Test
    public void shouldNotBeHexStringWithPrefixSinceNotHex() {
        assertFalse(isHexStringWithPrefix("0xabc123defg"));
    }

    @Test
    public void shouldNotBeHexStringWithPrefixSinceMissing() {
        assertFalse(isHexStringWithPrefix("0x"));
    }

    @Test
    public void shouldNotBeHexStringWithPrefixSinceEmpty() {
        assertFalse(isHexStringWithPrefix(""));
    }

    @Test
    public void shouldNotBeHexStringWithPrefixSinceNull() {
        assertFalse(isHexStringWithPrefix(null));
    }


    @Test
    public void shouldBeHexString() {
        assertTrue(isHexString("0xabc123"));
    }

    @Test
    public void shouldBeHexStringWithoutPrefix() {
        assertTrue(isHexString("abc123"));
    }

    @Test
    public void shouldNotBeHexStringSinceNotHexa() {
        assertFalse(isHexString("0xabc123defg"));
    }

    @Test
    public void shouldNotBeHexStringSinceEmptyWithPrefix() {
        assertFalse(isHexString("0x"));
    }

    @Test
    public void shouldNotBeHexStringSinceEmpty() {
        assertFalse(isHexString(""));
    }

    @Test
    public void shouldNotBeHexStringSinceNull() {
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
    public void shouldBeABytes32() {
        assertTrue(isBytes32(bytes));
        assertTrue(isBytes32(stringToBytes(hexaString)));
    }

    @Test
    public void shouldNotBeAByte32() {
        assertFalse(isBytes32(stringToBytes("0xabc123defg")));
    }

    @Test
    public void shouldNotBeAByte32Null() {
        assertFalse(isBytes32(new byte[0]));
        assertFalse(isBytes32(new byte[128]));
    }

    @Test
    void shouldConvertHexStringToBytes32() {
        byte[] bytes32 = stringToBytes32(hexaString);
        assertEquals(32, bytes32.length);
        assertArrayEquals(this.bytes, bytes32);
        assertEquals(hexaString, bytesToString(bytes32));
    }

    @Test
    void shouldConvertShortHexStringToFullBytes32() {
        String shortHexString = "0x916a7d68e0ed8714fde137ed60de0e586e75467ae6ca0b090950f772ca9ac8"; //short version for 0x0091[...]c8
        byte[] bytes32 = stringToBytes32(shortHexString);
        assertEquals(32, bytes32.length);
        assertArrayEquals(this.bytes, bytes32);
        assertEquals(hexaString, bytesToString(bytes32)); //0x91[...]c8 becomes 0x0091[...]c8
    }

    @Test
    void shouldConvertNullHexStringToEmptyBytes32() {
        String hexString = null;
        byte[] bytes32 = stringToBytes32(hexString);
        assertEquals(0, bytes32.length);
        assertArrayEquals(new byte[0], bytes32);
        assertEquals("0x", bytesToString(bytes32));
    }

}
