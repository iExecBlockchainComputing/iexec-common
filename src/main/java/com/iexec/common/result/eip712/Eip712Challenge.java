/*
 * Copyright 2022 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.result.eip712;

import com.iexec.common.chain.eip712.EIP712Domain;
import com.iexec.common.chain.eip712.EIP712Entity;
import com.iexec.common.chain.eip712.TypeParam;

import java.util.Collections;
import java.util.List;

public class Eip712Challenge extends EIP712Entity<Message> {
    private static final String DOMAIN_NAME = "iExec Result Repository";
    private static final String DOMAIN_VERSION = "1";
    private static final String PRIMARY_TYPE = "Challenge";
    private static final List<TypeParam> MESSAGE_TYPE_PARAMS = Collections.singletonList(
            new TypeParam("challenge", "string")
    );

    public Eip712Challenge(String challenge, long chainId) {
        this(challenge, chainId, DOMAIN_NAME, DOMAIN_VERSION);
    }

    public Eip712Challenge(String challenge, long chainId, String domainName) {
        this(challenge, chainId, domainName, DOMAIN_VERSION);
    }

    public Eip712Challenge(String challenge, long chainId, String domainName, String domainVersion) {
        super(
                new EIP712Domain(domainName, domainVersion, chainId, null),
                Message.builder().challenge(challenge).build()
        );
    }

    @Override
    public String getPrimaryType() {
        return PRIMARY_TYPE;
    }

    @Override
    public String getMessageHash() {
        return super.hashMessageValues(
                getMessage().getChallenge()
        );
    }

    @Override
    public List<TypeParam> getMessageTypeParams() {
        return MESSAGE_TYPE_PARAMS;
    }
}
