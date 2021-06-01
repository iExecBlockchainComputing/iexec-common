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
import org.junit.jupiter.api.Test;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tuples.generated.Tuple9;

import java.math.BigInteger;

import static com.iexec.common.chain.ChainDeal.stringToDealParams;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class ChainDealTest {

    public static final Tuple9<String, String, BigInteger, String, String, BigInteger, String, String, BigInteger>
            DEAL_PT_1 =
            new Tuple9<>("1",
                    "2",
                    BigInteger.valueOf(3),
                    "4",
                    "5",
                    BigInteger.valueOf(6),
                    "7",
                    "8",
                    BigInteger.valueOf(9));
    public static final Tuple6<BigInteger, byte[], String, String, String, String>
            DEAL_PT_2 =
            new Tuple6<>(BigInteger.valueOf(1),
                    new byte[2],
                    "3",
                    "4",
                    "5",
                    "6");
    public static final Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>
            CONFIG =
            new Tuple6<>(BigInteger.valueOf(1),
                    BigInteger.valueOf(2),
                    BigInteger.valueOf(3),
                    BigInteger.valueOf(4),
                    BigInteger.valueOf(5),
                    BigInteger.valueOf(6));
    public static final String CHAIN_DEAL_ID = "chainDeal";
    private static final String ARGS = "argument for the worker";
    private static final String FILE1 = "http://test.com/image1.png";
    private static final String FILE2 = "http://test.com/image2.png";
    private static final String FILE3 = "http://test.com/image3.png";

    @Test
    void shouldReadArgsWithoutJson() {
        DealParams params = stringToDealParams(ARGS);
        assertEquals(ARGS, params.getIexecArgs());
        assertEquals(0, params.getIexecInputFiles().size());
    }

    @Test
    void shouldReadArgsInJson() {
        DealParams params = stringToDealParams("{\"iexec_args\":\"" + ARGS + "\"}");
        assertEquals(ARGS, params.getIexecArgs());
        assertEquals(0, params.getIexecInputFiles().size());
    }

    @Test
    void shouldReadArgsInJsonAndEmptyInputFiles() {
        DealParams params = stringToDealParams("{\"iexec_args\":\"" + ARGS + "\"," +
                "\"iexec_input_files\":[]}");
        assertEquals(ARGS, params.getIexecArgs());
        assertEquals(0, params.getIexecInputFiles().size());
    }


    @Test
    void shouldReadArgsAndOneInputFile() {
        DealParams params = stringToDealParams("{\"iexec_args\":\"" + ARGS + "\"," +
                "\"iexec_input_files\":[\"" + FILE1 + "\"]}");
        assertEquals(ARGS, params.getIexecArgs());
        assertEquals(1, params.getIexecInputFiles().size());
        assertEquals(FILE1, params.getIexecInputFiles().get(0));
    }

    @Test
    void shouldReadArgsAndMultipleFiles() {
        DealParams params = stringToDealParams("{\"iexec_args\":\"" + ARGS + "\"," +
                "\"iexec_input_files\":[\"" + FILE1 + "\",\"" + FILE2 + "\",\"" + FILE3 + "\"]}");
        assertEquals(ARGS, params.getIexecArgs());
        assertEquals(3, params.getIexecInputFiles().size());
        assertEquals(FILE1, params.getIexecInputFiles().get(0));
        assertEquals(FILE2, params.getIexecInputFiles().get(1));
        assertEquals(FILE3, params.getIexecInputFiles().get(2));
    }

    @Test
    void shouldReadNotCorrectJsonFile() {
        String wrongJson = "{\"wrong_field1\":\"wrong arg value\"," +
                "\"iexec_input_files\":[\"" + FILE1 + "\"]}";
        DealParams params = stringToDealParams(wrongJson);
        assertEquals(wrongJson, params.getIexecArgs());
        assertEquals(0, params.getIexecInputFiles().size());
    }

    @Test
    void shouldConvertToChainDeal() {
        Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> config =
                CONFIG;
        ChainApp app = mock(ChainApp.class);
        ChainCategory category = mock(ChainCategory.class);
        ChainDataset dataset = mock(ChainDataset.class);

        ChainDeal chainDeal = ChainDeal.parts2ChainDeal(CHAIN_DEAL_ID, DEAL_PT_1, DEAL_PT_2, config, app, category, dataset);

        assertEquals(chainDeal,
                ChainDeal.builder()
                        .chainDealId(CHAIN_DEAL_ID)
                        .chainApp(app)
                        .dappOwner(DEAL_PT_1.component2())
                        .dappPrice(DEAL_PT_1.component3())
                        .chainDataset(dataset)
                        .dataOwner(DEAL_PT_1.component5())
                        .dataPrice(DEAL_PT_1.component6())
                        .poolPointer(DEAL_PT_1.component7())
                        .poolOwner(DEAL_PT_1.component8())
                        .poolPrice(DEAL_PT_1.component9())
                        .trust(DEAL_PT_2.component1())
                        .tag(BytesUtils.bytesToString(DEAL_PT_2.component2()))
                        .requester(DEAL_PT_2.component3())
                        .beneficiary(DEAL_PT_2.component4())
                        .callback(DEAL_PT_2.component5())
                        .params(stringToDealParams(DEAL_PT_2.component6()))
                        .chainCategory(category)
                        .startTime(config.component2())
                        .botFirst(config.component3())
                        .botSize(config.component4())
                        .workerStake(config.component5())
                        .schedulerRewardRatio(config.component6())
                        .build()
        );
    }

    @Test
    void shouldGetEmptyChainDealSinceNoDealPt1() {
        ChainApp app = mock(ChainApp.class);
        ChainCategory category = mock(ChainCategory.class);
        ChainDataset dataset = mock(ChainDataset.class);

        ChainDeal chainDeal = ChainDeal.parts2ChainDeal(CHAIN_DEAL_ID,
                null,
                DEAL_PT_2,
                CONFIG,
                app,
                category,
                dataset);

        assertEquals(chainDeal, new ChainDeal());
    }

    @Test
    void shouldGetEmptyChainDealSinceNoDealPt2() {
        ChainApp app = mock(ChainApp.class);
        ChainCategory category = mock(ChainCategory.class);
        ChainDataset dataset = mock(ChainDataset.class);

        ChainDeal chainDeal = ChainDeal.parts2ChainDeal(CHAIN_DEAL_ID,
                DEAL_PT_1,
                null,
                CONFIG,
                app,
                category,
                dataset);

        assertEquals(chainDeal, new ChainDeal());
    }

    @Test
    void shouldGetEmptyChainDealSinceNoConfig() {
        ChainApp app = mock(ChainApp.class);
        ChainCategory category = mock(ChainCategory.class);
        ChainDataset dataset = mock(ChainDataset.class);

        ChainDeal chainDeal = ChainDeal.parts2ChainDeal(CHAIN_DEAL_ID,
                DEAL_PT_1,
                DEAL_PT_2,
                null,
                app,
                category,
                dataset);

        assertEquals(chainDeal, new ChainDeal());
    }

    @Test
    void shouldGetEmptyChainDealSinceNoApp() {
        ChainCategory category = mock(ChainCategory.class);
        ChainDataset dataset = mock(ChainDataset.class);

        ChainDeal chainDeal = ChainDeal.parts2ChainDeal(CHAIN_DEAL_ID,
                DEAL_PT_1,
                DEAL_PT_2,
                CONFIG,
                null,
                category,
                dataset);

        assertEquals(chainDeal, new ChainDeal());
    }

    @Test
    void shouldGetEmptyChainDealSinceNoCategory() {
        ChainApp app = mock(ChainApp.class);
        ChainDataset dataset = mock(ChainDataset.class);

        ChainDeal chainDeal = ChainDeal.parts2ChainDeal(CHAIN_DEAL_ID,
                DEAL_PT_1,
                DEAL_PT_2,
                CONFIG,
                app,
                null,
                dataset);

        assertEquals(chainDeal, new ChainDeal());
    }
}