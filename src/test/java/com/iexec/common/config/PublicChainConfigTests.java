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

public class PublicChainConfigTests {

    private final String errorMessage = "Java 8 date/time type `java.time.Duration` not supported by default";

    @Test
    void serializationShouldFailIfNoJavaTimeModule() {
        ObjectMapper mapper = new ObjectMapper();
        PublicChainConfig config = new PublicChainConfig();
        config.setBlockTime(Duration.ofSeconds(5L));
        config.setChainId(1);
        config.setChainNodeUrl("http://localhost:8545");
        config.setIexecHubContractAddress("0xabcd");
        config.setSidechain(false);
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
        PublicChainConfig refChainConfig = new PublicChainConfig();
        refChainConfig.setBlockTime(Duration.ofSeconds(5L));
        refChainConfig.setChainId(15);
        refChainConfig.setChainNodeUrl("http://localhost:8545");
        refChainConfig.setIexecHubContractAddress("0xabcd");
        refChainConfig.setSidechain(true);
        String jsonString = mapper.writeValueAsString(refChainConfig);
        PublicChainConfig chainConfig = mapper.readValue(jsonString, PublicChainConfig.class);
        assertThat(chainConfig).usingRecursiveComparison().isEqualTo(refChainConfig);
    }

}
