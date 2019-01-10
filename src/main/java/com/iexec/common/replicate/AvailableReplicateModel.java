package com.iexec.common.replicate;

import com.iexec.common.chain.ContributionAuthorization;
import com.iexec.common.dapp.DappType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableReplicateModel {

    private ContributionAuthorization contributionAuthorization;
    private DappType appType;
    private String appUri;
    private String cmd;
    private Date timeRef;
    private boolean isTrustedExecution;
}
