package com.iexec.common.contract.generated;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tuples.generated.Tuple9;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * The git commit sha for this version is dc6ef27883744930d5b1c145ca7240f4ece1f217 (git tag v3.0.35)
 *
 * <p>Generated with web3j version 4.5.5.
 */
@SuppressWarnings("rawtypes")
public class IexecClerkABILegacy extends Contract {
    private static final String BINARY = "0x";

    public static final String FUNC_EIP712DOMAIN_SEPARATOR = "EIP712DOMAIN_SEPARATOR";

    public static final String FUNC_M_PRESIGNED = "m_presigned";

    public static final String FUNC_KITTY_RATIO = "KITTY_RATIO";

    public static final String FUNC_POOL_STAKE_RATIO = "POOL_STAKE_RATIO";

    public static final String FUNC_M_REQUESTDEALS = "m_requestdeals";

    public static final String FUNC_KITTY_MIN = "KITTY_MIN";

    public static final String FUNC_M_CONSUMED = "m_consumed";

    public static final String FUNC_VIEWREQUESTDEALS = "viewRequestDeals";

    public static final String FUNC_VIEWCONSUMED = "viewConsumed";

    public static final String FUNC_LOCKCONTRIBUTION = "lockContribution";

    public static final String FUNC_UNLOCKCONTRIBUTION = "unlockContribution";

    public static final String FUNC_UNLOCKANDREWARDFORCONTRIBUTION = "unlockAndRewardForContribution";

    public static final String FUNC_SEIZECONTRIBUTION = "seizeContribution";

    public static final String FUNC_REWARDFORSCHEDULING = "rewardForScheduling";

    public static final String FUNC_SUCCESSWORK = "successWork";

    public static final String FUNC_FAILEDWORK = "failedWork";

    public static final String FUNC_VIEWDEALABILEGACY_PT1 = "viewDealABILegacy_pt1";

    public static final String FUNC_VIEWDEALABILEGACY_PT2 = "viewDealABILegacy_pt2";

    public static final String FUNC_VIEWCONFIGABILEGACY = "viewConfigABILegacy";

    public static final String FUNC_VIEWACCOUNTABILEGACY = "viewAccountABILegacy";

