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

import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

import static com.iexec.common.utils.FileHelper.readFile;

@Slf4j
public class CipherUtils {

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
     * @param size
     * @return Generated AES key if success,
     * empty byte array otherwise.
     */
    public static byte[] generateAesKey(int size) {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(size); // The AES key size in number of bits
            SecretKey secKey = generator.generateKey();
            return secKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            log.error("Failed to generate AES key", e);
            return new byte[0];
        }
    }

    /**
     * Encrypt binary data with AES/CBC/PKCS7Padding and
     * encode the encrypted data in Base64. The first
     * 16 bytes of the data must be the IV which means
     * that the data length should be greater than 16 bytes.
     * 
     * @param binaryDataWithIv Binary data to encrypt
     * @param base64Key Base64 encoded AES key
     * @return encrypted data encoded in Base64 if success,
     * empty byte array otherwise.
     * @throws GeneralSecurityException
     * @throws IllegalArgumentException if the data's length
     * is less than 16 bytes.
     * @see https://stackoverflow.com/a/34004582 for large files
     */
    public static byte[] aesEncrypt(byte[] binaryDataWithIv, byte[] base64Key)
            throws GeneralSecurityException {
        Objects.requireNonNull(binaryDataWithIv, "Binary data cannot be null");
        Objects.requireNonNull(base64Key, "Base64 AES key cannot be null");
        if (binaryDataWithIv.length < 16) {
            throw new IllegalArgumentException("Data cannot be less than 16 bytes");
        }
        byte[] binaryIv = Arrays.copyOfRange(binaryDataWithIv, 0, 16);
        byte[] binaryData = Arrays.copyOfRange(binaryDataWithIv, 16, binaryDataWithIv.length);
        byte[] binaryKey = Base64.getDecoder().decode(base64Key);
        return aesEncrypt(binaryData, binaryKey, binaryIv);
    }

    /**
     * Encrypt binary data with AES/CBC/PKCS7Padding.
     * 
     * @param binaryData Binary data to encrypt
     * @param binaryKey Binary AES key
     * @param binaryIv Binary initialization vector
     * @return encrypted data encoded in Base64 if success.
     * @throws GeneralSecurityException
     * 
     * @see https://stackoverflow.com/a/34004582 for large files
     */
    public static byte[] aesEncrypt(byte[] binaryData, byte[] binaryKey, byte[] binaryIv)
            throws GeneralSecurityException {
        Objects.requireNonNull(binaryData, "data cannot be null");
        Objects.requireNonNull(binaryKey, "AES key cannot be null");
        Objects.requireNonNull(binaryIv, "IV cannot be null");
        // try {
            // byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        SecretKey secretKey = new SecretKeySpec(binaryKey, "AES");
        IvParameterSpec ivParam = new IvParameterSpec(binaryIv);
        Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        aesCipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParam);
        byte[] byteCipherText = aesCipher.doFinal(binaryData);
        return byteCipherText;
        // } catch(GeneralSecurityException e) {
        //     return new byte[0];
        // }
        // catch (IllegalBlockSizeException | BadPaddingException |
        //         NoSuchPaddingException | NoSuchAlgorithmException |
        //         InvalidKeyException | InvalidAlgorithmParameterException e) {
        //     log.error("Failed to AES encrypt data", e);
        //     return new byte[0];
        // }
    }

    public static String encrypt(String algorithm, String input, SecretKey key, IvParameterSpec iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    /*
     * AES decryption
     * */
    public static byte[] aesDecrypt(byte[] encryptedData, byte[] aesKey) {
        byte[] decryptedData = null;
        try {
            byte[] decodedKey = Base64.getDecoder().decode(aesKey);
            SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

            // AES defaults to AES/ECB/PKCS5Padding in Java 7
            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.DECRYPT_MODE, originalKey);
            decryptedData = aesCipher.doFinal(Base64.getDecoder().decode(encryptedData));//heap size issues after 500MB
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return decryptedData;
    }

    /****************
     *
     *  RSA material
     *
     * **************/

    /*
     * Generate RSA keys
     * */
    public static KeyPair generateRsaKeys(int size) {
        KeyPair keyPair = null;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(size);
            keyPair = keyPairGenerator.generateKeyPair();
            return keyPair;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keyPair;
    }

    public static KeyPair generateRsaKeys() {
        return generateRsaKeys(2048);
    }

    /*
     * RSA encryption
     * */
    public static byte[] rsaDecrypt(byte[] encryptedData, PrivateKey privateKey) {
        byte[] rsaDecryptedAesKey = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            rsaDecryptedAesKey = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return rsaDecryptedAesKey;
    }

    /*
     * RSA decryption
     * */
    public static byte[] rsaEncrypt(byte[] data, PublicKey publicKey) {
        byte[] rsaEncryptedAesKey = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            rsaEncryptedAesKey = Base64.getEncoder().encode(cipher.doFinal(data));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return rsaEncryptedAesKey;
    }

    /*
     * Read RSA keyPair from files
     * */
    public static KeyPair getRsaKeyPair(String publicKeyPath, String privateKeyPath) {
        String plainTextRsaPub = readFile(publicKeyPath);
        String plainTextRsaPriv = readFile(privateKeyPath);

        if (!plainTextRsaPub.isEmpty() && !plainTextRsaPriv.isEmpty()) {
            return new KeyPair(
                    plainText2RsaPublicKey(plainTextRsaPub),
                    plainText2RsaPrivateKey(plainTextRsaPriv)
            );
        }

        return null;
    }
    
    /*
     * Read RSA publicKey from fileBytes
     * */
    public static PublicKey plainText2RsaPublicKey(String plainTextRsaPub) {
        PublicKey publicKey = null;
        try {
            //String base64Key = new String(publicKeyBytes);
            plainTextRsaPub = plainTextRsaPub
                    .replace("\n", "")
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "");
            byte[] decodedKey = Base64.getDecoder().decode(plainTextRsaPub.getBytes());

            X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");

            publicKey = kf.generatePublic(spec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    /*
     * Read RSA privateKey from file
     * */
    public static PrivateKey plainText2RsaPrivateKey(String plainTextRsaPriv) {
        PrivateKey privateKey = null;
        try {
            plainTextRsaPriv = plainTextRsaPriv
                    .replace("\n", "")
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "");
            byte[] decodedKey = Base64.getDecoder().decode(plainTextRsaPriv.getBytes());

            PKCS8EncodedKeySpec spec =
                    new PKCS8EncodedKeySpec(decodedKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");

            privateKey = kf.generatePrivate(spec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

}
