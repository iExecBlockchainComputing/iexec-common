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

package com.iexec.common.replicate;

import com.iexec.common.chain.ChainReceipt;
import com.iexec.common.tee.TeeSessionGenerationError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplicateStatusDetails {

    public static final int MAX_STDOUT_LENGTH = 100000;

    private ChainReceipt chainReceipt;
    private String resultLink;
    private String chainCallbackData;
    private String errorMessage;
    private ReplicateStatusCause cause;
    private String stdout;
    private Integer exitCode; //null means unset
    private TeeSessionGenerationError teeSessionGenerationError;    // null means unset

    public ReplicateStatusDetails(ReplicateStatusDetails details) {
        chainReceipt = details.getChainReceipt();
        resultLink = details.getResultLink();
        chainCallbackData = details.getChainCallbackData();
        errorMessage = details.getErrorMessage();
        cause = details.getCause();
        stdout = details.getStdout();
        exitCode = details.getExitCode();
        teeSessionGenerationError = details.getTeeSessionGenerationError();
    }

    public ReplicateStatusDetails(long blockNumber) {
        this.chainReceipt = ChainReceipt.builder().blockNumber(blockNumber).build();
    }

    public ReplicateStatusDetails(ReplicateStatusCause cause) {
        this.cause = cause;
    }

    public ReplicateStatusDetails tailStdout() {
        if (stdout != null && stdout.length() > MAX_STDOUT_LENGTH) {
            stdout = stdout.substring(stdout.length() - MAX_STDOUT_LENGTH);
        }
        return this;
    }
}
