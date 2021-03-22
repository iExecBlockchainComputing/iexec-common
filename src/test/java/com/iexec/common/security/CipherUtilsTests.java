package com.iexec.common.security;

import com.iexec.common.utils.FileHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.GeneralSecurityException;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

public class CipherUtilsTests {

    // AES
    private static final String AES = "src/test/resources/security/aes/";
    private static final String AES_KEY_FILE = AES + "key.txt";
    private static final String AES_PLAIN_DATA_FILE = AES + "plain-data.txt";
    private static final String AES_ENC_DATA_FILE = AES + "encrypted-data.bin";
    // RSA
    private static final String RSA = "src/test/resources/security/rsa/";

    // ###############
    // #     AES     #
    // ###############

    @Test
    public void shouldGenerate256AesKey() {
        byte[] aesKey = CipherUtils.generateAesKey();
        assertThat(aesKey).hasSize(32);
    }

    @Test
    public void shouldGenerateDifferent256AesKey() {
        byte[] aesKey1 = CipherUtils.generateAesKey();
        byte[] aesKey2 = CipherUtils.generateAesKey();
        assertThat(aesKey1).isNotEqualTo(aesKey2);
    }

    @Test
    public void shouldGenerateAesKeyWithGivenSize() {
        byte[] aesKey = CipherUtils.generateAesKey(128);
        assertThat(aesKey).hasSize(16);
    }

    @Test
    public void shouldGenerateIvWithSize16bytes() {
        byte[] iv = CipherUtils.generateIv();
        assertThat(iv).hasSize(16);
    }

    @Test
    public void shouldGenerateDifferentIvs() {
        byte[] iv1 = CipherUtils.generateIv();
        byte[] iv2 = CipherUtils.generateIv();
        assertThat(iv1).isNotEqualTo(iv2);
    }

    @Test
    public void shouldGet16BytesIvFromEncryptedData() {
        byte[] encryptedData = FileHelper.readAllBytes(AES_ENC_DATA_FILE);
        assertThat(CipherUtils.getIvFromEncryptedData(encryptedData)).hasSize(16);
    }

    @Test
    public void shouldStripIvFromEncryptedData() {
        byte[] encryptedData = FileHelper.readAllBytes(AES_ENC_DATA_FILE);
        byte[] strippedData = CipherUtils.stripIvFromEncryptedData(encryptedData);
        assertThat(encryptedData.length).isEqualTo(strippedData.length + 16);
    }

    @Test
    public void shouldGenerateIvWithGivenSize() {
        byte[] iv = CipherUtils.generateIv(32);
        assertThat(iv).hasSize(32);
    }

    // encryption

    @Test
    public void shouldEncryptAndDecryptDataWithAesKey() throws Exception {
        byte[] plainData = FileHelper.readAllBytes(AES_PLAIN_DATA_FILE);
        byte[] base64Key = FileHelper.readAllBytes(AES_KEY_FILE);
        byte[] encryptedData = CipherUtils.aesEncrypt(plainData, base64Key);
        byte[] decryptedData = CipherUtils.aesDecrypt(encryptedData, base64Key);
        assertThat(decryptedData).isEqualTo(plainData);
    }

    @Test
    public void shouldEncryptDataWithDifferentIvs() throws Exception {
        byte[] plainData = FileHelper.readAllBytes(AES_PLAIN_DATA_FILE);
        byte[] base64Key = FileHelper.readAllBytes(AES_KEY_FILE);
        byte[] encryptedData1 = CipherUtils.aesEncrypt(plainData, base64Key);
        byte[] iv1 = CipherUtils.getIvFromEncryptedData(encryptedData1);
        byte[] encryptedData2 = CipherUtils.aesEncrypt(plainData, base64Key);
        byte[] iv2 = CipherUtils.getIvFromEncryptedData(encryptedData2);
        assertThat(encryptedData1).isNotEqualTo(encryptedData2);
        assertThat(iv1).isNotEqualTo(iv2);
    }

    // decryption

    @Test
    public void shouldDecryptDataEncryptedWithSdk() throws Exception {
        byte[] plainData = FileHelper.readAllBytes(AES_PLAIN_DATA_FILE);
        byte[] base64Key = FileHelper.readAllBytes(AES_KEY_FILE);
        byte[] sdkEncryptedData = FileHelper.readAllBytes(AES_ENC_DATA_FILE);
        byte[] decryptedData = CipherUtils.aesDecrypt(sdkEncryptedData, base64Key);
        assertThat(decryptedData).isEqualTo(plainData);
    }

    @Test
    public void shouldNotDecryptDataWithBadKey() {
        byte[] badKey = Base64.getEncoder().encode(CipherUtils.generateAesKey());
        byte[] encryptedData = FileHelper.readAllBytes(AES_ENC_DATA_FILE);
        Assertions.assertThrows(
                GeneralSecurityException.class,
                () -> CipherUtils.aesDecrypt(encryptedData, badKey));
    }

    @Test
    public void shouldNotDecryptDataWithBadIv() throws Exception {
        byte[] plainData = FileHelper.readAllBytes(AES_PLAIN_DATA_FILE);
        byte[] base64Key = FileHelper.readAllBytes(AES_KEY_FILE);
        byte[] encryptedData = FileHelper.readAllBytes(AES_ENC_DATA_FILE);
        byte[] strippedData = CipherUtils.stripIvFromEncryptedData(encryptedData);
        byte[] badIv = CipherUtils.generateIv();
        byte[] encryptedDataWithBadIv =
                CipherUtils.prependIvToEncryptedData(badIv, strippedData);
        byte[] badDecryptedData =
                CipherUtils.aesDecrypt(encryptedDataWithBadIv, base64Key);
        System.out.println("bad: " + new String(badDecryptedData));
        System.out.println("correct: " + new String(plainData));
        assertThat(badDecryptedData).isNotEqualTo(plainData);
    }

    // ###############
    // #     RSA     #
    // ###############
}
