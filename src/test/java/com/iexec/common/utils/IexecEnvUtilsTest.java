/*
 * Copyright 2021-2024 IEXEC BLOCKCHAIN TECH
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

import com.iexec.commons.poco.chain.DealParams;
import com.iexec.commons.poco.task.TaskDescription;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IexecEnvUtilsTest {

    private static final String CHAIN_TASK_ID = "chainTaskId";
    private static final String DATASET_URL = "datasetUrl";
    private static final String DATASET_NAME = "datasetName";
    private static final String DATASET_CHECKSUM = "datasetChecksum";
    private static final String DATASET_ADDRESS = "datasetAddress";
    private static final String INPUT_FILE_1 = "http://host/filename";

    @Test
    void getAllIexecEnv() {
        final DealParams dealParams = DealParams.builder()
                .iexecInputFiles(List.of(INPUT_FILE_1))
                .build();
        final TaskDescription taskDescription = TaskDescription.builder()
                .chainTaskId(CHAIN_TASK_ID)
                .datasetAddress(DATASET_ADDRESS)
                .datasetUri(DATASET_URL)
                .datasetChecksum(DATASET_CHECKSUM)
                .datasetName(DATASET_NAME)
                .botSize(1)
                .botFirstIndex(0)
                .dealParams(dealParams)
                .build();
        Map<String, String> map = IexecEnvUtils.getAllIexecEnv(taskDescription);
        assertEquals(DATASET_URL, map.get("IEXEC_DATASET_URL"));
        assertEquals(DATASET_CHECKSUM, map.get("IEXEC_DATASET_CHECKSUM"));
        assertEquals(INPUT_FILE_1, map.get("IEXEC_INPUT_FILE_URL_1"));
        checkComputeStageMap(map, taskDescription);
    }

    @Test
    void getComputeStageEnvMap() {
        final DealParams dealParams = DealParams.builder()
                .iexecInputFiles(List.of(INPUT_FILE_1))
                .build();
        final TaskDescription taskDescription = TaskDescription.builder()
                .chainTaskId(CHAIN_TASK_ID)
                .datasetAddress(DATASET_ADDRESS)
                .datasetName(DATASET_NAME)
                .botSize(1)
                .botFirstIndex(0)
                .dealParams(dealParams)
                .build();
        Map<String, String> map =
                IexecEnvUtils.getComputeStageEnvMap(taskDescription);
        checkComputeStageMap(map, taskDescription);
    }

    @Test
    void getComputeStageEnvList() {
        final DealParams dealParams = DealParams.builder()
                .iexecInputFiles(List.of(INPUT_FILE_1))
                .build();
        final TaskDescription taskDescription = TaskDescription.builder()
                .chainTaskId(CHAIN_TASK_ID)
                .datasetName(DATASET_NAME)
                .datasetAddress(DATASET_ADDRESS)
                .botSize(1)
                .botFirstIndex(0)
                .dealParams(dealParams)
                .build();
        List<String> expected = Arrays
                .asList("IEXEC_TASK_ID=chainTaskId",
                        "IEXEC_IN=/iexec_in",
                        "IEXEC_OUT=/iexec_out",
                        "IEXEC_DATASET_ADDRESS=datasetAddress",
                        "IEXEC_DATASET_FILENAME=datasetAddress",
                        "IEXEC_INPUT_FILES_FOLDER=/iexec_in",
                        "IEXEC_INPUT_FILES_NUMBER=1",
                        "IEXEC_INPUT_FILE_NAME_1=filename");
        List<String> actual = IexecEnvUtils.getComputeStageEnvList(taskDescription);
        Collections.sort(expected);
        Collections.sort(actual);
        assertEquals(expected, actual);
    }

    private void checkComputeStageMap(Map<String, String> map, TaskDescription taskDescription) {
        assertEquals(taskDescription.getChainTaskId(), map.get("IEXEC_TASK_ID"));
        assertEquals(IexecFileHelper.SLASH_IEXEC_IN, map.get("IEXEC_IN"));
        assertEquals(IexecFileHelper.SLASH_IEXEC_OUT, map.get("IEXEC_OUT"));
        assertEquals(taskDescription.getDatasetAddress(), map.get("IEXEC_DATASET_ADDRESS"));
        assertEquals(taskDescription.getDatasetAddress(), map.get("IEXEC_DATASET_FILENAME"));
        assertEquals("1", map.get("IEXEC_INPUT_FILES_NUMBER"));
        assertEquals(IexecFileHelper.SLASH_IEXEC_IN, map.get("IEXEC_INPUT_FILES_FOLDER"));
        assertEquals("filename", map.get("IEXEC_INPUT_FILE_NAME_1"));
    }
}
