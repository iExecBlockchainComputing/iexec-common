package com.iexec.common.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskNotification {

    String taskId;
    String workerAddress;
    TaskNotificationType taskNotificationType;

}
