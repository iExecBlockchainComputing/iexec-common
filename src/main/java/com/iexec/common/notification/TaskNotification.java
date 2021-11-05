/*
 * Copyright 2020 IEXEC BLOCKCHAIN TECH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iexec.common.notification;

import com.iexec.common.task.TaskAbortCause;
import lombok.*;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskNotification {

    // Id of the task concerned by the notification.
    String chainTaskId;

    // List of workers targeted by the notification.
    List<String> workersAddress;

    // Type of the notification.
    TaskNotificationType taskNotificationType;

    // Optional extra metadata provided with the notification    
    TaskNotificationExtra taskNotificationExtra;

    /**
     * Builder method used only to create "Abort" task notification.
     * This will fail if the reason behind abort is not specified.
     * In all other cases, use the default "builder()" method.
     * 
     * @param chainTaskId
     * @param workersAddress
     * @param taskNotificationType
     * @param taskAbortCause
     */
    @Builder(builderMethodName = "abortBuilder", builderClassName = "TaskNotificationAbortBuilder")
    private TaskNotification(
            String chainTaskId,
            List<String> workersAddress,
            TaskAbortCause taskAbortCause) {
        requireNonNull(taskAbortCause, "Task abort cause must not be null");
        this.chainTaskId = chainTaskId;
        this.workersAddress = workersAddress;
        this.taskNotificationType = TaskNotificationType.PLEASE_ABORT;
        this.taskNotificationExtra = TaskNotificationExtra.builder()
                .taskAbortCause(taskAbortCause)
                .build();
    }

    /**
     * Check if the current notification is of type ABORT.
     * 
     * @return true if taskNotificationType is PLEASE_ABORT
     *         and abort cause is not null, false otherwise.
     */
    public boolean isAbortNotification() {
        return TaskNotificationType.PLEASE_ABORT.equals(taskNotificationType)
                && taskNotificationExtra != null
                && taskNotificationExtra.getTaskAbortCause() != null;
    }
}
