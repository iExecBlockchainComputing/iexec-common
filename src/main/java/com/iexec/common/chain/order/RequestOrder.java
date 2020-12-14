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

package com.iexec.common.chain.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigInteger;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestOrder implements Order {

    // specific
    @JsonProperty("app")
    private String app;
    @JsonProperty("appmaxprice")
    private BigInteger appmaxprice;
    @JsonProperty("dataset")
    private String dataset;
    @JsonProperty("appmaxprice")
    private BigInteger datasetmaxprice;
    @JsonProperty("workerpool")
    private String workerpool;
    @JsonProperty("workerpoolmaxprice")
    private BigInteger workerpoolmaxprice;
    //common
    @JsonProperty("requester")
    private String address;
    @JsonProperty("volume")
    private BigInteger volume;
    private String tag;
    // specific
    @JsonProperty("category")
    private BigInteger category;
    @JsonProperty("trust")
    private BigInteger trust;
    @JsonProperty("beneficiary")
    private String beneficiary;
    @JsonProperty("callback")
    private String callback;
    @JsonProperty("params")
    private String params;
    // common
    private String salt;

    @Override
    public BigInteger getPrice() {
        return appmaxprice.add(datasetmaxprice.add(workerpoolmaxprice));
    }

    @Override
    public void setPrice(BigInteger price) {
        //TODO delete
    }
}