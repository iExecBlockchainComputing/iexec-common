package com.iexec.common.utils;

import org.bouncycastle.util.Arrays;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import java.nio.charset.StandardCharsets;

public class HashUtils {

    private HashUtils() {
        throw new UnsupportedOperationException();
    }

    public static String concatenateAndHash(String... hexaString) {

        // convert
        byte[] res = new byte[0];
        for (String str : hexaString) {
            res = Arrays.concatenate(res, BytesUtils.stringToBytes(str));
        }

        // Hash the result and convert to String
        return Numeric.toHexString(Hash.sha3(res));
    }

    public static String sha256(String utf8Input) {
        byte[] input = utf8Input.getBytes(StandardCharsets.UTF_8);
        byte[] hexHash = Hash.sha256(input);
        return BytesUtils.bytesToString(hexHash);
    }
}
