package com.iexec.common.result.eip712;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TypesTests {

    @Test
    public void typeParamsToString() {
        Eip712Challenge eip712Challenge = new Eip712Challenge("abcd", 1);
        assertEquals(Types.typeParamsToString(eip712Challenge.getTypes().getDomainTypeParams()), "string name,string version,uint256 chainId");
    }
}