    public static final Event ORDERSMATCHED_EVENT = new Event("OrdersMatched", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event CLOSEDAPPORDER_EVENT = new Event("ClosedAppOrder", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final Event CLOSEDDATASETORDER_EVENT = new Event("ClosedDatasetOrder", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final Event CLOSEDWORKERPOOLORDER_EVENT = new Event("ClosedWorkerpoolOrder", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final Event CLOSEDREQUESTORDER_EVENT = new Event("ClosedRequestOrder", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final Event SCHEDULERNOTICE_EVENT = new Event("SchedulerNotice", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Bytes32>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected IexecClerkABILegacy(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IexecClerkABILegacy(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IexecClerkABILegacy(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IexecClerkABILegacy(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<byte[]> EIP712DOMAIN_SEPARATOR() {
        final Function function = new Function(FUNC_EIP712DOMAIN_SEPARATOR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<Boolean> m_presigned(byte[] param0) {
        final Function function = new Function(FUNC_M_PRESIGNED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> KITTY_RATIO() {
        final Function function = new Function(FUNC_KITTY_RATIO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> POOL_STAKE_RATIO() {
        final Function function = new Function(FUNC_POOL_STAKE_RATIO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<byte[]> m_requestdeals(byte[] param0, BigInteger param1) {
        final Function function = new Function(FUNC_M_REQUESTDEALS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<BigInteger> KITTY_MIN() {
        final Function function = new Function(FUNC_KITTY_MIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> m_consumed(byte[] param0) {
        final Function function = new Function(FUNC_M_CONSUMED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public List<OrdersMatchedEventResponse> getOrdersMatchedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ORDERSMATCHED_EVENT, transactionReceipt);
        ArrayList<OrdersMatchedEventResponse> responses = new ArrayList<OrdersMatchedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OrdersMatchedEventResponse typedResponse = new OrdersMatchedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.dealid = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.appHash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.datasetHash = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.workerpoolHash = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.requestHash = (byte[]) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.volume = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OrdersMatchedEventResponse> ordersMatchedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, OrdersMatchedEventResponse>() {
            @Override
            public OrdersMatchedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ORDERSMATCHED_EVENT, log);
                OrdersMatchedEventResponse typedResponse = new OrdersMatchedEventResponse();
                typedResponse.log = log;
                typedResponse.dealid = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.appHash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.datasetHash = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.workerpoolHash = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.requestHash = (byte[]) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.volume = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OrdersMatchedEventResponse> ordersMatchedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ORDERSMATCHED_EVENT));
        return ordersMatchedEventFlowable(filter);
    }

    public List<ClosedAppOrderEventResponse> getClosedAppOrderEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CLOSEDAPPORDER_EVENT, transactionReceipt);
        ArrayList<ClosedAppOrderEventResponse> responses = new ArrayList<ClosedAppOrderEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ClosedAppOrderEventResponse typedResponse = new ClosedAppOrderEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.appHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ClosedAppOrderEventResponse> closedAppOrderEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ClosedAppOrderEventResponse>() {
            @Override
            public ClosedAppOrderEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CLOSEDAPPORDER_EVENT, log);
                ClosedAppOrderEventResponse typedResponse = new ClosedAppOrderEventResponse();
                typedResponse.log = log;
                typedResponse.appHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ClosedAppOrderEventResponse> closedAppOrderEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLOSEDAPPORDER_EVENT));
        return closedAppOrderEventFlowable(filter);
    }

    public List<ClosedDatasetOrderEventResponse> getClosedDatasetOrderEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CLOSEDDATASETORDER_EVENT, transactionReceipt);
        ArrayList<ClosedDatasetOrderEventResponse> responses = new ArrayList<ClosedDatasetOrderEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ClosedDatasetOrderEventResponse typedResponse = new ClosedDatasetOrderEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.datasetHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ClosedDatasetOrderEventResponse> closedDatasetOrderEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ClosedDatasetOrderEventResponse>() {
            @Override
            public ClosedDatasetOrderEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CLOSEDDATASETORDER_EVENT, log);
                ClosedDatasetOrderEventResponse typedResponse = new ClosedDatasetOrderEventResponse();
                typedResponse.log = log;
                typedResponse.datasetHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ClosedDatasetOrderEventResponse> closedDatasetOrderEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLOSEDDATASETORDER_EVENT));
        return closedDatasetOrderEventFlowable(filter);
    }

    public List<ClosedWorkerpoolOrderEventResponse> getClosedWorkerpoolOrderEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CLOSEDWORKERPOOLORDER_EVENT, transactionReceipt);
        ArrayList<ClosedWorkerpoolOrderEventResponse> responses = new ArrayList<ClosedWorkerpoolOrderEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ClosedWorkerpoolOrderEventResponse typedResponse = new ClosedWorkerpoolOrderEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.workerpoolHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ClosedWorkerpoolOrderEventResponse> closedWorkerpoolOrderEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ClosedWorkerpoolOrderEventResponse>() {
            @Override
            public ClosedWorkerpoolOrderEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CLOSEDWORKERPOOLORDER_EVENT, log);
                ClosedWorkerpoolOrderEventResponse typedResponse = new ClosedWorkerpoolOrderEventResponse();
                typedResponse.log = log;
                typedResponse.workerpoolHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ClosedWorkerpoolOrderEventResponse> closedWorkerpoolOrderEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLOSEDWORKERPOOLORDER_EVENT));
        return closedWorkerpoolOrderEventFlowable(filter);
    }

    public List<ClosedRequestOrderEventResponse> getClosedRequestOrderEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CLOSEDREQUESTORDER_EVENT, transactionReceipt);
        ArrayList<ClosedRequestOrderEventResponse> responses = new ArrayList<ClosedRequestOrderEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ClosedRequestOrderEventResponse typedResponse = new ClosedRequestOrderEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.requestHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ClosedRequestOrderEventResponse> closedRequestOrderEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ClosedRequestOrderEventResponse>() {
            @Override
            public ClosedRequestOrderEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CLOSEDREQUESTORDER_EVENT, log);
                ClosedRequestOrderEventResponse typedResponse = new ClosedRequestOrderEventResponse();
                typedResponse.log = log;
                typedResponse.requestHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ClosedRequestOrderEventResponse> closedRequestOrderEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLOSEDREQUESTORDER_EVENT));
        return closedRequestOrderEventFlowable(filter);
    }

    public List<SchedulerNoticeEventResponse> getSchedulerNoticeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SCHEDULERNOTICE_EVENT, transactionReceipt);
        ArrayList<SchedulerNoticeEventResponse> responses = new ArrayList<SchedulerNoticeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SchedulerNoticeEventResponse typedResponse = new SchedulerNoticeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.workerpool = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.dealid = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SchedulerNoticeEventResponse> schedulerNoticeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, SchedulerNoticeEventResponse>() {
            @Override
            public SchedulerNoticeEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SCHEDULERNOTICE_EVENT, log);
                SchedulerNoticeEventResponse typedResponse = new SchedulerNoticeEventResponse();
                typedResponse.log = log;
                typedResponse.workerpool = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.dealid = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SchedulerNoticeEventResponse> schedulerNoticeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SCHEDULERNOTICE_EVENT));
        return schedulerNoticeEventFlowable(filter);
    }

