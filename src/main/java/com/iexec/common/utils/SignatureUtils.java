package com.iexec.common.utils;

import org.web3j.crypto.*;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.security.SignatureException;

import com.iexec.common.security.Signature;

import static com.iexec.common.utils.BytesUtils.stringToBytes;

public class SignatureUtils {

    public static final Signature EMPTY_SIGNATURE =  new Signature();

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

    public static boolean isSignatureValid(byte[] message, Signature sign, String signerAddress) {
        BigInteger publicKey = null;
        Sign.SignatureData signatureData = new Sign.SignatureData(sign.getV(), sign.getR(), sign.getS());

        try {
            publicKey = Sign.signedPrefixedMessageToKey(message, signatureData);
        } catch (SignatureException e) {
            e.printStackTrace();
            return false;
        }

        if (publicKey == null) return false;

        String addressRecovered = "0x" + Keys.getAddress(publicKey);
        return addressRecovered.equalsIgnoreCase(signerAddress);
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

    public static Signature emptySignature() {
        return new Signature (
            BytesUtils.stringToBytes(BytesUtils.EMPTY_HEXASTRING_64),
            BytesUtils.stringToBytes(BytesUtils.EMPTY_HEXASTRING_64),
            new byte[1]
        );

    }

    private static String createStringFromSignature(Sign.SignatureData sign) {
        String r = Numeric.toHexString(sign.getR());
        String s = Numeric.toHexString(sign.getS());
        String v = Numeric.toHexString(sign.getV());
        return String.join("", r, Numeric.cleanHexPrefix(s), v);
    }

    /*
     * web3j signMessageHash(..) base method [built on EthereumMessageHash]
     * */
    private static Sign.SignatureData signMessageHash(String messageHash, ECKeyPair ecKeyPair) {
        return Sign.signPrefixedMessage(stringToBytes(messageHash), ecKeyPair);
    }

    /*
     * web3j signMessageHash(..) returns Sign.SignatureData
     * */
    public static Sign.SignatureData signMessageHashAndGetSignatureData(String messageHash, String privateKey) {
        ECKeyPair ecKeyPair = ECKeyPair.create(BytesUtils.stringToBytes32(privateKey));
        return signMessageHash(messageHash, ecKeyPair);
    }

    /*
     * iExec signMessageHash(..) returns Signature
     * */
    public static Signature signMessageHashAndGetSignature(String messageHash, String privateKey) {
        Sign.SignatureData signatureData = signMessageHashAndGetSignatureData(messageHash, privateKey);
        return new Signature(signatureData);
    }

    /*
     * web3j signedMessageHashToSignerAddress(..) accepts Sign.SignatureData [built on EthereumMessageHash]
     * */
    public static String signedMessageHashToSignerAddress(String messageHash, Sign.SignatureData signatureData){
        String signerAddress = "";
        try {
            BigInteger publicKey = Sign.signedPrefixedMessageToKey(stringToBytes(messageHash), signatureData);
            signerAddress = Numeric.prependHexPrefix(Keys.getAddress(publicKey));
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return signerAddress;
    }

    /*
     * iExec signedMessageHashToSignerAddress(..) accepts Signature
     * */
    public static String signedMessageHashToSignerAddress(String messageHash, Signature signature){
        Sign.SignatureData signatureData = new Sign.SignatureData(signature.getV()[0], signature.getR(), signature.getS());
        return signedMessageHashToSignerAddress(messageHash, signatureData);
    }

    /*
     * iExec isExpectedSignerOnSignedMessageHash(..) accepts Signature
     * */
    public static boolean isExpectedSignerOnSignedMessageHash(String messageHash, Signature signature, String expectedSigner){
        String signerAddress = signedMessageHashToSignerAddress(messageHash, signature);
        return signerAddress.equalsIgnoreCase(expectedSigner);
    }

}
