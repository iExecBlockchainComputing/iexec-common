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

import com.iexec.common.result.ComputedFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IexecFileHelperTest {

    private static final String CHAIN_TASK_ID = "CHAIN_TASK_ID";

    @Test
    public void shouldReadComputedFile() {
        String computedFileDirPath = "src/test/resources/result/valid/";

        ComputedFile computedFile = IexecFileHelper.readComputedFile(CHAIN_TASK_ID, computedFileDirPath);
        Assertions.assertEquals("/iexec_out/computing-trace.txt", computedFile.getDeterministicOutputPath());
    }

    @Test
    public void shouldReadComputedFileWithoutTrailingSlash() {
        String computedFileDirPath = "src/test/resources/result/valid";

        ComputedFile computedFile = IexecFileHelper.readComputedFile(CHAIN_TASK_ID, computedFileDirPath);
        Assertions.assertEquals("/iexec_out/computing-trace.txt", computedFile.getDeterministicOutputPath());
    }

    @Test
    public void shouldNotReadComputedFileEmptyPath() {
        String computedFileDirPath = "";

        ComputedFile computedFile = IexecFileHelper.readComputedFile(CHAIN_TASK_ID, computedFileDirPath);
        Assertions.assertNull(computedFile);
    }

    @Test
    public void shouldNotReadComputedFileSinceInvalidPath() {
        String computedFileDirPath = "/nowhere/nowhere/";

        ComputedFile computedFile = IexecFileHelper.readComputedFile(CHAIN_TASK_ID, computedFileDirPath);
        Assertions.assertNull(computedFile);
    }

}