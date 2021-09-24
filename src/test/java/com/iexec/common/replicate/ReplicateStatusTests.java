/*
 * Copyright 2020 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.replicate;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.iexec.common.replicate.ReplicateStatus.*;
import static org.junit.jupiter.api.Assertions.*;


public class ReplicateStatusTests {

    @Test
    public void shouldGetMissingStatuses() {
        List<ReplicateStatus> missingStatuses = ReplicateStatus.getMissingStatuses(CREATED, COMPUTING);

        assertEquals(missingStatuses.size(), 7);
        assertEquals(missingStatuses.get(0), STARTING);
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

    @Test
    void shouldBeRunningFailures() {
        final List<ReplicateStatus> runningFailures = List.of(
                START_FAILED,
                APP_DOWNLOAD_FAILED,
                DATA_DOWNLOAD_FAILED,
                COMPUTE_FAILED
        );
        final List<ReplicateStatus> notRunningFailures = List.of(
                CREATED,
                STARTING,
                STARTED,
                APP_DOWNLOADING,
                APP_DOWNLOADED,
                DATA_DOWNLOADING,
                DATA_DOWNLOADED,
                COMPUTING,
                COMPUTED,
                CONTRIBUTING,
                CONTRIBUTE_FAILED,
                CONTRIBUTED,
                REVEALING,
                REVEAL_FAILED,
                REVEALED,
                RESULT_UPLOAD_REQUESTED,
                RESULT_UPLOAD_REQUEST_FAILED,
                RESULT_UPLOADING,
                RESULT_UPLOAD_FAILED,
                RESULT_UPLOADED,
                COMPLETING,
                COMPLETE_FAILED,
                COMPLETED,
                FAILED,
                ABORTED,
                RECOVERING,
                WORKER_LOST
        );

        runningFailures   .forEach(status -> assertTrue (ReplicateStatus.isRunningFailure(status)));
        notRunningFailures.forEach(status -> assertFalse(ReplicateStatus.isRunningFailure(status)));
    }
}
