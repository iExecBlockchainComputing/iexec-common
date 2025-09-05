/*
 * Copyright 2025 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.worker.tee;

public enum TeeSessionEnvironmentVariable {
    // common
    IEXEC_TASK_ID,
    SIGN_WORKER_ADDRESS,
    SIGN_TEE_CHALLENGE_PRIVATE_KEY,
    IEXEC_IN,
    IEXEC_OUT,
    // pre-compute
    IEXEC_BOT_TASK_INDEX,
    IEXEC_BOT_SIZE,
    IEXEC_BOT_FIRST_INDEX,
    IS_DATASET_REQUIRED,
    IEXEC_DATASET_ADDRESS,
    IEXEC_DATASET_URL,
    IEXEC_DATASET_FILENAME,
    IEXEC_DATASET_CHECKSUM,
    IEXEC_DATASET_KEY,
    IEXEC_INPUT_FILES_NUMBER,
    IEXEC_INPUT_FILES_FOLDER,
    IEXEC_PRE_COMPUTE_OUT,
    BULK_SIZE,
    // post-compute
    RESULT_ENCRYPTION,
    RESULT_ENCRYPTION_PUBLIC_KEY,
    RESULT_STORAGE_PROVIDER,
    RESULT_STORAGE_PROXY,
    RESULT_STORAGE_TOKEN,
    RESULT_STORAGE_CALLBACK;
}
