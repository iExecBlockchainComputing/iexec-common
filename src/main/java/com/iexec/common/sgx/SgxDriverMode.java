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

package com.iexec.common.sgx;

import java.nio.file.Path;

public enum SgxDriverMode {
    NONE(),
    LEGACY(Path.of("/dev", "isgx")),
    NATIVE(Path.of("/dev", "sgx_enclave"), Path.of("/dev", "sgx_provision"));

    private final Path[] devices;

    SgxDriverMode(Path... driverNames) {
        this.devices = driverNames;
    }

    public Path[] getDevices() {
        return devices;
    }

    /**
     * Returns {@literal false} if given {@link SgxDriverMode} is {@literal null}
     * or {@link SgxDriverMode#NONE}, {@literal true} otherwise.
     *
     * @param driverMode {@link SgxDriverMode} object to check.
     * @return {@literal false} if given {@link SgxDriverMode} is {@literal null}
     * or {@link SgxDriverMode#NONE}, {@literal true} otherwise.
     */
    public static boolean isDriverModeNotNone(SgxDriverMode driverMode) {
        return driverMode != null && driverMode != (NONE);
    }
}
