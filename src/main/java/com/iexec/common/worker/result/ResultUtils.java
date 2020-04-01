package com.iexec.common.worker.result;

import com.iexec.common.utils.BytesUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public static String getCallbackDataFromPath(String callbackFilePathName) {
        String hexaString = "";
        try {
            Path callbackFilePath = Paths.get(callbackFilePathName);

            if (callbackFilePath.toFile().exists()) {
                byte[] callbackFileBytes = Files.readAllBytes(callbackFilePath);
                hexaString = new String(callbackFileBytes);
                boolean isHexaString = BytesUtils.isHexaString(hexaString);
                if (isHexaString) {
                    return hexaString;
                } else {
                    log.error("Callback is not an hex string [callbackFilePathName:{}, hexaString:{}, isHexaString:{}]", callbackFilePathName, hexaString, isHexaString);
                    return "";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hexaString;
    }
}
