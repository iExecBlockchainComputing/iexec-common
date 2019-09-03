package com.iexec.common.replicate;

import com.iexec.common.chain.ChainReceipt;
import com.iexec.common.replicate.ReplicateStatus;
import com.iexec.common.replicate.ReplicateStatusCause;
import com.iexec.common.replicate.ReplicateStatusDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplicateActionResponse {

    private ReplicateStatus status;
    private ReplicateStatusDetails details;

    public ReplicateActionResponse(ReplicateStatus status) {
        this.status = status;
    }

    public ReplicateActionResponse(ReplicateStatus status, ReplicateStatusCause cause) {
        this.status = status;
        this.details = ReplicateStatusDetails.builder().cause(cause).build();
    }

    public ReplicateActionResponse(ReplicateStatus status, ChainReceipt chainReceipt) {
        this.status = status;
        this.details = ReplicateStatusDetails.builder().chainReceipt(chainReceipt).build();
    }

    public ReplicateActionResponse(ReplicateStatus status, String resultLink, String callbackData) {
        this.status = status;
        this.details = ReplicateStatusDetails.builder()
                .resultLink(resultLink)
                .chainCallbackData(callbackData)
                .build();
    }
}