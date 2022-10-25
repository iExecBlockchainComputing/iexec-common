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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class TeeEnclaveConfigurationValidatorTests {

    private static final String ENTRYPOINT = "entrypoint";
    private static final long HEAP = 1;
    private static final String FINGERPRINT = "01ba4719c80b6fe911b091a7c05124b64eeece964e09c058ef8f9805daca546b";

    @Test
    void isValid() {
        Assertions.assertTrue(new TeeEnclaveConfigurationValidator(getEnclaveConfig())
                .isValid());
    }

    @Test
    void validate() {
        Assertions.assertTrue(new TeeEnclaveConfigurationValidator(getEnclaveConfig())
                .validate().isEmpty());
    }

    @Test
    void hasEntrypointViolation() {
        TeeEnclaveConfiguration enclaveConfig = TeeEnclaveConfiguration.builder()
                .entrypoint("")
                .heapSize(HEAP)
                .fingerprint(FINGERPRINT)
                .build();
        List<String> violations = new TeeEnclaveConfigurationValidator(enclaveConfig)
                .validate();
        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("Empty entrypoint", violations.get(0));
    }

    @Test
    void hasHeapSizeViolationSinceNegative() {
        TeeEnclaveConfiguration enclaveConfig = TeeEnclaveConfiguration.builder()
                .entrypoint(ENTRYPOINT)
                .heapSize(-1)
                .fingerprint(FINGERPRINT)
                .build();
        List<String> violations = new TeeEnclaveConfigurationValidator(enclaveConfig)
                .validate();
        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("Empty or negative heap  size: -1", violations.get(0));
    }

    @Test
    void hasHeapSizeViolationSinceZero() {
        TeeEnclaveConfiguration enclaveConfig = TeeEnclaveConfiguration.builder()
                .entrypoint(ENTRYPOINT)
                .heapSize(0)
                .fingerprint(FINGERPRINT)
                .build();
        List<String> violations = new TeeEnclaveConfigurationValidator(enclaveConfig)
                .validate();
        Assertions.assertEquals(1, violations.size());
        Assertions.assertTrue(violations.get(0).contains("Empty or negative heap  size"));
    }

    @Test
    void hasFingerprintViolationSinceEmpty() {
        TeeEnclaveConfiguration enclaveConfig = TeeEnclaveConfiguration.builder()
                .entrypoint(ENTRYPOINT)
                .heapSize(HEAP)
                .fingerprint("")
                .build();
        List<String> violations = new TeeEnclaveConfigurationValidator(enclaveConfig)
                .validate();
        Assertions.assertEquals(1, violations.size());
        Assertions.assertTrue(violations.get(0).contains("Fingerprint size is not 32"));
    }

    @Test
    void hasFingerprintViolationSinceWrong() {
        TeeEnclaveConfiguration enclaveConfig = TeeEnclaveConfiguration.builder()
                .entrypoint(ENTRYPOINT)
                .heapSize(HEAP)
                .fingerprint("badFingerPrint")
                .build();
        List<String> violations = new TeeEnclaveConfigurationValidator(enclaveConfig)
                .validate();
        Assertions.assertEquals(1, violations.size());
        Assertions.assertTrue(violations.get(0).contains("Fingerprint size is not 32"));
    }

    private TeeEnclaveConfiguration getEnclaveConfig() {
        return TeeEnclaveConfiguration.builder()
                .entrypoint(ENTRYPOINT)
                .heapSize(HEAP)
                .fingerprint(FINGERPRINT)
                .build();
    }


}