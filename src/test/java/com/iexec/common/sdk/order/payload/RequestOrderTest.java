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

package com.iexec.common.sdk.order.payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RequestOrderTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldSerializeAndDeserialize() throws JsonProcessingException {
        RequestOrder requestOrder = RequestOrder.builder().build();
        String jsonString = mapper.writeValueAsString(requestOrder);
        assertThat(jsonString).isEqualTo("{\"volume\":null,\"tag\":null,\"salt\":null,\"sign\":null," +
                "\"app\":\"\",\"appmaxprice\":null,\"dataset\":\"\",\"datasetmaxprice\":null,\"workerpool\":\"\",\"workerpoolmaxprice\":null," +
                "\"requester\":\"\",\"category\":null,\"trust\":null,\"beneficiary\":\"\",\"callback\":\"\",\"params\":null}");
        RequestOrder parsedRequestOrder = mapper.readValue(jsonString, RequestOrder.class);
        assertThat(parsedRequestOrder).usingRecursiveComparison().isEqualTo(requestOrder);
    }

}
