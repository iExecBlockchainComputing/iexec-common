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

    public static ReplicateActionResponse successWithStdout(String stdout) {
        ReplicateStatusDetails details = ReplicateStatusDetails.builder()
                .stdout(stdout)
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
                .stdout(stdout)
                .build();
        return new ReplicateActionResponse(false, details);
    }

    public static ReplicateActionResponse failureWithStdout(ReplicateStatusCause cause, String stdout) {
        ReplicateStatusDetails details = ReplicateStatusDetails.builder()
                .cause(cause)
                .stdout(stdout)
                .build();
        return new ReplicateActionResponse(false, details);
    }
}
