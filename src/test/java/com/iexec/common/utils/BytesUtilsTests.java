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

    private String hexaString = "0x9e916a7d68e0ed8714fde137ed60de0e586e75467ae6ca0b090950f772ca9ac8";
    private byte[] bytes = new byte[]{-98, -111, 106, 125, 104, -32, -19, -121, 20, -3, -31, 55, -19, 96, -34, 14, 88, 110,
            117, 70, 122, -26, -54, 11, 9, 9, 80, -9, 114, -54, -102, -56};

    @Test
    public void shouldBeValid() {
        assertEquals(hexaString, bytesToString(bytes));
        assertArrayEquals(stringToBytes(hexaString), bytes);

        assertEquals(bytesToString(stringToBytes(hexaString)), hexaString);
        assertArrayEquals(stringToBytes(bytesToString(bytes)), bytes);
    }

    @Test
    public void shoudBeHexaStringWithPrefix() {
        assertTrue(isHexaString("0xabc123"));
    }

    @Test
    public void shoudBeHexaStringWithoutPrefix() {
        assertTrue(isHexaString("abc123"));
    }

    @Test
    public void shoudNotBeHexaStringSinceNotHexa() {
        assertFalse(isHexaString("0xabc123defg"));
    }

    @Test
    public void shoudNotBeHexaStringSinceEmptyWithPrefix() {
        assertFalse(isHexaString("0x"));
    }

    @Test
    public void shoudNotBeHexaStringSinceEmpty() {
        assertFalse(isHexaString(""));
    }

    // isHexStringWithProperBytesSize(..) tests

    @Test
    void shouldBeHexStringWithProperByteSize() {
        assertTrue(isHexStringWithProperBytesSize("0xabc123", 3));
    }

    @Test
    void shouldNotBeHexStringWithProperByteSizeSinceExpectedSizeTooSmall() {
        assertFalse(isHexStringWithProperBytesSize("0xabc123", 0));
    }

    @Test
    void shouldNotBeHexStringWithProperByteSizeSinceEmptyInput() {
        assertFalse(isHexStringWithProperBytesSize("", 3));
    }

    @Test
    void shouldNotBeHexStringWithProperByteSizeSinceNoPrefix() {
        assertFalse(isHexStringWithProperBytesSize("abc123", 3));
    }

    @Test
    void shouldNotBeHexStringWithProperByteSizeSinceWrongSize() {
        assertFalse(isHexStringWithProperBytesSize("0xabc123", 2));
    }

    // isHexStringWithNonEmptyProperBytesSize(..) tests

    @Test
    void shouldBeHexStringWithNonEmptyProperByteSize() {
        assertTrue(isHexStringWithNonEmptyProperBytesSize("0xabc123", 3));
    }

    @Test
    void shouldNotBeHexStringWithNonEmptyProperByteSizeSinceEmpty() {
        assertFalse(isHexStringWithNonEmptyProperBytesSize("0x000000", 3));
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
    public void shouldReturnSameStringSinceAlreadyBytes32() {
        byte[] bytes32 = stringToBytes32(hexaString);
        assertEquals(bytes32.length, 32);
        assertArrayEquals(bytes32, bytes);
        assertEquals(bytesToString(bytes32), hexaString);
    }

    @Test
    public void shouldPadStringToBeBytes32() {
        String notBytes32String = "0xabc";
        String bytes32String = "0xabc0000000000000000000000000000000000000000000000000000000000000";

        byte[] returnedBytes = stringToBytes32(notBytes32String);

        assertEquals(returnedBytes.length, 32);
        assertEquals(bytesToString(returnedBytes), bytes32String);
    }
}
