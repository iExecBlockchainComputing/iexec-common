package com.iexec.common.notification;

import com.iexec.common.chain.ContributionAuthorization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskNotificationExtra {

    ContributionAuthorization contributionAuthorization;

    /**
     * those are not used for all notification types
     */

    boolean isTeeTask;

    // block number from which this notification makes sense
    long blockNumber;

}
