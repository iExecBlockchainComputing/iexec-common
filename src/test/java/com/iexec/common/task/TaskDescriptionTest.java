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

import com.iexec.common.chain.*;
import com.iexec.common.dapp.DappType;
import com.iexec.common.tee.TeeEnclaveConfiguration;
import com.iexec.common.tee.TeeUtils;
import com.iexec.common.utils.BytesUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

class TaskDescriptionTest {

    public static final String CHAIN_TASK_ID = "chainTaskId";
    public static final String REQUESTER = "requester";
    public static final String BENEFICIARY = "beneficiary";
    public static final String CALLBACK = "callback";
    public static final DappType APP_TYPE = DappType.DOCKER;
    public static final String APP_URI = "https://uri";
    public static final String APP_ADDRESS = "appAddress";
    public static final TeeEnclaveConfiguration enclaveConfig = new TeeEnclaveConfiguration();
    public static final String CMD = "cmd";
    public static final int MAX_EXECUTION_TIME = 1;
    public static final boolean IS_TEE_TASK = true;
    public static final int BOT_SIZE = 1;
    public static final int BOT_FIRST = 2;
    public static final int TASK_IDX = 3;
    public static final boolean DEVELOPER_LOGGER_ENABLED = true;
    public static final String DATASET_ADDRESS = "datasetAddress";
    public static final String DATASET_URI = "https://datasetUri";
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
    void shouldBuildAndGetTaskDescription() {
        TaskDescription task = TaskDescription.builder()
                .chainTaskId(CHAIN_TASK_ID)
                .requester(REQUESTER)
                .beneficiary(BENEFICIARY)
                .callback(CALLBACK)
                .appType(APP_TYPE)
                .appUri(APP_URI)
                .appAddress(APP_ADDRESS)
                .appEnclaveConfiguration(enclaveConfig)
                .cmd(CMD)
                .maxExecutionTime(MAX_EXECUTION_TIME)
                .isTeeTask(IS_TEE_TASK)
                .botSize(BOT_SIZE)
                .botFirstIndex(BOT_FIRST)
                .botIndex(TASK_IDX)
                .developerLoggerEnabled(DEVELOPER_LOGGER_ENABLED)
                .datasetAddress(DATASET_ADDRESS)
                .datasetUri(DATASET_URI)
                .datasetName(DATASET_NAME)
                .datasetChecksum(DATASET_CHECKSUM)
                .inputFiles(INPUT_FILES)
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
        Assertions.assertEquals(APP_ADDRESS,
                task.getAppAddress());
        Assertions.assertEquals(enclaveConfig,
                task.getAppEnclaveConfiguration());
        Assertions.assertEquals(CMD,
                task.getCmd());
        Assertions.assertEquals(MAX_EXECUTION_TIME,
                task.getMaxExecutionTime());
        Assertions.assertEquals(IS_TEE_TASK,
                task.isTeeTask());
        Assertions.assertEquals(TASK_IDX,
                task.getBotIndex());
        Assertions.assertEquals(BOT_SIZE,
                task.getBotSize());
        Assertions.assertEquals(BOT_FIRST,
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
                task.containsCallback());
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
        Assertions.assertEquals(true, task.containsDataset());
    }

