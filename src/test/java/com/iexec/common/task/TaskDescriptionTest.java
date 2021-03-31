/*
 * Copyright 2021 IEXEC BLOCKCHAIN TECH
 *
 * Licensed under the Apache License,
Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iexec.common.task;

import com.iexec.common.dapp.DappType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

class TaskDescriptionTest {

    public static final String CHAIN_TASK_ID = "chainTaskId";
    public static final String REQUESTER = "requester";
    public static final String BENEFICIARY = "beneficiary";
    public static final String CALLBACK = "callback";
    public static final DappType APP_TYPE = DappType.DOCKER;
    public static final String APP_URI = "appUri";
    public static final String CMD = "cmd";
    public static final int MAX_EXECUTION_TIME = 1;
    public static final boolean IS_TEE_TASK = true;
    public static final int BOT_INDEX = 1;
    public static final int BOT_SIZE = 3;
    public static final int BOT_FIRST_INDEX = 2;
    public static final boolean DEVELOPER_LOGGER_ENABLED = true;
    public static final String DATASET_URI = "datasetUri";
    public static final String DATASET_NAME = "datasetName";
    public static final String DATASET_CHECKSUM = "datasetChecksum";
    public static final List<String> INPUT_FILES = Collections.singletonList("inputFiles");
    public static final boolean IS_CALLBACK_REQUESTED = true;
    public static final boolean IS_RESULT_ENCRYPTION = true;
    public static final String RESULT_STORAGE_PROVIDER = "resultStorageProvider";
    public static final String RESULT_STORAGE_PROXY = "resultStorageProxy";
    public static final String TEE_POST_COMPUTE_IMAGE = "teePostComputeImage";
    public static final String TEE_POST_COMPUTE_FINGERPRINT = "teePostComputeFingerprint";

    @Test
    void shouldBuildAndGetTAskDescription() {
        TaskDescription task = TaskDescription.builder()
                .chainTaskId(CHAIN_TASK_ID)
                .requester(REQUESTER)
                .beneficiary(BENEFICIARY)
                .callback(CALLBACK)
                .appType(APP_TYPE)
                .appUri(APP_URI)
                .cmd(CMD)
                .maxExecutionTime(MAX_EXECUTION_TIME)
                .isTeeTask(IS_TEE_TASK)
                .botIndex(BOT_INDEX)
                .botSize(BOT_SIZE)
                .botFirstIndex(BOT_FIRST_INDEX)
                .developerLoggerEnabled(DEVELOPER_LOGGER_ENABLED)
                .datasetUri(DATASET_URI)
                .datasetName(DATASET_NAME)
                .datasetChecksum(DATASET_CHECKSUM)
                .inputFiles(INPUT_FILES)
                .isCallbackRequested(IS_CALLBACK_REQUESTED)
                .isResultEncryption(IS_RESULT_ENCRYPTION)
                .resultStorageProvider(RESULT_STORAGE_PROVIDER)
                .resultStorageProxy(RESULT_STORAGE_PROXY)
                .teePostComputeImage(TEE_POST_COMPUTE_IMAGE)
                .teePostComputeFingerprint(TEE_POST_COMPUTE_FINGERPRINT)
                .build();
        Assertions.assertEquals(CHAIN_TASK_ID,
                task.getChainTaskId());
        Assertions.assertEquals(REQUESTER,
                task.getRequester());
        Assertions.assertEquals(BENEFICIARY,
                task.getBeneficiary());
        Assertions.assertEquals(CALLBACK,
                task.getCallback());
        Assertions.assertEquals(APP_TYPE,
                task.getAppType());
        Assertions.assertEquals(APP_URI,
                task.getAppUri());
        Assertions.assertEquals(CMD,
                task.getCmd());
        Assertions.assertEquals(MAX_EXECUTION_TIME,
                task.getMaxExecutionTime());
        Assertions.assertEquals(IS_TEE_TASK,
                task.isTeeTask());
        Assertions.assertEquals(BOT_INDEX,
                task.getBotIndex());
        Assertions.assertEquals(BOT_SIZE,
                task.getBotSize());
        Assertions.assertEquals(BOT_FIRST_INDEX,
                task.getBotFirstIndex());
        Assertions.assertEquals(DEVELOPER_LOGGER_ENABLED,
                task.isDeveloperLoggerEnabled());
        Assertions.assertEquals(DATASET_URI,
                task.getDatasetUri());
        Assertions.assertEquals(DATASET_NAME,
                task.getDatasetName());
        Assertions.assertEquals(DATASET_CHECKSUM,
                task.getDatasetChecksum());
        Assertions.assertEquals(INPUT_FILES,
                task.getInputFiles());
        Assertions.assertEquals(IS_CALLBACK_REQUESTED,
                task.isCallbackRequested());
        Assertions.assertEquals(IS_RESULT_ENCRYPTION,
                task.isResultEncryption());
        Assertions.assertEquals(RESULT_STORAGE_PROVIDER,
                task.getResultStorageProvider());
        Assertions.assertEquals(RESULT_STORAGE_PROXY,
                task.getResultStorageProxy());
        Assertions.assertEquals(TEE_POST_COMPUTE_IMAGE,
                task.getTeePostComputeImage());
        Assertions.assertEquals(TEE_POST_COMPUTE_FINGERPRINT,
                task.getTeePostComputeFingerprint());
    }
}