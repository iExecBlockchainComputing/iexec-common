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

import org.junit.jupiter.api.Test;
import org.web3j.crypto.Hash;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HashUtilsTests {

    @Test
    public void shouldBeCorrectOneValue() {
        String hexa1 = "0x748e091bf16048cb5103E0E10F9D5a8b7fBDd860";

        String expected = Hash.sha3(hexa1);

        assertEquals(expected, HashUtils.concatenateAndHash(hexa1));
    }

    @Test
    public void shouldBeCorrectTwoValues() {
        String hexa1 = "0x748e091bf16048cb5103E0E10F9D5a8b7fBDd860";
        String hexa2 = "0xd94b63fc2d3ec4b96daf84b403bbafdc8c8517e8e2addd51fec0fa4e67801be8";

        String expected = "0x9ca8cbf81a285c62778678c874dae13fdc6857566b67a9a825434dd557e18a8d";

        assertEquals(expected, HashUtils.concatenateAndHash(hexa1, hexa2));
    }

    @Test
    public void shouldBeCorrectThreeValues() {
        String hexa1 = "0x748e091bf16048cb5103E0E10F9D5a8b7fBDd860";
        String hexa2 = "0xd94b63fc2d3ec4b96daf84b403bbafdc8c8517e8e2addd51fec0fa4e67801be8";
        String hexa3 = "0x9a43BB008b7A657e1936ebf5d8e28e5c5E021596";

        String expected = "0x54a76d209e8167e1ffa3bde8e3e7b30068423ca9554e1d605d8ee8fd0f165562";

        assertEquals(expected, HashUtils.concatenateAndHash(hexa1, hexa2, hexa3));
    }


    @Test
    public void shouldGetFileSha256() {
        String filePath = "src/test/resources/utils/file-helper/file-hash/output/iexec_out/result1.txt";

        String sha256 = HashUtils.sha256(new File(filePath));
        assertEquals("0xd885f429a59e0816822fc0927be6a6af20ade2c79db49df6c6022f79cb27ac16", sha256);
    }

    @Test
    public void shouldNotGetFileSha256SinceInvalidPath() {
        String filePath = "/nowhere/nowhere";

        String sha256 = HashUtils.sha256(new File(filePath));
        assertEquals("", sha256);
    }

    @Test
    public void shouldGetStringSha256() {
        assertEquals("0xb33845db05fb0822f1f1e3677cc6787b8a1a7a21f3c12f9e97c70cb596222218",
                HashUtils.sha256("utf8String"));
    }

    @Test
    public void shouldGetBytesSha256() {
        byte[] bytes = "hello".getBytes();
        assertEquals("0x2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824",
                HashUtils.sha256(bytes));
    }

    @Test
    public void shouldGetFileTreeSha256SinceFileTree() {
        String filePath = "src/test/resources/utils/file-helper/file-hash/output/iexec_out/";

        String sha256 = HashUtils.getFileTreeSha256(filePath);
        assertEquals("0xcc77508549295dd5de5876a2f4f00d4c3c27a547c6403450e43ab4de191bf1bc", sha256);
    }

    @Test
    public void shouldGetFileTreeSha256SinceFile() {
        String filePath = "src/test/resources/utils/file-helper/file-hash/output/iexec_out/result1.txt";

        String sha256 = HashUtils.getFileTreeSha256(filePath);
        assertEquals("0xd885f429a59e0816822fc0927be6a6af20ade2c79db49df6c6022f79cb27ac16", sha256);
        assertEquals(sha256, HashUtils.getFileTreeSha256(filePath));
    }

    @Test
    public void shouldNotGetFileTreeSha256SinceInvalidPath() {
        String filePath = "/nowhere/nowhere";

        String sha256 = HashUtils.getFileTreeSha256(filePath);
        assertEquals("", sha256);
    }
}
