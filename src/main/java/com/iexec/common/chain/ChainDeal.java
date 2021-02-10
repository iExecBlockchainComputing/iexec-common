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

package com.iexec.common.chain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ChainDeal {

    String chainDealId;
    // deal_pt1
    ChainApp chainApp;
    String dappOwner;
    BigInteger dappPrice;
    ChainDataset chainDataset;
    String dataPointer;
    String dataOwner;
    BigInteger dataPrice;
    String poolPointer;
    String poolOwner;
    BigInteger poolPrice;

    // deal_pt2
    BigInteger trust;
    String tag;
    String requester;
    String beneficiary;
    String callback;
    DealParams params;

    // config
    ChainCategory chainCategory;
    BigInteger startTime;
    BigInteger botFirst;
    BigInteger botSize;
    BigInteger workerStake;
    BigInteger schedulerRewardRatio;

    public static DealParams stringToDealParams(String params) {
        try {
            DealParams dealParams = new ObjectMapper().readValue(params, DealParams.class);
            if(dealParams.getIexecInputFiles() == null) {
                dealParams.setIexecInputFiles(new ArrayList<>());
            }
            return dealParams;
        } catch (IOException e) {
            //the requester want to execute one task with the whole string
            return DealParams.builder()
                    .iexecArgs(params)
                    .iexecInputFiles(new ArrayList<>())
                    .iexecDeveloperLoggerEnabled(false)
                    .build();
        }
    }
}
