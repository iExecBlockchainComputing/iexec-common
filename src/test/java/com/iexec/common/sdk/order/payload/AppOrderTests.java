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

package com.iexec.common.sdk.order.payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class AppOrderTests {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldSerializeAndDeserialize() throws JsonProcessingException {
        AppOrder appOrder = AppOrder.builder().build();
        String jsonString = mapper.writeValueAsString(appOrder);
        assertThat(jsonString).isEqualTo("{\"volume\":null,\"tag\":null,\"salt\":null,\"sign\":null," +
                "\"app\":null,\"appprice\":null,\"datasetrestrict\":\"\",\"workerpoolrestrict\":\"\",\"requesterrestrict\":\"\"}");
        AppOrder parsedAppOrder = mapper.readValue(jsonString, AppOrder.class);
        assertThat(parsedAppOrder).usingRecursiveComparison().isEqualTo(appOrder);
    }

}