    @Test
    void toTaskDescription() {
        ChainDeal chainDeal = ChainDeal.builder()
                .requester(REQUESTER)
                .beneficiary(BENEFICIARY)
                .callback(CALLBACK)
                .chainApp(ChainApp.builder()
                        .chainAppId(APP_ADDRESS)
                        .type(APP_TYPE.toString())
                        .uri(BytesUtils.bytesToString(APP_URI.getBytes(StandardCharsets.UTF_8)))
                        .build())
                .params(DealParams.builder()
                        .iexecArgs(CMD)
                        .iexecInputFiles(INPUT_FILES)
                        .iexecDeveloperLoggerEnabled(DEVELOPER_LOGGER_ENABLED)
                        .iexecResultStorageProvider(RESULT_STORAGE_PROVIDER)
                        .iexecResultStorageProxy(RESULT_STORAGE_PROXY)
                        .iexecResultEncryption(IS_RESULT_ENCRYPTION)
                        .iexecTeePostComputeImage(TEE_POST_COMPUTE_IMAGE)
                        .iexecTeePostComputeFingerprint(TEE_POST_COMPUTE_FINGERPRINT)
                        .build())
                .chainDataset(ChainDataset.builder()
                        .chainDatasetId(DATASET_ADDRESS)
                        .name(DATASET_NAME)
                        .uri(BytesUtils.bytesToString(DATASET_URI.getBytes(StandardCharsets.UTF_8)))
                        .checksum(DATASET_CHECKSUM).build())
                .tag(TeeUtils.TEE_TAG)
                .chainCategory(ChainCategory.builder()
                        .maxExecutionTime(MAX_EXECUTION_TIME)
                        .build())
                .botFirst(BigInteger.valueOf(BOT_FIRST))
                .botSize(BigInteger.valueOf(BOT_SIZE))
                .build();

        TaskDescription task =
                TaskDescription.toTaskDescription(CHAIN_TASK_ID, TASK_IDX, chainDeal);

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
        Assertions.assertEquals(APP_ADDRESS,
                task.getAppAddress());
        Assertions.assertEquals(CMD,
                task.getCmd());
        Assertions.assertEquals(MAX_EXECUTION_TIME,
                task.getMaxExecutionTime());
        Assertions.assertEquals(IS_TEE_TASK,
                task.isTeeTask());
        Assertions.assertEquals(TASK_IDX,
                task.getBotIndex());
        Assertions.assertEquals(BOT_SIZE,
                task.getBotSize());
        Assertions.assertEquals(BOT_FIRST,
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
                task.containsCallback());
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

    @Test
    void shouldContainDataset() {
        Assertions.assertTrue(TaskDescription.builder()
                .datasetAddress(DATASET_ADDRESS)
                .datasetUri(DATASET_URI)
                .datasetName(DATASET_NAME)
                .datasetChecksum(DATASET_CHECKSUM)
                .build()
                .containsDataset());
    }

    @Test
    void shouldNotContainDataset() {
        Assertions.assertFalse(TaskDescription.builder()
                // .datasetAddress(DATASET_ADDRESS)
                .datasetUri(DATASET_URI)
                .datasetName(DATASET_NAME)
                .datasetChecksum(DATASET_CHECKSUM)
                .build()
                .containsDataset());

        Assertions.assertFalse(TaskDescription.builder()
                .datasetAddress(DATASET_ADDRESS)
                // .datasetUri(DATASET_URI)
                .datasetName(DATASET_NAME)
                .datasetChecksum(DATASET_CHECKSUM)
                .build()
                .containsDataset());

        Assertions.assertFalse(TaskDescription.builder()
                .datasetAddress(DATASET_ADDRESS)
                .datasetUri(DATASET_URI)
                // .datasetName(DATASET_NAME)
                .datasetChecksum(DATASET_CHECKSUM)
                .build()
                .containsDataset());

        Assertions.assertFalse(TaskDescription.builder()
                .datasetAddress(DATASET_ADDRESS)
                .datasetUri(DATASET_URI)
                .datasetName(DATASET_NAME)
                // .datasetChecksum(DATASET_CHECKSUM)
                .build()
                .containsDataset());
    }

    @Test
    void shouldContainCallback() {
        Assertions.assertTrue(TaskDescription.builder()
                .callback(CALLBACK)
                .build()
                .containsCallback());
    }

    @Test
    void shouldNotContainCallback() {
        Assertions.assertFalse(TaskDescription.builder()
                .callback(BytesUtils.EMPTY_ADDRESS)
                .build()
                .containsCallback());
        Assertions.assertFalse(TaskDescription.builder()
                // .callback(CALLBACK)
                .build()
                .containsCallback());
    }

    @Test
    void shouldContainInputFiles() {
        Assertions.assertTrue(TaskDescription.builder()
                .chainTaskId(CHAIN_TASK_ID)
                .inputFiles(List.of("http://file1", "http://file2"))
                .build()
                .containsInputFiles());
    }

    @Test
    void shouldNotContainInputFiles() {
        Assertions.assertFalse(TaskDescription.builder()
                .chainTaskId(CHAIN_TASK_ID)
                // .inputFiles(List.of("http://file1", "http://file2"))
                .build()
                .containsInputFiles());
    }

    @Test
    void shouldContainPostCompute() {
        Assertions.assertTrue(TaskDescription.builder()
                .chainTaskId(CHAIN_TASK_ID)
                .teePostComputeImage(TEE_POST_COMPUTE_IMAGE)
                .teePostComputeFingerprint(TEE_POST_COMPUTE_FINGERPRINT)
                .build()
                .containsPostCompute());
    }

    @Test
    void shouldNotContainPostComputeIfMissingImage() {
        Assertions.assertFalse(TaskDescription.builder()
                .chainTaskId(CHAIN_TASK_ID)
                // .teePostComputeImage(TEE_POST_COMPUTE_IMAGE)
                .teePostComputeFingerprint(TEE_POST_COMPUTE_FINGERPRINT)
                .build()
                .containsPostCompute());
    }

    @Test
    void shouldNotContainPostComputeIfMissingFingerprint() {
        Assertions.assertFalse(TaskDescription.builder()
                .chainTaskId(CHAIN_TASK_ID)
                .teePostComputeImage(TEE_POST_COMPUTE_IMAGE)
                // .teePostComputeFingerprint(TEE_POST_COMPUTE_FINGERPRINT)
                .build()
                .containsPostCompute());
    }

    @Test
    void shouldNotContainPostComputeIfMissingBothImageAndFingerprint() {
        Assertions.assertFalse(TaskDescription.builder()
                .chainTaskId(CHAIN_TASK_ID)
                // .teePostComputeImage(TEE_POST_COMPUTE_IMAGE)
                // .teePostComputeFingerprint(TEE_POST_COMPUTE_FINGERPRINT)
                .build()
                .containsPostCompute());
    }
}