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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.iexec.common.utils.HashUtils;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the {@code EIP712Domain} part an EIP-712 compliant data structure.
 * <p>
 * This class allows to:
 * <ul>
 * <li> describe a domain with the correct fields
 * <li> represent the domain structure as string like {@code EIP712Domain(string name,string version,unit256 chainId)}
 * <li> compute the {@code domainSeparator}, the hash of the concatenation of the domain structure hash and the hash of field values
 * </ul>
 */
@Getter
public class EIP712Domain {

    public static final String primaryType = "EIP712Domain";

    private final String name;
    private final String version;
    private final long chainId;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private final String verifyingContract;
    @JsonIgnore
    private final List<TypeParam> types = new ArrayList<>();

    public EIP712Domain() {
        this("", "", 0L, null);
    }

    public EIP712Domain(long chainId, String verifyingContract) {
        this("iExecODB", "5.0.0", chainId, verifyingContract);
    }

    public EIP712Domain(String name, String version, long chainId, String verifyingContract) {
        this.name = name;
        this.version = version;
        this.chainId = chainId;
        this.verifyingContract = verifyingContract;

        this.initTypes();
    }

    private void initTypes() {
        this.types.addAll(Arrays.asList(
                new TypeParam("name", "string"),
                new TypeParam("version", "string"),
                new TypeParam("chainId", "uint256")
        ));

        if (StringUtils.isNotEmpty(this.verifyingContract)) {
            this.types.add(
                    new TypeParam("verifyingContract", "address"));
        }
    }

    /**
     * Gets the representation of the EIP712Domain instance.
     * @return The {@code EIP712Domain(string name,string version,uint256 chainId,...)} string
     */
    @JsonIgnore
    public String getDomainType() {
        return primaryType + "(" +
                types.stream().map(TypeParam::toDescription)
                        .collect(Collectors.joining(",")) + ")";
    }

    /**
     * Gets the {@code domainSeparator} of an EIP-712 compliant data structure.
     * <p>
     * It concatenates the hash of {@link #getDomainType()} and the hash of each field value for the current instance.
     * The result of this concatenation is then hashed.
     * @return The computed hash.
     */
    @JsonIgnore
    public String getDomainSeparator() {
        String domainType = getDomainType();

        if (StringUtils.isNotEmpty(this.verifyingContract)) {
            return HashUtils.concatenateAndHash(
                    EIP712Utils.encodeData(domainType),
                    EIP712Utils.encodeData(name),
                    EIP712Utils.encodeData(version),
                    EIP712Utils.encodeData(chainId),
                    EIP712Utils.encodeData(verifyingContract));
        }

        return HashUtils.concatenateAndHash(
                EIP712Utils.encodeData(domainType),
                EIP712Utils.encodeData(name),
                EIP712Utils.encodeData(version),
                EIP712Utils.encodeData(chainId));
    }

}
