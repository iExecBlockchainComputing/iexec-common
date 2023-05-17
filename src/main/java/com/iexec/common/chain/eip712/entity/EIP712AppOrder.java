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

package com.iexec.common.chain.eip712.entity;

import com.iexec.commons.poco.eip712.EIP712Domain;
import com.iexec.commons.poco.eip712.EIP712Entity;
import com.iexec.commons.poco.eip712.TypeParam;
import com.iexec.commons.poco.order.AppOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * @deprecated Use {@link com.iexec.commons.poco.eip712.entity.EIP712AppOrder}
 */
@Deprecated(forRemoval = true)
@Getter
@NoArgsConstructor
public class EIP712AppOrder extends EIP712Entity<AppOrder> {

    public EIP712AppOrder(EIP712Domain domain, AppOrder appOrder) {
        super(domain, appOrder);
    }

    @Override
    public String getPrimaryType() {
        return "AppOrder";
    }

    @Override
    public List<TypeParam> getMessageTypeParams() {
        return Arrays.asList(
                new TypeParam("app", "address"),
                new TypeParam("appprice", "uint256"),
                new TypeParam("volume", "uint256"),
                new TypeParam("tag", "bytes32"),
                new TypeParam("datasetrestrict", "address"),
                new TypeParam("workerpoolrestrict", "address"),
                new TypeParam("requesterrestrict", "address"),
                new TypeParam("salt", "bytes32"));
    }

    @Override
    public String getMessageHash() {
        return super.hashMessageValues(
                getMessage().getApp(),
                getMessage().getAppprice(),
                getMessage().getVolume(),
                getMessage().getTag(),
                getMessage().getDatasetrestrict(),
                getMessage().getWorkerpoolrestrict(),
                getMessage().getRequesterrestrict(),
                getMessage().getSalt()
        );
    }

}