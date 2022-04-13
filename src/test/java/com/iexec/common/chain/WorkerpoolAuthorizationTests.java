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

package com.iexec.common.chain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkerpoolAuthorizationTests {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldSerializeAndDeserializeWorkerpoolAuthorization() throws JsonProcessingException {
        WorkerpoolAuthorization wpAuthorization = WorkerpoolAuthorization.builder()
                .chainTaskId("walletAddress")
                .chainTaskId("chainTaskId")
                .chainTaskId("enclaveChallenge")
                .build();
        String jsonString = mapper.writeValueAsString(wpAuthorization);
        WorkerpoolAuthorization deserializedWpAuthorization = mapper.readValue(jsonString, WorkerpoolAuthorization.class);
        assertThat(deserializedWpAuthorization).usingRecursiveComparison().isEqualTo(wpAuthorization);
    }

}
