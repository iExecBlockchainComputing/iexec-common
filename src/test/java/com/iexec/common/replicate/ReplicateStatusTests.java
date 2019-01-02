package com.iexec.common.replicate;

import org.junit.Test;

import java.util.List;

import static com.iexec.common.replicate.ReplicateStatus.*;
import static org.junit.Assert.assertEquals;


public class ReplicateStatusTests {

    public static void main(String[] args) {
        System.out.println(ReplicateStatus.getMissingStatuses(CREATED, COMPUTING));
    }

    @Test
    public void shouldGetMissingStatuses() {
        List<ReplicateStatus> missingStatuses = ReplicateStatus.getMissingStatuses(CREATED, COMPUTING);

        assertEquals(missingStatuses.size(), 4);
        assertEquals(missingStatuses.get(0), RUNNING);
        assertEquals(missingStatuses.get(1), APP_DOWNLOADING);
        assertEquals(missingStatuses.get(2), APP_DOWNLOADED);
        assertEquals(missingStatuses.get(3), COMPUTING);
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

}
