package com.iexec.common.contract.generated;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
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

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.6.0.
 */
public class IexecClerkABILegacy extends Contract {
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
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
            }, new TypeReference<Bytes32>() {
            }, new TypeReference<Bytes32>() {
            }, new TypeReference<Bytes32>() {
            }, new TypeReference<Bytes32>() {
            }, new TypeReference<Uint256>() {
            }));
    public static final Event CLOSEDAPPORDER_EVENT = new Event("ClosedAppOrder",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
            }));
    ;
    public static final Event CLOSEDDATASETORDER_EVENT = new Event("ClosedDatasetOrder",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
            }));
    ;
    public static final Event CLOSEDWORKERPOOLORDER_EVENT = new Event("ClosedWorkerpoolOrder",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
            }));
    ;
    public static final Event CLOSEDUSERORDER_EVENT = new Event("ClosedUserOrder",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
            }));
    ;
    public static final Event SCHEDULERNOTICE_EVENT = new Event("SchedulerNotice",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Bytes32>() {
            }));
    ;
    protected static final HashMap<String, String> _addresses;
    ;
    private static final String BINARY = "0x";

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

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public RemoteCall<byte[]> EIP712DOMAIN_SEPARATOR() {
        final Function function = new Function(FUNC_EIP712DOMAIN_SEPARATOR,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<Boolean> m_presigned(byte[] param0) {
        final Function function = new Function(FUNC_M_PRESIGNED,
                Arrays.<Type>asList(new Bytes32(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<BigInteger> KITTY_RATIO() {
        final Function function = new Function(FUNC_KITTY_RATIO,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> POOL_STAKE_RATIO() {
        final Function function = new Function(FUNC_POOL_STAKE_RATIO,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<byte[]> m_userdeals(byte[] param0, BigInteger param1) {
        final Function function = new Function(FUNC_M_USERDEALS,
                Arrays.<Type>asList(new Bytes32(param0),
                        new Uint256(param1)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<BigInteger> KITTY_MIN() {
        final Function function = new Function(FUNC_KITTY_MIN,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> m_consumed(byte[] param0) {
        final Function function = new Function(FUNC_M_CONSUMED,
                Arrays.<Type>asList(new Bytes32(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public List<OrdersMatchedEventResponse> getOrdersMatchedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ORDERSMATCHED_EVENT, transactionReceipt);
        ArrayList<OrdersMatchedEventResponse> responses = new ArrayList<OrdersMatchedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            OrdersMatchedEventResponse typedResponse = new OrdersMatchedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.dealid = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.appHash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.datasetHash = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.workerpoolHash = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
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
                typedResponse.appHash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.datasetHash = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.workerpoolHash = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
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

    public List<ClosedAppOrderEventResponse> getClosedAppOrderEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CLOSEDAPPORDER_EVENT, transactionReceipt);
        ArrayList<ClosedAppOrderEventResponse> responses = new ArrayList<ClosedAppOrderEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ClosedAppOrderEventResponse typedResponse = new ClosedAppOrderEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.appHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ClosedAppOrderEventResponse> closedAppOrderEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ClosedAppOrderEventResponse>() {
            @Override
            public ClosedAppOrderEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CLOSEDAPPORDER_EVENT, log);
                ClosedAppOrderEventResponse typedResponse = new ClosedAppOrderEventResponse();
                typedResponse.log = log;
                typedResponse.appHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ClosedAppOrderEventResponse> closedAppOrderEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLOSEDAPPORDER_EVENT));
        return closedAppOrderEventObservable(filter);
    }

    public List<ClosedDatasetOrderEventResponse> getClosedDatasetOrderEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CLOSEDDATASETORDER_EVENT, transactionReceipt);
        ArrayList<ClosedDatasetOrderEventResponse> responses = new ArrayList<ClosedDatasetOrderEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ClosedDatasetOrderEventResponse typedResponse = new ClosedDatasetOrderEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.datasetHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ClosedDatasetOrderEventResponse> closedDatasetOrderEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ClosedDatasetOrderEventResponse>() {
            @Override
            public ClosedDatasetOrderEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CLOSEDDATASETORDER_EVENT, log);
                ClosedDatasetOrderEventResponse typedResponse = new ClosedDatasetOrderEventResponse();
                typedResponse.log = log;
                typedResponse.datasetHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ClosedDatasetOrderEventResponse> closedDatasetOrderEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLOSEDDATASETORDER_EVENT));
        return closedDatasetOrderEventObservable(filter);
    }

    public List<ClosedWorkerpoolOrderEventResponse> getClosedWorkerpoolOrderEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CLOSEDWORKERPOOLORDER_EVENT, transactionReceipt);
        ArrayList<ClosedWorkerpoolOrderEventResponse> responses = new ArrayList<ClosedWorkerpoolOrderEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ClosedWorkerpoolOrderEventResponse typedResponse = new ClosedWorkerpoolOrderEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.workerpoolHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ClosedWorkerpoolOrderEventResponse> closedWorkerpoolOrderEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ClosedWorkerpoolOrderEventResponse>() {
            @Override
            public ClosedWorkerpoolOrderEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CLOSEDWORKERPOOLORDER_EVENT, log);
                ClosedWorkerpoolOrderEventResponse typedResponse = new ClosedWorkerpoolOrderEventResponse();
                typedResponse.log = log;
                typedResponse.workerpoolHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ClosedWorkerpoolOrderEventResponse> closedWorkerpoolOrderEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLOSEDWORKERPOOLORDER_EVENT));
        return closedWorkerpoolOrderEventObservable(filter);
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
            typedResponse.workerpool = (String) eventValues.getIndexedValues().get(0).getValue();
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
                typedResponse.workerpool = (String) eventValues.getIndexedValues().get(0).getValue();
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
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {
                }));
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
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Boolean> checkRestriction(String _restriction, String _candidate, byte[] _mask) {
        final Function function = new Function(FUNC_CHECKRESTRICTION,
                Arrays.<Type>asList(new Address(_restriction),
                        new Address(_candidate),
                        new org.web3j.abi.datatypes.generated.Bytes1(_mask)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
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
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }, new TypeReference<Address>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Address>() {
                }, new TypeReference<Address>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Address>() {
                }, new TypeReference<Address>() {
                }, new TypeReference<Uint256>() {
                }));
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
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Address>() {
                }, new TypeReference<Address>() {
                }, new TypeReference<Address>() {
                }, new TypeReference<Utf8String>() {
                }));
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
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }));
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
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }));
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

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class OrdersMatchedEventResponse {
        public Log log;

        public byte[] dealid;

        public byte[] appHash;

        public byte[] datasetHash;

        public byte[] workerpoolHash;

        public byte[] userHash;

        public BigInteger volume;
    }

    public static class ClosedAppOrderEventResponse {
        public Log log;

        public byte[] appHash;
    }

    public static class ClosedDatasetOrderEventResponse {
        public Log log;

        public byte[] datasetHash;
    }

    public static class ClosedWorkerpoolOrderEventResponse {
        public Log log;

        public byte[] workerpoolHash;
    }

    public static class ClosedUserOrderEventResponse {
        public Log log;

        public byte[] userHash;
    }

    public static class SchedulerNoticeEventResponse {
        public Log log;

        public String workerpool;

        public byte[] dealid;
    }
}
