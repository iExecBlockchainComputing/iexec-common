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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TeeEnclaveConfigurationTest {

    public static final long KB = 1024;
    public static final long MB = 1024 * KB;
    public static final long GB = 1024 * MB;
    public static final TeeFramework FRAMEWORK = TeeFramework.SCONE;
    public static final String VERSION = "v1.0.0";
    public static final String ENTRYPOINT = "python /app/app.py";
    public static final long HEAP_SIZE = 4 * GB;
    public static final String FINGERPRINT = "01ba4719c80b6fe911b091a7c05124b64eeece964e09c058ef8f9805daca546b";
    public static final TeeEnclaveConfiguration ENCLAVE_CONFIGURATION = TeeEnclaveConfiguration.builder()
            .framework(FRAMEWORK)
            .version(VERSION)
            .entrypoint(ENTRYPOINT)
            .heapSize(HEAP_SIZE)
            .fingerprint(FINGERPRINT)
            .build();
    public static final String ENCLAVE_CONFIGURATION_JSON_STRING = "{" +
            "\"framework\":\"" + FRAMEWORK + "\"," +
            "\"version\":\"" + VERSION + "\"," +
            "\"entrypoint\":\"" + ENTRYPOINT + "\"," +
            "\"heapSize\":" + HEAP_SIZE + "," +
            "\"fingerprint\":\"" + FINGERPRINT + "\"" +
            "}";

    @Test
    void buildEnclaveConfigurationFromJsonString() throws JsonProcessingException {
        Assertions.assertEquals(ENCLAVE_CONFIGURATION,
                TeeEnclaveConfiguration
                        .buildEnclaveConfigurationFromJsonString(ENCLAVE_CONFIGURATION_JSON_STRING));
    }

    @Test
    void convertEnclaveConfigurationToJsonString() throws JsonProcessingException {
        Assertions.assertEquals(ENCLAVE_CONFIGURATION_JSON_STRING,
                ENCLAVE_CONFIGURATION.toJsonString());
    }
}