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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DatasetOrder implements Order {

    // common
    @JsonProperty("dataset")
    private String address;
    @JsonProperty("datasetprice")
    private BigInteger price;
    @JsonProperty("volume")
    private BigInteger volume;
    private String tag;
    // specific
    @JsonProperty("apprestrict")
    private String appRestrict;
    @JsonProperty("workerpoolrestrict")
    private String workerpoolRestrict;
    @JsonProperty("requesterrestrict")
    private String requesterRestrict;
    // common
    private String salt;

}