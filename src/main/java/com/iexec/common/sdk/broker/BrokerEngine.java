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

package com.iexec.common.sdk.broker;

import com.iexec.common.chain.ChainUtils;
import com.iexec.common.sdk.cli.IexecCli;
import com.iexec.common.sdk.cli.input.BrokerOrderCliInput;
import com.iexec.common.sdk.cli.input.CliInput;
import com.iexec.common.sdk.cli.output.FillOrdersCliOutput;
import com.iexec.common.sdk.order.payload.AppOrder;
import com.iexec.common.sdk.order.payload.DatasetOrder;
import com.iexec.common.sdk.order.payload.RequestOrder;
import com.iexec.common.sdk.order.payload.WorkerpoolOrder;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;

@Slf4j
public class BrokerEngine {

    private final IexecCli iexecCli;
    private final int chainId;

    public BrokerEngine(int chainId, String walletPassword) {
        this.chainId = chainId;
        this.iexecCli = new IexecCli(chainId, walletPassword);
    }

    public String fillBrokerOrder(BrokerOrder brokerOrder, long deposit, boolean withDataset) {
        if (brokerOrder == null) {
            throw new IllegalArgumentException("Broker order cannot be null");
        }
        AppOrder appOrder = brokerOrder.getAppOrder();
        WorkerpoolOrder workerpoolOrder = brokerOrder.getWorkerpoolOrder();
        DatasetOrder datasetOrder = brokerOrder.getDatasetOrder();
        RequestOrder requestOrder = brokerOrder.getRequestOrder();
        if (appOrder == null) {
            throw new IllegalArgumentException("App order cannot be null");
        }
        if (workerpoolOrder == null) {
            throw new IllegalArgumentException("Workerpool order cannot be null");
        }
        if (requestOrder == null) {
            throw new IllegalArgumentException("Request order cannot be null");
        }
        if (withDataset && datasetOrder == null) {
            throw new IllegalArgumentException("Dataset order cannot be null");
        }
        BigInteger datasetPrice = withDataset ? datasetOrder.getDatasetprice() : BigInteger.ZERO;
        if (!hasRequesterAcceptedPrices(brokerOrder.getRequestOrder(),
                appOrder.getAppprice(),
                workerpoolOrder.getWorkerpoolprice(),
                datasetPrice)) {
            throw new IllegalStateException("Incompatible prices");
        }
        if (!hasRequesterDepositedEnough(brokerOrder.getRequestOrder(), deposit)) {
            throw new IllegalStateException("Deposit too low");
        }
        CliInput cliInput = new BrokerOrderCliInput(chainId, brokerOrder);
        iexecCli.copyInputToHomeDir(cliInput, "orders.json");
        String beneficiary = brokerOrder.getRequestOrder().getBeneficiary();
        log.info("Filling valid request order [requester:{}, beneficiary:{}, " +
                "pool:{}, app:{}, dataset:{}]", requestOrder.getRequester(), beneficiary,
                workerpoolOrder.getWorkerpool(), appOrder.getApp(), datasetOrder.getDataset());
        String chainDealId = iexecCli.run("iexec order fill --skip-request-check", FillOrdersCliOutput.class);
        if (chainDealId == null || chainDealId.isEmpty()) {
            throw new RuntimeException("Failed to fill order");
        }
        String chainTaskId = ChainUtils.generateChainTaskId(chainDealId, 0);
        log.info("Filled valid request order [chainTaskId:{}, requester:{}, beneficiary:{}, " +
                "pool:{}, app:{}, dataset:{}]", chainTaskId, requestOrder.getRequester(), beneficiary,
                workerpoolOrder.getWorkerpool(), appOrder.getApp(), datasetOrder.getDataset());
        return chainTaskId;
    }

    public boolean hasRequesterAcceptedPrices(
        RequestOrder requestOrder,
        BigInteger appPrice,
        BigInteger workerpoolPrice,
        BigInteger datasetPrice
    ) {
        if (requestOrder == null || requestOrder.getWorkerpoolmaxprice() == null
                || requestOrder.getAppmaxprice() == null
                || appPrice == null || workerpoolPrice == null) {
            log.error("Failed to check hasRequesterAcceptedPrices (null requestOrder)");
            return false;
        }
        boolean isAppPriceAccepted = requestOrder.getAppmaxprice().longValue() >= appPrice.longValue();
        boolean isPoolPriceAccepted = requestOrder.getWorkerpoolmaxprice().longValue() >= workerpoolPrice.longValue();
        boolean isDatasetPriceAccepted = requestOrder.getDatasetmaxprice().longValue() >= datasetPrice.longValue();
        boolean isAccepted = isAppPriceAccepted && isPoolPriceAccepted && isDatasetPriceAccepted;
        if (!isAccepted) {
            log.error("Prices not accepted (too expensive) [isAppPriceAccepted:{}, " +
                "isPoolPriceAccepted:{}]", isAppPriceAccepted, isPoolPriceAccepted);
        }
        return isAccepted;
    }

    public boolean hasRequesterDepositedEnough(RequestOrder requestOrder, long deposit) {
        if (requestOrder == null || requestOrder.getWorkerpoolmaxprice() == null
                || requestOrder.getAppmaxprice() == null) {
            log.error("Failed to check hasRequesterDepositedEnough (null requestOrder)");
            return false;
        }
        long price = requestOrder.getWorkerpoolmaxprice().add(requestOrder.getAppmaxprice()).longValue();
        if (price > deposit) {
            log.error("Deposit too low [price:{}, deposit:{}]", price, deposit);
            return false;
        }
        return true;
    }
}
