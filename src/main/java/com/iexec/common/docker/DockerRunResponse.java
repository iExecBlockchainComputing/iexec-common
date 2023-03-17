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

package com.iexec.common.docker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @deprecated Use <a href="http://github.com/iExecBlockchainComputing/iexec-commons-containers">iexec-commons-containers</a> instead.
 */
@Deprecated(forRemoval = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DockerRunResponse {

    private DockerRunFinalStatus finalStatus;
    private DockerLogs dockerLogs;
    private int containerExitCode;

    public boolean isSuccessful() {
        return finalStatus == DockerRunFinalStatus.SUCCESS;
    }

    public String getStdout() {
        if (dockerLogs != null && dockerLogs.getStdout() != null) {
            return dockerLogs.getStdout();
        }
        return "";
    }

    public String getStderr() {
        if (dockerLogs != null && dockerLogs.getStderr() != null) {
            return dockerLogs.getStderr();
        }
        return "";
    }
}