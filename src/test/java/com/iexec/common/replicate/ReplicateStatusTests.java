package com.iexec.common.replicate;

import org.junit.Test;

import java.util.List;

import static com.iexec.common.replicate.ReplicateStatus.*;
import static org.junit.Assert.assertEquals;


public class ReplicateStatusTests {

    @Test
    public void shouldGetMissingStatuses() {
        List<ReplicateStatus> missingStatuses = ReplicateStatus.getMissingStatuses(CREATED, COMPUTING);
        System.out.println(missingStatuses);

        assertEquals(missingStatuses.size(), 7);
        assertEquals(missingStatuses.get(0), RUNNING);
        assertEquals(missingStatuses.get(1), STARTED);
        assertEquals(missingStatuses.get(2), APP_DOWNLOADING);
        assertEquals(missingStatuses.get(3), APP_DOWNLOADED);
        assertEquals(missingStatuses.get(4), DATA_DOWNLOADING);
        assertEquals(missingStatuses.get(5), DATA_DOWNLOADED);
        assertEquals(missingStatuses.get(6), COMPUTING);
    }

    @Test
    public void shouldNotGetMissingStatusesWhenFromGreaterThanTo() {
        List<ReplicateStatus> missingStatuses = ReplicateStatus.getMissingStatuses(COMPUTING, CREATED);
        assertEquals(missingStatuses.size(), 0);
    }

    @Test
    public void shouldNotGetMissingStatusesWhenFromEqualsTo() {
        List<ReplicateStatus> missingStatuses = ReplicateStatus.getMissingStatuses(CREATED, CREATED);
        assertEquals(missingStatuses.size(), 0);
    }

    @Test
    public void shouldNotGetMissingStatusesIfFromOrToIsNotInSuccessList() {
        List<ReplicateStatus> missingStatuses = ReplicateStatus.getMissingStatuses(CREATED, COMPUTE_FAILED);
        assertEquals(missingStatuses.size(), 0);
    }

}
