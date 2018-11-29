package com.iexec.common.result;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskNotification {

    String chainTaskId;
    List<String> workersAddress;
    TaskNotificationType taskNotificationType;

}
