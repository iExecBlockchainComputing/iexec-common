package com.iexec.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import java.util.regex.Pattern;

/**
 * Account utility functions.
 */
public class EthAddress extends org.web3j.abi.datatypes.Address {

    public EthAddress(String hexValue) {
        super(hexValue);
    }

    private static final String IGNORE_CASE_ADDRESS_PATTERN = "(?i)^(0x)?[0-9a-f]{40}$";
    private static final String LOWERCASE_ADDRESS_PATTERN = "^(0x)?[0-9a-f]{40}$";
    private static final String UPPERCASE_ADDRESS_PATTERN = "^(0x)?[0-9A-F]{40}$";

    /**
     * Check if the given string is a valid Ethereum address. Inspired by
     * the web3js implementation.
     * 
     * @param address in hex
     * @return true if address is valid, false otherwise
     * @see https://github.com/ChainSafe/web3.js/blob/
     * 5d027191c5cb7ffbcd44083528bdab19b4e14744/packages/web3-utils/src/
     * utils.js#L88
     */
    public static boolean validate(String address) {
        // check address is not empty and contains the valid
        // number (40 without 0x) and type (alphanumeric) of characters
        if (StringUtils.isEmpty(address) ||
                !matchesRegex(address, IGNORE_CASE_ADDRESS_PATTERN)) {
            return false;
        }
        // check for all upper/lower case
        if (matchesRegex(address, LOWERCASE_ADDRESS_PATTERN)
                || matchesRegex(address, UPPERCASE_ADDRESS_PATTERN)) {
            return true;
        }
        // validate checksum address when mixed case
        return validateChecksummed(address);
    }

    /**
     * Checks if the given string is a checksummed address. Inspired by
     * the web3js implementation.
     * 
     * @param address in hex
     * @return true if valid checksummed address, false otherwise
     * @see https://github.com/ChainSafe/web3.js/blob/
     * 5d027191c5cb7ffbcd44083528bdab19b4e14744/packages/web3-utils/src/
     * utils.js#L110
     */
    public static boolean validateChecksummed(String address) {
        // check address is not empty and contains the valid
        // number (40 without 0x) and type (alphanumeric) of characters
        if (StringUtils.isEmpty(address) ||
                !matchesRegex(address, IGNORE_CASE_ADDRESS_PATTERN)) {
            return false;
        }
        address = Numeric.cleanHexPrefix(address);
        String hash = Numeric.toHexStringNoPrefix(Hash.sha3(address.toLowerCase().getBytes()));
        for (int i = 0; i < 40; i++) {
            if (!Character.isLetter(address.charAt(i))) {
                continue;
            }
            // each uppercase letter should correlate with a first bit of 1 in the hash
            // char with the same index, and each lowercase letter with a 0 bit
            int charInt = Integer.parseInt(Character.toString(hash.charAt(i)), 16);
            if ((Character.isUpperCase(address.charAt(i)) && charInt <= 7)
            || (Character.isLowerCase(address.charAt(i)) && charInt > 7)) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean matchesRegex(String address, String regex) {
        return Pattern.compile(regex).matcher(address).find();
    }
}