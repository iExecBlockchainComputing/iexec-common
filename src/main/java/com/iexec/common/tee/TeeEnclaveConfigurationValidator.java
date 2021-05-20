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

import com.iexec.common.utils.BytesUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TeeEnclaveConfigurationValidator {

    private final TeeEnclaveConfiguration teeEnclaveConfiguration;

    public TeeEnclaveConfigurationValidator(@NonNull TeeEnclaveConfiguration teeEnclaveConfiguration) {
        this.teeEnclaveConfiguration = teeEnclaveConfiguration;
    }

    /**
     * Check if a TeeEnclaveConfiguration object is valid.
     *
     * @return true if TeeEnclaveConfiguration is valid
     */
    public boolean isValid() {
        return validate().isEmpty();
    }

    /**
     * Validate attributes of a TeeEnclaveConfiguration object.
     * Inspired by JSR 303 (see JSR 380 too)
     *
     * @return list of violations
     */
    public List<String> validate() {
        List<String> violations = new ArrayList<>();
        if (StringUtils.isEmpty(teeEnclaveConfiguration.getEntrypoint())) {
            violations.add("Empty entrypoint");
        }
        if (teeEnclaveConfiguration.getHeapSize() <= 0) {
            violations.add("Empty or negative heap  size: "
                    + teeEnclaveConfiguration.getHeapSize());
        }
        String fingerprint = teeEnclaveConfiguration.getFingerprint();
        if (StringUtils.isEmpty(fingerprint)
                || BytesUtils.stringToBytes(fingerprint).length != 32) {
            violations.add("Fingerprint size is not 32: " + fingerprint);
        }
        return violations;
    }

}
