package com.iexec.common.worker.result;

import com.iexec.common.utils.BytesUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class ResultUtils {

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
                if (isHexaString){
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
