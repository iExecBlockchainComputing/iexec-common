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

import com.iexec.common.chain.ChainDeal;
import com.iexec.common.chain.ChainTask;
import com.iexec.common.chain.ChainTaskStatus;
import com.iexec.common.chain.WorkerpoolAuthorization;
import com.iexec.common.security.Signature;
import com.iexec.common.tee.TeeUtils;

public class TestUtils {

    // pool credentials
    public static final String POOL_ADDRESS = "0xc911f9345717ba7c8ec862ce002af3e058df84e4";
    public static final String POOL_PRIVATE = "0xe2a973b083fae8043543f15313955aecee9de809a318656c1cfb22d3a6d52de1";
    public static final String POOL_PUBLIC = "0xf57804d7d4057f12652d4e2cfe8e6cdbc456c44fcb0bac3beb8bc6051bc775e1317"
            + "242f93eb81034b3d43438570417d1049b431ad5a0449bb0b75968636546c1";
    public static final String POOL_WRONG_SIGNATURE = "0xf869daaca2407b7eabd27c3c4c5a3f3565172ca7211ac1d8bfacea2beb511a4029446a07cccc0884"
            + "c2193b269dfb341461db8c680a8898bb53862d6e48340c2e1b";

    // worker credentials
    public static final String WORKER_ADDRESS = "0x87ae2b87b5db23830572988fb1f51242fbc471ce";
    public static final String WORKER_PRIVATE = "0xd0db6df0ebcd1d41439d91d86c5fc5c1806ee9cd8e71e3d5544bb7294b435c26";
    public static final String WORKER_PUBLIC = "0x76941b6e95be43ebfad0e81d7e2fae6268aa5f57e26cf3112adee8791d08775645"
            + "a0c0879d7621ecc4f8c8b41b370ea0ffadd82693ffc429127fd6acd090f1ab";

    public static final String CHAIN_TASK_ID = "0x1111111111111111111111111111111111111111111111111111111111111111";
    public static final String DEAL_ID = "0x2222222222222222222222222222222222222222222222222222222222222222";
    
    public static final String NULL_ADDRESS = BytesUtils.EMPTY_ADDRESS;
    public static final String APP_ADDRESS = "0xaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    public static final String REQUESTER_ADDRESS = "0xbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";
    public static final String DATASET_ADDRESS = "0xdddddddddddddddddddddddddddddddddddddddd";
    public static final String ENCLAVE_ADDRESS = "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
    public final static String WALLET_WORKER_1 = "0x1a69b2eb604db8eba185df03ea4f5288dcbbd248";
    public final static String WALLET_WORKER_2 = "0x2ab2674aa374fe6415d11f0a8fcbd8027fc1e6a9";
    public final static String WALLET_WORKER_3 = "0x3a3406e69adf886c442ff1791cbf67cea679275d";
    public final static String WALLET_WORKER_4 = "0x4aef50214110fdad4e8b9128347f2ba1ec72f614";

    public static final String NON_TEE_TAG =    "0x0000000000000000000000000000000000000000000000000000000000000000";
    public static final String TEE_TAG =        TeeUtils.TEE_TAG;

    private TestUtils() {
        throw new UnsupportedOperationException();
    }

    // deal
    public static ChainDeal getChainDeal() {
        return ChainDeal.builder().poolOwner(POOL_ADDRESS).build();
    }

    // task
    public static ChainTask getChainTask(ChainTaskStatus status) {
        return ChainTask.builder().dealid(DEAL_ID).status(status).build();
    }

    // contribution authorization
    public static WorkerpoolAuthorization getTeeWorkerpoolAuth() {
        String hash = HashUtils.concatenateAndHash(WORKER_ADDRESS, CHAIN_TASK_ID, ENCLAVE_ADDRESS);
        Signature signature = SignatureUtils.signMessageHashAndGetSignature(hash, POOL_PRIVATE);
        return WorkerpoolAuthorization.builder()
                .chainTaskId(CHAIN_TASK_ID)
                .workerWallet(WORKER_ADDRESS)
                .enclaveChallenge(ENCLAVE_ADDRESS)
                .signature(signature)
                .build();
    }
}