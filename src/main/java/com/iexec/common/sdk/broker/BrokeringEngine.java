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

import com.iexec.common.sdk.cli.IexecCli;
import com.iexec.common.sdk.order.payload.AppOrder;
import com.iexec.common.sdk.order.payload.DatasetOrder;
import com.iexec.common.sdk.order.payload.RequestOrder;
import com.iexec.common.sdk.order.payload.WorkerpoolOrder;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;

@Slf4j
@Builder
public class BrokeringEngine {

    private final int chainId;
    private final String walletContent;
    private final String walletPassword;

    public BrokeringEngine(int chainId, String walletContent, String walletPassword) {
        this.chainId = chainId;
        this.walletContent = walletContent;
        this.walletPassword = walletPassword;
    }

    public String matchOrders(BrokerOrder brokerOrder, long deposit, boolean withDataset) {
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
        String beneficiary = brokerOrder.getRequestOrder().getBeneficiary();
        log.info("Matching valid orders onchain [requester:{}, beneficiary:{}, " +
                "pool:{}, app:{}, dataset:{}]", requestOrder.getRequester(), beneficiary,
                workerpoolOrder.getWorkerpool(), appOrder.getApp(), datasetOrder.getDataset());        
        String chainDealId = IexecCli.runIexecFillCommand(chainId, brokerOrder, walletContent, walletPassword);
        if (chainDealId == null || chainDealId.isEmpty()) {
            throw new RuntimeException("Failed to fill order");
        }
        return chainDealId;
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
