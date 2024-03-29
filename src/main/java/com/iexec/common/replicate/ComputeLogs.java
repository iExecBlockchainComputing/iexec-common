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

package com.iexec.common.replicate;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ComputeLogs {
    public static final int MAX_LOG_LENGTH = 100000;

    private String walletAddress;
    private String stdout;
    private String stderr;

    public ComputeLogs tailLogs() {
        stdout = tailLog(stdout);
        stderr = tailLog(stderr);
        return this;
    }

    private String tailLog(String log) {
        if (log != null && log.length() > MAX_LOG_LENGTH) {
            return log.substring(log.length() - MAX_LOG_LENGTH);
        }
        return log;
    }
}
