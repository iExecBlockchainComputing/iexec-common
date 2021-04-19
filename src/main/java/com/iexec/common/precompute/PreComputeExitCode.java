/*
 * Copyright 2020 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.precompute;

import javax.annotation.CheckForNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * To avoid confusion with exit codes of the chroot standard
 * (followed also by docker), we use exit codes between
 * 64 and 113 which is also conform with the C/C++ standard.
 * @see https://tldp.org/LDP/abs/html/exitcodes.html
 * @see https://docs.docker.com/engine/reference/run/#exit-status
 */
public enum PreComputeExitCode {

    UNKNOWN_ERROR(-1),
    SUCCESS(0),
    EMPTY_REQUIRED_ENV_VAR(64),
    OUTPUT_FOLDER_NOT_FOUND(65),
    DATASET_DOWNLOAD_FAILED(66),
    INVALID_DATASET_CHECKSUM(67),
    INVALID_DATASET_KEY(68),
    DATASET_DECRYPTION_FAILED(69),
    WRITING_PLAIN_DATASET_FAILED(70);

    private final int value;

    private PreComputeExitCode(int n) {
        this.value = n;
    }

    public int value() {
        return value;
    }

    public static boolean contains(int n) {
        return List.of(values())
                .stream()
                .map(PreComputeExitCode::value)
                .collect(Collectors.toList())
                .contains(n);
    }

    public static boolean isSuccess(int n) {
        return SUCCESS.value() == n;
    }

    /**
     * Get element e such as "e.getValue() == n".
     * @param n
     * @return element or null if nothing matched.
     */
    @CheckForNull
    public static PreComputeExitCode nameOf(int n) {
        return List.of(values())
                .stream()
                .filter(code -> code.value() == n)
                .findFirst()
                .orElse(null);
    }
}
