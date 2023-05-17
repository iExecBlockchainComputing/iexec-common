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

package com.iexec.common.chain.eip712;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ObjectArrays;
import com.iexec.commons.poco.utils.HashUtils;
import com.iexec.commons.poco.utils.SignatureUtils;
import lombok.NoArgsConstructor;
import org.web3j.crypto.ECKeyPair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @deprecated Use {@link com.iexec.commons.poco.eip712.EIP712Entity}
 */
@Deprecated(forRemoval = true)
@NoArgsConstructor
public abstract class EIP712Entity<M> implements EIP712<M> {

    private Map<String, List<TypeParam>> types;
    private EIP712Domain domain;
    private M message;

    protected EIP712Entity(EIP712Domain domain, M message) {
        this.domain = domain;
        this.message = message;
        this.types = Map.of(
                EIP712Domain.primaryType, domain.getTypes(),
                getPrimaryType(), getMessageTypeParams()
        );
    }

    @Override
    public Map<String, List<TypeParam>> getTypes() {
        return new HashMap<>(types);
    }

    @Override
    public EIP712Domain getDomain() {
        return domain;
    }

    @Override
    public M getMessage() {
        return message;
    }

    @Override
    public String getHash() {
        return HashUtils.concatenateAndHash(
                "0x1901",
                getDomain().getDomainSeparator(),
                getMessageHash());
    }

    public String hashMessageValues(Object... values) {
        String type = getPrimaryType() + "(" + getMessageTypeParams().stream()
                .map(TypeParam::toDescription)
                .collect(Collectors.joining(",")) + ")";
        //MyEntity(address param1, string param2, ..)
        String typeHash = EIP712Utils.encodeData(type);
        String[] encodedValues = Arrays.stream(values).map(EIP712Utils::encodeData).toArray(String[]::new);
        return HashUtils.concatenateAndHash(ObjectArrays.concat(typeHash, encodedValues));
    }

    public String signMessage(ECKeyPair ecKeyPair) {
        return SignatureUtils.signAsString(this.getHash(), ecKeyPair);
    }

    @JsonIgnore
    public List<TypeParam> getDomainTypeParams() {
        return new ArrayList<>(types.get(EIP712Domain.primaryType));
    }
}
