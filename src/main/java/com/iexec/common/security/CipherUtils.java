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
import jakarta.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CipherUtils {

    private final static String NOT_NULL_DATA = "Data cannot be null";
    private final static String NOT_NULL_AES_KEY = "AES key cannot be null";

    // ###############
    // #     AES     #
    // ###############

    /**
     * Generate a 256 bits AES key.
     *
     * @return 256 bits key if success,
     * empty byte array otherwise.
     */
    public static byte[] generateAesKey() {
        return generateAesKey(256);
    }

    /**
     * Generate an AES symmetric key with the
     * given size.
     *
     * @param size The AES key size in number of bits
     * @return Generated AES key if success,
     * empty byte array otherwise.
     */
    public static byte[] generateAesKey(int size) {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(size);
            SecretKey secKey = generator.generateKey();
            return secKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            log.error("Failed to generate AES key", e);
            return new byte[0];
        }
    }

    /**
     * Generate a 16 bytes initialization vector.
     *
     * @return generated binary IV.
     */
    public static byte[] generateIv() {
        return generateIv(16);
    }

    /**
     * Generate an initialization vector with
     * the given size.
     *
     * @param size The IV size in bytes
     * @return generated binary IV.
     */
    public static byte[] generateIv(int size) {
        byte[] iv = new byte[size];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    /**
     * Extract IV (initialization vector) from the
     * encrypted data. The IV occupies the first
     * 16 bytes of the data.
     *
     * @param dataWithIv Encrypted data
     * @return Extracted 16 bytes IV (0 -> 15)
     */
    public static byte[] getIvFromEncryptedData(byte[] dataWithIv) {
        return Arrays.copyOfRange(dataWithIv, 0, 16);
    }

    /**
     * Remove prepended IV (initialization vector)
     * from the encrypted data.
     *
     * @param dataWithIv Encrypted data
     * @return a new array without the IV.
     */
    public static byte[] stripIvFromEncryptedData(byte[] dataWithIv) {
        return Arrays.copyOfRange(dataWithIv, 16, dataWithIv.length);
    }

    /**
     * Concatenate IV (initialization vector) to the
     * beginning of the encrypted data.
     *
     * @param iv            Initialization vector
     * @param encryptedData Encrypted data
     * @return IV prepended to the encrypted data
     * as an array of bytes.
     * @throws IOException if an I/O error occurs during
     *                     the write operations.
     */
    public static byte[] prependIvToEncryptedData(byte[] iv, byte[] encryptedData)
            throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        result.write(iv);
        result.write(encryptedData);
        return result.toByteArray();
    }

    /**
     * Decode Base64 key and encrypt binary data
     * with AES256/CBC/PKCS5Padding. The first 16
     * bytes of the result will contain the IV
     * (initialization vector).
     * <p>
     * Note: PKCS5Padding is equivalent to PKCS7Padding
     * <p>
     * Ref: <a href="https://stackoverflow.com/a/10194082/7631879">https://stackoverflow.com/a/10194082/7631879</a>
     *
     * @param plainData to encrypt
     * @param key       AES key
     * @return encrypted data prepended with the IV.
     * @throws GeneralSecurityException if an error occurs during AES encryption.
     * @see <a href="https://stackoverflow.com/a/34004582">AES encryption on file over 1GB</a> for large files
     */
    public static byte[] aesEncrypt(byte[] plainData, byte[] key)
            throws GeneralSecurityException, IOException {
        Objects.requireNonNull(plainData, NOT_NULL_DATA);
        Objects.requireNonNull(key, NOT_NULL_AES_KEY);
        byte[] iv = generateIv();
        byte[] encryptedData = aesEncrypt(plainData, key, iv);
        return prependIvToEncryptedData(iv, encryptedData);
    }

    /**
     * Encrypt binary data with AES256/CBC/PKCS5Padding.
     * <p>
     * Note: PKCS5Padding is equivalent to PKCS7Padding
     * <p>
     * Ref: <a href="https://stackoverflow.com/a/10194082/7631879">https://stackoverflow.com/a/10194082/7631879</a>
     *
     * @param plainData to encrypt
     * @param key       AES key
     * @param iv        Initialization vector
     * @return Encrypted data
     * @throws GeneralSecurityException if a security related error occurs.
     * @see <a href="https://stackoverflow.com/a/34004582">AES encryption on file over 1GB</a> for large files
     */
    public static byte[] aesEncrypt(
            @Nonnull byte[] plainData,
            @Nonnull byte[] key,
            @Nonnull byte[] iv) throws GeneralSecurityException {
        Objects.requireNonNull(plainData, NOT_NULL_DATA);
        Objects.requireNonNull(key, NOT_NULL_AES_KEY);
        Objects.requireNonNull(iv, "IV cannot be null");
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        IvParameterSpec ivParam = new IvParameterSpec(iv);
        Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aesCipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParam);
        return aesCipher.doFinal(plainData);
    }

    /**
     * Decode Base64 key, extract the IV and decrypt the data
     * with AES/CBC/PKCS5Padding. The IV (Initialization
     * Vector) must occupy the first 16 bytes of the data which
     * means its length should be greater than 16 bytes.
     * <p>
     * Note: PKCS5Padding is equivalent to PKCS7Padding
     * <p>
     * Ref: <a href="https://stackoverflow.com/a/10194082/7631879">https://stackoverflow.com/a/10194082/7631879</a>
     *
     * @param ivAndEncryptedData to decrypt
     * @param key                AES key
     * @return Decrypted data
     * @throws GeneralSecurityException if an error occurs during AES decryption.
     * @throws IllegalArgumentException if the data's length
     *                                  is less than 16 bytes.
     * @see <a href="https://stackoverflow.com/a/34004582">AES encryption on file over 1GB</a> for large files
     */
    public static byte[] aesDecrypt(byte[] ivAndEncryptedData, byte[] key)
            throws GeneralSecurityException {
        Objects.requireNonNull(ivAndEncryptedData, NOT_NULL_DATA);
        Objects.requireNonNull(key, "Base64 AES key cannot be null");
        if (ivAndEncryptedData.length < 16) {
            throw new IllegalArgumentException("Data cannot be less than 16 bytes");
        }
        byte[] iv = getIvFromEncryptedData(ivAndEncryptedData);
        byte[] data = stripIvFromEncryptedData(ivAndEncryptedData);
        return aesDecrypt(data, key, iv);
    }

    /**
     * Decrypt data with AES256/CBC/PKCS5Padding.
     * <p>
     * Note: PKCS5Padding is equivalent to PKCS7Padding
     * <p>
     * Ref: <a href="https://stackoverflow.com/a/10194082/7631879">https://stackoverflow.com/a/10194082/7631879</a>
     *
     * @param plainData Data to decrypt
     * @param key       AES key
     * @param iv        Initialization vector
     * @return Decrypted data
     * @throws GeneralSecurityException if a security related error occurs.
     */
    public static byte[] aesDecrypt(
            @Nonnull byte[] plainData,
            @Nonnull byte[] key,
            @Nonnull byte[] iv) throws GeneralSecurityException {
        Objects.requireNonNull(plainData, NOT_NULL_DATA);
        Objects.requireNonNull(key, NOT_NULL_AES_KEY);
        Objects.requireNonNull(iv, "IV cannot be null");
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        IvParameterSpec ivParam = new IvParameterSpec(iv);
        Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aesCipher.init(Cipher.DECRYPT_MODE, secretKey, ivParam);
        return aesCipher.doFinal(plainData);  // heap size issues after 500MB
    }

    // ###############
    // #     RSA     #
    // ###############

    /**
     * Generate 2048 bits RSA key pair.
     *
     * @return Optional of generated key pair
     * if success, empty optional otherwise.
     */
    public static Optional<KeyPair> generateRsaKeyPair() {
        return generateRsaKeyPair(2048);
    }

    /**
     * Generate RSA key pair with the given size.
     *
     * @param size in bits
     * @return Optional of generated key pair if
     * success, empty optional otherwise.
     */
    public static Optional<KeyPair> generateRsaKeyPair(int size) {
        try {
            KeyPairGenerator keyPairGenerator =
                    KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(size);
            return Optional.of(keyPairGenerator.generateKeyPair());
        } catch (NoSuchAlgorithmException e) {
            log.error("Failed to generate RSA key pair", e);
            return Optional.empty();
        }
    }

    /**
     * RSA-Encrypt data with the public key.
     *
     * @param plainData Data to encrypt
     * @param publicKey Public RSA key to encrypt with
     * @return encrypted data
     * @throws GeneralSecurityException if an error occurs during RSA encryption.
     */
    public static byte[] rsaEncrypt(
            @Nonnull byte[] plainData,
            @Nonnull PublicKey publicKey) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plainData);
    }

    /**
     * RSA-Decrypt data with the private key.
     *
     * @param encryptedData Encrypted data to decrypt
     * @param privateKey    Private RSA key to decrypt with
     * @return decrypted data
     * @throws GeneralSecurityException if an error occurs during RSA decryption.
     */
    public static byte[] rsaDecrypt(
            @Nonnull byte[] encryptedData,
            @Nonnull PrivateKey privateKey) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encryptedData);
    }

    /**
     * Read RSA key pair from files.
     *
     * @param publicKeyFilepath  Path to the public key file
     * @param privateKeyFilepath Path to the private key file
     * @return Optional of keyPair if success,
     * empty optional otherwise.
     */
    public static Optional<KeyPair> readRsaKeyPair(String publicKeyFilepath, String privateKeyFilepath) {
        String base64RsaPub = FileHelper.readFile(publicKeyFilepath);
        String base64RsaPriv = FileHelper.readFile(privateKeyFilepath);
        if (base64RsaPub.isEmpty() || base64RsaPriv.isEmpty()) {
            return Optional.empty();
        }
        Optional<PublicKey> publicKey = base64ToRsaPublicKey(base64RsaPub);
        Optional<PrivateKey> privateKey = base64ToRsaPrivateKey(base64RsaPriv);
        if (publicKey.isEmpty() || privateKey.isEmpty()) {
            return Optional.empty();
        }
        KeyPair keyPair = new KeyPair(publicKey.get(), privateKey.get());
        return Optional.of(keyPair);
    }

    /**
     * Get RSA public key from Base64 string.
     *
     * @param base64RsaPublicKey Base64 RSA public key
     * @return Optional of publicKey is success,
     * empty optional otherwise.
     */
    public static Optional<PublicKey> base64ToRsaPublicKey(String base64RsaPublicKey) {
        String strippedBase64RsaPub = base64RsaPublicKey
                .replace("\n", "")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "");
        byte[] decodedKey = Base64.getDecoder().decode(strippedBase64RsaPub);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return Optional.of(keyFactory.generatePublic(spec));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            log.error("Failed to get RSA public key from Base64 string", e);
            return Optional.empty();
        }
    }

    /**
     * Get RSA private key from Base64 text.
     *
     * @param base64RsaPrivateKey Base64 RSA private key
     * @return Optional of privateKey if success,
     * empty optional otherwise.
     */
    public static Optional<PrivateKey> base64ToRsaPrivateKey(String base64RsaPrivateKey) {
        String strippedBase64RsaPriv = base64RsaPrivateKey
                .replace("\n", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "");
        byte[] decodedKey = Base64.getDecoder().decode(strippedBase64RsaPriv);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decodedKey);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return Optional.of(keyFactory.generatePrivate(spec));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            log.error("Failed to get RSA private key from Base64 string", e);
            return Optional.empty();
        }
    }
}
