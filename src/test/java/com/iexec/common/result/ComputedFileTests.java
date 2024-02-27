/*
 * Copyright 2024-2024 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ComputedFileTests {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldSerializeAndDeserialize() throws JsonProcessingException {
        ComputedFile computedFile = ComputedFile.builder().build();
        String jsonString = mapper.writeValueAsString(computedFile);
        assertThat(jsonString).isEqualTo("{\"deterministic-output-path\":null,\"callback-data\":null," +
                "\"task-id\":null,\"result-digest\":null,\"enclave-signature\":null,\"error-message\":null}");
        ComputedFile parsedComputedFile = mapper.readValue(jsonString, ComputedFile.class);
        assertThat(parsedComputedFile).usingRecursiveComparison().isEqualTo(computedFile);
        assertThat(computedFile).hasToString(
                "ComputedFile(deterministicOutputPath=null, callbackData=null, taskId=null" +
                        ", resultDigest=null, enclaveSignature=null, errorMessage=null)"
        );
    }
}
