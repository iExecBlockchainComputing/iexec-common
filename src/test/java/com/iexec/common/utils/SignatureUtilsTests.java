package com.iexec.common.utils;

import com.iexec.common.security.Signature;
import org.junit.Assert;
import org.junit.Test;
import org.web3j.crypto.Hash;

import static com.iexec.common.utils.SignatureUtils.isExpectedSignerOnSignedMessageHash;
import static com.iexec.common.utils.SignatureUtils.signMessageHashAndGetSignature;
import static org.junit.Assert.assertEquals;

public class SignatureUtilsTests {

    @Test
    public void shouldMatchExpectedSigner(){
        String messageHash = "0xf0cea2ffdb802c106aef2a032b01c7d271a454473709016c2e2c406097acdfd3";
        String privateKey = "0x6dacd24b3d49d0c50c555aa728c60a57aa08beb363e3a90cce2e4e5d327c6ee2";
        String address = CredentialsUtils.getAddress(privateKey);

        Signature signature = signMessageHashAndGetSignature(messageHash, privateKey);

        boolean isExpectedSigner = isExpectedSignerOnSignedMessageHash(messageHash, signature, address);

        Assert.assertTrue(isExpectedSigner);
    }

}
