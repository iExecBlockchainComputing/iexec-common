package com.iexec.common.contract.generated;

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
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tuples.generated.Tuple9;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.6.0.
 *
 *  PoCo commit - https://github.com/iExecBlockchainComputing/PoCo-dev/commit/b7015ecb9e3b623cfc089b57dfc13e2bd2928bff
 *  Oct 25th
 *
 */
public class IexecClerkABILegacy extends Contract {
    private static final String BINARY = "0x";

    public static final String FUNC_EIP712DOMAIN_SEPARATOR = "EIP712DOMAIN_SEPARATOR";

    public static final String FUNC_M_PRESIGNED = "m_presigned";

    public static final String FUNC_KITTY_RATIO = "KITTY_RATIO";

    public static final String FUNC_POOL_STAKE_RATIO = "POOL_STAKE_RATIO";

    public static final String FUNC_M_USERDEALS = "m_userdeals";

    public static final String FUNC_KITTY_MIN = "KITTY_MIN";

    public static final String FUNC_M_CONSUMED = "m_consumed";

    public static final String FUNC_VIEWUSERDEALS = "viewUserDeals";

    public static final String FUNC_VIEWCONSUMED = "viewConsumed";

    public static final String FUNC_CHECKRESTRICTION = "checkRestriction";

    public static final String FUNC_LOCKSUBSCRIPTION = "lockSubscription";

    public static final String FUNC_UNLOCKSUBSCRIPTION = "unlockSubscription";

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

