package com.iexec.common.worker.result;

import com.iexec.common.result.ComputedFile;
import com.iexec.common.utils.HashUtils;
import lombok.extern.slf4j.Slf4j;
import org.web3j.crypto.Hash;

import java.io.File;

@Slf4j
public class ResultUtils {

    /*
     * Following env vars are required by iexec-sms and tee-worker-post-compute
     * */
    // result
    public static final String RESULT_TASK_ID = "RESULT_TASK_ID";
    //result encryption
    public static final String RESULT_ENCRYPTION = "RESULT_ENCRYPTION";
    public static final String RESULT_ENCRYPTION_PUBLIC_KEY = "RESULT_ENCRYPTION_PUBLIC_KEY";//BASE64
    //result storage
    public static final String RESULT_STORAGE_PROVIDER = "RESULT_STORAGE_PROVIDER";
    public static final String RESULT_STORAGE_PROXY = "RESULT_STORAGE_PROXY";
    public static final String RESULT_STORAGE_TOKEN = "RESULT_STORAGE_TOKEN";
    public static final String RESULT_STORAGE_CALLBACK = "RESULT_STORAGE_CALLBACK";
    //result sign
    public static final String RESULT_SIGN_WORKER_ADDRESS = "RESULT_SIGN_WORKER_ADDRESS";
    public static final String RESULT_SIGN_TEE_CHALLENGE_PRIVATE_KEY = "RESULT_SIGN_TEE_CHALLENGE_PRIVATE_KEY";

    private ResultUtils() {
        throw new UnsupportedOperationException();
    }

    public static String computeWeb3ResultDigest(ComputedFile computedFile) {
        if (computedFile == null) {
            return "";
        }

        if (computedFile.getCallbackData() != null && computedFile.getCallbackData().isEmpty()) {
            log.error("Failed to computeWeb3ResultDigest (callbackData empty)[chainTaskId:{}]",
                    computedFile.getTaskId());
            return "";
        }

        return Hash.sha3(computedFile.getCallbackData());
    }

    /*
     * Common usage : iexec-worker-tee-post-compute
     * */
    public static String computeWeb2ResultDigest(ComputedFile computedFile) {
        return computeWeb2ResultDigest(computedFile, "");
    }

    /*
     * A deterministicOutputRootDir is required when mounting volumes from container to host
     *
     * DeterministicOutput path:
     * From Container -              /iexec_out/deterministic-trace.txt
     * From Host - /tmp/worker1/[...]/iexec_out/deterministic-trace.txt
     *
     * Common usage : iexec-worker
     * */
    public static String computeWeb2ResultDigest(ComputedFile computedFile, String hostBindingDir) {
        if (computedFile == null) {
            return "";
        }

        if (computedFile.getDeterministicOutputPath().isEmpty()) {
            log.error("Failed to computeWeb2ResultDigest (deterministicOutputPath empty)[chainTaskId:{}]",
                    computedFile.getTaskId());
            return "";
        }

        String hostDeterministicOutputPath = hostBindingDir + computedFile.getDeterministicOutputPath();
        File deterministicOutputFile = new File(hostDeterministicOutputPath); //could be a directory

        if (!deterministicOutputFile.exists()) {
            log.error("Failed to computeWeb2ResultDigest (hostDeterministicOutputPath missing) [chainTaskId:{}]",
                    computedFile.getTaskId());
            return "";
        }

        return HashUtils.getFileTreeSha256(hostDeterministicOutputPath);
    }

    public static String computeResultHash(String chainTaskId, String resultDigest) {
        return HashUtils.concatenateAndHash(chainTaskId, resultDigest);
    }

    public static String computeResultSeal(String walletAddress, String chainTaskId, String resultDigest) {
        return HashUtils.concatenateAndHash(walletAddress, chainTaskId, resultDigest);
    }

}
