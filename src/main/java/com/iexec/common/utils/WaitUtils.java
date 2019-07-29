package com.iexec.common.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WaitUtils {

    private WaitUtils() {
        throw new UnsupportedOperationException();
    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            log.error("Failed to sleep [duration:{}, exception:{}]", seconds, e.getMessage());
        }
    }

}
