/*
 * Copyright 2020-2023 IEXEC BLOCKCHAIN TECH
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

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileHashUtilsTests {

    @Test
    void shouldGetFileSha256() {
        String filePath = "src/test/resources/utils/file-helper/file-hash/output/iexec_out/result1.txt";

        String sha256 = FileHashUtils.sha256(new File(filePath));
        assertEquals("0xd885f429a59e0816822fc0927be6a6af20ade2c79db49df6c6022f79cb27ac16", sha256);
    }

    @Test
    void shouldNotGetFileSha256SinceInvalidPath() {
        String filePath = "/nowhere/nowhere";

        String sha256 = FileHashUtils.sha256(new File(filePath));
        assertEquals("", sha256);
    }

    @Test
    void shouldGetFileTreeSha256SinceFileTree() {
        String filePath = "src/test/resources/utils/file-helper/file-hash/output/iexec_out/";

        String sha256 = FileHashUtils.getFileTreeSha256(filePath);
        assertEquals("0xcc77508549295dd5de5876a2f4f00d4c3c27a547c6403450e43ab4de191bf1bc", sha256);
    }

    @Test
    void shouldGetFileTreeSha256SinceFile() {
        String filePath = "src/test/resources/utils/file-helper/file-hash/output/iexec_out/result1.txt";

        String sha256 = FileHashUtils.getFileTreeSha256(filePath);
        assertEquals("0xd885f429a59e0816822fc0927be6a6af20ade2c79db49df6c6022f79cb27ac16", sha256);
        assertEquals(sha256, FileHashUtils.getFileTreeSha256(filePath));
    }

    @Test
    void shouldNotGetFileTreeSha256SinceInvalidPath() {
        String filePath = "/nowhere/nowhere";

        String sha256 = FileHashUtils.getFileTreeSha256(filePath);
        assertEquals("", sha256);
    }
}
