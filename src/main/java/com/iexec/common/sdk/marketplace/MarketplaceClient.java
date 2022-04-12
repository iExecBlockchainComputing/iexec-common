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
import com.iexec.common.sdk.order.markeplace.AppMarketOrder;
import com.iexec.common.sdk.order.markeplace.DatasetMarketOrder;
import com.iexec.common.sdk.order.markeplace.WorkerpoolMarketOrder;
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
    public List<WorkerpoolMarketOrder> getWorkerpoolOrders() {
        return getOrders(OrderType.WORKERPOOL);
    }

    @Override
    public Optional<WorkerpoolMarketOrder> getOneWorkerpoolOrderByWorkerpoolAddress(String workerpoolAddress) {
        return getWorkerpoolOrdersByWorkerpoolAddress(workerpoolAddress)
                .stream()
                .findFirst();        
    }

    @Override
    public List<WorkerpoolMarketOrder> getWorkerpoolOrdersByWorkerpoolAddress(String workerpoolAddress) {
        MPQueryParams queryParams = MPQueryParams.builder()
                .workerpool(workerpoolAddress)
                .build();
        return getOrders(OrderType.WORKERPOOL, queryParams);
    }

    @Override
    public List<WorkerpoolMarketOrder> getWorkerpoolOrdersByTag(String tag) {
        final MPQueryParams queryParams = MPQueryParams.builder()
                .minTag(tag)
                .maxTag(tag)
                .build();
        return getOrders(OrderType.WORKERPOOL, queryParams);
    }

    @Override
    public Optional<WorkerpoolMarketOrder> getOneWorkerpoolOrderByWorkerpoolAddressAndTag(String workerpoolAddress, String tag) {
        return getWorkerpoolOrdersByWorkerpoolAddressAndTag(workerpoolAddress, tag)
                .stream()
                .findFirst();
    }

    @Override
    public List<WorkerpoolMarketOrder> getWorkerpoolOrdersByWorkerpoolAddressAndTag(String workerpoolAddress, String tag) {
        MPQueryParams queryParams = MPQueryParams.builder()
                .workerpool(workerpoolAddress)
                .minTag(tag)
                .maxTag(tag)
                .build();
        return getOrders(OrderType.WORKERPOOL, queryParams);
    }

    @Override
    public Optional<AppMarketOrder> getOneAppOrder(String appAddress) {
        return getAppOrders(appAddress)
                .stream()
                .findFirst();        
    }

    @Override
    public Optional<AppMarketOrder> getOneRequesterRestrictedAppOrder(
        String appAddress,
        String requesterAddress
    ) {
        MPQueryParams queryParams = MPQueryParams.builder()
                .app(appAddress)
                .requester(requesterAddress)
                .build();
        List<AppMarketOrder> orders = getOrders(OrderType.APP, queryParams);
        return orders.stream().findFirst();
    }

    @Override
    public List<AppMarketOrder> getAppOrders(String appAddress) {
        MPQueryParams queryParams = MPQueryParams.builder()
                .app(appAddress)
                .build();
        return getOrders(OrderType.APP, queryParams);
    }

    @Override
    public Optional<DatasetMarketOrder> getOneDatasetOrder(String datasetAddress) {
        return getDatasetOrders(datasetAddress)
                .stream()
                .findFirst();        
    }

    @Override
    public Optional<DatasetMarketOrder> getOneRequesterRestrictedDatasetOrder(
        String datasetAddress,
        String requesterAddress
    ) {
        MPQueryParams queryParams = MPQueryParams.builder()
                .dataset(datasetAddress)
                .requester(requesterAddress)
                .build();
        List<DatasetMarketOrder> orders = getOrders(OrderType.DATASET, queryParams);
        return orders.stream().findFirst();
    }

    @Override
    public List<DatasetMarketOrder> getDatasetOrders(String datasetAddress) {
        MPQueryParams queryParams = MPQueryParams.builder()
                .dataset(datasetAddress)
                .build();
        return getOrders(OrderType.DATASET, queryParams);
    }

    private <T> List<T> getOrders(OrderType type) {
        return getOrders(type, new MPQueryParams());
    }

    @SuppressWarnings("unchecked")
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
        @SuppressWarnings("rawtypes")
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

    @SuppressWarnings("rawtypes")
    private Orderbook convertJsonToOrderbook(
        OrderType type,
        String jsonPayload
    ) throws JsonMappingException, JsonProcessingException {
        switch (type) {
            case WORKERPOOL:
                return parse(jsonPayload, new TypeReference<Orderbook<WorkerpoolMarketOrder>>(){});
            case APP:
                return parse(jsonPayload, new TypeReference<Orderbook<AppMarketOrder>>(){});
            case DATASET:
                return parse(jsonPayload, new TypeReference<Orderbook<DatasetMarketOrder>>(){});
            case REQUEST:
                return parse(jsonPayload, new TypeReference<Orderbook<DatasetMarketOrder>>(){});
            default:
                throw new RuntimeException("Unknown order type");
        }
    }

    private <T> T parse(String jsonString, TypeReference<T> typeReference)
            throws JsonMappingException, JsonProcessingException {
            return new ObjectMapper().readValue(jsonString, typeReference);
    }
}
