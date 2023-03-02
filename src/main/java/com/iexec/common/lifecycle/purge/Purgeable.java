package com.iexec.common.lifecycle.purge;

import javax.annotation.PreDestroy;

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
     */
    @PreDestroy
    void purgeAllTasksData();
}
