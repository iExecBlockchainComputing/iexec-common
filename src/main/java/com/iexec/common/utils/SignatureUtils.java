package com.iexec.common.utils;

import org.web3j.crypto.*;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

import com.iexec.common.security.Signature;

public class SignatureUtils {

    private SignatureUtils() {
        throw new UnsupportedOperationException();
    }

    public static boolean doesSignatureMatchesAddress(byte[] signatureR,
                                                      byte[] signatureS,
                                                      String hashToCheck,
                                                      String signerAddress){
        // check that the public address of the signer can be found
        for (int i = 0; i < 4; i++) {
            BigInteger publicKey = Sign.recoverFromSignature((byte) i,
                    new ECDSASignature(
                            new BigInteger(1, signatureR),
                            new BigInteger(1, signatureS)),
                    BytesUtils.stringToBytes(hashToCheck));

            if (publicKey != null) {
                String addressRecovered = "0x" + Keys.getAddress(publicKey);

                if (addressRecovered.equals(signerAddress)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static Signature hashAndSign(String stringToSign, String walletAddress, ECKeyPair ecKeyPair) {
        byte[] message = Hash.sha3(BytesUtils.stringToBytes(stringToSign));
        Sign.SignatureData sign = Sign.signMessage(message, ecKeyPair, false);

        return new Signature(sign.getR(), sign.getS(), sign.getV());
    }

    public static String hashAndSignAsString(String stringToSign, ECKeyPair ecKeyPair) {
        byte[] message = Hash.sha3(BytesUtils.stringToBytes(stringToSign));
        Sign.SignatureData sign = Sign.signMessage(message, ecKeyPair, false);
        return createStringFromSignature(sign);
    }

    public static String signAsString(String stringToSign, ECKeyPair ecKeyPair) {
        byte[] message = Numeric.hexStringToByteArray(stringToSign);
        Sign.SignatureData sign = Sign.signMessage(message, ecKeyPair, false);
        return createStringFromSignature(sign);
    }

    private static String createStringFromSignature(Sign.SignatureData sign) {
        String r = Numeric.toHexString(sign.getR());
        String s = Numeric.toHexString(sign.getS());
        String v = Integer.toHexString(sign.getV());
        return String.join("", r, Numeric.cleanHexPrefix(s), v);
    }

}
