/*
 * Copyright 2022-2024 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.replicate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static com.iexec.common.replicate.ReplicateStatus.CREATED;
import static org.assertj.core.api.Assertions.assertThat;

class ReplicateStatusUpdateTests {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldSerializeAndDeserializeReplicateStatusUpdate() throws JsonProcessingException {
        ReplicateStatusUpdate statusUpdate = ReplicateStatusUpdate.poolManagerRequest(CREATED);
        Date date = statusUpdate.getDate();
        String jsonString = mapper.writeValueAsString(statusUpdate);
        String expectedString = "{"
                + "\"status\":\"CREATED\","
                + "\"modifier\":\"POOL_MANAGER\","
                + "\"date\":" + date.getTime() + ","
                + "\"details\":null,"
                + "\"success\":true"
                + "}";
        assertThat(jsonString).isEqualTo(expectedString);
        ReplicateStatusUpdate deserializedStatusUpdate = mapper.readValue(jsonString, ReplicateStatusUpdate.class);
        assertThat(deserializedStatusUpdate).usingRecursiveComparison().isEqualTo(statusUpdate);
    }

}
