/*
 * Copyright 2022 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.tee;

public enum TeeSessionGenerationError {
    REQUEST_NOT_SIGNED_BY_HIMSELF,

    EXECUTION_NOT_AUTHORIZED_EMPTY_PARAMS_UNAUTHORIZED,
    EXECUTION_NOT_AUTHORIZED_NO_MATCH_ONCHAIN_TYPE,
    EXECUTION_NOT_AUTHORIZED_GET_CHAIN_TASK_FAILED,
    EXECUTION_NOT_AUTHORIZED_TASK_NOT_ACTIVE,
    EXECUTION_NOT_AUTHORIZED_GET_CHAIN_DEAL_FAILED,
    EXECUTION_NOT_AUTHORIZED_INVALID_SIGNATURE,

    GET_TASK_DESCRIPTION_FAILED,

    NO_SESSION_REQUEST,
    NO_TASK_DESCRIPTION,

    PRE_COMPUTE_GET_DATASET_SECRET_FAILED,

    COMPUTE_NO_ENCLAVE_CONFIG,
    COMPUTE_INVALID_ENCLAVE_CONFIG,

    POST_COMPUTE_GET_ENCRYPTION_TOKENS_FAILED_UNKNOWN_ISSUE,    // If `getPostComputeEncryptionTokens` fails with no known reason
    POST_COMPUTE_GET_ENCRYPTION_TOKENS_FAILED_EMPTY_BENEFICIARY_KEY,

    POST_COMPUTE_GET_STORAGE_TOKENS_FAILED_UNKNOWN_ISSUE,   // If `getPostComputeStorageTokens` fails with no known reason
    POST_COMPUTE_GET_STORAGE_TOKENS_FAILED,

    POST_COMPUTE_GET_SIGNATURE_TOKENS_FAILED_UNKNOWN_ISSUE, // If `getPostComputeSignTokens` fails with no known reason
    POST_COMPUTE_GET_SIGNATURE_TOKENS_FAILED_EMPTY_WORKER_ADDRESS,
    POST_COMPUTE_GET_SIGNATURE_TOKENS_FAILED_EMPTY_PUBLIC_ENCLAVE_CHALLENGE,
    POST_COMPUTE_GET_SIGNATURE_TOKENS_FAILED_EMPTY_TEE_CHALLENGE,
    POST_COMPUTE_GET_SIGNATURE_TOKENS_FAILED_EMPTY_TEE_CREDENTIALS,

    GET_SESSION_YML_FAILED,

    SECURE_SESSION_GENERATION_FAILED,

    UNKNOWN_ISSUE
}
