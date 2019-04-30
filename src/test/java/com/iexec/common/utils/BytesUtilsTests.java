package com.iexec.common.utils;

import org.junit.Test;

import static com.iexec.common.utils.BytesUtils.*;
import static org.junit.Assert.*;

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

    @Test
    public void shouldBeAByte32() {
        assertTrue(isByte32(bytes));
        assertTrue(isByte32(stringToBytes(hexaString)));
    }

    @Test
    public void shouldNotBeAByte32() {
        assertFalse(isByte32(stringToBytes("0xabc123defg")));
    }

    @Test
    public void shouldNotBeAByte32Null() {
        assertFalse(isByte32(new byte[0]));
        assertFalse(isByte32(new byte[128]));
    }
}
