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
import com.fasterxml.jackson.annotation.JsonProperty;
import org.web3j.crypto.ECKeyPair;

import java.util.HashMap;
import java.util.List;

public interface EIP712<M> {

    @JsonProperty("types")
    HashMap<String, List<TypeParam>> getTypes();

    @JsonProperty("domain")
    EIP712Domain getDomain();

    @JsonProperty("primaryType")
    String getPrimaryType();

    @JsonProperty("message")
    M getMessage();

    @JsonIgnore
    String getMessageHash();

    @JsonIgnore
    List<TypeParam> getMessageTypeParams();

    @JsonIgnore
    String getHash();

    String signMessage(ECKeyPair ecKeyPair);

}
