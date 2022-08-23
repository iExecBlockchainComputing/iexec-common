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

import com.iexec.common.chain.eip712.EIP712Domain;
import com.iexec.common.chain.eip712.entity.Challenge;
import com.iexec.common.chain.eip712.entity.EIP712Challenge;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CredentialsAbstractServiceTests {

    @TempDir
    File tempDir;

    public static final String WALLET_PASS = "wallet-pass";

    static class CredentialsServiceStub extends CredentialsAbstractService {
        public CredentialsServiceStub(String walletPassword, String walletPath) throws Exception {
            super(walletPassword, walletPath);
        }

        public CredentialsServiceStub(Credentials credentials) throws Exception {
            super(credentials);
        }

        public CredentialsServiceStub() throws Exception {
            super();
        }
    }

    // region constructor (Credentials)
    @Test
    void shouldConstructFromCredentials() throws Exception {
        Credentials credentials = Credentials.create(Hash.sha3(""));

        CredentialsServiceStub credentialsService = new CredentialsServiceStub(credentials);
        Credentials claimedCredentials = credentialsService.getCredentials();

        assertThat(claimedCredentials).isEqualTo(credentials);
        assertThat(claimedCredentials.getAddress()).isEqualTo("0x9cce34f7ab185c7aba1b7c8140d620b4bda941d6");
    }

    @Test
    void shouldNotConstructFromNullCredentials() throws Exception {
        Credentials credentials = mock(Credentials.class);
        assertThrows(ExceptionInInitializerError.class, () -> new CredentialsServiceStub(credentials));
    }

    @Test
    void shouldNotConstructFromInvalidCredentials() throws Exception {
        Credentials credentials = mock(Credentials.class);
        when(credentials.getAddress()).thenReturn("0xwrongFormat");
        assertThrows(ExceptionInInitializerError.class, () -> new CredentialsServiceStub(credentials));
    }
    // endregion

    // region constructor (String, String)
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
    // endregion

    // region EIP712Challenge
    @Test
    void shouldBuildAuthorizationTokenFromCredentials() throws Exception {
        final Challenge challenge = Challenge.builder().challenge("abcd").build();
        final EIP712Domain domain = new EIP712Domain("OTHER DOMAIN", "2", 13L, null);
        final EIP712Challenge eip712Challenge = new EIP712Challenge(domain, challenge);
        final Credentials credentials = Credentials.create("0x2a46e8c1535792f6689b10d5c882c9363910c30751ec193ae71ec71630077909");

        final String expectedToken = "0xe001855eda78679dfa4972de06d1cf28c630561e17fc6b075130ce688f448bfe" +
                "_0xc0b3f255c47783e482e1932923dc388cfb5a737ebebdcec04b8ad7ac427c8c9d5155c3211a375704416b639ff8aa7571ef999122a0259bfaf1bbf822345505b11c" +
                "_0x2d29bfbec903479fe4ba991918bab99b494f2bef";

        final CredentialsServiceStub credentialsServiceStub = spy(CredentialsServiceStub.class);
        when(credentialsServiceStub.getCredentials()).thenReturn(credentials);

        final String token = credentialsServiceStub.signEIP712EntityAndBuildToken(eip712Challenge);
        assertThat(token)
                .isNotEmpty()
                .isEqualTo(expectedToken);
    }
    // endregion


    String createTempWallet(String password) throws Exception {
        String walletFilename = WalletUtils.generateFullNewWalletFile(password, tempDir);
        return tempDir + "/" + walletFilename;
    }
}