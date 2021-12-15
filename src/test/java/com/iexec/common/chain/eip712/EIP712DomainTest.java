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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EIP712DomainTest {

    private static final String DOMAIN_NAME = "iExec Custom Module Domain";
    private static final String DOMAIN_VERSION = "1";
    private static final long DOMAIN_CHAIN_ID = 2;
    private static final String DOMAIN_VERIFYING_CONTRACT = "0x0000000000000000000000000000000000000001";

    @Test
    void shouldGetDomain() throws JsonProcessingException {
        Assertions.assertEquals("{" +
                        "\"name\":\"iExec Custom Module Domain\"," +
                        "\"version\":\"1\"," +
                        "\"chainId\":2," +
                        "\"verifyingContract\":\"0x0000000000000000000000000000000000000001\"}",
                new ObjectMapper().writeValueAsString(new EIP712Domain(DOMAIN_NAME,
                        DOMAIN_VERSION,
                        DOMAIN_CHAIN_ID,
                        DOMAIN_VERIFYING_CONTRACT)));
    }

    @Test
    void shouldGetDomainWithoutVerifyingContract() throws JsonProcessingException {
        Assertions.assertEquals("{" +
                        "\"name\":\"iExec Custom Module Domain\"," +
                        "\"version\":\"1\"," +
                        "\"chainId\":2" +
                        "}",
                new ObjectMapper().writeValueAsString(new EIP712Domain(DOMAIN_NAME,
                        DOMAIN_VERSION,
                        DOMAIN_CHAIN_ID,
                        null)));
    }

}