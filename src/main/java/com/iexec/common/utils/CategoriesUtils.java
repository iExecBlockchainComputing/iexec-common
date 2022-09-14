package com.iexec.common.utils;

import java.time.Duration;

/**
 * This class may be a good start point for protocol evolution:
 * retrieve categories information and store it locally so that we could work around it
 * instead of having hard-coded values such as now.
 */
public class CategoriesUtils {
    private CategoriesUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * An XL task timeout happens after 100 hours.
     */
    public static final Duration LONGEST_TASK_TIMEOUT = Duration.ofHours(100);
}
