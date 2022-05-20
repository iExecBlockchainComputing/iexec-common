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

package com.iexec.common.chain.eip712.entity;

import com.iexec.common.chain.eip712.EIP712Domain;
import com.iexec.common.chain.eip712.EIP712Entity;
import com.iexec.common.chain.eip712.TypeParam;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * EIP-712 compliant challenge based on {@link Challenge} type and {@link EIP712Domain}.
 * <p>
 * In the current implementation, {@link EIP712Domain} fields are {@code name}, {@code version} and {@code chainID}.
 * There is no {@code verifyingContract}.
 */
@Slf4j
@NoArgsConstructor
public class EIP712Challenge extends EIP712Entity<Challenge> {

    public EIP712Challenge(EIP712Domain domain, Challenge message) {
        super(domain, message);
    }

    public String getPrimaryType() {
        return "Challenge";
    }

    @Override
    public List<TypeParam> getMessageTypeParams() {
        return Collections.singletonList(
                new TypeParam("challenge", "string")
        );
    }

    @Override
    public String getMessageHash() {
        return super.hashMessageValues(
                getMessage().getChallenge()
        );
    }

}
