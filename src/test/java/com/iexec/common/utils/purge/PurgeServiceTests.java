package com.iexec.common.utils.purge;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class PurgeServiceTests {
    private static final String CHAIN_TASK_ID = "chainTaskId";

    // region purgeAllServices
    @Test
    void shouldPurgeAllServices() {
        final List<Purgeable> services = IntStream.range(0, 5)
                .mapToObj(i -> spy(new PurgeableService()))
                .collect(Collectors.toList());

        final boolean purged = new PurgeService(services).purgeAllServices(CHAIN_TASK_ID);
        assertTrue(purged);

        for (Purgeable service : services) {
            assertTrue(((PurgeableService) service).hasBeenPurged);
            verify(service).purgeTask(CHAIN_TASK_ID);
        }
    }
    // endregion

    private static class PurgeableService implements Purgeable {
        boolean hasBeenPurged = false;

        @Override
        public boolean purgeTask(String chainTaskId) {
            this.hasBeenPurged = true;
            return true;
        }

        @Override
        public void purgeAllTasksData() {
            this.hasBeenPurged = true;
        }
    }
}
