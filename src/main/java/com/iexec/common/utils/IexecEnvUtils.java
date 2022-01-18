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

package com.iexec.common.utils;

import com.iexec.common.task.TaskDescription;
import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IexecEnvUtils {

    /*
     * Env variables that will be injected in the container of
     * a task computation.
     *
     * /!\ If you change those values please don't forget to update
     * the palaemon config file.
     */
    public static final String IEXEC_TASK_ID = "IEXEC_TASK_ID";
    public static final String IEXEC_IN = "IEXEC_IN";
    public static final String IEXEC_OUT = "IEXEC_OUT";
    // dataset
    public static final String IEXEC_DATASET_ADDRESS = "IEXEC_DATASET_ADDRESS";
    public static final String IEXEC_DATASET_URL = "IEXEC_DATASET_URL";
    public static final String IEXEC_DATASET_FILENAME = "IEXEC_DATASET_FILENAME";
    public static final String IEXEC_DATASET_CHECKSUM = "IEXEC_DATASET_CHECKSUM";
    // BoT
    public static final String IEXEC_BOT_TASK_INDEX = "IEXEC_BOT_TASK_INDEX";
    public static final String IEXEC_BOT_SIZE = "IEXEC_BOT_SIZE";
    public static final String IEXEC_BOT_FIRST_INDEX = "IEXEC_BOT_FIRST_INDEX";
    // input files
    public static final String IEXEC_INPUT_FILES_NUMBER = "IEXEC_INPUT_FILES_NUMBER";
    public static final String IEXEC_INPUT_FILES_FOLDER = "IEXEC_INPUT_FILES_FOLDER";
    public static final String IEXEC_INPUT_FILE_NAME_PREFIX = "IEXEC_INPUT_FILE_NAME_";
    public static final String IEXEC_INPUT_FILE_URL_PREFIX = "IEXEC_INPUT_FILE_URL_";

    private IexecEnvUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Get compute stage environment variables plus other additional ones
     * used by the pre-compute stage (e.g. IEXEC_DATASET_URL,
     * IEXEC_DATASET_CHECKSUM, IEXEC_INPUT_FILE_URL_1, ...etc).
     * 
     * @param taskDescription
     * @return
     */
    public static Map<String, String> getAllIexecEnv(TaskDescription taskDescription) {
        Map<String, String> envMap = new HashMap<>();
        envMap.putAll(getComputeStageEnvMap(taskDescription));
        envMap.put(IEXEC_DATASET_URL, taskDescription.getDatasetUri());
        envMap.put(IEXEC_DATASET_CHECKSUM, taskDescription.getDatasetChecksum());
        if (taskDescription.getInputFiles() == null) {
            return envMap;
        }
        int index = 1;
        for (String inputFileUrl : taskDescription.getInputFiles()) {
            envMap.put(IEXEC_INPUT_FILE_URL_PREFIX + index, inputFileUrl);
            index++;
        }
        return envMap;
    }

    /**
     * Get environment variables available for the compute stage
     * (e.g. IEXEC_TASK_ID, IEXEC_IN, IEXEC_OUT,
     * IEXEC_DATASET_FILENAME, IEXEC_INPUT_FILE_NAME_1, ...etc).
     * 
     * @param taskDescription
     * @return
     */
    public static Map<String, String> getComputeStageEnvMap(TaskDescription taskDescription) {
        Map<String, String> map = new HashMap<>();
        map.put(IEXEC_TASK_ID, taskDescription.getChainTaskId());
        map.put(IEXEC_IN, IexecFileHelper.SLASH_IEXEC_IN);
        map.put(IEXEC_OUT, IexecFileHelper.SLASH_IEXEC_OUT);
        // dataset
        map.put(IEXEC_DATASET_ADDRESS, taskDescription.getDatasetAddress());
        map.put(IEXEC_DATASET_FILENAME, taskDescription.getDatasetName());
        // BoT
        map.put(IEXEC_BOT_SIZE, String.valueOf(taskDescription.getBotSize()));
        map.put(IEXEC_BOT_FIRST_INDEX, String.valueOf(taskDescription.getBotFirstIndex()));
        map.put(IEXEC_BOT_TASK_INDEX, String.valueOf(taskDescription.getBotIndex()));
        // input files
        int nbFiles = taskDescription.getInputFiles() == null ? 0 : taskDescription.getInputFiles().size();
        map.put(IEXEC_INPUT_FILES_NUMBER, String.valueOf(nbFiles));
        map.put(IEXEC_INPUT_FILES_FOLDER, IexecFileHelper.SLASH_IEXEC_IN);
        if (nbFiles == 0) {
            return map;
        }
        int index = 1;
        for (String inputFileUrl : taskDescription.getInputFiles()) {
            map.put(IEXEC_INPUT_FILE_NAME_PREFIX + index, FilenameUtils.getName(inputFileUrl));
            index++;
        }
        return map;
    }

    public static List<String> getComputeStageEnvList(TaskDescription taskDescription) {
        List<String> list = new ArrayList<>();
        getComputeStageEnvMap(taskDescription).forEach((key, value) -> list.add(key + "=" + value));
        return list;
    }
}