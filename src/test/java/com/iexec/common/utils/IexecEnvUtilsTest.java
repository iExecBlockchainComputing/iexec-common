/*
 * Copyright 2021-2025 IEXEC BLOCKCHAIN TECH
 *
 * Licensed under the Apache License),Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing),software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND),either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iexec.common.utils;

import com.iexec.common.worker.tee.TeeSessionEnvironmentVariable;
import com.iexec.commons.poco.chain.DealParams;
import com.iexec.commons.poco.task.TaskDescription;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.iexec.common.utils.IexecEnvUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

class IexecEnvUtilsTest {

    private static final String CHAIN_DEAL_ID = "chainDealId";
    private static final String CHAIN_TASK_ID = "chainTaskId";
    private static final String DATASET_URL = "datasetUrl";
    private static final String DATASET_CHECKSUM = "datasetChecksum";
    private static final String DATASET_ADDRESS = "datasetAddress";
    private static final String INPUT_FILE_1 = "http://host/filename";
    private static final String INPUT_FILE_NAME_1 = FileHashUtils.createFileNameFromUri(INPUT_FILE_1);

    private final TaskDescription.TaskDescriptionBuilder taskDescriptionBuilder = TaskDescription.builder()
            .chainDealId(CHAIN_DEAL_ID)
            .chainTaskId(CHAIN_TASK_ID);

    // region getAllIexecEnv
    @Test
    void shouldGetAllIexecEnvWhenNoInputFiles() {
        final TaskDescription taskDescription = taskDescriptionBuilder
                .botIndex(0)
                .datasetAddress(DATASET_ADDRESS)
                .datasetUri(DATASET_URL)
                .datasetChecksum(DATASET_CHECKSUM)
                .dealParams(DealParams.builder().build())
                .build();
        final Map<String, String> map = getAllIexecEnv(taskDescription);
        assertThat(map)
                .containsAllEntriesOf(getExpectedComputeStageMapValues(taskDescription))
                .containsEntry(TeeSessionEnvironmentVariable.IEXEC_INPUT_FILES_NUMBER.name(), "0")
                .containsEntry(TeeSessionEnvironmentVariable.IEXEC_DATASET_URL.name(), DATASET_URL)
                .containsEntry(TeeSessionEnvironmentVariable.IEXEC_DATASET_CHECKSUM.name(), DATASET_CHECKSUM);
    }

    @Test
    void shouldGetAllIexecEnv() {
        final DealParams dealParams = DealParams.builder()
                .iexecInputFiles(List.of(INPUT_FILE_1))
                .build();
        final TaskDescription taskDescription = taskDescriptionBuilder
                .datasetAddress(DATASET_ADDRESS)
                .datasetUri(DATASET_URL)
                .datasetChecksum(DATASET_CHECKSUM)
                .dealParams(dealParams)
                .build();
        final Map<String, String> map = getAllIexecEnv(taskDescription);
        assertThat(map)
                .containsAllEntriesOf(getExpectedComputeStageMapValues(taskDescription))
                .containsEntry(TeeSessionEnvironmentVariable.IEXEC_DATASET_URL.name(), DATASET_URL)
                .containsEntry(TeeSessionEnvironmentVariable.IEXEC_DATASET_CHECKSUM.name(), DATASET_CHECKSUM)
                .containsEntry(TeeSessionEnvironmentVariable.IEXEC_INPUT_FILES_FOLDER.name(), IexecFileHelper.SLASH_IEXEC_IN)
                .containsEntry(TeeSessionEnvironmentVariable.IEXEC_INPUT_FILES_NUMBER.name(), "1")
                .containsEntry("IEXEC_INPUT_FILE_URL_1", INPUT_FILE_1)
                .containsEntry("IEXEC_INPUT_FILE_NAME_1", INPUT_FILE_NAME_1);
    }
    // endregion

    // region getComputeStageEnvMap
    @Test
    void shouldGetComputeStageEnvMapWhenNoInputFiles() {
        final TaskDescription taskDescription = taskDescriptionBuilder
                .datasetAddress(DATASET_ADDRESS)
                .botSize(1)
                .botFirstIndex(0)
                .dealParams(DealParams.builder().build())
                .build();
        final Map<String, String> map = getComputeStageEnvMap(taskDescription);
        assertThat(map)
                .containsAllEntriesOf(getExpectedComputeStageMapValues(taskDescription))
                .containsEntry(TeeSessionEnvironmentVariable.IEXEC_INPUT_FILES_NUMBER.name(), "0");
    }

    @Test
    void shouldGetComputeStageEnvMap() {
        final DealParams dealParams = DealParams.builder()
                .iexecInputFiles(List.of(INPUT_FILE_1))
                .build();
        final TaskDescription taskDescription = taskDescriptionBuilder
                .datasetAddress(DATASET_ADDRESS)
                .botSize(1)
                .botFirstIndex(0)
                .dealParams(dealParams)
                .build();
        final Map<String, String> map = getComputeStageEnvMap(taskDescription);
        assertThat(map)
                .containsAllEntriesOf(getExpectedComputeStageMapValues(taskDescription))
                .containsEntry(TeeSessionEnvironmentVariable.IEXEC_INPUT_FILES_NUMBER.name(), "1")
                .containsEntry(TeeSessionEnvironmentVariable.IEXEC_INPUT_FILES_FOLDER.name(), IexecFileHelper.SLASH_IEXEC_IN)
                .containsEntry("IEXEC_INPUT_FILE_NAME_1", INPUT_FILE_NAME_1);
    }
    // endregion

    @Test
    void shouldGetComputeStageEnvList() {
        final DealParams dealParams = DealParams.builder()
                .iexecInputFiles(List.of(INPUT_FILE_1))
                .build();
        final TaskDescription taskDescription = taskDescriptionBuilder
                .datasetAddress(DATASET_ADDRESS)
                .botSize(1)
                .botFirstIndex(0)
                .dealParams(dealParams)
                .build();
        final List<String> expected = List.of(
                "DEAL_ID=chainDealId",
                "TASK_INDEX=0",
                "IEXEC_TASK_ID=chainTaskId",
                "IEXEC_IN=/iexec_in",
                "IEXEC_OUT=/iexec_out",
                "IEXEC_DATASET_ADDRESS=datasetAddress",
                "IEXEC_DATASET_FILENAME=datasetAddress",
                "IEXEC_BOT_SIZE=1",
                "IEXEC_BOT_FIRST_INDEX=0",
                "IEXEC_BOT_TASK_INDEX=0",
                "IEXEC_INPUT_FILES_FOLDER=/iexec_in",
                "IEXEC_INPUT_FILES_NUMBER=1",
                "IEXEC_INPUT_FILE_NAME_1=" + INPUT_FILE_NAME_1);
        final List<String> actual = getComputeStageEnvList(taskDescription);
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    private Map<String, String> getExpectedComputeStageMapValues(final TaskDescription taskDescription) {
        return Map.of(
                TeeSessionEnvironmentVariable.DEAL_ID.name(), taskDescription.getChainDealId(),
                TeeSessionEnvironmentVariable.TASK_INDEX.name(), String.valueOf(taskDescription.getBotIndex()),
                TeeSessionEnvironmentVariable.IEXEC_TASK_ID.name(), taskDescription.getChainTaskId(),
                TeeSessionEnvironmentVariable.IEXEC_IN.name(), IexecFileHelper.SLASH_IEXEC_IN,
                TeeSessionEnvironmentVariable.IEXEC_OUT.name(), IexecFileHelper.SLASH_IEXEC_OUT,
                TeeSessionEnvironmentVariable.IEXEC_DATASET_ADDRESS.name(), taskDescription.getDatasetAddress(),
                TeeSessionEnvironmentVariable.IEXEC_DATASET_FILENAME.name(), taskDescription.getDatasetAddress()
        );
    }
}
