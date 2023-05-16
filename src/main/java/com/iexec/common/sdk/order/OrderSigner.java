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

package com.iexec.common.sdk.order;

import com.iexec.common.chain.eip712.EIP712Domain;
import com.iexec.common.chain.eip712.entity.EIP712AppOrder;
import com.iexec.common.chain.eip712.entity.EIP712DatasetOrder;
import com.iexec.common.chain.eip712.entity.EIP712RequestOrder;
import com.iexec.common.chain.eip712.entity.EIP712WorkerpoolOrder;
import com.iexec.common.sdk.order.payload.AppOrder;
import com.iexec.common.sdk.order.payload.DatasetOrder;
import com.iexec.common.sdk.order.payload.RequestOrder;
import com.iexec.common.sdk.order.payload.WorkerpoolOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.web3j.crypto.ECKeyPair;

@Slf4j
@Deprecated(forRemoval = true)
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

    public AppOrder signAppOrder(AppOrder appOrder) {
        if (appOrder == null) {
            return null;
        }
        String signature = new EIP712AppOrder(eip712Domain, appOrder)
                .signMessage(ecKeyPair);
        if (StringUtils.isEmpty(signature)) {
            log.error("Empty signature [appOrder:{}]", appOrder);
            return null;
        }
        return appOrder.withSignature(signature);
    }

    public WorkerpoolOrder signWorkerpoolOrder(WorkerpoolOrder workerpoolOrder) {
        if (workerpoolOrder == null) {
            return null;
        }
        String signature = new EIP712WorkerpoolOrder(eip712Domain, workerpoolOrder)
                .signMessage(ecKeyPair);
        if (StringUtils.isEmpty(signature)) {
            log.error("Empty signature [workerpoolOrder:{}]", workerpoolOrder);
            return null;
        }
        return workerpoolOrder.withSignature(signature);
    }

    public DatasetOrder signDatasetOrder(DatasetOrder datasetOrder) {
        if (datasetOrder == null) {
            return null;
        }
        String signature = new EIP712DatasetOrder(eip712Domain, datasetOrder)
                .signMessage(ecKeyPair);
        if (StringUtils.isEmpty(signature)) {
            log.error("Empty signature [datasetOrder:{}]", datasetOrder);
            return null;
        }
        return datasetOrder.withSignature(signature);
    }

    public RequestOrder signRequestOrder(RequestOrder requestOrder) {
        if (requestOrder == null) {
            return null;
        }
        String signature = new EIP712RequestOrder(eip712Domain, requestOrder)
                .signMessage(ecKeyPair);
        if (StringUtils.isEmpty(signature)) {
            log.error("Empty signature [requestOrder:{}]", requestOrder);
            return null;
        }
        return requestOrder.withSignature(signature);
    }
}
