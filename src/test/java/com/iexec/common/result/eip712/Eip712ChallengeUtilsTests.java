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
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Eip712ChallengeUtilsTests {

    private static final Eip712Challenge eip712Challenge =
            new Eip712Challenge("0x10ff103511e3e233033628dbd641136d4670c16c33a4ce11950ab316ef18bce9", 17);
    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldSerializeAndDeserializeEip712Challenge() throws JsonProcessingException {
        String jsonString = mapper.writeValueAsString(eip712Challenge);
        Eip712Challenge deserializedChallenge = mapper.readValue(jsonString, Eip712Challenge.class);
        assertThat(deserializedChallenge).usingRecursiveComparison().isEqualTo(eip712Challenge);
    }

    @Test
    void shouldGetChallengeAsJson() throws JsonProcessingException {
        assertThat(mapper.writeValueAsString(eip712Challenge))
                .isEqualTo("{" +
                "\"types\":{\"EIP712Domain\":[" +
                    "{\"name\":\"name\",\"type\":\"string\"}," +
                    "{\"name\":\"version\",\"type\":\"string\"}," +
                    "{\"name\":\"chainId\",\"type\":\"uint256\"}" +
                    "]," +
                "\"Challenge\":[{\"name\":\"challenge\",\"type\":\"string\"}]}," +
                "\"domain\":{\"name\":\"iExec Result Repository\",\"version\":\"1\",\"chainId\":17}," +
                "\"message\":{\"challenge\":\"0x10ff103511e3e233033628dbd641136d4670c16c33a4ce11950ab316ef18bce9\"}," +
                "\"primaryType\":\"Challenge\"" +
                "}");
    }

    @Test
    void shouldGetCorrectDomainSeparator() {
        assertThat(Eip712ChallengeUtils.getDomainSeparator(eip712Challenge))
                .isEqualTo("0x73b2ffe0e9f80f155eba2ca6ad915b2bd92e0d89c354112dc63b6dd70c30f51e");
    }

    @Test
    void shouldGetCorrectMessageHash() {
        assertThat(Eip712ChallengeUtils.getMessageHash(eip712Challenge))
                .isEqualTo("0x2ff97da3e19fd11436479ffcec54baa5501c439d8a65f01dec5a217f7bf4bc70");
    }

    @Test
    void shouldGetCorrectChallengeHashToSign() {
        assertThat(Eip712ChallengeUtils.getEip712ChallengeString(eip712Challenge))
                .isEqualTo("0x3bb958b947bc47479a7ee767d74a45146e41ac703d989d72f2b9c8f1aaf00a13");
    }


}