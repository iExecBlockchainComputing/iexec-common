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

package com.iexec.common.chain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

class CredentialsAbstractServiceTests {

    @TempDir
    File tempDir;

    public static final String WALLET_PASS = "wallet-pass";

    static class CredentialsServiceStub extends CredentialsAbstractService {
        public CredentialsServiceStub(String walletPassword, String walletPath) throws Exception {
            super(walletPassword, walletPath);
        }

        public CredentialsServiceStub() throws Exception {
            super();
        }
    }

    @Test
    void shouldLoadCorrectCredentials() throws Exception {
        String walletPath = createTempWallet(WALLET_PASS);

        CredentialsServiceStub credentialsService = new CredentialsServiceStub(WALLET_PASS, walletPath);
        Credentials claimedCredentials = credentialsService.getCredentials();
        Credentials correctCredentials = WalletUtils.loadCredentials(WALLET_PASS, walletPath);

        assertThat(claimedCredentials).isEqualTo(correctCredentials);
        assertThat(claimedCredentials.getAddress()).isEqualTo(correctCredentials.getAddress());
    }

    @Test
    void shouldThrowIOExceptionSinceWalletFileNotFind() throws Exception {
        assertThrows(FileNotFoundException.class, () -> new CredentialsServiceStub(WALLET_PASS, "dummy-path"));
    }

    @Test
    void shouldThrowIOExceptionSinceCorruptedWalletFile() throws Exception {
        String walletPath = createTempWallet(WALLET_PASS);
        FileWriter fw = new FileWriter(walletPath);
        fw.write("{new: devilish corrupted content}");
        fw.close();
        assertThrows(IOException.class, () -> new CredentialsServiceStub(WALLET_PASS, walletPath));
    }

    @Test
    void shouldThrowCipherExceptionSinceWrongPassword() throws Exception {
        String wrongPass = "wrong-pass";
        String walletPath = createTempWallet(WALLET_PASS);
        assertThrows(CipherException.class, () -> new CredentialsServiceStub(wrongPass, walletPath));
    }

    String createTempWallet(String password) throws Exception {
        String walletFilename = WalletUtils.generateFullNewWalletFile(password, tempDir);
        return tempDir + "/" + walletFilename;
    }
}