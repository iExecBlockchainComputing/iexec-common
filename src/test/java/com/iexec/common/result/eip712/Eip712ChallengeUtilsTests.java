/*
 * Copyright 2020 IEXEC BLOCKCHAIN TECH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iexec.common.result.eip712;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Eip712ChallengeUtilsTests {

    private Eip712Challenge eip712Challenge;

    @BeforeEach
    public void init() {
        eip712Challenge = new Eip712Challenge("0x10ff103511e3e233033628dbd641136d4670c16c33a4ce11950ab316ef18bce9", 17);
    }

    @Test
    void shouldGetChallengeAsJson() throws JsonProcessingException {
        assertEquals("{" +
                "\"types\":{\"EIP712Domain\":[" +
                    "{\"name\":\"name\",\"type\":\"string\"}," +
                    "{\"name\":\"version\",\"type\":\"string\"}," +
                    "{\"name\":\"chainId\",\"type\":\"uint256\"}" +
                    "]," +
                "\"Challenge\":[{\"name\":\"challenge\",\"type\":\"string\"}]}," +
                "\"domain\":{\"name\":\"iExec Result Repository\",\"version\":\"1\",\"chainId\":17}," +
                "\"primaryType\":\"Challenge\"," +
                "\"message\":{\"challenge\":\"0x10ff103511e3e233033628dbd641136d4670c16c33a4ce11950ab316ef18bce9\"}" +
                "}", new ObjectMapper().writeValueAsString(eip712Challenge));
    }

    @Test
    public void shouldGetCorrectDomainSeparator() {
        assertEquals(Eip712ChallengeUtils.getDomainSeparator(eip712Challenge), "0x73b2ffe0e9f80f155eba2ca6ad915b2bd92e0d89c354112dc63b6dd70c30f51e");
    }

    @Test
    public void shouldGetCorrectMessageHash() {
        assertEquals(Eip712ChallengeUtils.getMessageHash(eip712Challenge), "0x2ff97da3e19fd11436479ffcec54baa5501c439d8a65f01dec5a217f7bf4bc70");
    }

    @Test
    public void shouldGetCorrectChallengeHashToSign() {
        assertEquals(Eip712ChallengeUtils.getEip712ChallengeString(eip712Challenge), "0x3bb958b947bc47479a7ee767d74a45146e41ac703d989d72f2b9c8f1aaf00a13");
    }


}