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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iexec.common.result.ComputedFile;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class IexecFileHelper {

    public static final String SLASH_IEXEC_OUT = File.separator + "iexec_out";
    public static final String SLASH_IEXEC_IN = File.separator + "iexec_in";
    public static final String SLASH_OUTPUT = File.separator + "output";
    public static final String SLASH_INPUT = File.separator + "input";    
    public static final String COMPUTED_JSON = "computed.json";
    public static final String SLASH_COMPUTED_JSON = File.separator + COMPUTED_JSON;

    private IexecFileHelper() {
        throw new UnsupportedOperationException();
    }

    public static ComputedFile readComputedFile(String chainTaskId, String computedFileDir) {
        if (chainTaskId.isEmpty()){
            log.error("Failed to read compute file (empty chainTaskId) [chainTaskId:{}, computedFileDir:{}]",
                    chainTaskId, computedFileDir);
            return null;
        }

        if (computedFileDir.isEmpty()){
            log.error("Failed to read compute file (empty computedFileDir) [chainTaskId:{}, computedFileDir:{}]",
                    chainTaskId, computedFileDir);
            return null;
        }
        computedFileDir = computedFileDir.endsWith(File.separator) ? computedFileDir : computedFileDir + File.separator;

        String computedFilePath = computedFileDir + COMPUTED_JSON;
        String jsonString = FileHelper.readFile(computedFilePath);

        if (jsonString.isEmpty()) {
            log.error("Failed to read compute file (invalid path) [chainTaskId:{}, computedFilePath:{}]",
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
                    chainTaskId, computedFileDir, e);
        }
        return null;
    }


}