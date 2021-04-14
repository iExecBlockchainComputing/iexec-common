/*
 * Copyright 2021 IEXEC BLOCKCHAIN TECH
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

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static com.iexec.common.contract.generated.DatasetRegistry.FUNC_CREATEDATASET;
import static com.iexec.common.contract.generated.IexecHubContract.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


class Web3jAbstractServiceTest {

    @Test
    void getGasLimitForFunction() {
        assertEquals(Web3jAbstractService
                        .getGasLimitForFunction(FUNC_INITIALIZE),
                BigInteger.valueOf(300000));
        assertEquals(Web3jAbstractService
                        .getGasLimitForFunction(FUNC_CONTRIBUTE),
                BigInteger.valueOf(500000));
        assertEquals(Web3jAbstractService
                        .getGasLimitForFunction(FUNC_REVEAL),
                BigInteger.valueOf(100000));
        assertEquals(Web3jAbstractService
                        .getGasLimitForFunction(FUNC_FINALIZE),
                BigInteger.valueOf(3000000));
        assertEquals(Web3jAbstractService
                        .getGasLimitForFunction(FUNC_REOPEN),
                BigInteger.valueOf(500000));
        assertEquals(Web3jAbstractService
                        .getGasLimitForFunction(FUNC_CREATEDATASET),
                BigInteger.valueOf(700000));
        assertEquals(Web3jAbstractService
                        .getGasLimitForFunction("randomfunction"),
                BigInteger.valueOf(500000));
    }
    
}