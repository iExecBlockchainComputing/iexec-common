package com.iexec.common.notification;

import com.iexec.common.chain.ContributionAuthorization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskNotificationExtra {

    ContributionAuthorization contributionAuthorization;

    // block number from which this notification makes sense
    // (it is not used for all notification types)
    long blockNumber;
}