/*
 * Copyright 2021 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.chain.eip712;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iexec.common.utils.HashUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class EIP712DomainTest {

    private static final String DOMAIN_NAME = "iExec Custom Module Domain";
    private static final String DOMAIN_VERSION = "1";
    private static final long DOMAIN_CHAIN_ID = 2;
    private static final String DOMAIN_VERIFYING_CONTRACT = "0x0000000000000000000000000000000000000001";
    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldGetDomainWithVerifyingContract() throws JsonProcessingException {
        EIP712Domain domain =
                new EIP712Domain(DOMAIN_NAME, DOMAIN_VERSION, DOMAIN_CHAIN_ID, DOMAIN_VERIFYING_CONTRACT);
        String jsonString = mapper.writeValueAsString(domain);
        assertThat(jsonString)
                .isEqualTo("{" +
                        "\"name\":\"iExec Custom Module Domain\"," +
                        "\"version\":\"1\"," +
                        "\"chainId\":2," +
                        "\"verifyingContract\":\"0x0000000000000000000000000000000000000001\"}");
        assertThat(domain.getDomainType())
                .isEqualTo("EIP712Domain(string name,string version,uint256 chainId,address verifyingContract)");
        String domainSeparator = HashUtils.concatenateAndHash(
                Numeric.toHexString(Hash.sha3(domain.getDomainType().getBytes(StandardCharsets.UTF_8))),
                Numeric.toHexString(Hash.sha3(domain.getName().getBytes(StandardCharsets.UTF_8))),
                Numeric.toHexString(Hash.sha3(domain.getVersion().getBytes(StandardCharsets.UTF_8))),
                Numeric.toHexString(Numeric.toBytesPadded(BigInteger.valueOf(domain.getChainId()), 32)),
                Numeric.toHexString(Numeric.toBytesPadded(Numeric.toBigInt(domain.getVerifyingContract()), 32)));
        assertThat(domain.getDomainSeparator())
                .isEqualTo(domainSeparator)
                .isEqualTo("0xb3b1dcc957351eff67866cbc947be104a71d09ff7e146a2c42e8f7edc2bf501c");
        EIP712Domain deserializedDomain = mapper.readValue(jsonString, EIP712Domain.class);
        assertThat(deserializedDomain)
                .usingRecursiveComparison()
                .isEqualTo(domain);
    }

    @Test
    void shouldGetDomainWithoutVerifyingContract() throws JsonProcessingException {
        EIP712Domain domain =
                new EIP712Domain(DOMAIN_NAME, DOMAIN_VERSION, DOMAIN_CHAIN_ID, null);
        String jsonString = mapper.writeValueAsString(domain);
        assertThat(jsonString)
                .isEqualTo("{" +
                        "\"name\":\"iExec Custom Module Domain\"," +
                        "\"version\":\"1\"," +
                        "\"chainId\":2" +
                        "}");
        assertThat(domain.getDomainType())
                .isEqualTo("EIP712Domain(string name,string version,uint256 chainId)");
        String domainSeparator = HashUtils.concatenateAndHash(
                Numeric.toHexString(Hash.sha3(domain.getDomainType().getBytes(StandardCharsets.UTF_8))),
                Numeric.toHexString(Hash.sha3(domain.getName().getBytes(StandardCharsets.UTF_8))),
                Numeric.toHexString(Hash.sha3(domain.getVersion().getBytes(StandardCharsets.UTF_8))),
                Numeric.toHexString(Numeric.toBytesPadded(BigInteger.valueOf(domain.getChainId()), 32)));
        assertThat(domain.getDomainSeparator())
                .isEqualTo(domainSeparator)
                .isEqualTo("0xc1ca0800eccc218e09b4b90ca3ba732ee9a923d115f880316caf56c466c0bc34");
        EIP712Domain deserializedDomain = mapper.readValue(jsonString, EIP712Domain.class);
        assertThat(deserializedDomain)
                .usingRecursiveComparison()
                .isEqualTo(domain);
    }

}
