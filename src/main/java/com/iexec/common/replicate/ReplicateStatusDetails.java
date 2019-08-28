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
public class ReplicateStatusDetails {

    private ChainReceipt chainReceipt;
    private String resultLink;
    private String chainCallbackData;
    private String errorMessage;
    private ReplicateStatusCause cause;

    public ReplicateStatusDetails(long blockNumber) {
        this.chainReceipt = ChainReceipt.builder().blockNumber(blockNumber).build();
    }
}
