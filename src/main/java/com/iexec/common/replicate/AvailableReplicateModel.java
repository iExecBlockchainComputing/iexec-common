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

    private ContributionAuthorization contributionAuthorization;
    private DappType appType;
    private String appUri;
    private String cmd;
    private long maxExecutionTime;
    private boolean isTrustedExecution;
    private String datasetUri;
}
