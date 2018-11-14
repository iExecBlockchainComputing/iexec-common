package com.iexec.common.result;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskNotification {

    String chainTaskId;
    String workerAddress;
    TaskNotificationType taskNotificationType;

}
