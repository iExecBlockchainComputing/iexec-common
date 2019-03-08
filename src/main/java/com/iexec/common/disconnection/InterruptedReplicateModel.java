package com.iexec.common.disconnection;

import com.iexec.common.chain.ContributionAuthorization;
import com.iexec.common.disconnection.RecoveryAction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterruptedReplicateModel {

    ContributionAuthorization contributionAuthorization;
    RecoveryAction recoveryAction;
}