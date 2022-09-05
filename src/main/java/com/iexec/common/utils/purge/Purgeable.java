package com.iexec.common.utils.purge;

public interface Purgeable {
    /**
     * Purge a service from a task values after its completion.
     * Each service implementing this interface should be purged by {@link PurgeService} on a task completion/failure.
     *
     * @param chainTaskId Id of the task to purge.
     * @return {@literal true} if the purge went well, false otherwise.
     */
    boolean purgeTask(String chainTaskId);
}
