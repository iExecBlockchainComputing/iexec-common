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

package com.iexec.common.utils;

import com.iexec.common.chain.IexecHubAbstractService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class RetryerTest {

    public static final String CONTRACT_ADDRESS = "0xcontract";
    public static final String OWNER = "0xowner";
    public static final int RETRY_DELAY = 10;
    public static final int MAX_RETRY = 3;

    @Mock
    private IexecHubAbstractService iexecHubAbstractService;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void repeatCallWithSuccess() {
        when(iexecHubAbstractService.getOwner(CONTRACT_ADDRESS))
                .thenReturn("")
                .thenReturn("")
                .thenReturn(OWNER);

        String foundOwner = new Retryer<String>()
                .repeatCall(() -> iexecHubAbstractService.getOwner(CONTRACT_ADDRESS),
                        StringUtils::isEmpty,
                        RETRY_DELAY, MAX_RETRY,
                        String.format("getOwner(owner) [owner:%s]", CONTRACT_ADDRESS));

        Assertions.assertFalse(foundOwner.isEmpty());
        Assertions.assertEquals(OWNER, foundOwner);
        verify(iexecHubAbstractService, times(3))
                .getOwner(CONTRACT_ADDRESS);
    }

    @Test
    void repeatCallWithFailure() {
        when(iexecHubAbstractService.getOwner(CONTRACT_ADDRESS))
                .thenReturn("");

        String foundOwner = new Retryer<String>()
                .repeatCall(() -> iexecHubAbstractService.getOwner(CONTRACT_ADDRESS),
                        StringUtils::isEmpty,
                        RETRY_DELAY, MAX_RETRY,
                        String.format("getOwner(owner) [owner:%s]", CONTRACT_ADDRESS));

        Assertions.assertTrue(foundOwner.isEmpty());
        verify(iexecHubAbstractService, times(1 + MAX_RETRY))
                .getOwner(CONTRACT_ADDRESS);
    }

}