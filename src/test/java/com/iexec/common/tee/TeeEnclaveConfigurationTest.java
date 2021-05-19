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

package com.iexec.common.tee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TeeEnclaveConfigurationTest {

    public static final long KB = 1024;
    public static final long MB = 1024 * KB;
    public static final long GB = 1024 * MB;

    @Test
    void buildEnclaveConfigurationFromJsonString() throws JsonProcessingException {
        TeeEnclaveConfiguration enclaveConfiguration = TeeEnclaveConfiguration.builder()
                .provider(TeeEnclaveProvider.SCONE)
                .version("v1.0.0")
                .entrypoint("python /app/app.py")
                .heapSize(4 * GB)
                .fingerprint("01ba4719c80b6fe911b091a7c05124b64eeece964e09c058ef8f9805daca546b")
                .build();
        Assertions.assertEquals("{" +
                        "\"provider\":\"SCONE\"," +
                        "\"version\":\"v1.0.0\"," +
                        "\"entrypoint\":\"python /app/app.py\"," +
                        "\"heapSize\":4294967296," +
                        "\"fingerprint\":\"01ba4719c80b6fe911b091a7c05124b64eeece964e09c058ef8f9805daca546b\"" +
                        "}",
                new ObjectMapper().writeValueAsString(enclaveConfiguration));
    }
}