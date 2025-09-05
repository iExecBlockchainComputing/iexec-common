/*
 * Copyright 2020-2025 IEXEC BLOCKCHAIN TECH
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

import java.util.ArrayList;
import java.util.List;

import static com.iexec.common.replicate.ReplicateStatus.*;
import static org.assertj.core.api.Assertions.assertThat;

class ReplicateStatusTests {

    // region getWorkflowStatus
    @Test
    void checkWorkflowStatuses() {
        final List<ReplicateStatus> finalStatuses = getFinalStatuses();
        final List<ReplicateStatus> nonFinalStatuses = getNonFinalWorkflowStatuses();
        final List<ReplicateStatus> expectedStatuses = new ArrayList<>(nonFinalStatuses);
        expectedStatuses.addAll(finalStatuses);
        assertThat(getWorkflowStatuses()).isEqualTo(expectedStatuses);
    }
    // endregion

    // region getMissingStatuses
    @Test
    void shouldGetMissingStatuses() {
        final List<ReplicateStatus> missingStatuses = ReplicateStatus.getMissingStatuses(CREATED, COMPUTING);

        assertThat(missingStatuses).hasSize(7);
        assertThat(missingStatuses.get(0)).isEqualTo(STARTING);
        assertThat(missingStatuses.get(1)).isEqualTo(STARTED);
        assertThat(missingStatuses.get(2)).isEqualTo(APP_DOWNLOADING);
        assertThat(missingStatuses.get(3)).isEqualTo(APP_DOWNLOADED);
        assertThat(missingStatuses.get(4)).isEqualTo(DATA_DOWNLOADING);
        assertThat(missingStatuses.get(5)).isEqualTo(DATA_DOWNLOADED);
        assertThat(missingStatuses.get(6)).isEqualTo(COMPUTING);
    }

    @Test
    void shouldNotGetMissingStatusesWhenFromGreaterThanTo() {
        final List<ReplicateStatus> missingStatuses = ReplicateStatus.getMissingStatuses(COMPUTING, CREATED);
        assertThat(missingStatuses).isEmpty();
    }

    @Test
    void shouldNotGetMissingStatusesWhenFromEqualsTo() {
        final List<ReplicateStatus> missingStatuses = ReplicateStatus.getMissingStatuses(CREATED, CREATED);
        assertThat(missingStatuses).isEmpty();
    }

    @Test
    void shouldNotGetMissingStatusesIfFromOrToIsNotInSuccessList() {
        final List<ReplicateStatus> missingStatuses = ReplicateStatus.getMissingStatuses(CREATED, COMPUTE_FAILED);
        assertThat(missingStatuses).isEmpty();
    }
    // endregion

    @Test
    void shouldBeFailedBeforeComputed() {
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

        runningFailures.forEach(status -> assertThat(ReplicateStatus.isFailedBeforeComputed(status)).isTrue());
        notRunningFailures.forEach(status -> assertThat(ReplicateStatus.isFailedBeforeComputed(status)).isFalse());
        runningFailures.forEach(status -> assertThat(status.isFailedBeforeComputed()).isTrue());
        notRunningFailures.forEach(status -> assertThat(status.isFailedBeforeComputed()).isFalse());
    }

    // region getUnCompletableStatuses
    @Test
    void checkUncompletableStatuses() {
        assertThat(ReplicateStatus.getUncompletableStatuses())
                .isEqualTo(List.of(CONTRIBUTE_FAILED, REVEAL_FAILED, CONTRIBUTE_AND_FINALIZE_FAILED));
    }
    // endregion
}
