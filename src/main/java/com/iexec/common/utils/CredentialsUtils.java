package com.iexec.common.utils;

import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

public class CredentialsUtils {

    private CredentialsUtils() {
        throw new UnsupportedOperationException();
    }

    public static String getAddress(String privateKey) {
        ECKeyPair ecKeyPair = ECKeyPair.create(BytesUtils.stringToBytes32(privateKey));
        return Numeric.prependHexPrefix(Keys.getAddress(ecKeyPair));
    }

}
