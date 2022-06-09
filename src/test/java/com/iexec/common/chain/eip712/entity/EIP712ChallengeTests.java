/*
 * Copyright 2022 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.chain.eip712.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iexec.common.chain.eip712.EIP712Domain;
import com.iexec.common.chain.eip712.TypeParam;
import com.iexec.common.utils.HashUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class EIP712ChallengeTests {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldSerializeAndDeserialize() throws JsonProcessingException {
        Challenge challenge = Challenge.builder().challenge("challenge").build();
        EIP712Domain domain = new EIP712Domain();
        EIP712Challenge eip712Challenge = new EIP712Challenge(domain, challenge);
        String jsonString = mapper.writeValueAsString(eip712Challenge);
        EIP712Challenge deserializedChallenge = mapper.readValue(jsonString, EIP712Challenge.class);
        assertThat(deserializedChallenge).usingRecursiveComparison().isEqualTo(eip712Challenge);
    }

    @Test
    void shouldReturnJsonString() throws JsonProcessingException {
        Challenge challenge = Challenge.builder().challenge("challenge").build();
        EIP712Domain domain = new EIP712Domain("COMMON", "1", 15L, null);
        EIP712Challenge eip712Challenge = new EIP712Challenge(domain, challenge);
        assertThat(mapper.writeValueAsString(eip712Challenge))
                .isNotEmpty()
                .isEqualTo("{\"types\":{\"EIP712Domain\":[" +
                        "{\"name\":\"name\",\"type\":\"string\"}," +
                        "{\"name\":\"version\",\"type\":\"string\"}," +
                        "{\"name\":\"chainId\",\"type\":\"uint256\"}" +
                        "]," +
                        "\"Challenge\":[{\"name\":\"challenge\",\"type\":\"string\"}]}," +
                        "\"domain\":{\"name\":\"COMMON\",\"version\":\"1\",\"chainId\":15}," +
                        "\"message\":{\"challenge\":\"challenge\"}," +
                        "\"primaryType\":\"Challenge\"}");
        String domainType = "EIP712Domain(string name,string version,uint256 chainId)";
        String domainSeparator = HashUtils.concatenateAndHash(
                Numeric.toHexString(Hash.sha3(domainType.getBytes())),
                Numeric.toHexString(Hash.sha3(domain.getName().getBytes())),
                Numeric.toHexString(Hash.sha3(domain.getVersion().getBytes())),
                Numeric.toHexString(Numeric.toBytesPadded(BigInteger.valueOf(domain.getChainId()), 32)));
        assertThat(eip712Challenge.getDomain().getDomainSeparator())
                .isEqualTo(domainSeparator)
                .isEqualTo("0x032ca689dd6e3f3369c7a74aae8b74a5cf8d5deb2511d68755eed205595e57a5");
        String messageType = "Challenge(string challenge)";
        String messageHash = HashUtils.concatenateAndHash(
                Numeric.toHexString(Hash.sha3(messageType.getBytes())),
                Numeric.toHexString(Hash.sha3("challenge".getBytes())));
        assertThat(eip712Challenge.getMessageHash())
                .isEqualTo(messageHash)
                .isEqualTo("0xffa80746e15229833c32e80aa3a3c161e0c5a0e47ff6fd68e713c8501a1456e5");
        String hash = HashUtils.concatenateAndHash(
                "0x1901",
                domainSeparator,
                messageHash);
        assertThat(eip712Challenge.getHash())
                .isEqualTo(hash)
                .isEqualTo("0xea5ec041da81859f2c04a4876d5999ed8e66ad221b5b8699ca91f6814693a80e");
    }

    @Test
    void shouldValidateDomainTypeParams() {
        Challenge challenge = Challenge.builder().challenge("abcd").build();
        EIP712Domain domain = new EIP712Domain("OTHER DOMAIN", "2", 13L, null);
        EIP712Challenge eip712Challenge = new EIP712Challenge(domain, challenge);
        assertThat(eip712Challenge.getDomainTypeParams())
                .isEqualTo(List.of(
                        new TypeParam("name", "string"),
                        new TypeParam("version", "string"),
                        new TypeParam("chainId", "uint256")));
        String domainType = "EIP712Domain(string name,string version,uint256 chainId)";
        String domainSeparator = HashUtils.concatenateAndHash(
                Numeric.toHexString(Hash.sha3(domainType.getBytes())),
                Numeric.toHexString(Hash.sha3(domain.getName().getBytes())),
                Numeric.toHexString(Hash.sha3(domain.getVersion().getBytes())),
                Numeric.toHexString(Numeric.toBytesPadded(BigInteger.valueOf(domain.getChainId()), 32)));
        assertThat(eip712Challenge.getDomain().getDomainSeparator())
                .isEqualTo(domainSeparator)
                .isEqualTo("0x1ab64efc51b26dc09fa88c5377082acb34c14b43948c78d6a1472c0308a40aab");
    }

    @Test
    void shouldValidateTypeParams() {
        Challenge challenge = Challenge.builder().challenge("abcd").build();
        EIP712Domain domain = new EIP712Domain("OTHER DOMAIN", "2", 13L, null);
        EIP712Challenge eip712Challenge = new EIP712Challenge(domain, challenge);
        assertThat(eip712Challenge.getMessageTypeParams())
                .isEqualTo(List.of(new TypeParam("challenge", "string")));
        String messageType = "Challenge(string challenge)";
        String messageHash = HashUtils.concatenateAndHash(
                Numeric.toHexString(Hash.sha3(messageType.getBytes())),
                Numeric.toHexString(Hash.sha3("abcd".getBytes())));
        assertThat(eip712Challenge.getMessageHash())
                .isEqualTo(messageHash)
                .isEqualTo("0x58c09e3e047920707563968aa3b3d1782e643869669d82283250c6ef75c8792f");
    }

    @Test
    void shouldBuildAuthorizationTokenFromCredentials() {
        final Challenge challenge = Challenge.builder().challenge("abcd").build();
        final EIP712Domain domain = new EIP712Domain("OTHER DOMAIN", "2", 13L, null);
        final EIP712Challenge eip712Challenge = new EIP712Challenge(domain, challenge);
        final Credentials credentials = Credentials.create("0x2a46e8c1535792f6689b10d5c882c9363910c30751ec193ae71ec71630077909");

        final String expectedToken = "0xe001855eda78679dfa4972de06d1cf28c630561e17fc6b075130ce688f448bfe" +
                "_0xc0b3f255c47783e482e1932923dc388cfb5a737ebebdcec04b8ad7ac427c8c9d5155c3211a375704416b639ff8aa7571ef999122a0259bfaf1bbf822345505b11c" +
                "_0x2d29bfbec903479fe4ba991918bab99b494f2bef";

        final String token = eip712Challenge.buildAuthorizationTokenFromCredentials(credentials);
        assertThat(token)
                .isNotEmpty()
                .isEqualTo(expectedToken);
    }
}
