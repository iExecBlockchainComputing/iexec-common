/*
 * Copyright 2020-2023 IEXEC BLOCKCHAIN TECH
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

import com.iexec.commons.poco.task.TaskAbortCause;

public enum ReplicateStatusCause {

    // region chain
    CHAIN_UNREACHABLE,
    BLOCK_NOT_REACHED,
    STAKE_TOO_LOW,
    OUT_OF_GAS,
    TASK_NOT_ACTIVE,
    TASK_DESCRIPTION_INVALID,
    TASK_DESCRIPTION_NOT_FOUND,
    CONTRIBUTION_ALREADY_SET,
    CONSENSUS_REACHED,
    CHAIN_RECEIPT_NOT_VALID,
    CONSENSUS_BLOCK_MISSING,
    // endregion

    // region download
    APP_IMAGE_DOWNLOAD_FAILED,
    INPUT_FILES_DOWNLOAD_FAILED,
    DATASET_FILE_DOWNLOAD_FAILED,
    DATASET_FILE_BAD_CHECKSUM,
    // endregion

    // region TEE session generation
    // Authorization
    TEE_SESSION_GENERATION_INVALID_AUTHORIZATION,
    TEE_SESSION_GENERATION_EXECUTION_NOT_AUTHORIZED_EMPTY_PARAMS_UNAUTHORIZED,
    TEE_SESSION_GENERATION_EXECUTION_NOT_AUTHORIZED_NO_MATCH_ONCHAIN_TYPE,
    TEE_SESSION_GENERATION_EXECUTION_NOT_AUTHORIZED_GET_CHAIN_TASK_FAILED,
    TEE_SESSION_GENERATION_EXECUTION_NOT_AUTHORIZED_TASK_NOT_ACTIVE,
    TEE_SESSION_GENERATION_EXECUTION_NOT_AUTHORIZED_GET_CHAIN_DEAL_FAILED,
    TEE_SESSION_GENERATION_EXECUTION_NOT_AUTHORIZED_INVALID_SIGNATURE,

    // Pre-compute
    TEE_SESSION_GENERATION_PRE_COMPUTE_GET_DATASET_SECRET_FAILED,

    // App-compute
    TEE_SESSION_GENERATION_APP_COMPUTE_NO_ENCLAVE_CONFIG,
    TEE_SESSION_GENERATION_APP_COMPUTE_INVALID_ENCLAVE_CONFIG,

    // Post-compute
    TEE_SESSION_GENERATION_POST_COMPUTE_GET_ENCRYPTION_TOKENS_FAILED_EMPTY_BENEFICIARY_KEY,
    TEE_SESSION_GENERATION_POST_COMPUTE_GET_STORAGE_TOKENS_FAILED,

    TEE_SESSION_GENERATION_POST_COMPUTE_GET_SIGNATURE_TOKENS_FAILED_EMPTY_WORKER_ADDRESS,
    TEE_SESSION_GENERATION_POST_COMPUTE_GET_SIGNATURE_TOKENS_FAILED_EMPTY_PUBLIC_ENCLAVE_CHALLENGE,
    TEE_SESSION_GENERATION_POST_COMPUTE_GET_SIGNATURE_TOKENS_FAILED_EMPTY_TEE_CHALLENGE,
    TEE_SESSION_GENERATION_POST_COMPUTE_GET_SIGNATURE_TOKENS_FAILED_EMPTY_TEE_CREDENTIALS,

    // Secure session generation
    TEE_SESSION_GENERATION_SECURE_SESSION_STORAGE_CALL_FAILED,
    TEE_SESSION_GENERATION_SECURE_SESSION_GENERATION_FAILED,
    TEE_SESSION_GENERATION_SECURE_SESSION_NO_TEE_FRAMEWORK,
    @Deprecated(forRemoval = true)
    TEE_SESSION_GENERATION_SECURE_SESSION_NO_TEE_PROVIDER,

    // Miscellaneous
    TEE_SESSION_GENERATION_GET_TASK_DESCRIPTION_FAILED,
    TEE_SESSION_GENERATION_NO_SESSION_REQUEST,
    TEE_SESSION_GENERATION_NO_TASK_DESCRIPTION,
    TEE_SESSION_GENERATION_GET_SESSION_FAILED,

    TEE_SESSION_GENERATION_UNKNOWN_ISSUE,
    // endregion

    // region compute
    APP_NOT_FOUND_LOCALLY,
    APP_COMPUTE_FAILED,
    APP_COMPUTE_TIMEOUT,
    // endregion

    // region pre-compute
    PRE_COMPUTE_MISSING_ENCLAVE_CONFIGURATION,
    PRE_COMPUTE_INVALID_ENCLAVE_CONFIGURATION,
    PRE_COMPUTE_INVALID_ENCLAVE_HEAP_CONFIGURATION,
    PRE_COMPUTE_IMAGE_MISSING,
    PRE_COMPUTE_TASK_ID_MISSING, //exit 3
    PRE_COMPUTE_EXIT_REPORTING_FAILED, //exit 2
    PRE_COMPUTE_OUTPUT_PATH_MISSING, //exit 1 (known)
    PRE_COMPUTE_IS_DATASET_REQUIRED_MISSING,
    PRE_COMPUTE_DATASET_URL_MISSING,
    PRE_COMPUTE_DATASET_KEY_MISSING,
    PRE_COMPUTE_DATASET_CHECKSUM_MISSING,
    PRE_COMPUTE_DATASET_FILENAME_MISSING,
    PRE_COMPUTE_INPUT_FILES_NUMBER_MISSING,
    PRE_COMPUTE_AT_LEAST_ONE_INPUT_FILE_URL_MISSING,
    PRE_COMPUTE_OUTPUT_FOLDER_NOT_FOUND,
    PRE_COMPUTE_DATASET_DOWNLOAD_FAILED,
    PRE_COMPUTE_INVALID_DATASET_CHECKSUM,
    PRE_COMPUTE_DATASET_DECRYPTION_FAILED,
    PRE_COMPUTE_SAVING_PLAIN_DATASET_FAILED,
    PRE_COMPUTE_INPUT_FILE_DOWNLOAD_FAILED,
    PRE_COMPUTE_TIMEOUT,
    PRE_COMPUTE_FAILED_UNKNOWN_ISSUE, //exit 1 (unknown)
    // endregion

    // region post-compute
    POST_COMPUTE_IMAGE_MISSING,
    POST_COMPUTE_TASK_ID_MISSING, // exit 3
    POST_COMPUTE_EXIT_REPORTING_FAILED, // exit 2
    POST_COMPUTE_WORKER_ADDRESS_MISSING, //exit 1 (known)
    POST_COMPUTE_TEE_CHALLENGE_PRIVATE_KEY_MISSING,
    POST_COMPUTE_ENCRYPTION_PUBLIC_KEY_MISSING,
    POST_COMPUTE_STORAGE_TOKEN_MISSING,
    POST_COMPUTE_COMPUTED_FILE_NOT_FOUND,
    POST_COMPUTE_RESULT_DIGEST_COMPUTATION_FAILED,
    POST_COMPUTE_OUT_FOLDER_ZIP_FAILED,
    POST_COMPUTE_TOO_LONG_RESULT_FILE_NAME,
    POST_COMPUTE_ENCRYPTION_FAILED,
    POST_COMPUTE_RESULT_FILE_NOT_FOUND,
    POST_COMPUTE_DROPBOX_UPLOAD_FAILED,
    POST_COMPUTE_IPFS_UPLOAD_FAILED,
    POST_COMPUTE_INVALID_TEE_SIGNATURE,
    POST_COMPUTE_SEND_COMPUTED_FILE_FAILED,
    POST_COMPUTE_TIMEOUT,
    POST_COMPUTE_FAILED_UNKNOWN_ISSUE, //exit 1 (unknown)
    // endregion

    // region contribute
    TEE_NOT_SUPPORTED,
    DETERMINISM_HASH_NOT_FOUND,
    ENCLAVE_SIGNATURE_NOT_FOUND,
    WORKERPOOL_AUTHORIZATION_NOT_FOUND,
    // endregion

    // region upload
    RESULT_LINK_MISSING,
    // endregion

    // region contributeAndFinalize
    TRUST_NOT_1,
    TASK_ALREADY_CONTRIBUTED,
    // endregion

    // region timeout
    CONTRIBUTION_TIMEOUT,
    REVEAL_TIMEOUT,
    // AFTER_DEADLINE,
    // endregion

    // region miscellaneous
    TEE_PREPARATION_FAILED,
    UNKNOWN_SMS,
    GET_TEE_SERVICES_CONFIGURATION_FAILED,
    ABORTED_BY_WORKER,
    CANNOT_REVEAL,
    UNKNOWN;
    // endregion

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
