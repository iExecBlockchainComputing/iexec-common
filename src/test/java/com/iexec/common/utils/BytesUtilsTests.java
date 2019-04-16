package com.iexec.common.utils;

import org.junit.Test;

import static com.iexec.common.utils.BytesUtils.bytesToString;
import static com.iexec.common.utils.BytesUtils.stringToBytes;
import static org.junit.Assert.*;

public class BytesUtilsTests {

	@Test
	public void shouldBeValid() {

        String hexaString = "0x9e916a7d68e0ed8714fde137ed60de0e586e75467ae6ca0b090950f772ca9ac8";
        byte[] bytes = new byte[]{-98, -111, 106, 125, 104, -32, -19, -121, 20, -3, -31, 55, -19, 96, -34, 14, 88, 110,
                117, 70, 122, -26, -54, 11, 9, 9, 80, -9, 114, -54, -102, -56};

        assertEquals(hexaString, bytesToString(bytes));
        assertArrayEquals(stringToBytes(hexaString), bytes);

        assertEquals(bytesToString(stringToBytes(hexaString)), hexaString);
        assertArrayEquals(stringToBytes(bytesToString(bytes)), bytes);

	}

    @Test
    public void shoudBeHexaStringWithPrefix() {
        assertTrue(BytesUtils.isHexaString("0xabc123"));
    }

    @Test
    public void shoudBeHexaStringWithoutPrefix() {
        assertTrue(BytesUtils.isHexaString("abc123"));
    }

    @Test
    public void shoudNotBeHexaStringSinceNotHexa() {
        assertFalse(BytesUtils.isHexaString("0xabc123defg"));
    }

    @Test
    public void shoudNotBeHexaStringSinceEmptyWithPrefix() {
        assertFalse(BytesUtils.isHexaString("0x"));
    }

    @Test
    public void shoudNotBeHexaStringSinceEmpty() {
        assertFalse(BytesUtils.isHexaString(""));
    }
}
