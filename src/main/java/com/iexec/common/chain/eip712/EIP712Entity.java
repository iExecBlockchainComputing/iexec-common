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

package com.iexec.common.chain.eip712;

import com.google.common.collect.ObjectArrays;
import com.iexec.common.utils.HashUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
public abstract class EIP712Entity<T> implements EIP712<T> {

    private final EIP712Domain domain;
    private final T message;

    protected EIP712Entity(EIP712Domain domain, T message) {
        this.domain = domain;
        this.message = message;
    }

    @Override
    public HashMap<String, List<TypeParam>> getTypes() {
        HashMap<String, List<TypeParam>> types = new HashMap<>();
        types.put(EIP712Domain.primaryType, domain.getTypes());
        types.put(getPrimaryType(), getMessageTypeParams());
        return types;
    }

    @Override
    public EIP712Domain getDomain() {
        return domain;
    }

    @Override
    public T getMessage() {
        return message;
    }

    public String hashMessageValues(Object... values) {
        String type = getPrimaryType() + "(" + EIP712Utils.typeParamsToString(getMessageTypeParams()) + ")";//MyEntity(address param1, string param2, ..)
        String typeHash = EIP712Utils.encodeData(type);
        String[] encodedValues = Arrays.stream(values).map(EIP712Utils::encodeData).toArray(String[]::new);
        return HashUtils.concatenateAndHash(ObjectArrays.concat(typeHash, encodedValues));
    }

    public String getHash() {
        return HashUtils.concatenateAndHash(
                "0x1901",
                getDomain().getDomainSeparator(),
                getMessageHash());
    }

}


