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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

public class CredentialsAbstractServiceTests {

    class CredentialsServiceStub extends CredentialsAbstractService {
        public CredentialsServiceStub(String walletPassword, String walletPath) throws Exception {
            super(walletPassword, walletPath);
        }

        public CredentialsServiceStub() throws Exception {
            super();
        }
    }

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    public static final String WALLET_PASS = "wallet-pass";

    @Test
    public void shouldLoadCorrectCredentials() throws Exception {
        String walletPath = createTempWallet(WALLET_PASS);

        CredentialsServiceStub credentialsService = new CredentialsServiceStub(WALLET_PASS, walletPath);
        Credentials claimedCredentials = credentialsService.getCredentials();
        Credentials correctCredentials = WalletUtils.loadCredentials(WALLET_PASS, walletPath);

        assertThat(claimedCredentials).isEqualTo(correctCredentials);
        assertThat(claimedCredentials.getAddress()).isEqualTo(correctCredentials.getAddress());
    }

    @Test(expected=FileNotFoundException.class)
    public void shouldThrowIOExceptionSinceWalletFileNotFind() throws Exception {
        new CredentialsServiceStub(WALLET_PASS, "dummy-path");
    }

    @Test(expected=IOException.class)
    public void shouldThrowIOExceptionSinceCorruptedWalletFile() throws Exception {
        String walletPath = createTempWallet(WALLET_PASS);
        FileWriter fw = new FileWriter(walletPath);
        fw.write("{new: devilish corrupted content}");
        fw.close();
        new CredentialsServiceStub(WALLET_PASS, walletPath);
    }

    @Test(expected=CipherException.class)
    public void shouldThrowCipherExceptionSinceWrongPassword() throws Exception {
        String wrongPass = "wrong-pass";
        String walletPath = createTempWallet(WALLET_PASS);
        new CredentialsServiceStub(wrongPass, walletPath);
    }

    String createTempWallet(String password) throws Exception {
        File tempDir = temporaryFolder.newFolder("temp-dir");
        String walletFilename = WalletUtils.generateFullNewWalletFile(password, tempDir);
        return tempDir + "/" + walletFilename;
    }
}