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

import com.iexec.commons.poco.utils.HashUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class FileHashUtils {

    private FileHashUtils() {
        throw new UnsupportedOperationException();
    }
    /**
     * Generates SHA-256 digest for the given file.
     *
     * @param file
     * @return SHA-256 digest in hex string format,
     * e.g: 0x66daf4e6810d83d4859846a5df1afabf88c9fda135bc732ea977f25348d98ede
     */
    public static String sha256(File file) {
        Objects.requireNonNull(file, "File must not be null");
        String filepath = file.getAbsolutePath();
        if (!file.exists()) {
            log.error("File not found [filepath:{}]", filepath);
            return "";
        }
        byte[] fileBytes = FileHelper.readAllBytes(filepath);
        if (fileBytes == null) {
            log.error("Null file content [filepath:{}]", filepath);
            return "";
        }
        return HashUtils.sha256(fileBytes);
    }

    public static String getFileTreeSha256(String fileTreePath) {
        File fileTree = new File(fileTreePath);
        if (!fileTree.exists()) {
            return "";
        }
        //fileTree is a leaf, a single file
        if (!fileTree.isDirectory()) {
            return sha256(fileTree);
        }
        //fileTree is a tree, with multiple files
        File[] files = fileTree.listFiles();
        if (files != null) {
            List<String> hashes = new ArrayList<>();
            java.util.Arrays.sort(files); // /!\ files MUST be sorted to ensure final concatenateAndHash(..) is always the same (order matters)
            for (File file : files) {
                hashes.add(sha256(file));
            }
            return HashUtils.concatenateAndHash(hashes.toArray(new String[0]));
        }
        return "";
    }
}
