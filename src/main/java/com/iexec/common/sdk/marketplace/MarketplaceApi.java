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

import com.iexec.common.sdk.order.OrderType;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

interface MarketplaceApi {

    /**
     * Get orders from the marketplace.
     * 
     * @param orderType
     * @param queryParameters query parameter to be populated
     * when constructing the request. Can be empty but not null.
     * @return json string of orderbook
     */
    @RequestLine("GET /{orderType}orders?chainId={chainId}")
    String getOrders(
            @Param("orderType") OrderType orderType,
            // can contain requester=0xrequester or other
            @QueryMap MPQueryParams queryParameters
    );
}
