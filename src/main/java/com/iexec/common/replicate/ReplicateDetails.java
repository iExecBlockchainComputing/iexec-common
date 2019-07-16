package com.iexec.common.replicate;

import com.iexec.common.chain.ChainReceipt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplicateDetails {

    private String resultLink;
    private String chainCallbackData;
    private ChainReceipt chainReceipt;
    private ReplicateStatusCause replicateStatusCause;


    public ReplicateDetails(long blockNumber) {
        this.chainReceipt = ChainReceipt.builder().blockNumber(blockNumber).build();
    }
}
