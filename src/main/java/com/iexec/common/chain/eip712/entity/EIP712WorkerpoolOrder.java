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

import com.iexec.common.chain.eip712.EIP712Domain;
import com.iexec.common.chain.eip712.EIP712Entity;
import com.iexec.common.chain.eip712.TypeParam;
import com.iexec.common.sdk.order.payload.WorkerpoolOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
public class EIP712WorkerpoolOrder extends EIP712Entity<WorkerpoolOrder> {

    public EIP712WorkerpoolOrder(EIP712Domain domain, WorkerpoolOrder workerpoolOrder) {
        super(domain, workerpoolOrder);
    }

    @Override
    public String getPrimaryType() {
        return "WorkerpoolOrder";
    }

    @Override
    public List<TypeParam> getMessageTypeParams() {
        return Arrays.asList(
                new TypeParam("workerpool", "address"),
                new TypeParam("workerpoolprice", "uint256"),
                new TypeParam("volume", "uint256"),
                new TypeParam("tag", "bytes32"),
                new TypeParam("category", "uint256"),
                new TypeParam("trust", "uint256"),
                new TypeParam("apprestrict", "address"),
                new TypeParam("datasetrestrict", "address"),
                new TypeParam("requesterrestrict", "address"),
                new TypeParam("salt", "bytes32"));
    }

    @Override
    public String getMessageHash() {
        return super.hashMessageValues(
                getMessage().getWorkerpool(),
                getMessage().getWorkerpoolprice(),
                getMessage().getVolume(),
                getMessage().getTag(),
                getMessage().getCategory(),
                getMessage().getTrust(),
                getMessage().getApprestrict(),
                getMessage().getDatasetrestrict(),
                getMessage().getRequesterrestrict(),
                getMessage().getSalt()
        );
    }

}