    public RemoteFunctionCall<List> viewRequestDeals(byte[] _id) {
        final Function function = new Function(FUNC_VIEWREQUESTDEALS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> viewConsumed(byte[] _id) {
        final Function function = new Function(FUNC_VIEWCONSUMED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> lockContribution(byte[] _dealid, String _worker) {
        final Function function = new Function(
                FUNC_LOCKCONTRIBUTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_dealid), 
                new org.web3j.abi.datatypes.Address(_worker)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> unlockContribution(byte[] _dealid, String _worker) {
        final Function function = new Function(
                FUNC_UNLOCKCONTRIBUTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_dealid), 
                new org.web3j.abi.datatypes.Address(_worker)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> unlockAndRewardForContribution(byte[] _dealid, String _worker, BigInteger _amount, byte[] _taskid) {
        final Function function = new Function(
                FUNC_UNLOCKANDREWARDFORCONTRIBUTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_dealid), 
                new org.web3j.abi.datatypes.Address(_worker), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount), 
                new org.web3j.abi.datatypes.generated.Bytes32(_taskid)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> seizeContribution(byte[] _dealid, String _worker, byte[] _taskid) {
        final Function function = new Function(
                FUNC_SEIZECONTRIBUTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_dealid), 
                new org.web3j.abi.datatypes.Address(_worker), 
                new org.web3j.abi.datatypes.generated.Bytes32(_taskid)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> rewardForScheduling(byte[] _dealid, BigInteger _amount, byte[] _taskid) {
        final Function function = new Function(
                FUNC_REWARDFORSCHEDULING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_dealid), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount), 
                new org.web3j.abi.datatypes.generated.Bytes32(_taskid)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> successWork(byte[] _dealid, byte[] _taskid) {
        final Function function = new Function(
                FUNC_SUCCESSWORK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_dealid), 
                new org.web3j.abi.datatypes.generated.Bytes32(_taskid)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> failedWork(byte[] _dealid, byte[] _taskid) {
        final Function function = new Function(
                FUNC_FAILEDWORK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_dealid), 
                new org.web3j.abi.datatypes.generated.Bytes32(_taskid)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple9<String, String, BigInteger, String, String, BigInteger, String, String, BigInteger>> viewDealABILegacy_pt1(byte[] _id) {
        final Function function = new Function(FUNC_VIEWDEALABILEGACY_PT1, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple9<String, String, BigInteger, String, String, BigInteger, String, String, BigInteger>>(function,
                new Callable<Tuple9<String, String, BigInteger, String, String, BigInteger, String, String, BigInteger>>() {
                    @Override
                    public Tuple9<String, String, BigInteger, String, String, BigInteger, String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple9<String, String, BigInteger, String, String, BigInteger, String, String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (String) results.get(6).getValue(), 
                                (String) results.get(7).getValue(), 
                                (BigInteger) results.get(8).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple6<BigInteger, byte[], String, String, String, String>> viewDealABILegacy_pt2(byte[] _id) {
        final Function function = new Function(FUNC_VIEWDEALABILEGACY_PT2, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple6<BigInteger, byte[], String, String, String, String>>(function,
                new Callable<Tuple6<BigInteger, byte[], String, String, String, String>>() {
                    @Override
                    public Tuple6<BigInteger, byte[], String, String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<BigInteger, byte[], String, String, String, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (String) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>> viewConfigABILegacy(byte[] _id) {
        final Function function = new Function(FUNC_VIEWCONFIGABILEGACY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple2<BigInteger, BigInteger>> viewAccountABILegacy(String _user) {
        final Function function = new Function(FUNC_VIEWACCOUNTABILEGACY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple2<BigInteger, BigInteger>>(function,
                new Callable<Tuple2<BigInteger, BigInteger>>() {
                    @Override
                    public Tuple2<BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    @Deprecated
    public static IexecClerkABILegacy load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IexecClerkABILegacy(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IexecClerkABILegacy load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IexecClerkABILegacy(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IexecClerkABILegacy load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IexecClerkABILegacy(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IexecClerkABILegacy load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IexecClerkABILegacy(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<IexecClerkABILegacy> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IexecClerkABILegacy.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IexecClerkABILegacy> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IexecClerkABILegacy.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<IexecClerkABILegacy> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IexecClerkABILegacy.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IexecClerkABILegacy> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IexecClerkABILegacy.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class OrdersMatchedEventResponse extends BaseEventResponse {
        public byte[] dealid;

        public byte[] appHash;

        public byte[] datasetHash;

        public byte[] workerpoolHash;

        public byte[] requestHash;

        public BigInteger volume;
    }

    public static class ClosedAppOrderEventResponse extends BaseEventResponse {
        public byte[] appHash;
    }

    public static class ClosedDatasetOrderEventResponse extends BaseEventResponse {
        public byte[] datasetHash;
    }

    public static class ClosedWorkerpoolOrderEventResponse extends BaseEventResponse {
        public byte[] workerpoolHash;
    }

    public static class ClosedRequestOrderEventResponse extends BaseEventResponse {
        public byte[] requestHash;
    }

    public static class SchedulerNoticeEventResponse extends BaseEventResponse {
        public String workerpool;

        public byte[] dealid;
    }
}
