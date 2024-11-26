/*
 * Copyright 2020-2024 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.utils;

import com.iexec.commons.poco.task.TaskDescription;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides environment variables for worker {@code AppComputeService} and SMS {@code SecretSessionBaseService}
 *
 * @see <a href="https://protocol.docs.iex.ec/for-developers/technical-references/application-io#runtime-variables">
 * Runtime variables in protocol documentation</a>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IexecEnvUtils {

    public static final String IEXEC_TASK_ID = "IEXEC_TASK_ID";
    public static final String IEXEC_IN = "IEXEC_IN";
    public static final String IEXEC_OUT = "IEXEC_OUT";
    // BoT
    public static final String IEXEC_BOT_TASK_INDEX = "IEXEC_BOT_TASK_INDEX";
    public static final String IEXEC_BOT_SIZE = "IEXEC_BOT_SIZE";
    public static final String IEXEC_BOT_FIRST_INDEX = "IEXEC_BOT_FIRST_INDEX";
    // dataset
    public static final String IEXEC_DATASET_ADDRESS = "IEXEC_DATASET_ADDRESS";
    public static final String IEXEC_DATASET_URL = "IEXEC_DATASET_URL";
    public static final String IEXEC_DATASET_FILENAME = "IEXEC_DATASET_FILENAME";
    public static final String IEXEC_DATASET_CHECKSUM = "IEXEC_DATASET_CHECKSUM";
    // input files
    public static final String IEXEC_INPUT_FILES_NUMBER = "IEXEC_INPUT_FILES_NUMBER";
    public static final String IEXEC_INPUT_FILES_FOLDER = "IEXEC_INPUT_FILES_FOLDER";
    public static final String IEXEC_INPUT_FILE_NAME_PREFIX = "IEXEC_INPUT_FILE_NAME_";
    public static final String IEXEC_INPUT_FILE_URL_PREFIX = "IEXEC_INPUT_FILE_URL_";

    /**
     * Get compute stage environment variables plus additional ones used by the pre-compute stage
     * <p>
     * Pre-compute stage specific variables:
     * IEXEC_DATASET_URL, IEXEC_DATASET_CHECKSUM, IEXEC_INPUT_FILE_URL_1, ...
     *
     * @param taskDescription The description of the task
     * @return A key-value map containing each environment variable and its associated value
     * @see #getComputeStageEnvMap(TaskDescription)
     */
    public static Map<String, String> getAllIexecEnv(final TaskDescription taskDescription) {
        final Map<String, String> envMap = new HashMap<>();
        envMap.putAll(getComputeStageEnvMap(taskDescription));
        envMap.put(IEXEC_DATASET_URL, taskDescription.getDatasetUri());
        envMap.put(IEXEC_DATASET_CHECKSUM, taskDescription.getDatasetChecksum());
        if (!taskDescription.containsInputFiles()) {
            return envMap;
        }
        int index = 1;
        for (final String inputFileUrl : taskDescription.getDealParams().getIexecInputFiles()) {
            envMap.put(IEXEC_INPUT_FILE_URL_PREFIX + index, inputFileUrl);
            index++;
        }
        return envMap;
    }

    /**
     * Get environment variables available for the compute stage.
     * <p>
     * Compute stage specific variables :
     * IEXEC_TASK_ID, IEXEC_IN, IEXEC_OUT, IEXEC_DATASET_FILENAME, IEXEC_INPUT_FILE_NAME_1, ...
     *
     * @param taskDescription The description of the task
     * @return A key-value map containing each environment variable and its associated value
     */
    public static Map<String, String> getComputeStageEnvMap(final TaskDescription taskDescription) {
        final Map<String, String> map = new HashMap<>();
        map.put(IEXEC_TASK_ID, taskDescription.getChainTaskId());
        map.put(IEXEC_IN, IexecFileHelper.SLASH_IEXEC_IN);
        map.put(IEXEC_OUT, IexecFileHelper.SLASH_IEXEC_OUT);
        // BoT
        map.put(IEXEC_BOT_SIZE, String.valueOf(taskDescription.getBotSize()));
        map.put(IEXEC_BOT_FIRST_INDEX, String.valueOf(taskDescription.getBotFirstIndex()));
        map.put(IEXEC_BOT_TASK_INDEX, String.valueOf(taskDescription.getBotIndex()));
        // dataset
        map.put(IEXEC_DATASET_ADDRESS, taskDescription.getDatasetAddress());
        map.put(IEXEC_DATASET_FILENAME, taskDescription.getDatasetAddress());
        // input files
        if (!taskDescription.containsInputFiles()) {
            map.put(IEXEC_INPUT_FILES_NUMBER, "0");
            return map;
        }
        // We are sure input files are present
        final List<String> inputFiles = taskDescription.getDealParams().getIexecInputFiles();
        map.put(IEXEC_INPUT_FILES_FOLDER, IexecFileHelper.SLASH_IEXEC_IN);
        map.put(IEXEC_INPUT_FILES_NUMBER, String.valueOf(inputFiles.size()));
        int index = 1;
        for (final String inputFileUrl : inputFiles) {
            map.put(IEXEC_INPUT_FILE_NAME_PREFIX + index, FilenameUtils.getName(inputFileUrl));
            index++;
        }
        return map;
    }

    /**
     * Get compute stage environment variable for docker-java API.
     *
     * @param taskDescription The description of the task
     * @return A list of KEY=VALUE strings corresponding to environment variables to run a standard app
     * @see #getComputeStageEnvMap(TaskDescription)
     */
    public static List<String> getComputeStageEnvList(final TaskDescription taskDescription) {
        List<String> list = new ArrayList<>();
        getComputeStageEnvMap(taskDescription).forEach((key, value) -> list.add(key + "=" + value));
        return list;
    }
}
