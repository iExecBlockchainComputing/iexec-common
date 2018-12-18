package com.iexec.common.utils;

import org.junit.Test;
import org.web3j.crypto.Hash;

import java.math.BigInteger;

import static com.iexec.common.chain.ChainUtils.generateChainTaskId;
import static org.junit.Assert.assertEquals;

public class ChainUtilsTests {

    @Test
    public void shouldBeCorrectOneValue(){
        String dealId = "0xa0b0fd396b0f79f14e4d6b34af7180bd9e80e2d86afda91c6127c5c17a268e66";
        BigInteger taskIndex = BigInteger.valueOf(0);
        String chainTaskId = generateChainTaskId(dealId, taskIndex);

        String expectedChainTaskId = "0xe06c86d6bb750dbd9be9e002482854b3b3f21550dbe37236767b9cac29e3ce28";
        assertEquals(chainTaskId, expectedChainTaskId);
    }

}
