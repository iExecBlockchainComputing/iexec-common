package com.iexec.common.lifecycle.purge;

import net.jodah.expiringmap.ExpiringMap;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpiringTaskMapFactoryTests {
    private final static String CHAIN_TASK_ID = "chainTaskId";

    // region getExpiringTaskMap
    @Test
    void shouldGetExpiringTaskMap() {
        final Integer object = 1;
        final Map<String, Integer> map = ExpiringTaskMapFactory.getExpiringTaskMap();
        map.put(CHAIN_TASK_ID, object);

        assertEquals(1, map.size());
        assertEquals(object, map.get(CHAIN_TASK_ID));

        assertEquals(100 * 60 * 60 * 1000,
                ((ExpiringMap<String, Integer>) map).getExpiration());
    }
    // endregion
}