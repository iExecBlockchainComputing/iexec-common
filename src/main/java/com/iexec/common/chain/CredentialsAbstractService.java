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

package com.iexec.common.chain;

import com.iexec.common.chain.eip712.EIP712Entity;
import com.iexec.common.utils.EthAddress;
import com.iexec.commons.poco.security.Signature;
import com.iexec.commons.poco.utils.SignatureUtils;
import lombok.extern.slf4j.Slf4j;
import org.web3j.crypto.*;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@Slf4j
public abstract class CredentialsAbstractService {

    private final Credentials credentials;

    protected CredentialsAbstractService() throws Exception {
        try {
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            credentials = Credentials.create(ecKeyPair);
            log.info("Created new wallet credentials [address:{}] ", credentials.getAddress());
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            log.error("Cannot create new wallet credentials", e);
            throw e;
        }
    }

    protected CredentialsAbstractService(Credentials credentials) {
        this.credentials = credentials;
        if (credentials != null
                && EthAddress.validate(credentials.getAddress())) {
            log.info("Loaded wallet credentials [address:{}]",
                    credentials.getAddress());
        } else {
            throw new ExceptionInInitializerError("Cannot create credential service");
        }
    }

    protected CredentialsAbstractService(String walletPassword, String walletPath) throws Exception {
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

    /**
     * Builds an authorization token for given {@link EIP712Entity}.
     * <p>
     * An authorization token is the concatenation of the following values,
     * delimited by an underscore:
     * <ol>
     *     <li>Entity hash;</li>
     *     <li>Signed message;</li>
     *     <li>Credentials address.</li>
     * </ol>
     *
     * @param eip712Entity Entity to sign a token for.
     * @return The authorization token.
     */
    public String signEIP712EntityAndBuildToken(EIP712Entity<?> eip712Entity) {
        final Credentials currentCredentials = this.getCredentials();

        final String hash = eip712Entity.getHash();
        final String signedMessage = eip712Entity.signMessage(currentCredentials.getEcKeyPair());
        return String.join("_", hash, signedMessage, currentCredentials.getAddress());
    }
}
