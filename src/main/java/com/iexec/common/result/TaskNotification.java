package com.iexec.common.result;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskNotification {

    String taskId;
    String workerAddress;
    TaskNotificationType taskNotificationType;

}
