package com.iexec.common.security;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CipherHelper {


    /****************
     *
     *  AES material
     *
     * **************/

    /*
     * Generate AES key
     * */
    public static byte[] generateAesKey(int size) {
        byte[] encodedKey = null;
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(size); // The AES key size in number of bits
            SecretKey secKey = generator.generateKey();
            encodedKey = Base64.getEncoder().encode(secKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodedKey;
    }

    public static byte[] generateAesKey() {
        return generateAesKey(128);
    }

    /*
     * AES encryption
     *
     * For large files: https://stackoverflow.com/a/34004582
     * */
    public static byte[] aesEncrypt(byte[] data, byte[] aesKey) {
        byte[] encryptedData = null;
        try {
            byte[] decodedKey = Base64.getDecoder().decode(aesKey);
            SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

            // AES defaults to AES/ECB/PKCS5Padding in Java 7
            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.ENCRYPT_MODE, originalKey);
            byte[] byteCipherText = aesCipher.doFinal(data);
            encryptedData = Base64.getEncoder().encode(byteCipherText);
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return encryptedData;
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
        return new KeyPair(
                getRsaPublicKey(publicKeyPath),
                getRsaPrivateKey(privateKeyPath)
        );
    }

    /*
     * Read RSA publicKey from file
     * */
    public static PublicKey getRsaPublicKey(String filename) {
        PublicKey publicKey = null;
        try {
            byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());

            String base64Key = new String(keyBytes);
            base64Key = base64Key
                    .replace("\n", "")
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "");
            byte[] decodedKey = Base64.getDecoder().decode(base64Key.getBytes());

            X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");

            publicKey = kf.generatePublic(spec);
        } catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    /*
     * Read RSA privateKey from file
     * */
    public static PrivateKey getRsaPrivateKey(String filename) {
        PrivateKey privateKey = null;
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get(filename));

            String base64Key = new String(keyBytes);
            base64Key = base64Key
                    .replace("\n", "")
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "");
            byte[] decodedKey = Base64.getDecoder().decode(base64Key.getBytes());

            PKCS8EncodedKeySpec spec =
                    new PKCS8EncodedKeySpec(decodedKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");

            privateKey = kf.generatePrivate(spec);
        } catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

}
