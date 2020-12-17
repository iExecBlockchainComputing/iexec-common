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

import com.iexec.common.contract.generated.IexecLibOrders_v5;
import com.iexec.common.sdk.order.payload.RequestOrder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Getter
public class EIP712RequestOrder extends EIP712Entity<RequestOrder> {

    public EIP712RequestOrder(EIP712Domain domain, RequestOrder requestOrder) {
        super(domain, requestOrder);
    }

    @Override
    public String getPrimaryType() {
        return "RequestOrder";
    }

    @Override
    public List<TypeParam> getMessageTypeParams() {
        return Arrays.asList(
                new TypeParam("app", "address"),
                new TypeParam("appmaxprice", "uint256"),
                new TypeParam("dataset", "address"),
                new TypeParam("datasetmaxprice", "uint256"),
                new TypeParam("workerpool", "address"),
                new TypeParam("workerpoolmaxprice", "uint256"),
                new TypeParam("requester", "address"),
                new TypeParam("volume", "uint256"),
                new TypeParam("tag", "bytes32"),
                new TypeParam("category", "uint256"),
                new TypeParam("trust", "uint256"),
                new TypeParam("beneficiary", "address"),
                new TypeParam("callback", "address"),
                new TypeParam("params", "string"),
                new TypeParam("salt", "bytes32"));
    }

    @Override
    public String getMessageHash() {
        return super.hashMessageValues(
                getMessage().getApp(),
                getMessage().getAppmaxprice(),
                getMessage().getDataset(),
                getMessage().getDatasetmaxprice(),
                getMessage().getWorkerpool(),
                getMessage().getWorkerpoolmaxprice(),
                getMessage().getRequester(),
                getMessage().getVolume(),
                getMessage().getTag(),
                getMessage().getCategory(),
                getMessage().getTrust(),
                getMessage().getBeneficiary(),
                getMessage().getCallback(),
                getMessage().getParams(),
                getMessage().getSalt()
        );
    }

}