package com.iexec.common.security;

import org.junit.Test;

public class SignatureTests {

    private String validSignature = "0x1b0b90d9f17a30d42492c8a2f98a24374600729a98d4e0b663a44ed48b589cab0e445eec300245e590150c7d88340d902c27e0d8673f3257cb8393f647d6c75c1b";
    private String dummyWallet = "0xabcd1339Ec7e762e639f4887E2bFe5EE8023E23E";

    @Test
    public void shouldBeValid() {
        Signature sign = new Signature(validSignature);
        Signature sign2 = new Signature(sign.getR(), sign.getS(), sign.getV());


    }

}
