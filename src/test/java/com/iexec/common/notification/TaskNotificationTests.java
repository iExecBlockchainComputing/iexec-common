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
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskNotificationTests {

    @Test
    public void shouldCreateAbortNotificationWithCause() {
        TaskAbortCause taskAbortCause = TaskAbortCause.CONTRIBUTION_TIMEOUT;
        TaskNotification notification = TaskNotification.abortBuilder()
                        .chainTaskId("chainTaskId")
                        .workersAddress(List.of())
                        .taskAbortCause(taskAbortCause)
                        .build();
        assertEquals(notification.getTaskNotificationType(), TaskNotificationType.PLEASE_ABORT);
        assertEquals(notification.getTaskNotificationExtra().getTaskAbortCause(), taskAbortCause);
    }

    @Test
    public void shouldNotCreateAbortNotificationWithoutCause() {
        NullPointerException e = assertThrows(NullPointerException.class,
                () -> TaskNotification.abortBuilder()
                        .chainTaskId("chainTaskId")
                        .workersAddress(List.of())
                        // .taskAbortCause()
                        .build());
        assertTrue(e.getMessage().contains("Task abort cause must not be null"));
    }
}
