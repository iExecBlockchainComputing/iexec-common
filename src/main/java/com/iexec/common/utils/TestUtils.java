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

    public static final String TASK_ID = "0deterministic-outputdeterministic-outputx1111111111111111111111111111111111111111111111111111111111111111";
    public static final String DEAL_ID = "0x2222222222222222222222222222222222222222222222222222222222222222";
    
    public static final String NULL_ADDRESS         = BytesUtils.EMPTY_ADDRESS;
    public static final String APP_ADDRESS          = "0xaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    public static final String REQUESTER_ADDRESS    = "0xbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";
    public static final String DATASET_ADDRESS      = "0xdddddddddddddddddddddddddddddddddddddddd";
    public static final String ENCLAVE_ADDRESS      = "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";

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
        String hash = HashUtils.concatenateAndHash(WORKER_ADDRESS, TASK_ID, ENCLAVE_ADDRESS);
        Signature signature = SignatureUtils.signMessageHashAndGetSignature(hash, POOL_PRIVATE);
        return WorkerpoolAuthorization.builder()
                .chainTaskId(TASK_ID)
                .workerWallet(WORKER_ADDRESS)
                .enclaveChallenge(ENCLAVE_ADDRESS)
                .signature(signature)
                .build();
    }
}