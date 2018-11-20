package com.iexec.common.utils;

import org.junit.Test;
import org.web3j.crypto.Hash;

import static org.junit.Assert.assertEquals;

public class HashUtilsTests {

    @Test
    public void shouldBeCorrectOneValue(){
        String hexa1 = "0x748e091bf16048cb5103E0E10F9D5a8b7fBDd860";

        String expected = Hash.sha3(hexa1);

        assertEquals(expected, HashUtils.concatenateAndHash(hexa1));
    }

    @Test
    public void shouldBeCorrectTwoValues(){
        String hexa1 = "0x748e091bf16048cb5103E0E10F9D5a8b7fBDd860";
        String hexa2 = "0xd94b63fc2d3ec4b96daf84b403bbafdc8c8517e8e2addd51fec0fa4e67801be8";

        String expected = "0x9ca8cbf81a285c62778678c874dae13fdc6857566b67a9a825434dd557e18a8d";

        assertEquals(expected, HashUtils.concatenateAndHash(hexa1, hexa2));
    }

    @Test
    public void shouldBeCorrectThreeValues(){
        String hexa1 = "0x748e091bf16048cb5103E0E10F9D5a8b7fBDd860";
        String hexa2 = "0xd94b63fc2d3ec4b96daf84b403bbafdc8c8517e8e2addd51fec0fa4e67801be8";
        String hexa3 = "0x9a43BB008b7A657e1936ebf5d8e28e5c5E021596";

        String expected = "0x54a76d209e8167e1ffa3bde8e3e7b30068423ca9554e1d605d8ee8fd0f165562";

        assertEquals(expected, HashUtils.concatenateAndHash(hexa1, hexa2, hexa3));
    }
}
