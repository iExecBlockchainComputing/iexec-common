/*
 * Copyright 2020-2025 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.worker.result;

import com.iexec.common.result.ComputedFile;
import com.iexec.common.utils.FileHashUtils;
import com.iexec.common.utils.FileHelper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.web3j.crypto.Hash;

import java.io.File;
import java.nio.file.Path;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResultUtils {

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
        if (computedFile == null || computedFile.getDeterministicOutputPath() == null) {
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

        return FileHashUtils.getFileTreeSha256(hostDeterministicOutputPath);
    }

    public static String zipIexecOut(String iexecOutPath) {
        String saveIn = Path.of(iexecOutPath).getParent().toAbsolutePath().toString();
        return zipIexecOut(iexecOutPath, saveIn);
    }

    public static String zipIexecOut(String iexecOutPath, String saveIn) {
        File zipFile = FileHelper.zipFolder(iexecOutPath, saveIn);
        if (zipFile == null) {
            log.error("Could not zip iexec_out [iexecOutPath:{}]", iexecOutPath);
            return "";
        }
        log.info("Created iexec_out zip [zipPath:{}]", zipFile.getAbsolutePath());
        return zipFile.getAbsolutePath();
    }
}
