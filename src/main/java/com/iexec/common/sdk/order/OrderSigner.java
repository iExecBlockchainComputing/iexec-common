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

package com.iexec.common.sdk.order;

import com.iexec.common.chain.eip712.EIP712Domain;
import com.iexec.common.chain.eip712.entity.EIP712DatasetOrder;
import com.iexec.common.chain.eip712.entity.EIP712RequestOrder;
import com.iexec.common.sdk.order.payload.DatasetOrder;
import com.iexec.common.sdk.order.payload.RequestOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.web3j.crypto.ECKeyPair;

@Slf4j
public class OrderSigner {

    private final EIP712Domain eip712Domain;
    private final ECKeyPair ecKeyPair;

    public OrderSigner(
            int chainId,
            String verifyingContract,
            ECKeyPair ecKeyPair
    ) {
        this.ecKeyPair = ecKeyPair;
        eip712Domain = new EIP712Domain(chainId, verifyingContract);
    }

    public DatasetOrder signDatasetOrder(DatasetOrder datasetOrder) {
        if (datasetOrder == null) {
            return null;
        }
        String signature = new EIP712DatasetOrder(eip712Domain, datasetOrder)
                .signMessage(ecKeyPair);
        if (StringUtils.isEmpty(signature)) {
            log.error("Empty signature [datasetOrder:{}]", datasetOrder.toString());
            return null;
        }
        datasetOrder.setSign(signature);
        return datasetOrder;
    }

    public RequestOrder signRequestOrder(RequestOrder requestOrder) {
        if (requestOrder == null) {
            return null;
        }
        String signature = new EIP712RequestOrder(eip712Domain, requestOrder)
                .signMessage(ecKeyPair);
        if (StringUtils.isEmpty(signature)) {
            log.error("Empty signature [requestOrder:{}]", requestOrder.toString());
            return null;
        }
        requestOrder.setSign(signature);
        return requestOrder;
    }
}
