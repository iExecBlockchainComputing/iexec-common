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

package com.iexec.common.sdk.marketplace;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iexec.common.sdk.order.OrderType;
import com.iexec.common.sdk.order.markeplace.MPAppOrder;
import com.iexec.common.sdk.order.markeplace.MPDatasetOrder;
import com.iexec.common.sdk.order.markeplace.MPWorkerpoolOrder;
import feign.Feign;
import feign.FeignException;
import feign.jackson.JacksonEncoder;
import feign.querymap.BeanQueryMapEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class MarketplaceClient implements Marketplace {

    private final String baseUrl;
    private final int chainId;
    private final MarketplaceApi api;

    public MarketplaceClient(String baseUrl, int chainId) {
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new IllegalArgumentException("Marketplace url should not be null");
        }
        if (chainId == 0) {
            throw new IllegalArgumentException("Chain id should not be zero");
        }
        this.baseUrl = baseUrl;
        this.chainId = chainId;
        api = Feign.builder()
                .encoder(new JacksonEncoder())
                // don't use a decoder or parsing
                // String as response will fail
                // .decoder(new JacksonDecoder())
                .queryMapEncoder(new BeanQueryMapEncoder())
                .target(MarketplaceApi.class, this.baseUrl);
    }

    @Override
    public List<MPWorkerpoolOrder> getWorkerpoolOrders() {
        return getOrders(OrderType.WORKERPOOL);
    }

    @Override
    public Optional<MPWorkerpoolOrder> getOneWorkerpoolOrder(String workerpoolAddress) {
        return getWorkerpoolOrders(workerpoolAddress)
                .stream()
                .findFirst();        
    }

    @Override
    public List<MPWorkerpoolOrder> getWorkerpoolOrders(String workerpoolAddress) {
        MPQueryParams queryParams = MPQueryParams.builder()
                .workerpool(workerpoolAddress)
                .build();
        return getOrders(OrderType.WORKERPOOL, queryParams);
    }

    @Override
    public Optional<MPAppOrder> getOneAppOrder(String appAddress) {
        return getAppOrders(appAddress)
                .stream()
                .findFirst();        
    }

    @Override
    public List<MPAppOrder> getAppOrders(String appAddress) {
        MPQueryParams queryParams = MPQueryParams.builder()
                .app(appAddress)
                .build();
        return getOrders(OrderType.APP, queryParams);
    }

    @Override
    public Optional<MPDatasetOrder> getOneDatasetOrder(String datasetAddress) {
        return getDatasetOrders(datasetAddress)
                .stream()
                .findFirst();        
    }

    @Override
    public List<MPDatasetOrder> getDatasetOrders(String datasetAddress) {
        MPQueryParams queryParams = MPQueryParams.builder()
                .dataset(datasetAddress)
                .build();
        return getOrders(OrderType.DATASET, queryParams);
    }

    private <T> List<T> getOrders(OrderType type) {
        return getOrders(type, new MPQueryParams());
    }

    private <T> List<T> getOrders(OrderType orderType, MPQueryParams queryParams) {
        if (orderType == null) {
            throw new IllegalArgumentException("Order type should not be null");
        }
        if (queryParams == null) {
            throw new IllegalArgumentException("Query params should not be null");
        }
        queryParams.setChainId(this.chainId);
        String jsonPayload;
        try {
            jsonPayload = api.getOrders(orderType, queryParams);
        } catch (FeignException e) {
            log.error("Error while getting order [type:{}, queryParams:{}]", orderType, queryParams, e);
            return List.of();
        }
        Orderbook orderbook;
        try {
            orderbook = convertJsonToOrderbook(orderType, jsonPayload);
        } catch (Exception e) {
            log.error("Failed to parse json payload [orderType:{}]", orderType);
            throw new RuntimeException(e);
        }
        if (!orderbook.isOk()) {
            log.error("Failed to get orders [queryParams:{}, error:{}]",
                    queryParams, orderbook.getError());
            List.of();
        }
        return orderbook.getOrders() != null ? orderbook.getOrders() : List.of();
    }

    private Orderbook convertJsonToOrderbook(
        OrderType type,
        String jsonPayload
    ) throws JsonMappingException, JsonProcessingException {
        switch (type) {
            case WORKERPOOL:
                return parse(jsonPayload, new TypeReference<Orderbook<MPWorkerpoolOrder>>(){});
            case APP:
                return parse(jsonPayload, new TypeReference<Orderbook<MPAppOrder>>(){});
            case DATASET:
                return parse(jsonPayload, new TypeReference<Orderbook<MPDatasetOrder>>(){});
            case REQUEST:
                return parse(jsonPayload, new TypeReference<Orderbook<MPDatasetOrder>>(){});
            default:
                throw new RuntimeException("Unknown order type");
        }
    }

    private <T> T parse(String jsonString, TypeReference<T> typeReference)
            throws JsonMappingException, JsonProcessingException {
            return new ObjectMapper().readValue(jsonString, typeReference);
    }
}
