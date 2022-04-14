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
    APP_COMPUTE_FAILED,
    // pre-computation
    PRE_COMPUTE_EMPTY_REQUIRED_ENV_VAR, //exit 1 known start
    PRE_COMPUTE_OUTPUT_FOLDER_NOT_FOUND,
    PRE_COMPUTE_DATASET_DOWNLOAD_FAILED,
    PRE_COMPUTE_INVALID_DATASET_CHECKSUM,
    PRE_COMPUTE_INVALID_DATASET_KEY,
    PRE_COMPUTE_DATASET_DECRYPTION_FAILED,
    PRE_COMPUTE_SAVING_PLAIN_DATASET_FAILED,
    PRE_COMPUTE_BAD_INPUT_FILE_ARGS,
    PRE_COMPUTE_INPUT_FILE_DOWNLOAD_FAILED, //exit 1 known end
    PRE_COMPUTE_UNKNOWN_FAILED, //exit 1 unknown
    PRE_COMPUTE_NO_REPORT_FAILED, //exit 2
    PRE_COMPUTE_NO_CONTEXT_FAILED, //exit 3
    // post-computation
    POST_COMPUTE_FAILED,
    TEE_NOT_SUPPORTED,
    APP_NOT_FOUND_LOCALLY,
    DETERMINISM_HASH_NOT_FOUND,
    ENCLAVE_SIGNATURE_NOT_FOUND,
    TEE_EXECUTION_NOT_VERIFIED,
    CONTRIBUTION_AUTHORIZATION_NOT_FOUND,
    RESULT_ENCRYPTION_FAILED,
    RESULT_LINK_MISSING,

    // Post compute
    POST_COMPUTE_UNKNOWN_ISSUE,
    POST_COMPUTE_MISSING_TASK_ID,
    POST_COMPUTE_MISSING_WORKER_ADDRESS,
    POST_COMPUTE_MISSING_TEE_CHALLENGE_PRIVATE_KEY,
    POST_COMPUTE_MISSING_ENCRYPTION_PUBLIC_KEY,
    POST_COMPUTE_MISSING_STORAGE_TOKEN,
    POST_COMPUTE_COMPUTED_FILE_NOT_FOUND,
    POST_COMPUTE_RESULT_DIGEST_COMPUTATION_FAILED,
    POST_COMPUTE_OUT_FOLDER_ZIP_FAILED,
    POST_COMPUTE_ENCRYPTION_FAILED,
    POST_COMPUTE_RESULT_FILE_NOT_FOUND,
    POST_COMPUTE_DROPBOX_UPLOAD_FAILED,
    POST_COMPUTE_IPFS_UPLOAD_RAILED,
    POST_COMPUTE_INVALID_TEE_SIGNATURE,
    POST_COMPUTE_SEND_COMPUTED_FILE_FAILED,

    // timeout
    CONTRIBUTION_TIMEOUT,
    REVEAL_TIMEOUT,
    // AFTER_DEADLINE,

    ABORTED_BY_WORKER,
    CANNOT_REVEAL,
    UNKNOWN;

    public static ReplicateStatusCause getReplicateAbortCause(TaskAbortCause taskAbortCause) {
        switch (taskAbortCause) {
            case CONSENSUS_REACHED:
                return ReplicateStatusCause.CONSENSUS_REACHED;
            case CONTRIBUTION_TIMEOUT:
                return ReplicateStatusCause.CONTRIBUTION_TIMEOUT;
            default:
                return ReplicateStatusCause.UNKNOWN;
        }
    }
}