    public static final Event CLOSEDDAPPORDER_EVENT = new Event("ClosedDappOrder", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final Event CLOSEDDATAORDER_EVENT = new Event("ClosedDataOrder", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final Event CLOSEDPOOLORDER_EVENT = new Event("ClosedPoolOrder", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final Event CLOSEDUSERORDER_EVENT = new Event("ClosedUserOrder", 
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

    public RemoteCall<byte[]> EIP712DOMAIN_SEPARATOR() {
        final Function function = new Function(FUNC_EIP712DOMAIN_SEPARATOR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<Boolean> m_presigned(byte[] param0) {
        final Function function = new Function(FUNC_M_PRESIGNED, 
                Arrays.<Type>asList(new Bytes32(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<BigInteger> KITTY_RATIO() {
        final Function function = new Function(FUNC_KITTY_RATIO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> POOL_STAKE_RATIO() {
        final Function function = new Function(FUNC_POOL_STAKE_RATIO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<byte[]> m_userdeals(byte[] param0, BigInteger param1) {
        final Function function = new Function(FUNC_M_USERDEALS, 
                Arrays.<Type>asList(new Bytes32(param0),
                new Uint256(param1)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<BigInteger> KITTY_MIN() {
        final Function function = new Function(FUNC_KITTY_MIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> m_consumed(byte[] param0) {
        final Function function = new Function(FUNC_M_CONSUMED, 
                Arrays.<Type>asList(new Bytes32(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public List<OrdersMatchedEventResponse> getOrdersMatchedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ORDERSMATCHED_EVENT, transactionReceipt);
        ArrayList<OrdersMatchedEventResponse> responses = new ArrayList<OrdersMatchedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            OrdersMatchedEventResponse typedResponse = new OrdersMatchedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.dealid = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.dappHash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.dataHash = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.poolHash = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.userHash = (byte[]) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.volume = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<OrdersMatchedEventResponse> ordersMatchedEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, OrdersMatchedEventResponse>() {
            @Override
            public OrdersMatchedEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(ORDERSMATCHED_EVENT, log);
                OrdersMatchedEventResponse typedResponse = new OrdersMatchedEventResponse();
                typedResponse.log = log;
                typedResponse.dealid = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.dappHash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.dataHash = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.poolHash = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.userHash = (byte[]) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.volume = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<OrdersMatchedEventResponse> ordersMatchedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ORDERSMATCHED_EVENT));
        return ordersMatchedEventObservable(filter);
    }

    public List<ClosedDappOrderEventResponse> getClosedDappOrderEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CLOSEDDAPPORDER_EVENT, transactionReceipt);
        ArrayList<ClosedDappOrderEventResponse> responses = new ArrayList<ClosedDappOrderEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ClosedDappOrderEventResponse typedResponse = new ClosedDappOrderEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.dappHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ClosedDappOrderEventResponse> closedDappOrderEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ClosedDappOrderEventResponse>() {
            @Override
            public ClosedDappOrderEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CLOSEDDAPPORDER_EVENT, log);
                ClosedDappOrderEventResponse typedResponse = new ClosedDappOrderEventResponse();
                typedResponse.log = log;
                typedResponse.dappHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ClosedDappOrderEventResponse> closedDappOrderEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLOSEDDAPPORDER_EVENT));
        return closedDappOrderEventObservable(filter);
    }

    public List<ClosedDataOrderEventResponse> getClosedDataOrderEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CLOSEDDATAORDER_EVENT, transactionReceipt);
        ArrayList<ClosedDataOrderEventResponse> responses = new ArrayList<ClosedDataOrderEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ClosedDataOrderEventResponse typedResponse = new ClosedDataOrderEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.dataHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ClosedDataOrderEventResponse> closedDataOrderEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ClosedDataOrderEventResponse>() {
            @Override
            public ClosedDataOrderEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CLOSEDDATAORDER_EVENT, log);
                ClosedDataOrderEventResponse typedResponse = new ClosedDataOrderEventResponse();
                typedResponse.log = log;
                typedResponse.dataHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ClosedDataOrderEventResponse> closedDataOrderEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLOSEDDATAORDER_EVENT));
        return closedDataOrderEventObservable(filter);
    }

    public List<ClosedPoolOrderEventResponse> getClosedPoolOrderEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CLOSEDPOOLORDER_EVENT, transactionReceipt);
        ArrayList<ClosedPoolOrderEventResponse> responses = new ArrayList<ClosedPoolOrderEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ClosedPoolOrderEventResponse typedResponse = new ClosedPoolOrderEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.poolHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ClosedPoolOrderEventResponse> closedPoolOrderEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ClosedPoolOrderEventResponse>() {
            @Override
            public ClosedPoolOrderEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CLOSEDPOOLORDER_EVENT, log);
                ClosedPoolOrderEventResponse typedResponse = new ClosedPoolOrderEventResponse();
                typedResponse.log = log;
                typedResponse.poolHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ClosedPoolOrderEventResponse> closedPoolOrderEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLOSEDPOOLORDER_EVENT));
        return closedPoolOrderEventObservable(filter);
    }

    public List<ClosedUserOrderEventResponse> getClosedUserOrderEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CLOSEDUSERORDER_EVENT, transactionReceipt);
        ArrayList<ClosedUserOrderEventResponse> responses = new ArrayList<ClosedUserOrderEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ClosedUserOrderEventResponse typedResponse = new ClosedUserOrderEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.userHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ClosedUserOrderEventResponse> closedUserOrderEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ClosedUserOrderEventResponse>() {
            @Override
            public ClosedUserOrderEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CLOSEDUSERORDER_EVENT, log);
                ClosedUserOrderEventResponse typedResponse = new ClosedUserOrderEventResponse();
                typedResponse.log = log;
                typedResponse.userHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ClosedUserOrderEventResponse> closedUserOrderEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLOSEDUSERORDER_EVENT));
        return closedUserOrderEventObservable(filter);
    }

    public List<SchedulerNoticeEventResponse> getSchedulerNoticeEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(SCHEDULERNOTICE_EVENT, transactionReceipt);
        ArrayList<SchedulerNoticeEventResponse> responses = new ArrayList<SchedulerNoticeEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            SchedulerNoticeEventResponse typedResponse = new SchedulerNoticeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.pool = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.dealid = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SchedulerNoticeEventResponse> schedulerNoticeEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, SchedulerNoticeEventResponse>() {
            @Override
            public SchedulerNoticeEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(SCHEDULERNOTICE_EVENT, log);
                SchedulerNoticeEventResponse typedResponse = new SchedulerNoticeEventResponse();
                typedResponse.log = log;
                typedResponse.pool = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.dealid = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<SchedulerNoticeEventResponse> schedulerNoticeEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SCHEDULERNOTICE_EVENT));
        return schedulerNoticeEventObservable(filter);
    }

    public RemoteCall<List> viewUserDeals(byte[] _id) {
        final Function function = new Function(FUNC_VIEWUSERDEALS, 
                Arrays.<Type>asList(new Bytes32(_id)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<BigInteger> viewConsumed(byte[] _id) {
        final Function function = new Function(FUNC_VIEWCONSUMED, 
                Arrays.<Type>asList(new Bytes32(_id)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Boolean> checkRestriction(String _restriction, String _candidate, byte[] _mask) {
        final Function function = new Function(FUNC_CHECKRESTRICTION, 
                Arrays.<Type>asList(new Address(_restriction),
                new Address(_candidate),
                new org.web3j.abi.datatypes.generated.Bytes1(_mask)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> lockSubscription(String _worker, BigInteger _amount) {
        final Function function = new Function(
                FUNC_LOCKSUBSCRIPTION, 
                Arrays.<Type>asList(new Address(_worker),
                new Uint256(_amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> unlockSubscription(String _worker, BigInteger _amount) {
        final Function function = new Function(
                FUNC_UNLOCKSUBSCRIPTION, 
                Arrays.<Type>asList(new Address(_worker),
                new Uint256(_amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> lockContribution(byte[] _dealid, String _worker) {
        final Function function = new Function(
                FUNC_LOCKCONTRIBUTION, 
                Arrays.<Type>asList(new Bytes32(_dealid),
                new Address(_worker)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> unlockContribution(byte[] _dealid, String _worker) {
        final Function function = new Function(
                FUNC_UNLOCKCONTRIBUTION, 
                Arrays.<Type>asList(new Bytes32(_dealid),
                new Address(_worker)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> unlockAndRewardForContribution(byte[] _dealid, String _worker, BigInteger _amount) {
        final Function function = new Function(
                FUNC_UNLOCKANDREWARDFORCONTRIBUTION, 
                Arrays.<Type>asList(new Bytes32(_dealid),
                new Address(_worker),
                new Uint256(_amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> seizeContribution(byte[] _dealid, String _worker) {
        final Function function = new Function(
                FUNC_SEIZECONTRIBUTION, 
                Arrays.<Type>asList(new Bytes32(_dealid),
                new Address(_worker)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> rewardForScheduling(byte[] _dealid, BigInteger _amount) {
        final Function function = new Function(
                FUNC_REWARDFORSCHEDULING, 
                Arrays.<Type>asList(new Bytes32(_dealid),
                new Uint256(_amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> successWork(byte[] _dealid) {
        final Function function = new Function(
                FUNC_SUCCESSWORK, 
                Arrays.<Type>asList(new Bytes32(_dealid)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> failedWork(byte[] _dealid) {
        final Function function = new Function(
                FUNC_FAILEDWORK, 
                Arrays.<Type>asList(new Bytes32(_dealid)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple9<String, String, BigInteger, String, String, BigInteger, String, String, BigInteger>> viewDealABILegacy_pt1(byte[] _id) {
        final Function function = new Function(FUNC_VIEWDEALABILEGACY_PT1, 
                Arrays.<Type>asList(new Bytes32(_id)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple9<String, String, BigInteger, String, String, BigInteger, String, String, BigInteger>>(
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

    public RemoteCall<Tuple6<BigInteger, BigInteger, String, String, String, String>> viewDealABILegacy_pt2(byte[] _id) {
        final Function function = new Function(FUNC_VIEWDEALABILEGACY_PT2, 
                Arrays.<Type>asList(new Bytes32(_id)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple6<BigInteger, BigInteger, String, String, String, String>>(
                new Callable<Tuple6<BigInteger, BigInteger, String, String, String, String>>() {
                    @Override
                    public Tuple6<BigInteger, BigInteger, String, String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<BigInteger, BigInteger, String, String, String, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (String) results.get(5).getValue());
                    }
                });
    }

    public RemoteCall<Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>> viewConfigABILegacy(byte[] _id) {
        final Function function = new Function(FUNC_VIEWCONFIGABILEGACY, 
                Arrays.<Type>asList(new Bytes32(_id)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>(
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

    public RemoteCall<Tuple2<BigInteger, BigInteger>> viewAccountABILegacy(String _user) {
        final Function function = new Function(FUNC_VIEWACCOUNTABILEGACY, 
                Arrays.<Type>asList(new Address(_user)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple2<BigInteger, BigInteger>>(
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

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class OrdersMatchedEventResponse {
        public Log log;

        public byte[] dealid;

        public byte[] dappHash;

        public byte[] dataHash;

        public byte[] poolHash;

        public byte[] userHash;

        public BigInteger volume;
    }

    public static class ClosedDappOrderEventResponse {
        public Log log;

        public byte[] dappHash;
    }

    public static class ClosedDataOrderEventResponse {
        public Log log;

        public byte[] dataHash;
    }

    public static class ClosedPoolOrderEventResponse {
        public Log log;

        public byte[] poolHash;
    }

    public static class ClosedUserOrderEventResponse {
        public Log log;

        public byte[] userHash;
    }

    public static class SchedulerNoticeEventResponse {
        public Log log;

        public String pool;

        public byte[] dealid;
    }
}
