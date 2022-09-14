package com.iexec.common.lifecycle.purge;

import net.jodah.expiringmap.ExpiringMap;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.iexec.common.utils.CategoriesUtils.LONGEST_TASK_TIMEOUT;

/**
 * An expiring task map is defined as a map of {@link String} as keys
 * and any type as keys,
 * whereas the map can be purged after a given amount of time -
 * max duration of any task, currently of 100 hours.
 */
public class ExpiringTaskMapFactory {
    private ExpiringTaskMapFactory() {

    }

    public static <T> Map<String, T> getExpiringTaskMap() {
        return ExpiringMap
                .builder()
                .expiration(LONGEST_TASK_TIMEOUT.getSeconds(), TimeUnit.SECONDS)
                .build();
    }
}
