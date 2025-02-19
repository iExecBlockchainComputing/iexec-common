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

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Optional;

import static com.iexec.common.security.CipherUtils.*;
import static com.iexec.common.utils.FileHelper.*;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EncryptionHelper {

    public static final String AES_KEY_RSA_FILENAME = "aes-key.rsa";
    public static final String ENCRYPTION_PREFIX = "encrypted-";
    public static final String DECRYPTION_PREFIX = "plain-";

    /*
     *
     * #1: Large file encryption is made with AES
     * #2: AES key is encrypted with RSA key
     *
     * before
     * └── result-0xabc.zip
     *
     * after
     * ├── result-0xabc.zip
     * ├── encrypted-result-0xabc
     * │   ├── aes-key.rsa
     * │   └── result-0xabc.zip.aes
     * └── encrypted-result-0xabc.zip (if produceZip)
     *
     * Returns: folder or zip path
     *
     * */
    public static String encryptData(String inDataFilePath, String plainTextRsaPub, boolean produceZip) throws GeneralSecurityException, IOException {
        String inDataFilename = FilenameUtils.getName(inDataFilePath);
        String outEncryptedDataFilename = inDataFilename + ".aes";

        String workDir = Paths.get(inDataFilePath).getParent().toString(); //"/tmp/scone";
        String outEncDir = workDir + "/" + ENCRYPTION_PREFIX + FilenameUtils.removeExtension(inDataFilename); //location of future encrypted files (./encrypted-0x1_result)

        // Get data to encrypt
        byte[] data = readAllBytes(inDataFilePath);
        if (data == null) {
            log.error("Failed to encryptData (readFile error) [inDataFilePath:{}]", inDataFilePath);
            return "";
        }
        // Generate AES key for data encryption
        byte[] aesKey = generateAesKey();
        if (aesKey == null) {
            log.error("Failed to encryptData (generateAesKey error) [inDataFilePath:{}]", inDataFilePath);
            return "";
        }
        // Encrypt data with Base64 AES key
        byte[] base64AesKey = Base64.getEncoder().encode(aesKey);
        byte[] encryptedData = aesEncrypt(data, base64AesKey);
        if (encryptedData == null) {
            log.error("Failed to encryptData (aesEncrypt error) [inDataFilePath:{}]", inDataFilePath);
            return "";
        }
        // Create folder for future outEncryptedData & outEncryptedAesKey
        boolean isOutDirCreated = createFolder(outEncDir);
        if (!isOutDirCreated) {
            log.error("Failed to encryptData (isOutDirCreated error) [inDataFilePath:{}]", inDataFilePath);
            return "";
        }
        // Store encrypted data in ./0xtask1 [outEncDir]
        boolean isEncryptedDataStored = writeFile(outEncDir + "/" + outEncryptedDataFilename, encryptedData);
        if (!isEncryptedDataStored) {
            log.error("Failed to encryptData (isEncryptedDataStored error) [inDataFilePath:{}]", inDataFilePath);
            return "";
        }
        // Get RSA public key
        Optional<PublicKey> rsaPublicKey = base64ToRsaPublicKey(plainTextRsaPub);
        if (rsaPublicKey.isEmpty()) {
            log.error("Failed to encryptData (getRsaPublicKey error) [inDataFilePath:{}]", inDataFilePath);
            return "";
        }
        // Encrypt AES key with RSA public key
        byte[] encryptedAesKey = rsaEncrypt(aesKey, rsaPublicKey.get());
        if (encryptedAesKey == null) {
            log.error("Failed to encryptData (rsaEncrypt error) [inDataFilePath:{}]", inDataFilePath);
            return "";
        }
        // Store encrypted AES key in ./0xtask1 [outEncDir]
        boolean isEncryptedAesKeyStored = writeFile(outEncDir + "/" + AES_KEY_RSA_FILENAME, encryptedAesKey);
        if (!isEncryptedAesKeyStored) {
            log.error("Failed to encryptData (isEncryptedAesKeyStored error) [inDataFilePath:{}]", inDataFilePath);
            return "";
        }

        if (produceZip) {
            // Zip encrypted files folder
            File outEncZip = zipFolder(outEncDir);
            if (outEncZip == null) {
                log.error("Failed to encryptData (outEncZip error) [inDataFilePath:{}]", inDataFilePath);
                return "";
            }
            return outEncZip.getAbsolutePath();
        }

        return outEncDir;
    }

    /*
     *
     * Required: aes-key.rsa file should be found next to encryptedDataFile
     *
     * #1: AES key is decrypted with RSA
     * #2: Data is decrypted with AES key
     *
     * before
     * └── encrypted-result-0xabc.zip
     * with zip content
     * ├── aes-key.rsa
     * └── result-0xabc.zip.aes
     *
     * after
     * ├── encrypted-result-0xabc.zip
     * └── plain-result-0xabc.zip
     *
     * Returns: clear data path (zip here)
     *
     * */
    public static String decryptData(String encryptedDataFilePath, String plainTextRsaPriv) throws GeneralSecurityException {
        String encryptedResultFolder = Paths.get(encryptedDataFilePath).getParent().toString();
        String outClearDataFilename = DECRYPTION_PREFIX + FilenameUtils.getBaseName(encryptedDataFilePath);
        String outClearDataFilePath = encryptedResultFolder + "/../" + outClearDataFilename;

        // Get encrypted AES key
        byte[] encryptedAesKey = readAllBytes(encryptedResultFolder + "/" + AES_KEY_RSA_FILENAME);
        if (encryptedAesKey == null) {
            log.error("Failed to decryptData (encryptedAesKey error) [encryptedDataFilePath:{}]", encryptedDataFilePath);
            return "";
        }
        // Get RSA private key
        Optional<PrivateKey> rsaPrivateKey = base64ToRsaPrivateKey(plainTextRsaPriv);
        if (rsaPrivateKey.isEmpty()) {
            log.error("Failed to decryptData (rsaPrivateKey error) [encryptedDataFilePath:{}]", encryptedDataFilePath);
            return "";
        }
        // Decrypt encrypted AES key
        byte[] aesKey = rsaDecrypt(encryptedAesKey, rsaPrivateKey.get());
        if (aesKey == null) {
            log.error("Failed to decryptData (aesKey error) [encryptedDataFilePath:{}]", encryptedDataFilePath);
            return "";
        }
        // Check is AES data
        boolean isAesExtensionEncryptedData = FilenameUtils.getExtension(encryptedDataFilePath).equals("aes");
        if (!isAesExtensionEncryptedData) {
            log.error("Failed to decryptData (isAesExtensionEncryptedData error) [encryptedDataFilePath:{}]", encryptedDataFilePath);
            return "";
        }
        // Get encrypted data
        byte[] encryptedData = readAllBytes(encryptedDataFilePath);
        if (encryptedData == null) {
            log.error("Failed to decryptData (encryptedData error) [encryptedDataFilePath:{}]", encryptedDataFilePath);
            return "";
        }
        // Decrypt data with Base64 AES key
        byte[] base64AesKey = Base64.getEncoder().encode(aesKey);
        byte[] clearData = aesDecrypt(encryptedData, base64AesKey);
        if (clearData == null) {
            log.error("Failed to decryptData (clearData error) [encryptedDataFilePath:{}]", encryptedDataFilePath);
            return "";
        }
        // Store clear data
        boolean isClearDataStored = writeFile(outClearDataFilePath, clearData);
        if (!isClearDataStored) {
            log.error("Failed to decryptData (isClearDataStored error) [encryptedDataFilePath:{}]", encryptedDataFilePath);
            return "";
        }

        return outClearDataFilePath;
    }

}
