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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplicateActionResponse {

    private boolean isSuccess;
    private ReplicateStatusDetails details;

    public static ReplicateActionResponse success() {
        return new ReplicateActionResponse(true, null);
    }

    public static ReplicateActionResponse success(ChainReceipt chainReceipt) {
        ReplicateStatusDetails details = ReplicateStatusDetails.builder()
                .chainReceipt(chainReceipt)
                .build();
        return new ReplicateActionResponse(true, details);
    }

    public static ReplicateActionResponse success(String resultLink, String callbackData) {
        ReplicateStatusDetails details = ReplicateStatusDetails.builder()
                .resultLink(resultLink)
                .chainCallbackData(callbackData)
                .build();
        return new ReplicateActionResponse(true, details);
    }

    public static ReplicateActionResponse successWithLogs(ReplicateLogs replicateLogs) {
        ReplicateStatusDetails details = ReplicateStatusDetails.builder()
                .replicateLogs(replicateLogs)
                .build();
        return new ReplicateActionResponse(true, details);
    }

    public static ReplicateActionResponse failure() {
        return new ReplicateActionResponse(false, null);
    }

    public static ReplicateActionResponse failure(ReplicateStatusCause cause) {
        ReplicateStatusDetails details = ReplicateStatusDetails.builder()
                .cause(cause)
                .build();
        return new ReplicateActionResponse(false, details);
    }

    public static ReplicateActionResponse failureWithStdout(String stdout) {
        ReplicateStatusDetails details = ReplicateStatusDetails.builder()
                .replicateLogs(ReplicateLogs.builder().stdout(stdout).build())
                .build();
        return new ReplicateActionResponse(false, details);
    }

    public static ReplicateActionResponse failureWithStdout(ReplicateStatusCause cause, String stdout) {
        ReplicateStatusDetails details = ReplicateStatusDetails.builder()
                .cause(cause)
                .replicateLogs(ReplicateLogs.builder().stdout(stdout).build())
                .build();
        return new ReplicateActionResponse(false, details);
    }

    public static ReplicateActionResponse failureWithDetails(ReplicateStatusDetails details) {
        return new ReplicateActionResponse(false, details);
    }
}
