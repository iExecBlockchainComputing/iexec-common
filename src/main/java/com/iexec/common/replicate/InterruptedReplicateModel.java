package com.iexec.common.replicate;

import java.util.List;

import com.iexec.common.chain.ContributionAuthorization;
import com.iexec.common.disconnection.RecoverableAction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterruptedReplicateModel {

    List<ContributionAuthorization> contributionNeededList;
    List<ContributionAuthorization> revealNeededList;
    List<ContributionAuthorization> resultUploadNeededList;

    // ContributionAuthorization contributionAuthorization;
    // RecoverableAction recoverableAction;
}