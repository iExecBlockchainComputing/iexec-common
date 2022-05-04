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

package com.iexec.common.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiResponseBodyTests {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldSerializeAndDeserializeWithErrors() throws JsonProcessingException {
        ApiResponseBody<Void, List<String>> responseBody = ApiResponseBody.<Void, List<String>>builder()
                .errors(List.of("error1", "error2"))
                .build();
        String jsonString = mapper.writeValueAsString(responseBody);
        assertThat(jsonString).isEqualTo("{\"errors\":[\"error1\",\"error2\"]}");
        ApiResponseBody<?, ?> deserializedResponseBody = mapper.readValue(jsonString, ApiResponseBody.class);
        assertThat(deserializedResponseBody).usingRecursiveComparison().isEqualTo(responseBody);
    }

    @Test
    void shouldSerializeAndDeserializeWithTypeInteger() throws JsonProcessingException {
        ApiResponseBody<Integer, Void> responseBody = ApiResponseBody.<Integer, Void>builder()
                .data(0)
                .build();
        String jsonString = mapper.writeValueAsString(responseBody);
        assertThat(jsonString).isEqualTo("{\"data\":0}");
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ApiResponseBody.class, Integer.class, Void.class);
        ApiResponseBody<String, Void> deserializedResponseBody = mapper.readValue(jsonString, javaType);
        assertThat(deserializedResponseBody).usingRecursiveComparison().isEqualTo(responseBody);
    }

    @Test
    void shouldSerializeAndDeserializeWithTypeString() throws JsonProcessingException {
        ApiResponseBody<String, Void> responseBody = ApiResponseBody.<String, Void>builder()
                .data("dummy-data")
                .build();
        String jsonString = mapper.writeValueAsString(responseBody);
        assertThat(jsonString).isEqualTo("{\"data\":\"dummy-data\"}");
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ApiResponseBody.class, String.class, Void.class);
        ApiResponseBody<String, Void> deserializedResponseBody = mapper.readValue(jsonString, javaType);
        assertThat(deserializedResponseBody).usingRecursiveComparison().isEqualTo(responseBody);
    }

}
