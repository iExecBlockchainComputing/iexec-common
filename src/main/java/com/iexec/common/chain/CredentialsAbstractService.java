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

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import com.iexec.common.security.Signature;
import com.iexec.common.utils.SignatureUtils;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.WalletUtils;
import org.web3j.utils.Numeric;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CredentialsAbstractService {

    private final Credentials credentials;

    public CredentialsAbstractService() throws Exception {
        try {
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            credentials = Credentials.create(ecKeyPair);
            log.info("Created new wallet credentials [address:{}] ", credentials.getAddress());
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            log.error("Cannot create new wallet credentials", e);
            throw e;
        }
    }

    public CredentialsAbstractService(String walletPassword, String walletPath) throws Exception {
        try {
            credentials = WalletUtils.loadCredentials(walletPassword, walletPath);
            log.info("Loaded wallet credentials [address:{}] ", credentials.getAddress());
        } catch (IOException | CipherException e) {
            log.error("Cannot load wallet credentials", e);
            throw e;
        }
    }

    public Credentials getCredentials() {
        return credentials;
    }

    /* 
    * Signs messages with Ethereum prefix
    */
    public Signature hashAndSignMessage(String message) {
        String hexPrivateKey = Numeric.toHexStringWithPrefix(credentials.getEcKeyPair().getPrivateKey());
        return SignatureUtils.signMessageHashAndGetSignature(message, hexPrivateKey);
    }
}
