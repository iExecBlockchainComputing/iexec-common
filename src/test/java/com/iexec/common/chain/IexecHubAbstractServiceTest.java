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

import com.iexec.common.contract.generated.IexecHubContract;
import com.iexec.common.task.TaskDescription;
import com.iexec.common.utils.BytesUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.tuples.generated.Tuple12;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.iexec.common.chain.ChainUtils.generateChainTaskId;
import static org.mockito.Mockito.*;

class IexecHubAbstractServiceTest {

    public static final int RETRY_DELAY = 10; //in ms
    public static final int MAX_RETRY = 3;
    private final static String CHAIN_DEAL_ID = BytesUtils.toByte32HexString(0x1);
    private static String CHAIN_TASK_ID;

    @Mock
    private IexecHubAbstractService iexecHubAbstractService;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        CHAIN_TASK_ID = generateChainTaskId(CHAIN_DEAL_ID, 0);
    }

    @Test
    void shouldGetChainTask() throws Exception {
        whenViewTaskReturnTaskTuple(CHAIN_TASK_ID, CHAIN_DEAL_ID);

        when(iexecHubAbstractService.getChainTask(CHAIN_TASK_ID))
                .thenCallRealMethod();
        Optional<ChainTask> foundTask = iexecHubAbstractService.getChainTask(CHAIN_TASK_ID);

        Assertions.assertTrue(foundTask.isPresent());
        Assertions.assertEquals(CHAIN_TASK_ID, foundTask.get().getChainTaskId());
        Assertions.assertEquals(CHAIN_DEAL_ID, foundTask.get().getDealid());
    }

    @Test
    void shouldNotGetChainTaskSinceEmptyHexStringDealIdFieldProvesInConsistency() throws Exception {
        whenViewTaskReturnTaskTuple(CHAIN_TASK_ID, BytesUtils.EMPTY_HEX_STRING_32);

        when(iexecHubAbstractService.getChainTask(CHAIN_TASK_ID))
                .thenCallRealMethod();
        Optional<ChainTask> foundTask = iexecHubAbstractService.getChainTask(CHAIN_TASK_ID);

        Assertions.assertTrue(foundTask.isEmpty());
    }

    @Test
    void shouldNotGetChainTaskSinceEmptyDealIdFieldProvesInConsistency() throws Exception {
        whenViewTaskReturnTaskTuple(CHAIN_TASK_ID, "");

        when(iexecHubAbstractService.getChainTask(CHAIN_TASK_ID))
                .thenCallRealMethod();
        Optional<ChainTask> foundTask = iexecHubAbstractService.getChainTask(CHAIN_TASK_ID);

        Assertions.assertTrue(foundTask.isEmpty());
    }

    @Test
    void shouldNotGetChainTaskSinceWrongDealIdFieldProvesInConsistency() throws Exception {
        whenViewTaskReturnTaskTuple(CHAIN_TASK_ID, "0x123");

        when(iexecHubAbstractService.getChainTask(CHAIN_TASK_ID))
                .thenCallRealMethod();
        Optional<ChainTask> foundTask = iexecHubAbstractService.getChainTask(CHAIN_TASK_ID);

        Assertions.assertTrue(foundTask.isEmpty());
    }

    @Test
    void repeatGetChainTaskWithSuccess() {
        ChainTask task = getMockTask();

        when(iexecHubAbstractService.getChainTask(CHAIN_TASK_ID))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(task));

        when(iexecHubAbstractService.repeatGetChainTask(CHAIN_TASK_ID, RETRY_DELAY, MAX_RETRY))
                .thenCallRealMethod();
        Optional<ChainTask> foundTask =
                iexecHubAbstractService.repeatGetChainTask(CHAIN_TASK_ID, RETRY_DELAY, MAX_RETRY);

        Assertions.assertTrue(foundTask.isPresent());
        Assertions.assertEquals(task, foundTask.get());
        verify(iexecHubAbstractService, times(3))
                .getChainTask(CHAIN_TASK_ID);
    }

    @Test
    void repeatGetChainTaskWithFailure() {
        when(iexecHubAbstractService.getChainTask(CHAIN_TASK_ID))
                .thenReturn(Optional.empty());

        when(iexecHubAbstractService.repeatGetChainTask(CHAIN_TASK_ID, RETRY_DELAY, MAX_RETRY))
                .thenCallRealMethod();
        Optional<ChainTask> foundTask =
                iexecHubAbstractService.repeatGetChainTask(CHAIN_TASK_ID, RETRY_DELAY, MAX_RETRY);

        Assertions.assertTrue(foundTask.isEmpty());
        verify(iexecHubAbstractService, times(1 + MAX_RETRY))
                .getChainTask(CHAIN_TASK_ID);
    }

    @Test
    void repeatGetChainDealWithSuccess() {
        ChainDeal chainDeal = getMockDeal();

        when(iexecHubAbstractService.getChainDeal(CHAIN_DEAL_ID))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(chainDeal));

        when(iexecHubAbstractService.repeatGetChainDeal(CHAIN_DEAL_ID, RETRY_DELAY, MAX_RETRY))
                .thenCallRealMethod();
        Optional<ChainDeal> foundDeal =
                iexecHubAbstractService.repeatGetChainDeal(CHAIN_DEAL_ID, RETRY_DELAY, MAX_RETRY);

        Assertions.assertTrue(foundDeal.isPresent());
        Assertions.assertEquals(chainDeal, foundDeal.get());
        verify(iexecHubAbstractService, times(3))
                .getChainDeal(CHAIN_DEAL_ID);
    }

    @Test
    void repeatGetChainDealWithFailure() {
        when(iexecHubAbstractService.getChainDeal(CHAIN_DEAL_ID))
                .thenReturn(Optional.empty());

        when(iexecHubAbstractService.repeatGetChainDeal(CHAIN_DEAL_ID, RETRY_DELAY, MAX_RETRY))
                .thenCallRealMethod();
        Optional<ChainDeal> foundDeal =
                iexecHubAbstractService.repeatGetChainDeal(CHAIN_DEAL_ID, RETRY_DELAY, MAX_RETRY);

        Assertions.assertTrue(foundDeal.isEmpty());
        verify(iexecHubAbstractService, times(1 + MAX_RETRY))
                .getChainDeal(CHAIN_DEAL_ID);
    }

    @Test
    void repeatGetTaskDescriptionFromChainWithSuccess() {
        ChainTask task = getMockTask();
        ChainDeal deal = getMockDeal();

        when(iexecHubAbstractService.repeatGetChainTask(CHAIN_TASK_ID, RETRY_DELAY, MAX_RETRY))
                .thenReturn(Optional.of(task));
        when(iexecHubAbstractService.repeatGetChainDeal(CHAIN_DEAL_ID, RETRY_DELAY, MAX_RETRY))
                .thenReturn(Optional.of(deal));

        when(iexecHubAbstractService.repeatGetTaskDescriptionFromChain(CHAIN_TASK_ID, RETRY_DELAY, MAX_RETRY))
                .thenCallRealMethod();
        Optional<TaskDescription> taskDescription =
                iexecHubAbstractService.repeatGetTaskDescriptionFromChain(CHAIN_TASK_ID, RETRY_DELAY, MAX_RETRY);

        Assertions.assertTrue(taskDescription.isPresent());
        Assertions.assertEquals(task.getChainTaskId(), taskDescription.get().getChainTaskId());
        Assertions.assertEquals(deal.getBotSize().intValue(), taskDescription.get().getBotSize());
    }

    @Test
    void repeatGetTaskDescriptionFromChainWithTaskFailure() {
        ChainDeal deal = getMockDeal();

        when(iexecHubAbstractService.repeatGetChainTask(CHAIN_TASK_ID, RETRY_DELAY, MAX_RETRY))
                .thenReturn(Optional.empty());
        when(iexecHubAbstractService.repeatGetChainDeal(CHAIN_DEAL_ID, RETRY_DELAY, MAX_RETRY))
                .thenReturn(Optional.of(deal));

        when(iexecHubAbstractService.repeatGetTaskDescriptionFromChain(CHAIN_TASK_ID, RETRY_DELAY, MAX_RETRY))
                .thenCallRealMethod();
        Optional<TaskDescription> taskDescription =
                iexecHubAbstractService.repeatGetTaskDescriptionFromChain(CHAIN_TASK_ID, RETRY_DELAY, MAX_RETRY);

        Assertions.assertTrue(taskDescription.isEmpty());
    }

    @Test
    void repeatGetTaskDescriptionFromChainWithDealFailure() {
        ChainTask task = getMockTask();

        when(iexecHubAbstractService.repeatGetChainTask(CHAIN_TASK_ID, RETRY_DELAY, MAX_RETRY))
                .thenReturn(Optional.of(task));
        when(iexecHubAbstractService.repeatGetChainDeal(CHAIN_DEAL_ID, RETRY_DELAY, MAX_RETRY))
                .thenReturn(Optional.empty());

        when(iexecHubAbstractService.repeatGetTaskDescriptionFromChain(CHAIN_TASK_ID, RETRY_DELAY, MAX_RETRY))
                .thenCallRealMethod();
        Optional<TaskDescription> taskDescription =
                iexecHubAbstractService.repeatGetTaskDescriptionFromChain(CHAIN_TASK_ID, RETRY_DELAY, MAX_RETRY);

        Assertions.assertTrue(taskDescription.isEmpty());
    }

    private void whenViewTaskReturnTaskTuple(String chainTaskId, String chainDealId) throws Exception {
        IexecHubContract iexecHubContract = mock(IexecHubContract.class);
        when(iexecHubAbstractService.getHubContract())
                .thenReturn(iexecHubContract);
        RemoteFunctionCall getTaskRemoteFunctionCall = mock(RemoteFunctionCall.class);
        when(iexecHubContract.viewTaskABILegacy(BytesUtils.stringToBytes(chainTaskId)))
                .thenReturn(getTaskRemoteFunctionCall);
        Tuple12 taskTuple = getMockTaskTuple(chainDealId); // requires mock-maker-inline
        when(getTaskRemoteFunctionCall.send()).thenReturn(taskTuple);
    }

    private ChainTask getMockTask() {
        return ChainTask.builder()
                .dealid(CHAIN_DEAL_ID)
                .idx(0)
                .build();
    }

    private Tuple12<BigInteger, byte[], BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, byte[], BigInteger, BigInteger, List<String>, byte[]>
    getMockTaskTuple(String dealId) {
        return new Tuple12<>(BigInteger.ONE, //active
                BytesUtils.stringToBytes(dealId), // deal ID
                BigInteger.ZERO, //task index
                BigInteger.ONE,
                BigInteger.ONE,
                BigInteger.ONE,
                BigInteger.ONE,
                BytesUtils.stringToBytes("0x1"),
                BigInteger.ONE,
                BigInteger.ONE,
                Arrays.asList("1", "2"),
                BytesUtils.stringToBytes("0x1"));
    }

    private ChainDeal getMockDeal() {
        return ChainDeal.builder()
                .chainApp(ChainApp.builder().uri("").build())
                .params(DealParams.builder().build())
                .chainCategory(ChainCategory.builder().build())
                .botSize(BigInteger.ONE)
                .botFirst(BigInteger.ONE)
                .build();
    }
}