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

package com.iexec.common.security;

import com.iexec.common.utils.BytesUtils;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Hash;

import java.security.KeyPair;
import java.util.Random;


import static com.iexec.common.security.CipherHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CipherHelperTests {

    private static final int KB = 1000;
    private static final int MB = 1000 * KB;

    @Test
    void shouldEncryptAndDecryptWithAes() {
        byte[] originalData = getRandomByteArray(1 * MB);

        byte[] aesKey = generateAesKey();

        byte[] encryptedOriginalData = aesEncrypt(originalData, aesKey);

        byte[] data = aesDecrypt(encryptedOriginalData, aesKey);

        assertEquals(
                BytesUtils.bytesToString(Hash.sha3(originalData)),
                BytesUtils.bytesToString(Hash.sha3(data))
        );
    }

    @Test
    void shouldEncryptAndDecryptWithRsaKeys() {
        byte[] originalData = getRandomByteArray(128);

        KeyPair rsaKeys = generateRsaKeys();

        byte[] encryptedOriginalData = rsaEncrypt(originalData, rsaKeys.getPublic());

        byte[] data = rsaDecrypt(encryptedOriginalData, rsaKeys.getPrivate());

        assertEquals(
                BytesUtils.bytesToString(Hash.sha3(originalData)),
                BytesUtils.bytesToString(Hash.sha3(data))
        );
    }

    @Test
    void shouldEncryptAndDecryptWithRsaKeysFile() {
        byte[] originalData = getRandomByteArray(128);

        String keyDirPath = "./src/test/resources/security";

        KeyPair rsaKeys = getRsaKeyPair(
                keyDirPath + "/test_rsa_key.pub",
                keyDirPath + "/test_rsa_key");

        byte[] encryptedOriginalData = rsaEncrypt(originalData, rsaKeys.getPublic());

        byte[] data = rsaDecrypt(encryptedOriginalData, rsaKeys.getPrivate());

        assertEquals(
                BytesUtils.bytesToString(Hash.sha3(originalData)),
                BytesUtils.bytesToString(Hash.sha3(data))
        );
    }

    private byte[] getRandomByteArray(int size) {
        byte[] randomByteArray = new byte[size];
        new Random().nextBytes(randomByteArray);
        return randomByteArray;
    }


}
