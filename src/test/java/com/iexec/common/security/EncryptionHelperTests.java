package com.iexec.common.security;

import com.iexec.common.utils.BytesUtils;
import com.iexec.common.utils.FileHelper;
import org.junit.Before;
import org.junit.Test;
import org.web3j.crypto.Hash;

import static com.iexec.common.utils.FileHelper.readAllBytes;
import static com.iexec.common.utils.FileHelper.readFile;
import static org.junit.Assert.assertEquals;

public class EncryptionHelperTests {

    private final static String DOT_SLASH = "./src/test/resources/security/";
    private String plainTextRsaPublicKey;
    private String plainTextRsaPrivateKey;

    @Before
    public void before() {
        plainTextRsaPublicKey = readFile(DOT_SLASH + "test_rsa_key.pub");
        plainTextRsaPrivateKey = readFile(DOT_SLASH + "test_rsa_key");
    }


    private void removeOldFiles() {
        FileHelper.deleteFolder(DOT_SLASH + "encrypted-result-0xabc");
        FileHelper.deleteFile(DOT_SLASH + "clear-result-0xabc.zip");
    }

    @Test
    public void shouldEncryptAndDecrypt() {
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
