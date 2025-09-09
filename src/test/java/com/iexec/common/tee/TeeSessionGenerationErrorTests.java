/*
 * Copyright 2025 IEXEC BLOCKCHAIN TECH
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

import com.iexec.common.replicate.ReplicateStatusCause;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class TeeSessionGenerationErrorTests {
    /**
     * Checks all TeeSessionGenerationError values are found in ReplicateStatusCause
     *
     * @param error The enum value under test
     */
    @ParameterizedTest
    @EnumSource(value = TeeSessionGenerationError.class)
    void shouldConvertToReplicateStatusCause(final TeeSessionGenerationError error) {
        assertThat(error.toReplicateStatusCause()).isNotNull();
    }

    /**
     * Checks all ReplicateStatusCause values starting with {@code TEE_SESSION_GENERATION_} prefix
     * are found in TeeSessionGenerationError
     *
     * @param cause The enum value starting with the correct prefix under test
     */
    @ParameterizedTest
    @EnumSource(value = ReplicateStatusCause.class, names = "TEE_SESSION_GENERATION_.*", mode = EnumSource.Mode.MATCH_ALL)
    void shouldReplicateStatusCauseExist(final ReplicateStatusCause cause) {
        final String expectedError = cause.name().replaceFirst("^TEE_SESSION_GENERATION_", "");
        assertThat(TeeSessionGenerationError.values()).contains(TeeSessionGenerationError.valueOf(expectedError));
    }
}
