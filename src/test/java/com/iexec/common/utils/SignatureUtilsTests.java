package com.iexec.common.utils;

import com.iexec.common.security.Signature;

import org.junit.Test;

public class SignatureUtilsTests {

    @Test
    public void name() {
        String message = "0x508286383bec0f87ca3b599255a0db8930e4f930ecba468bcded03057ff96fbc";
        String sign = "0x70894c9d321cccd3ee20f1bb3d05e4e8a91c12b42d5f827423085ab52e0684eb7b1372dc80dcac4ca83d843d6553be4480ccab390bcae851fbb22521cf2966df1c";
        String signerAddress = "0x1a69b2eb604db8eba185df03ea4f5288dcbbd248";
        Signature signature = new Signature(sign);

        boolean isValid = SignatureUtils.isSignatureValid(message, signature, signerAddress);
        boolean isValid2 = SignatureUtils.doesSignatureMatchesAddress(signature.getR(), signature.getS(), message, signerAddress);

        System.out.println("isValid: " + isValid);
        System.out.println("isValid2: " + isValid2);
    }
}