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
import static org.junit.jupiter.api.Assertions.assertEquals;


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

}
