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

package com.iexec.common.security;

import com.iexec.common.utils.FileHelper;
import com.iexec.commons.poco.utils.BytesUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Hash;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static com.iexec.common.utils.FileHelper.readAllBytes;
import static com.iexec.common.utils.FileHelper.readFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EncryptionHelperTests {

    private final static String DOT_SLASH = "./src/test/resources/security/";
    private String plainTextRsaPublicKey;
    private String plainTextRsaPrivateKey;

    @BeforeEach
    void before() {
        plainTextRsaPublicKey = readFile(DOT_SLASH + "test_rsa_key.pub");
        plainTextRsaPrivateKey = readFile(DOT_SLASH + "test_rsa_key");
    }


    private void removeOldFiles() {
        FileHelper.deleteFolder(DOT_SLASH + "encrypted-result-0xabc");
        FileHelper.deleteFile(DOT_SLASH + "plain-result-0xabc.zip");
    }

    @Test
    void shouldEncryptAndDecrypt() throws GeneralSecurityException, IOException {
        removeOldFiles();
        String inDataFileName = "result-0xabc.zip";

        // Encryption side
        String originalDataHash = BytesUtils.bytesToString(Hash.sha3(readAllBytes(DOT_SLASH + inDataFileName)));
        String encryptedResultFolder = EncryptionHelper.encryptData(DOT_SLASH + inDataFileName, plainTextRsaPublicKey, false);

        // Decryption side
        String clearDataPath = EncryptionHelper.decryptData(encryptedResultFolder + "/" + inDataFileName + ".aes", plainTextRsaPrivateKey);
        String clearDataHash = BytesUtils.bytesToString(Hash.sha3(readAllBytes(clearDataPath)));

        removeOldFiles();// comment this if you want to see created files
        assertEquals(originalDataHash, clearDataHash);
    }


}
