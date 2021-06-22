package com.iexec.common.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EthAddressTests {
    @Test
    public void validateAddress() {
        String validChecksumAddress = "0x1Ec09E1782a43a770D54e813379c730E0b29aD4B";
        // valid lowercase address
        assertTrue(EthAddress.validate(validChecksumAddress.toLowerCase()));
        // valid uppercase address
        assertTrue(EthAddress.validate(validChecksumAddress.toUpperCase().replace("0X", "0x")));
        // valid checksum address
        assertTrue(EthAddress.validate(validChecksumAddress));

        // invalid non-hex address
        assertFalse(EthAddress.validate(validChecksumAddress.replace("c", "z")));
        // invalid non-alphanumeric address
        assertFalse(EthAddress.validate(validChecksumAddress.replace("c", "&")));
        // invalid length address
        assertFalse(EthAddress.validate(validChecksumAddress.substring(0, 41)));
        // invalid checksum address
        assertFalse(EthAddress.validate(validChecksumAddress.replace('E', 'e')));
    }
}
