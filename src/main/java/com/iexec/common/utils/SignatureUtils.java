package com.iexec.common.utils;

import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Sign;

public class SignatureUtils {

    private SignatureUtils() {
        throw new UnsupportedOperationException();
    }

    //Note to dev: The following block is copied/pasted from web3j 4.0-beta at this commit:
    // https://github.com/web3j/web3j/commit/4997746e566faaf9c88defad78af54ede24db65b
    // Once we update to web3j 4.0, the signPrefixedMessage method should be directly available and this block should
    // be deleted

    //!!!!!!!!!!! Beginning block web3j 4.0-beta !!!!!!!!!!!
    public static final String MESSAGE_PREFIX = "\u0019Ethereum Signed Message:\n";

    public static byte[] getEthereumMessagePrefix(int messageLength) {
        return MESSAGE_PREFIX.concat(String.valueOf(messageLength)).getBytes();
    }

    public static byte[] getEthereumMessageHash(byte[] message) {
        byte[] prefix = getEthereumMessagePrefix(message.length);
        byte[] result = new byte[prefix.length + message.length];
        System.arraycopy(prefix, 0, result, 0, prefix.length);
        System.arraycopy(message, 0, result, prefix.length, message.length);
        return Hash.sha3(result);
    }

    public static Sign.SignatureData signPrefixedMessage(byte[] message, ECKeyPair keyPair) {
        return Sign.signMessage(getEthereumMessageHash(message), keyPair, false);
    }
    // !!!!!!!!!!! End block web3j 4.0-beta !!!!!!!!!!!
}
