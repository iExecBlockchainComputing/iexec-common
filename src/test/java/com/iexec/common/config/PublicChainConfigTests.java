/*
 * Copyright 2023 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PublicChainConfigTests {

    private final String errorMessage = "Java 8 date/time type `java.time.Duration` not supported by default";

    @Test
    void serializationShouldFailIfNoJavaTimeModule() {
        ObjectMapper mapper = new ObjectMapper();
        PublicChainConfig config = PublicChainConfig.builder()
                .blockTime(Duration.ofSeconds(5L))
                .chainId(1)
                .chainNodeUrl("http://localhost:8545")
                .iexecHubContractAddress("0xabcd")
                .sidechain(false)
                .build();
        assertThatThrownBy(() -> mapper.writeValueAsString(config))
                .isInstanceOf(InvalidDefinitionException.class)
                .hasMessageContaining(errorMessage);
    }

    @Test
    void deserializationShouldFailIfNoJavaTimeModule() {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "{\"chainId\":1,\"chainNodeUrl\":\"http://localhost:8545\"," +
                "\"iexecHubContractAddress\":\"0xabcd\",\"blockTime\":5.000000000,\"sidechain\":false}";
        assertThatThrownBy(() -> mapper.readValue(jsonString, PublicChainConfig.class))
                .isInstanceOf(InvalidDefinitionException.class)
                .hasMessageContaining(errorMessage);
    }

    @Test
    void shouldSerializeAndDeserializePublicChainConfig() throws JsonProcessingException {
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        PublicChainConfig refChainConfig = PublicChainConfig.builder()
                .blockTime(Duration.ofSeconds(5L))
                .chainId(15)
                .chainNodeUrl("http://localhost:8545")
                .iexecHubContractAddress("0xabcd")
                .sidechain(true)
                .build();
        String jsonString = mapper.writeValueAsString(refChainConfig);
        PublicChainConfig chainConfig = mapper.readValue(jsonString, PublicChainConfig.class);
        assertThat(chainConfig).usingRecursiveComparison().isEqualTo(refChainConfig);
    }

}
