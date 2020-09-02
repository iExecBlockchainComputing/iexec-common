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

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class ChainDealTest {

    private static final String ARGS = "argument for the worker";
    private static final String FILE1 = "http://test.com/image1.png";
    private static final String FILE2 = "http://test.com/image2.png";
    private static final String FILE3 = "http://test.com/image3.png";

    @Test
    public void shouldReadArgsWithoutJson() {
        DealParams params = ChainDeal.stringToDealParams(ARGS);
        assertEquals(ARGS, params.getIexecArgs());
        assertEquals(0, params.getIexecInputFiles().size());
    }

    @Test
    public void shouldReadArgsInJson() {
        DealParams params = ChainDeal.stringToDealParams("{\"iexec_args\":\"" + ARGS + "\"}");
        assertEquals(ARGS, params.getIexecArgs());
        assertEquals(0, params.getIexecInputFiles().size());
    }

    @Test
    public void shouldReadArgsInJsonAndEmptyInputFiles() {
        DealParams params = ChainDeal.stringToDealParams("{\"iexec_args\":\"" + ARGS + "\"," +
                "\"iexec_input_files\":[]}");
        assertEquals(ARGS, params.getIexecArgs());
        assertEquals(0, params.getIexecInputFiles().size());
    }


    @Test
    public void shouldReadArgsAndOneInputFile() {
        DealParams params = ChainDeal.stringToDealParams("{\"iexec_args\":\"" + ARGS + "\"," +
                "\"iexec_input_files\":[\"" + FILE1 + "\"]}");
        assertEquals(ARGS, params.getIexecArgs());
        assertEquals(1, params.getIexecInputFiles().size());
        assertEquals(FILE1, params.getIexecInputFiles().get(0));
    }

    @Test
    public void shouldReadArgsAndMultipleFiles() {
        DealParams params = ChainDeal.stringToDealParams("{\"iexec_args\":\"" + ARGS + "\"," +
                "\"iexec_input_files\":[\"" + FILE1 + "\",\"" + FILE2 + "\",\"" + FILE3+ "\"]}");
        assertEquals(ARGS, params.getIexecArgs());
        assertEquals(3, params.getIexecInputFiles().size());
        assertEquals(FILE1, params.getIexecInputFiles().get(0));
        assertEquals(FILE2, params.getIexecInputFiles().get(1));
        assertEquals(FILE3, params.getIexecInputFiles().get(2));
    }

    @Test
    public void shouldReadNotCorrectJsonFile() {
        String wrongJson = "{\"wrong_field1\":\"wrong arg value\"," +
                "\"iexec_input_files\":[\"" + FILE1 + "\"]}";
        DealParams params = ChainDeal.stringToDealParams(wrongJson);
        assertEquals(wrongJson, params.getIexecArgs());
        assertEquals(0, params.getIexecInputFiles().size());
    }
}