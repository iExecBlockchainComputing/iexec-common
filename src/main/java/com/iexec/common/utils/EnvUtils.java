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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iexec.common.task.TaskDescription;

import org.apache.commons.io.FilenameUtils;

public abstract class EnvUtils {

    /*
     * Env variables that will be injected in the container of
     * a task computation.
     * 
     * /!\ If you change those values please don't forget to update 
     * the palaemon config file.
     */
    public static final String IEXEC_IN_ENV_PROPERTY = "IEXEC_IN";
    public static final String IEXEC_OUT_ENV_PROPERTY = "IEXEC_OUT";
    public static final String IEXEC_DATASET_FILENAME_ENV_PROPERTY = "IEXEC_DATASET_FILENAME";
    public static final String IEXEC_BOT_TASK_INDEX_ENV_PROPERTY = "IEXEC_BOT_TASK_INDEX";
    public static final String IEXEC_BOT_SIZE_ENV_PROPERTY = "IEXEC_BOT_SIZE";
    public static final String IEXEC_BOT_FIRST_INDEX_ENV_PROPERTY = "IEXEC_BOT_FIRST_INDEX";
    public static final String IEXEC_NB_INPUT_FILES_ENV_PROPERTY = "IEXEC_NB_INPUT_FILES";
    public static final String IEXEC_INPUT_FILES_ENV_PROPERTY_PREFIX = "IEXEC_INPUT_FILE_NAME_";
    public static final String IEXEC_INPUT_FILES_FOLDER_ENV_PROPERTY = "IEXEC_INPUT_FILES_FOLDER";

    public static Map<String, String> getContainerEnvMap(TaskDescription taskDescription) {
        String datasetFilename = FileHelper.getFilenameFromUri(taskDescription.getDatasetUri());
        Map<String, String> map = new HashMap<String, String>();
        map.put(IEXEC_IN_ENV_PROPERTY, FileHelper.SLASH_IEXEC_IN);
        map.put(IEXEC_OUT_ENV_PROPERTY, FileHelper.SLASH_IEXEC_OUT);
        map.put(IEXEC_DATASET_FILENAME_ENV_PROPERTY, datasetFilename);
        map.put(IEXEC_BOT_SIZE_ENV_PROPERTY, String.valueOf(taskDescription.getBotSize()));
        map.put(IEXEC_BOT_FIRST_INDEX_ENV_PROPERTY, String.valueOf(taskDescription.getBotFirstIndex()));
        map.put(IEXEC_BOT_TASK_INDEX_ENV_PROPERTY, String.valueOf(taskDescription.getBotIndex()));
        int nbFiles = taskDescription.getInputFiles() == null ? 0 : taskDescription.getInputFiles().size();
        map.put(IEXEC_NB_INPUT_FILES_ENV_PROPERTY, String.valueOf(nbFiles));
        int inputFileIndex = 1;
        for (String inputFile : taskDescription.getInputFiles()) {
            map.put(IEXEC_INPUT_FILES_ENV_PROPERTY_PREFIX + inputFileIndex, FilenameUtils.getName(inputFile));
            inputFileIndex++;
        }
        map.put(IEXEC_INPUT_FILES_FOLDER_ENV_PROPERTY, FileHelper.SLASH_IEXEC_IN);
        return map;
    }

    public static List<String> getContainerEnvList(TaskDescription taskDescription) {
        List<String> list = new ArrayList<>();
        getContainerEnvMap(taskDescription).forEach((key, value) -> list.add(key + "=" + value));
        return list;        
    }
}