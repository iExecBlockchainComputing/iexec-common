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

import com.iexec.common.sdk.cli.IexecCli;
import com.iexec.common.sdk.cli.input.UnsignedRequestOrderCliInput;
import com.iexec.common.sdk.cli.output.SignRequestOrderCliOutput;
import com.iexec.common.sdk.order.payload.RequestOrder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderSigner {

    private final IexecCli iexecCli;

    public OrderSigner(
        int chainId,
        String walletPassword
    ) {
        this.iexecCli = new IexecCli(chainId, walletPassword);
    }

    public RequestOrder signRequestOrder(RequestOrder requestOrder) {
        if (requestOrder == null) {
            throw new IllegalArgumentException("Request order should not be null");
        }
        iexecCli.copyInputToHomeDir(new UnsignedRequestOrderCliInput(requestOrder), "iexec.json");
        RequestOrder signedRequestOrder = iexecCli.run("iexec order sign --request --skip-request-check",
                SignRequestOrderCliOutput.class);
        //not checking params since iexec-cli update it
        if (!requestOrder.equalsExcludedSaltSignAndParams(signedRequestOrder)){
            log.error("Failed to signRequestOrder (orders should be equals)[beneficiary:{}]",
                    requestOrder.getBeneficiary());
            return null;
        }
        if (signedRequestOrder == null || signedRequestOrder.getSalt() == null || signedRequestOrder.getSalt().isEmpty()
                || signedRequestOrder.getSign() == null || signedRequestOrder.getSign().isEmpty()) {
            log.error("Failed to signRequestOrder [beneficiary:{}]",
                    requestOrder.getBeneficiary());
            return null;
        }
        return signedRequestOrder;
    }
}
