/*
 * Copyright 2022-2025 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.lifecycle.purge;

public interface Purgeable {
    /**
     * Purge a service from all data related to a task after its completion.
     * Each service implementing this interface should be purged by {@link PurgeService} on a task completion/failure.
     *
     * @param chainTaskId Id of the task to purge.
     * @return {@literal true} if the purge went well, false otherwise.
     */
    boolean purgeTask(String chainTaskId);

    /**
     * Purge a service from all task-related data.
     * This can be called for example when the app shutdowns.
     * Implementing classes should annotate this method with {@code @jakarta.annotation.PreDestroy}
     * to ensure proper cleanup during application shutdown.
     */
    void purgeAllTasksData();
}
