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

import com.iexec.common.sdk.order.markeplace.AppMarketOrder;
import com.iexec.common.sdk.order.markeplace.DatasetMarketOrder;
import com.iexec.common.sdk.order.markeplace.WorkerpoolMarketOrder;

import java.util.List;
import java.util.Optional;

public interface Marketplace {

    List<WorkerpoolMarketOrder> getWorkerpoolOrders();

    Optional<WorkerpoolMarketOrder> getOneWorkerpoolOrder(String workerpoolAddress);

    List<WorkerpoolMarketOrder> getWorkerpoolOrders(String workerpoolAddress);

    List<WorkerpoolMarketOrder> getTaggedWorkerpoolOrders(String tag);

    Optional<WorkerpoolMarketOrder> getOneTaggedWorkerpoolOrder(String workerpoolAddress, String tag);

    List<WorkerpoolMarketOrder> getTaggedWorkerpoolOrders(String workerpoolAddress, String tag);

    Optional<AppMarketOrder> getOneAppOrder(String appAddress);

    Optional<AppMarketOrder> getOneRequesterRestrictedAppOrder(String appAddress, String requesterAddress);

    List<AppMarketOrder> getAppOrders(String appAddress);

    Optional<DatasetMarketOrder> getOneDatasetOrder(String datasetAddress);

    Optional<DatasetMarketOrder> getOneRequesterRestrictedDatasetOrder(String datasetAddress, String requesterAddress);

    List<DatasetMarketOrder> getDatasetOrders(String datasetAddress);
}
