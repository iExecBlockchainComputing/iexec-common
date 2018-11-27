package com.iexec.common.contract.generated;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple10;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple4;
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
public class IexecHubABILegacy extends Contract {
    public static final String FUNC_APPREGISTRY = "appregistry";
    public static final String FUNC_CONSENSUS_DURATION_RATIO = "CONSENSUS_DURATION_RATIO";
    public static final String FUNC_SCORE_UNITARY_SLASH = "SCORE_UNITARY_SLASH";
    public static final String FUNC_M_WORKERSCORES = "m_workerScores";
    public static final String FUNC_WORKERPOOLREGISTRY = "workerpoolregistry";
    public static final String FUNC_IEXECCLERK = "iexecclerk";
    public static final String FUNC_DATASETREGISTRY = "datasetregistry";
    public static final String FUNC_REVEAL_DURATION_RATIO = "REVEAL_DURATION_RATIO";
    public static final String FUNC_M_WORKERAFFECTATIONS = "m_workerAffectations";
    public static final String FUNC_ATTACHCONTRACTS = "attachContracts";
    public static final String FUNC_VIEWSCORE = "viewScore";
    public static final String FUNC_VIEWAFFECTATION = "viewAffectation";
    public static final String FUNC_CHECKRESOURCES = "checkResources";
    public static final String FUNC_RESULTFOR = "resultFor";
    public static final String FUNC_INITIALIZE = "initialize";
    public static final String FUNC_REVEAL = "reveal";
    public static final String FUNC_REOPEN = "reopen";
    public static final String FUNC_FINALIZE = "finalize";
    public static final String FUNC_CLAIM = "claim";
    public static final String FUNC_INITIALIZEARRAY = "initializeArray";
    public static final String FUNC_CLAIMARRAY = "claimArray";
    public static final String FUNC_SUBSCRIBE = "subscribe";
    public static final String FUNC_UNSUBSCRIBE = "unsubscribe";
    public static final String FUNC_EVICT = "evict";
    public static final String FUNC_VIEWTASKABILEGACY = "viewTaskABILegacy";
    public static final String FUNC_VIEWCONTRIBUTIONABILEGACY = "viewContributionABILegacy";
    public static final String FUNC_CONTRIBUTEABILEGACY = "contributeABILegacy";
    public static final String FUNC_VIEWCATEGORYABILEGACY = "viewCategoryABILegacy";
    public static final Event TASKINITIALIZE_EVENT = new Event("TaskInitialize",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {
            }, new TypeReference<Address>(true) {
            }));
    public static final Event TASKCONTRIBUTE_EVENT = new Event("TaskContribute",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Bytes32>() {
            }));
    ;
    public static final Event TASKCONSENSUS_EVENT = new Event("TaskConsensus",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {
            }, new TypeReference<Bytes32>() {
            }));
    ;
    public static final Event TASKREVEAL_EVENT = new Event("TaskReveal",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Bytes32>() {
            }));
    ;
    public static final Event TASKREOPEN_EVENT = new Event("TaskReopen",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {
            }));
    ;
    public static final Event TASKFINALIZE_EVENT = new Event("TaskFinalize",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {
            }, new TypeReference<DynamicBytes>() {
            }));
    ;
    public static final Event TASKCLAIMED_EVENT = new Event("TaskClaimed",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {
            }));
    ;
    public static final Event ACCURATECONTRIBUTION_EVENT = new Event("AccurateContribution",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Bytes32>(true) {
            }));
    ;
    public static final Event FAULTYCONTRIBUTION_EVENT = new Event("FaultyContribution",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Bytes32>(true) {
            }));
    ;
    public static final Event WORKERSUBSCRIPTION_EVENT = new Event("WorkerSubscription",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }));
    ;
    public static final Event WORKERUNSUBSCRIPTION_EVENT = new Event("WorkerUnsubscription",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }));
    ;
    public static final Event WORKEREVICTION_EVENT = new Event("WorkerEviction",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }));
    ;
    protected static final HashMap<String, String> _addresses;
    ;
    private static final String BINARY = "0x";

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected IexecHubABILegacy(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IexecHubABILegacy(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IexecHubABILegacy(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IexecHubABILegacy(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<IexecHubABILegacy> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IexecHubABILegacy.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IexecHubABILegacy> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IexecHubABILegacy.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<IexecHubABILegacy> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IexecHubABILegacy.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IexecHubABILegacy> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IexecHubABILegacy.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static IexecHubABILegacy load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IexecHubABILegacy(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IexecHubABILegacy load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IexecHubABILegacy(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IexecHubABILegacy load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IexecHubABILegacy(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IexecHubABILegacy load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IexecHubABILegacy(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public RemoteCall<String> appregistry() {
        final Function function = new Function(FUNC_APPREGISTRY,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> CONSENSUS_DURATION_RATIO() {
        final Function function = new Function(FUNC_CONSENSUS_DURATION_RATIO,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> SCORE_UNITARY_SLASH() {
        final Function function = new Function(FUNC_SCORE_UNITARY_SLASH,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> m_workerScores(String param0) {
        final Function function = new Function(FUNC_M_WORKERSCORES,
                Arrays.<Type>asList(new Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> workerpoolregistry() {
        final Function function = new Function(FUNC_WORKERPOOLREGISTRY,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> iexecclerk() {
        final Function function = new Function(FUNC_IEXECCLERK,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> datasetregistry() {
        final Function function = new Function(FUNC_DATASETREGISTRY,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> REVEAL_DURATION_RATIO() {
        final Function function = new Function(FUNC_REVEAL_DURATION_RATIO,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> m_workerAffectations(String param0) {
        final Function function = new Function(FUNC_M_WORKERAFFECTATIONS,
                Arrays.<Type>asList(new Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public List<TaskInitializeEventResponse> getTaskInitializeEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(TASKINITIALIZE_EVENT, transactionReceipt);
        ArrayList<TaskInitializeEventResponse> responses = new ArrayList<TaskInitializeEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TaskInitializeEventResponse typedResponse = new TaskInitializeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.workerpool = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TaskInitializeEventResponse> taskInitializeEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, TaskInitializeEventResponse>() {
            @Override
            public TaskInitializeEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(TASKINITIALIZE_EVENT, log);
                TaskInitializeEventResponse typedResponse = new TaskInitializeEventResponse();
                typedResponse.log = log;
                typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.workerpool = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<TaskInitializeEventResponse> taskInitializeEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TASKINITIALIZE_EVENT));
        return taskInitializeEventObservable(filter);
    }

    public List<TaskContributeEventResponse> getTaskContributeEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(TASKCONTRIBUTE_EVENT, transactionReceipt);
        ArrayList<TaskContributeEventResponse> responses = new ArrayList<TaskContributeEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TaskContributeEventResponse typedResponse = new TaskContributeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.worker = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TaskContributeEventResponse> taskContributeEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, TaskContributeEventResponse>() {
            @Override
            public TaskContributeEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(TASKCONTRIBUTE_EVENT, log);
                TaskContributeEventResponse typedResponse = new TaskContributeEventResponse();
                typedResponse.log = log;
                typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.worker = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<TaskContributeEventResponse> taskContributeEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TASKCONTRIBUTE_EVENT));
        return taskContributeEventObservable(filter);
    }

    public List<TaskConsensusEventResponse> getTaskConsensusEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(TASKCONSENSUS_EVENT, transactionReceipt);
        ArrayList<TaskConsensusEventResponse> responses = new ArrayList<TaskConsensusEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TaskConsensusEventResponse typedResponse = new TaskConsensusEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.consensus = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TaskConsensusEventResponse> taskConsensusEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, TaskConsensusEventResponse>() {
            @Override
            public TaskConsensusEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(TASKCONSENSUS_EVENT, log);
                TaskConsensusEventResponse typedResponse = new TaskConsensusEventResponse();
                typedResponse.log = log;
                typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.consensus = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<TaskConsensusEventResponse> taskConsensusEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TASKCONSENSUS_EVENT));
        return taskConsensusEventObservable(filter);
    }

    public List<TaskRevealEventResponse> getTaskRevealEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(TASKREVEAL_EVENT, transactionReceipt);
        ArrayList<TaskRevealEventResponse> responses = new ArrayList<TaskRevealEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TaskRevealEventResponse typedResponse = new TaskRevealEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.worker = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.digest = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TaskRevealEventResponse> taskRevealEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, TaskRevealEventResponse>() {
            @Override
            public TaskRevealEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(TASKREVEAL_EVENT, log);
                TaskRevealEventResponse typedResponse = new TaskRevealEventResponse();
                typedResponse.log = log;
                typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.worker = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.digest = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<TaskRevealEventResponse> taskRevealEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TASKREVEAL_EVENT));
        return taskRevealEventObservable(filter);
    }

    public List<TaskReopenEventResponse> getTaskReopenEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(TASKREOPEN_EVENT, transactionReceipt);
        ArrayList<TaskReopenEventResponse> responses = new ArrayList<TaskReopenEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TaskReopenEventResponse typedResponse = new TaskReopenEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TaskReopenEventResponse> taskReopenEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, TaskReopenEventResponse>() {
            @Override
            public TaskReopenEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(TASKREOPEN_EVENT, log);
                TaskReopenEventResponse typedResponse = new TaskReopenEventResponse();
                typedResponse.log = log;
                typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<TaskReopenEventResponse> taskReopenEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TASKREOPEN_EVENT));
        return taskReopenEventObservable(filter);
    }

    public List<TaskFinalizeEventResponse> getTaskFinalizeEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(TASKFINALIZE_EVENT, transactionReceipt);
        ArrayList<TaskFinalizeEventResponse> responses = new ArrayList<TaskFinalizeEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TaskFinalizeEventResponse typedResponse = new TaskFinalizeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.results = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TaskFinalizeEventResponse> taskFinalizeEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, TaskFinalizeEventResponse>() {
            @Override
            public TaskFinalizeEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(TASKFINALIZE_EVENT, log);
                TaskFinalizeEventResponse typedResponse = new TaskFinalizeEventResponse();
                typedResponse.log = log;
                typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.results = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<TaskFinalizeEventResponse> taskFinalizeEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TASKFINALIZE_EVENT));
        return taskFinalizeEventObservable(filter);
    }

    public List<TaskClaimedEventResponse> getTaskClaimedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(TASKCLAIMED_EVENT, transactionReceipt);
        ArrayList<TaskClaimedEventResponse> responses = new ArrayList<TaskClaimedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TaskClaimedEventResponse typedResponse = new TaskClaimedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TaskClaimedEventResponse> taskClaimedEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, TaskClaimedEventResponse>() {
            @Override
            public TaskClaimedEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(TASKCLAIMED_EVENT, log);
                TaskClaimedEventResponse typedResponse = new TaskClaimedEventResponse();
                typedResponse.log = log;
                typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<TaskClaimedEventResponse> taskClaimedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TASKCLAIMED_EVENT));
        return taskClaimedEventObservable(filter);
    }

    public List<AccurateContributionEventResponse> getAccurateContributionEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ACCURATECONTRIBUTION_EVENT, transactionReceipt);
        ArrayList<AccurateContributionEventResponse> responses = new ArrayList<AccurateContributionEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AccurateContributionEventResponse typedResponse = new AccurateContributionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.worker = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<AccurateContributionEventResponse> accurateContributionEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, AccurateContributionEventResponse>() {
            @Override
            public AccurateContributionEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(ACCURATECONTRIBUTION_EVENT, log);
                AccurateContributionEventResponse typedResponse = new AccurateContributionEventResponse();
                typedResponse.log = log;
                typedResponse.worker = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<AccurateContributionEventResponse> accurateContributionEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ACCURATECONTRIBUTION_EVENT));
        return accurateContributionEventObservable(filter);
    }

    public List<FaultyContributionEventResponse> getFaultyContributionEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(FAULTYCONTRIBUTION_EVENT, transactionReceipt);
        ArrayList<FaultyContributionEventResponse> responses = new ArrayList<FaultyContributionEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            FaultyContributionEventResponse typedResponse = new FaultyContributionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.worker = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<FaultyContributionEventResponse> faultyContributionEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, FaultyContributionEventResponse>() {
            @Override
            public FaultyContributionEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(FAULTYCONTRIBUTION_EVENT, log);
                FaultyContributionEventResponse typedResponse = new FaultyContributionEventResponse();
                typedResponse.log = log;
                typedResponse.worker = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<FaultyContributionEventResponse> faultyContributionEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FAULTYCONTRIBUTION_EVENT));
        return faultyContributionEventObservable(filter);
    }

    public List<WorkerSubscriptionEventResponse> getWorkerSubscriptionEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(WORKERSUBSCRIPTION_EVENT, transactionReceipt);
        ArrayList<WorkerSubscriptionEventResponse> responses = new ArrayList<WorkerSubscriptionEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            WorkerSubscriptionEventResponse typedResponse = new WorkerSubscriptionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.workerpool = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.worker = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<WorkerSubscriptionEventResponse> workerSubscriptionEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, WorkerSubscriptionEventResponse>() {
            @Override
            public WorkerSubscriptionEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(WORKERSUBSCRIPTION_EVENT, log);
                WorkerSubscriptionEventResponse typedResponse = new WorkerSubscriptionEventResponse();
                typedResponse.log = log;
                typedResponse.workerpool = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.worker = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<WorkerSubscriptionEventResponse> workerSubscriptionEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WORKERSUBSCRIPTION_EVENT));
        return workerSubscriptionEventObservable(filter);
    }

    public List<WorkerUnsubscriptionEventResponse> getWorkerUnsubscriptionEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(WORKERUNSUBSCRIPTION_EVENT, transactionReceipt);
        ArrayList<WorkerUnsubscriptionEventResponse> responses = new ArrayList<WorkerUnsubscriptionEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            WorkerUnsubscriptionEventResponse typedResponse = new WorkerUnsubscriptionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.workerpool = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.worker = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<WorkerUnsubscriptionEventResponse> workerUnsubscriptionEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, WorkerUnsubscriptionEventResponse>() {
            @Override
            public WorkerUnsubscriptionEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(WORKERUNSUBSCRIPTION_EVENT, log);
                WorkerUnsubscriptionEventResponse typedResponse = new WorkerUnsubscriptionEventResponse();
                typedResponse.log = log;
                typedResponse.workerpool = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.worker = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<WorkerUnsubscriptionEventResponse> workerUnsubscriptionEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WORKERUNSUBSCRIPTION_EVENT));
        return workerUnsubscriptionEventObservable(filter);
    }

    public List<WorkerEvictionEventResponse> getWorkerEvictionEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(WORKEREVICTION_EVENT, transactionReceipt);
        ArrayList<WorkerEvictionEventResponse> responses = new ArrayList<WorkerEvictionEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            WorkerEvictionEventResponse typedResponse = new WorkerEvictionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.workerpool = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.worker = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<WorkerEvictionEventResponse> workerEvictionEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, WorkerEvictionEventResponse>() {
            @Override
            public WorkerEvictionEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(WORKEREVICTION_EVENT, log);
                WorkerEvictionEventResponse typedResponse = new WorkerEvictionEventResponse();
                typedResponse.log = log;
                typedResponse.workerpool = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.worker = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<WorkerEvictionEventResponse> workerEvictionEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WORKEREVICTION_EVENT));
        return workerEvictionEventObservable(filter);
    }

    public RemoteCall<TransactionReceipt> attachContracts(String _iexecclerkAddress, String _appregistryAddress, String _datasetregistryAddress, String _workerpoolregistryAddress) {
        final Function function = new Function(
                FUNC_ATTACHCONTRACTS,
                Arrays.<Type>asList(new Address(_iexecclerkAddress),
                        new Address(_appregistryAddress),
                        new Address(_datasetregistryAddress),
                        new Address(_workerpoolregistryAddress)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> viewScore(String _worker) {
        final Function function = new Function(FUNC_VIEWSCORE,
                Arrays.<Type>asList(new Address(_worker)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> viewAffectation(String _worker) {
        final Function function = new Function(FUNC_VIEWAFFECTATION,
                Arrays.<Type>asList(new Address(_worker)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Boolean> checkResources(String aap, String dataset, String workerpool) {
        final Function function = new Function(FUNC_CHECKRESOURCES,
                Arrays.<Type>asList(new Address(aap),
                        new Address(dataset),
                        new Address(workerpool)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<byte[]> resultFor(byte[] id) {
        final Function function = new Function(FUNC_RESULTFOR,
                Arrays.<Type>asList(new Bytes32(id)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<TransactionReceipt> initialize(byte[] _dealid, BigInteger idx) {
        final Function function = new Function(
                FUNC_INITIALIZE,
                Arrays.<Type>asList(new Bytes32(_dealid),
                        new Uint256(idx)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> reveal(byte[] _taskid, byte[] _resultDigest) {
        final Function function = new Function(
                FUNC_REVEAL,
                Arrays.<Type>asList(new Bytes32(_taskid),
                        new Bytes32(_resultDigest)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> reopen(byte[] _taskid) {
        final Function function = new Function(
                FUNC_REOPEN,
                Arrays.<Type>asList(new Bytes32(_taskid)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> finalize(byte[] _taskid, byte[] _results) {
        final Function function = new Function(
                FUNC_FINALIZE,
                Arrays.<Type>asList(new Bytes32(_taskid),
                        new DynamicBytes(_results)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> claim(byte[] _taskid) {
        final Function function = new Function(
                FUNC_CLAIM,
                Arrays.<Type>asList(new Bytes32(_taskid)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> initializeArray(List<byte[]> _dealid, List<BigInteger> _idx) {
        final Function function = new Function(
                FUNC_INITIALIZEARRAY,
                Arrays.<Type>asList(new DynamicArray<Bytes32>(
                                org.web3j.abi.Utils.typeMap(_dealid, Bytes32.class)),
                        new DynamicArray<Uint256>(
                                org.web3j.abi.Utils.typeMap(_idx, Uint256.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> claimArray(List<byte[]> _taskid) {
        final Function function = new Function(
                FUNC_CLAIMARRAY,
                Arrays.<Type>asList(new DynamicArray<Bytes32>(
                        org.web3j.abi.Utils.typeMap(_taskid, Bytes32.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> subscribe(String _workerpool) {
        final Function function = new Function(
                FUNC_SUBSCRIBE,
                Arrays.<Type>asList(new Address(_workerpool)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> unsubscribe() {
        final Function function = new Function(
                FUNC_UNSUBSCRIBE,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> evict(String _worker) {
        final Function function = new Function(
                FUNC_EVICT,
                Arrays.<Type>asList(new Address(_worker)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple10<BigInteger, byte[], BigInteger, BigInteger, byte[], BigInteger, BigInteger, BigInteger, List<String>, byte[]>> viewTaskABILegacy(byte[] _taskid) {
        final Function function = new Function(FUNC_VIEWTASKABILEGACY,
                Arrays.<Type>asList(new Bytes32(_taskid)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {
                }, new TypeReference<Bytes32>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Bytes32>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<DynamicArray<Address>>() {
                }, new TypeReference<DynamicBytes>() {
                }));
        return new RemoteCall<Tuple10<BigInteger, byte[], BigInteger, BigInteger, byte[], BigInteger, BigInteger, BigInteger, List<String>, byte[]>>(
                new Callable<Tuple10<BigInteger, byte[], BigInteger, BigInteger, byte[], BigInteger, BigInteger, BigInteger, List<String>, byte[]>>() {
                    @Override
                    public Tuple10<BigInteger, byte[], BigInteger, BigInteger, byte[], BigInteger, BigInteger, BigInteger, List<String>, byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple10<BigInteger, byte[], BigInteger, BigInteger, byte[], BigInteger, BigInteger, BigInteger, List<String>, byte[]>(
                                (BigInteger) results.get(0).getValue(),
                                (byte[]) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue(),
                                (BigInteger) results.get(3).getValue(),
                                (byte[]) results.get(4).getValue(),
                                (BigInteger) results.get(5).getValue(),
                                (BigInteger) results.get(6).getValue(),
                                (BigInteger) results.get(7).getValue(),
                                convertToNative((List<Address>) results.get(8).getValue()),
                                (byte[]) results.get(9).getValue());
                    }
                });
    }

    public RemoteCall<Tuple4<BigInteger, byte[], byte[], String>> viewContributionABILegacy(byte[] _taskid, String _worker) {
        final Function function = new Function(FUNC_VIEWCONTRIBUTIONABILEGACY,
                Arrays.<Type>asList(new Bytes32(_taskid),
                        new Address(_worker)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {
                }, new TypeReference<Bytes32>() {
                }, new TypeReference<Bytes32>() {
                }, new TypeReference<Address>() {
                }));
        return new RemoteCall<Tuple4<BigInteger, byte[], byte[], String>>(
                new Callable<Tuple4<BigInteger, byte[], byte[], String>>() {
                    @Override
                    public Tuple4<BigInteger, byte[], byte[], String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<BigInteger, byte[], byte[], String>(
                                (BigInteger) results.get(0).getValue(),
                                (byte[]) results.get(1).getValue(),
                                (byte[]) results.get(2).getValue(),
                                (String) results.get(3).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> contributeABILegacy(byte[] _taskid, byte[] _resultHash, byte[] _resultSeal, String _enclaveChallenge, BigInteger _enclaveSign_v, byte[] _enclaveSign_r, byte[] _enclaveSign_s, BigInteger _poolSign_v, byte[] _poolSign_r, byte[] _poolSign_s) {
        final Function function = new Function(
                FUNC_CONTRIBUTEABILEGACY,
                Arrays.<Type>asList(new Bytes32(_taskid),
                        new Bytes32(_resultHash),
                        new Bytes32(_resultSeal),
                        new Address(_enclaveChallenge),
                        new Uint8(_enclaveSign_v),
                        new Bytes32(_enclaveSign_r),
                        new Bytes32(_enclaveSign_s),
                        new Uint8(_poolSign_v),
                        new Bytes32(_poolSign_r),
                        new Bytes32(_poolSign_s)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple3<String, String, BigInteger>> viewCategoryABILegacy(BigInteger _catid) {
        final Function function = new Function(FUNC_VIEWCATEGORYABILEGACY,
                Arrays.<Type>asList(new Uint256(_catid)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }, new TypeReference<Utf8String>() {
                }, new TypeReference<Uint256>() {
                }));
        return new RemoteCall<Tuple3<String, String, BigInteger>>(
                new Callable<Tuple3<String, String, BigInteger>>() {
                    @Override
                    public Tuple3<String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, String, BigInteger>(
                                (String) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class TaskInitializeEventResponse {
        public Log log;

        public byte[] taskid;

        public String workerpool;
    }

    public static class TaskContributeEventResponse {
        public Log log;

        public byte[] taskid;

        public String worker;

        public byte[] hash;
    }

    public static class TaskConsensusEventResponse {
        public Log log;

        public byte[] taskid;

        public byte[] consensus;
    }

    public static class TaskRevealEventResponse {
        public Log log;

        public byte[] taskid;

        public String worker;

        public byte[] digest;
    }

    public static class TaskReopenEventResponse {
        public Log log;

        public byte[] taskid;
    }

    public static class TaskFinalizeEventResponse {
        public Log log;

        public byte[] taskid;

        public byte[] results;
    }

    public static class TaskClaimedEventResponse {
        public Log log;

        public byte[] taskid;
    }

    public static class AccurateContributionEventResponse {
        public Log log;

        public String worker;

        public byte[] taskid;
    }

    public static class FaultyContributionEventResponse {
        public Log log;

        public String worker;

        public byte[] taskid;
    }

    public static class WorkerSubscriptionEventResponse {
        public Log log;

        public String workerpool;

        public String worker;
    }

    public static class WorkerUnsubscriptionEventResponse {
        public Log log;

        public String workerpool;

        public String worker;
    }

    public static class WorkerEvictionEventResponse {
        public Log log;

        public String workerpool;

        public String worker;
    }
}
