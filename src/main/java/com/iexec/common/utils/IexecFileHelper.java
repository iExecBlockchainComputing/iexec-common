package com.iexec.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iexec.common.result.ComputedFile;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class IexecFileHelper {

    public static final String COMPUTED_JSON = "computed.json";

    private IexecFileHelper() {
        throw new UnsupportedOperationException();
    }

    public static ComputedFile readComputedFile(String chainTaskId, String computedFileDir) {
        if (chainTaskId.isEmpty()){
            log.error("Failed to read compute file (empty chainTaskId)[chainTaskId:{}, computedFileDir:{}]",
                    chainTaskId, computedFileDir);
            return null;
        }

        if (computedFileDir.isEmpty()){
            log.error("Failed to read compute file (empty computedFileDir)[chainTaskId:{}, computedFileDir:{}]",
                    chainTaskId, computedFileDir);
            return null;
        }
        computedFileDir = computedFileDir.endsWith(File.separator) ? computedFileDir : computedFileDir + File.separator;

        String computedFilePath = computedFileDir + COMPUTED_JSON;
        String jsonString = FileHelper.readFile(computedFilePath);

        if (jsonString.isEmpty()) {
            log.error("Failed to read compute file (invalid path)[chainTaskId:{}, computedFilePath:{}]",
                    chainTaskId, computedFileDir);
            return null;
        }



        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ComputedFile computedFile = objectMapper.readValue(jsonString, ComputedFile.class);
            computedFile.setTaskId(chainTaskId);
            return computedFile;
        } catch (IOException e) {
            log.error("Failed to read compute file [chainTaskId:{}, computedFilePath:{}]",
                    chainTaskId, computedFileDir);
            e.printStackTrace();
        }
        return null;
    }


}