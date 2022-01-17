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

import com.iexec.common.utils.BytesUtils;
import lombok.*;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tuples.generated.Tuple9;

import java.math.BigInteger;

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

    public boolean containsDataset() {
        return getChainDataset() != null &&
                getChainDataset().getChainDatasetId() != null &&
                !getChainDataset().getChainDatasetId().equals(BytesUtils.EMPTY_ADDRESS);
    }

    public static ChainDeal parts2ChainDeal(String chainDealId, Tuple9<String, String, BigInteger, String, String, BigInteger, String, String, BigInteger> dealPt1, Tuple6<BigInteger, byte[], String, String, String, String> dealPt2, Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> config, ChainApp app, ChainCategory category, ChainDataset dataset) {
        if (dealPt1 == null || dealPt2 == null || config == null || app == null || category == null) {
            return new ChainDeal();
        }
        return ChainDeal.builder()
                .chainDealId(chainDealId)
                .chainApp(app)
                .dappOwner(dealPt1.component2())
                .dappPrice(dealPt1.component3())
                .chainDataset(dataset)
                .dataOwner(dealPt1.component5())
                .dataPrice(dealPt1.component6())
                .poolPointer(dealPt1.component7())
                .poolOwner(dealPt1.component8())
                .poolPrice(dealPt1.component9())
                .trust(dealPt2.component1())
                .tag(BytesUtils.bytesToString(dealPt2.component2()))
                .requester(dealPt2.component3())
                .beneficiary(dealPt2.component4())
                .callback(dealPt2.component5())
                .params(DealParams.createFromString(dealPt2.component6()))
                .chainCategory(category)
                .startTime(config.component2())
                .botFirst(config.component3())
                .botSize(config.component4())
                .workerStake(config.component5())
                .schedulerRewardRatio(config.component6())
                .build();
    }
}
