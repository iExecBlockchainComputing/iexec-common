package com.iexec.common.notification;

import com.iexec.common.chain.WorkerpoolAuthorization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskNotificationExtra {

    WorkerpoolAuthorization workerpoolAuthorization;

    // block number from which this notification makes sense
    // (it is not used for all notification types)
    long blockNumber;
}