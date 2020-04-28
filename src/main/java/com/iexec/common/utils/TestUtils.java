package com.iexec.common.utils;

import java.math.BigInteger;

import com.iexec.common.chain.ChainDeal;
import com.iexec.common.chain.ChainTask;
import com.iexec.common.chain.ChainTaskStatus;
import com.iexec.common.chain.ContributionAuthorization;
import com.iexec.common.security.Signature;

import org.web3j.utils.Numeric;

public class TestUtils {

    // pool credentials
    public static final String POOL_ADDRESS = "0xc911f9345717ba7c8ec862ce002af3e058df84e4";
    public static final String POOL_PRIVATE = "102522099393445372136873302035232363929034445624871422932329330474318520724961";
    public static final String POOL_PUBLIC = "1285624548294401086908574986105558640042478948396168890317212420080559158694027463"
            + "6961382756796765180200361366139934715110248365183641214991970771254200001";
    public static final String POOL_WRONG_SIGNATURE = "0xf869daaca2407b7eabd27c3c4c5a3f3565172ca7211ac1d8bfacea2beb511a4029446a07cccc0884"
            + "c2193b269dfb341461db8c680a8898bb53862d6e48340c2e1b";

    // worker credentials
    public static final String WORKER_ADDRESS = "0x87ae2b87b5db23830572988fb1f51242fbc471ce";
    public static final String WORKER_PRIVATE = "94468770798077642898983022974764469986911887443189349637725359812048150813734";
    public static final String WORKER_PUBLIC = "621046225357529381046383046352342803414716320125454009566703531976700763499391326"
            + "6802719233732680574953810107259987473213214847649017603280791327411859883";    

    public static final String TASK_ID = "0x1111111111111111111111111111111111111111111111111111111111111111";
    public static final String DEAL_ID = "0x2222222222222222222222222222222222222222222222222222222222222222";
    
    public static final String NULL_ADDRESS         = "0x0000000000000000000000000000000000000000";
    public static final String APP_ADDRESS          = "0xaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    public static final String REQUESTER_ADDRESS    = "0xbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";
    public static final String DATASET_ADDRESS      = "0xdddddddddddddddddddddddddddddddddddddddd";
    public static final String ENCLAVE_ADDRESS      = "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";

    public static final String NON_TEE_TAG =    "0x0000000000000000000000000000000000000000000000000000000000000000";
    public static final String TEE_TAG =        "0x0000000000000000000000000000000000000000000000000000000000000001";

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
    public static ContributionAuthorization getTeeContributionAuth() {
        String hexPrivateKey = Numeric.toHexStringWithPrefix(new BigInteger(POOL_PRIVATE));
        String hash = HashUtils.concatenateAndHash(WORKER_ADDRESS, TASK_ID, ENCLAVE_ADDRESS);
        Signature signature = SignatureUtils.signMessageHashAndGetSignature(hash, hexPrivateKey);
        return ContributionAuthorization.builder()
                .chainTaskId(TASK_ID)
                .workerWallet(WORKER_ADDRESS)
                .enclaveChallenge(ENCLAVE_ADDRESS)
                .signature(signature)
                .build();
    }
}