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

package com.iexec.common.sdk.cli;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iexec.common.sdk.broker.BrokerOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class BrokerOrderCliInput extends Pair<Integer, BrokerOrder> {

    /*
     * A pair is required for having a dynamic key (chainId field).
     * e.g: {"133":{"requestorder":.., "apporder":.., "workerpoolorder":..}}
     * */

    @JsonIgnore
    private Integer chainId;

    @JsonIgnore
    private BrokerOrder brokerOrder;

    @Override
    public Integer getLeft() {
        return chainId;
    }

    @Override
    public BrokerOrder getRight() {
        return brokerOrder;
    }

    @Override
    public BrokerOrder setValue(BrokerOrder brokerOrder) {
        return brokerOrder;
    }

}
