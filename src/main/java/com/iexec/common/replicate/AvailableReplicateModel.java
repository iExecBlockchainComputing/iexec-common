package com.iexec.common.replicate;

import com.iexec.common.chain.ContributionAuthorization;
import com.iexec.common.dapp.DappType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableReplicateModel {

    private String taskId;
    private String chainTaskId;
    private String workerAddress;
    private DappType dappType;
    private String dappName;
    private String cmd;
    private ReplicateStatus replicateStatus;
    private ContributionAuthorization contributionAuthorization;
}
