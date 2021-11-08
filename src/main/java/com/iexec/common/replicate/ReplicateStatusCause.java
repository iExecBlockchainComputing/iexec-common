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

import com.iexec.common.task.TaskAbortCause;

public enum ReplicateStatusCause {

    // chain
    CHAIN_UNREACHABLE,
    CHAIN_UNSYNCHRONIZED,
    BLOCK_NOT_REACHED,
    STAKE_TOO_LOW,
    OUT_OF_GAS,
    TASK_NOT_ACTIVE,
    TASK_DESCRIPTION_NOT_FOUND,
    CONTRIBUTION_ALREADY_SET,
    CONSENSUS_REACHED,
    CHAIN_RECEIPT_NOT_VALID,
    CONSENSUS_BLOCK_MISSING,

    // download
    APP_IMAGE_DOWNLOAD_FAILED,
    INPUT_FILES_DOWNLOAD_FAILED,
    DATASET_FILE_DOWNLOAD_FAILED,
    DATASET_FILE_BAD_CHECKSUM,

    // computation
    PRE_COMPUTE_FAILED,
    POST_COMPUTE_FAILED,
    TEE_NOT_SUPPORTED,
    APP_NOT_FOUND_LOCALLY,
    DETERMINISM_HASH_NOT_FOUND,
    ENCLAVE_SIGNATURE_NOT_FOUND,
    TEE_EXECUTION_NOT_VERIFIED,
    CONTRIBUTION_AUTHORIZATION_NOT_FOUND,
    RESULT_ENCRYPTION_FAILED,
    RESULT_LINK_MISSING,

    // timeout
    CONTRIBUTION_TIMEOUT,
    REVEAL_TIMEOUT,
    // AFTER_DEADLINE,

    ABORTED_BY_WORKER,
    CANNOT_REVEAL,
    UNKNOWN;

    public static ReplicateStatusCause getReplicateAbortCause(TaskAbortCause cause) {
        switch (cause) {
            case CONSENSUS_REACHED:
                return CONSENSUS_REACHED;
            case CONTRIBUTION_TIMEOUT:
                return CONTRIBUTION_TIMEOUT;
            default:
                return UNKNOWN;
        }
    }
}
