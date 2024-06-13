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

package com.iexec.common.security;

import com.iexec.commons.poco.utils.BytesUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Hash;

import java.security.KeyPair;
import java.security.SecureRandom;

import static com.iexec.common.security.CipherHelper.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CipherHelperTests {

    private static final int KB = 1000;
    private static final int MB = 1000 * KB;

    private final SecureRandom random = new SecureRandom();

    @Test
    void shouldEncryptAndDecryptWithAes() {
        byte[] originalData = getRandomByteArray(1 * MB);

        byte[] aesKey = generateAesKey();

        byte[] encryptedOriginalData = aesEncrypt(originalData, aesKey);

        byte[] data = aesDecrypt(encryptedOriginalData, aesKey);

        assertThat(BytesUtils.bytesToString(Hash.sha3(data)))
                .isEqualTo(BytesUtils.bytesToString(Hash.sha3(originalData)));
    }

    @Test
    void shouldEncryptAndDecryptWithRsaKeys() {
        byte[] originalData = getRandomByteArray(128);

        KeyPair rsaKeys = generateRsaKeys();

        byte[] encryptedOriginalData = rsaEncrypt(originalData, rsaKeys.getPublic());

        byte[] data = rsaDecrypt(encryptedOriginalData, rsaKeys.getPrivate());

        assertThat(BytesUtils.bytesToString(Hash.sha3(data)))
                .isEqualTo(BytesUtils.bytesToString(Hash.sha3(originalData)));
    }

    @Test
    void shouldEncryptAndDecryptWithRsaKeysFile() {
        byte[] originalData = getRandomByteArray(128);

        String keyDirPath = "./src/test/resources/security";

        KeyPair rsaKeys = getRsaKeyPair(
                keyDirPath + "/test_rsa_key.pub",
                keyDirPath + "/test_rsa_key");

        assertThat(rsaKeys).isNotNull();
        byte[] encryptedOriginalData = rsaEncrypt(originalData, rsaKeys.getPublic());

        byte[] data = rsaDecrypt(encryptedOriginalData, rsaKeys.getPrivate());

        assertThat(BytesUtils.bytesToString(Hash.sha3(data)))
                .isEqualTo(BytesUtils.bytesToString(Hash.sha3(originalData)));
    }

    // region errors
    @Test
    void shouldFailToAesEncryptWithEmptyKey() {
        final byte[] data = getRandomByteArray(1024);
        final byte[] key = new byte[0];
        assertThat(aesEncrypt(data, key)).isNull();
    }

    @Test
    void shouldFailToAesDecryptWithEmptyKey() {
        final byte[] data = getRandomByteArray(1024);
        final byte[] key = new byte[0];
        assertThat(aesDecrypt(data, key)).isNull();
    }

    @Test
    void shouldFailToRsaEncryptWithNullKey() {
        final byte[] data = getRandomByteArray(256);
        assertThat(rsaEncrypt(data, null)).isNull();
    }

    @Test
    void shouldFailToRsaDecryptWithNullKey() {
        final byte[] data = getRandomByteArray(256);
        assertThat(rsaDecrypt(data, null)).isNull();
    }

    @Test
    void errorExtractingRsaKeys() {
        final String weirdCharacters = RandomStringUtils.randomAlphanumeric(256);
        assertThat(plainText2RsaPrivateKey(weirdCharacters)).isNull();
        assertThat(plainText2RsaPublicKey(weirdCharacters)).isNull();
    }

    @Test
    void shouldNotGetRsaKeyPair() {
        assertThat(getRsaKeyPair(null, null)).isNull();
        assertThat(getRsaKeyPair("", "")).isNull();
    }
    // endregion

    private byte[] getRandomByteArray(int size) {
        byte[] randomByteArray = new byte[size];
        random.nextBytes(randomByteArray);
        return randomByteArray;
    }

}
