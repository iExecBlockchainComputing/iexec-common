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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Describe a single member of a struct in an EIP-712 compliant data types description.
 * <p>
 * This is part of {@code typeHash = keccak256(encodeType(typeOf(s)))} where {@code encodeType} is
 * {@code structName(paramType<1> paramName<1>,...,paramType<n> paramName<n>)}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeParam {

    private String name;
    private String type;

    /**
     * Gets the description of a single parameter as a String.
     * @return The {@code "type name"} string
     */
    public String toDescription() {
        return type + " " + name;
    }

}
