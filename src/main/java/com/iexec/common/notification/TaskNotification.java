package com.iexec.common.notification;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskNotification {

    // chain task Id concerned by the notification
    String chainTaskId;

    // list of workers to which this notification should be sent to
    List<String> workersAddress;

    // type of the notification
    TaskNotificationType taskNotificationType;

    TaskNotificationExtra taskNotificationExtra;
}
