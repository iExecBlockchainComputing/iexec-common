/*
 * Copyright 2024-2025 IEXEC BLOCKCHAIN TECH
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

import static com.iexec.common.result.ResultModel.EMPTY_WEB3_SIG;
import static com.iexec.commons.poco.utils.BytesUtils.EMPTY_HEX_STRING_32;
import static org.assertj.core.api.Assertions.assertThat;

class ResultModelTests {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldSerializeAndDeserialize() throws JsonProcessingException {
        ResultModel resultModel = ResultModel.builder().build();
        String jsonString = mapper.writeValueAsString(resultModel);
        assertThat(jsonString).isEqualTo("{\"chainTaskId\":\"" + EMPTY_HEX_STRING_32 + "\"," +
                "\"dealId\":\"" + EMPTY_HEX_STRING_32 + "\",\"taskIndex\":0" +
                ",\"image\":null,\"cmd\":null,\"zip\":\"\",\"deterministHash\":null," +
                "\"enclaveSignature\":\"" + EMPTY_WEB3_SIG + "\"}");
        ResultModel parsedResultModel = mapper.readValue(jsonString, ResultModel.class);
        assertThat(parsedResultModel).usingRecursiveComparison().isEqualTo(resultModel);
        assertThat(resultModel).hasToString(
                "ResultModel(chainTaskId=" + EMPTY_HEX_STRING_32 + ", dealId=" + EMPTY_HEX_STRING_32 +
                        ", taskIndex=0" + ", image=null, cmd=null, zip=[]"
                        + ", deterministHash=null, enclaveSignature=" + EMPTY_WEB3_SIG + ")"
        );
    }
}
