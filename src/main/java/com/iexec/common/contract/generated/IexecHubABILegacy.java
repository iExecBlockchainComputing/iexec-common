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
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple12;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple4;
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
public class IexecHubABILegacy extends Contract {
    private static final String BINARY = "0x";

    public static final String FUNC_APPREGISTRY = "appregistry";

    public static final String FUNC_CONSENSUS_DURATION_RATIO = "CONSENSUS_DURATION_RATIO";

    public static final String FUNC_WORKERPOOLREGISTRY = "workerpoolregistry";

    public static final String FUNC_IEXECCLERK = "iexecclerk";

    public static final String FUNC_DATASETREGISTRY = "datasetregistry";

    public static final String FUNC_REVEAL_DURATION_RATIO = "REVEAL_DURATION_RATIO";

    public static final String FUNC_ATTACHCONTRACTS = "attachContracts";

    public static final String FUNC_VIEWSCORE = "viewScore";

    public static final String FUNC_CHECKRESOURCES = "checkResources";

    public static final String FUNC_RESULTFOR = "resultFor";

    public static final String FUNC_INITIALIZE = "initialize";

    public static final String FUNC_CONTRIBUTE = "contribute";

    public static final String FUNC_REVEAL = "reveal";

    public static final String FUNC_REOPEN = "reopen";

    public static final String FUNC_FINALIZE = "finalize";

    public static final String FUNC_CLAIM = "claim";

    public static final String FUNC_INITIALIZEARRAY = "initializeArray";

    public static final String FUNC_CLAIMARRAY = "claimArray";

    public static final String FUNC_INITIALIZEANDCLAIMARRAY = "initializeAndClaimArray";

    public static final String FUNC_VIEWTASKABILEGACY = "viewTaskABILegacy";

    public static final String FUNC_VIEWCONTRIBUTIONABILEGACY = "viewContributionABILegacy";

    public static final String FUNC_VIEWCATEGORYABILEGACY = "viewCategoryABILegacy";

    public static final Event TASKINITIALIZE_EVENT = new Event("TaskInitialize", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event TASKCONTRIBUTE_EVENT = new Event("TaskContribute", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event TASKCONSENSUS_EVENT = new Event("TaskConsensus", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event TASKREVEAL_EVENT = new Event("TaskReveal", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event TASKREOPEN_EVENT = new Event("TaskReopen", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event TASKFINALIZE_EVENT = new Event("TaskFinalize", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event TASKCLAIMED_EVENT = new Event("TaskClaimed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event ACCURATECONTRIBUTION_EVENT = new Event("AccurateContribution", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event FAULTYCONTRIBUTION_EVENT = new Event("FaultyContribution", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Bytes32>(true) {}));
    ;

    protected static final HashMap<String, String> _addresses;

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

    public RemoteFunctionCall<String> appregistry() {
        final Function function = new Function(FUNC_APPREGISTRY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> CONSENSUS_DURATION_RATIO() {
        final Function function = new Function(FUNC_CONSENSUS_DURATION_RATIO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> workerpoolregistry() {
        final Function function = new Function(FUNC_WORKERPOOLREGISTRY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> iexecclerk() {
        final Function function = new Function(FUNC_IEXECCLERK, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> datasetregistry() {
        final Function function = new Function(FUNC_DATASETREGISTRY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> REVEAL_DURATION_RATIO() {
        final Function function = new Function(FUNC_REVEAL_DURATION_RATIO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public List<TaskInitializeEventResponse> getTaskInitializeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TASKINITIALIZE_EVENT, transactionReceipt);
        ArrayList<TaskInitializeEventResponse> responses = new ArrayList<TaskInitializeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TaskInitializeEventResponse typedResponse = new TaskInitializeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.workerpool = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TaskInitializeEventResponse> taskInitializeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TaskInitializeEventResponse>() {
            @Override
            public TaskInitializeEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TASKINITIALIZE_EVENT, log);
                TaskInitializeEventResponse typedResponse = new TaskInitializeEventResponse();
                typedResponse.log = log;
                typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.workerpool = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TaskInitializeEventResponse> taskInitializeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TASKINITIALIZE_EVENT));
        return taskInitializeEventFlowable(filter);
    }

    public List<TaskContributeEventResponse> getTaskContributeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TASKCONTRIBUTE_EVENT, transactionReceipt);
        ArrayList<TaskContributeEventResponse> responses = new ArrayList<TaskContributeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TaskContributeEventResponse typedResponse = new TaskContributeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.worker = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TaskContributeEventResponse> taskContributeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TaskContributeEventResponse>() {
            @Override
            public TaskContributeEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TASKCONTRIBUTE_EVENT, log);
                TaskContributeEventResponse typedResponse = new TaskContributeEventResponse();
                typedResponse.log = log;
                typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.worker = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TaskContributeEventResponse> taskContributeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TASKCONTRIBUTE_EVENT));
        return taskContributeEventFlowable(filter);
    }

    public List<TaskConsensusEventResponse> getTaskConsensusEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TASKCONSENSUS_EVENT, transactionReceipt);
        ArrayList<TaskConsensusEventResponse> responses = new ArrayList<TaskConsensusEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TaskConsensusEventResponse typedResponse = new TaskConsensusEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.consensus = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TaskConsensusEventResponse> taskConsensusEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TaskConsensusEventResponse>() {
            @Override
            public TaskConsensusEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TASKCONSENSUS_EVENT, log);
                TaskConsensusEventResponse typedResponse = new TaskConsensusEventResponse();
                typedResponse.log = log;
                typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.consensus = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TaskConsensusEventResponse> taskConsensusEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TASKCONSENSUS_EVENT));
        return taskConsensusEventFlowable(filter);
    }

    public List<TaskRevealEventResponse> getTaskRevealEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TASKREVEAL_EVENT, transactionReceipt);
        ArrayList<TaskRevealEventResponse> responses = new ArrayList<TaskRevealEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TaskRevealEventResponse typedResponse = new TaskRevealEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.worker = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.digest = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TaskRevealEventResponse> taskRevealEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TaskRevealEventResponse>() {
            @Override
            public TaskRevealEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TASKREVEAL_EVENT, log);
                TaskRevealEventResponse typedResponse = new TaskRevealEventResponse();
                typedResponse.log = log;
                typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.worker = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.digest = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TaskRevealEventResponse> taskRevealEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TASKREVEAL_EVENT));
        return taskRevealEventFlowable(filter);
    }

    public List<TaskReopenEventResponse> getTaskReopenEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TASKREOPEN_EVENT, transactionReceipt);
        ArrayList<TaskReopenEventResponse> responses = new ArrayList<TaskReopenEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TaskReopenEventResponse typedResponse = new TaskReopenEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TaskReopenEventResponse> taskReopenEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TaskReopenEventResponse>() {
            @Override
            public TaskReopenEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TASKREOPEN_EVENT, log);
                TaskReopenEventResponse typedResponse = new TaskReopenEventResponse();
                typedResponse.log = log;
                typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TaskReopenEventResponse> taskReopenEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TASKREOPEN_EVENT));
        return taskReopenEventFlowable(filter);
    }

    public List<TaskFinalizeEventResponse> getTaskFinalizeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TASKFINALIZE_EVENT, transactionReceipt);
        ArrayList<TaskFinalizeEventResponse> responses = new ArrayList<TaskFinalizeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TaskFinalizeEventResponse typedResponse = new TaskFinalizeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.results = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TaskFinalizeEventResponse> taskFinalizeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TaskFinalizeEventResponse>() {
            @Override
            public TaskFinalizeEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TASKFINALIZE_EVENT, log);
                TaskFinalizeEventResponse typedResponse = new TaskFinalizeEventResponse();
                typedResponse.log = log;
                typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.results = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TaskFinalizeEventResponse> taskFinalizeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TASKFINALIZE_EVENT));
        return taskFinalizeEventFlowable(filter);
    }

    public List<TaskClaimedEventResponse> getTaskClaimedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TASKCLAIMED_EVENT, transactionReceipt);
        ArrayList<TaskClaimedEventResponse> responses = new ArrayList<TaskClaimedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TaskClaimedEventResponse typedResponse = new TaskClaimedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TaskClaimedEventResponse> taskClaimedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TaskClaimedEventResponse>() {
            @Override
            public TaskClaimedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TASKCLAIMED_EVENT, log);
                TaskClaimedEventResponse typedResponse = new TaskClaimedEventResponse();
                typedResponse.log = log;
                typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TaskClaimedEventResponse> taskClaimedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TASKCLAIMED_EVENT));
        return taskClaimedEventFlowable(filter);
    }

    public List<AccurateContributionEventResponse> getAccurateContributionEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ACCURATECONTRIBUTION_EVENT, transactionReceipt);
        ArrayList<AccurateContributionEventResponse> responses = new ArrayList<AccurateContributionEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AccurateContributionEventResponse typedResponse = new AccurateContributionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.worker = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AccurateContributionEventResponse> accurateContributionEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, AccurateContributionEventResponse>() {
            @Override
            public AccurateContributionEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ACCURATECONTRIBUTION_EVENT, log);
                AccurateContributionEventResponse typedResponse = new AccurateContributionEventResponse();
                typedResponse.log = log;
                typedResponse.worker = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AccurateContributionEventResponse> accurateContributionEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ACCURATECONTRIBUTION_EVENT));
        return accurateContributionEventFlowable(filter);
    }

    public List<FaultyContributionEventResponse> getFaultyContributionEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(FAULTYCONTRIBUTION_EVENT, transactionReceipt);
        ArrayList<FaultyContributionEventResponse> responses = new ArrayList<FaultyContributionEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FaultyContributionEventResponse typedResponse = new FaultyContributionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.worker = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<FaultyContributionEventResponse> faultyContributionEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, FaultyContributionEventResponse>() {
            @Override
            public FaultyContributionEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(FAULTYCONTRIBUTION_EVENT, log);
                FaultyContributionEventResponse typedResponse = new FaultyContributionEventResponse();
                typedResponse.log = log;
                typedResponse.worker = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.taskid = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<FaultyContributionEventResponse> faultyContributionEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FAULTYCONTRIBUTION_EVENT));
        return faultyContributionEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> attachContracts(String _iexecclerkAddress, String _appregistryAddress, String _datasetregistryAddress, String _workerpoolregistryAddress) {
        final Function function = new Function(
                FUNC_ATTACHCONTRACTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_iexecclerkAddress), 
                new org.web3j.abi.datatypes.Address(_appregistryAddress), 
                new org.web3j.abi.datatypes.Address(_datasetregistryAddress), 
                new org.web3j.abi.datatypes.Address(_workerpoolregistryAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> viewScore(String _worker) {
        final Function function = new Function(FUNC_VIEWSCORE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_worker)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> checkResources(String aap, String dataset, String workerpool) {
        final Function function = new Function(FUNC_CHECKRESOURCES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(aap), 
                new org.web3j.abi.datatypes.Address(dataset), 
                new org.web3j.abi.datatypes.Address(workerpool)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<byte[]> resultFor(byte[] id) {
        final Function function = new Function(FUNC_RESULTFOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> initialize(byte[] _dealid, BigInteger idx) {
        final Function function = new Function(
                FUNC_INITIALIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_dealid), 
                new org.web3j.abi.datatypes.generated.Uint256(idx)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> contribute(byte[] _taskid, byte[] _resultHash, byte[] _resultSeal, String _enclaveChallenge, byte[] _enclaveSign, byte[] _workerpoolSign) {
        final Function function = new Function(
                FUNC_CONTRIBUTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_taskid), 
                new org.web3j.abi.datatypes.generated.Bytes32(_resultHash), 
                new org.web3j.abi.datatypes.generated.Bytes32(_resultSeal), 
                new org.web3j.abi.datatypes.Address(_enclaveChallenge), 
                new org.web3j.abi.datatypes.DynamicBytes(_enclaveSign), 
                new org.web3j.abi.datatypes.DynamicBytes(_workerpoolSign)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> reveal(byte[] _taskid, byte[] _resultDigest) {
        final Function function = new Function(
                FUNC_REVEAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_taskid), 
                new org.web3j.abi.datatypes.generated.Bytes32(_resultDigest)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> reopen(byte[] _taskid) {
        final Function function = new Function(
                FUNC_REOPEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_taskid)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> finalize(byte[] _taskid, byte[] _results) {
        final Function function = new Function(
                FUNC_FINALIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_taskid), 
                new org.web3j.abi.datatypes.DynamicBytes(_results)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> claim(byte[] _taskid) {
        final Function function = new Function(
                FUNC_CLAIM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_taskid)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> initializeArray(List<byte[]> _dealid, List<BigInteger> _idx) {
        final Function function = new Function(
                FUNC_INITIALIZEARRAY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_dealid, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_idx, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> claimArray(List<byte[]> _taskid) {
        final Function function = new Function(
                FUNC_CLAIMARRAY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_taskid, org.web3j.abi.datatypes.generated.Bytes32.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> initializeAndClaimArray(List<byte[]> _dealid, List<BigInteger> _idx) {
        final Function function = new Function(
                FUNC_INITIALIZEANDCLAIMARRAY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_dealid, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_idx, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple12<BigInteger, byte[], BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, byte[], BigInteger, BigInteger, List<String>, byte[]>> viewTaskABILegacy(byte[] _taskid) {
        final Function function = new Function(FUNC_VIEWTASKABILEGACY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_taskid)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicArray<Address>>() {}, new TypeReference<DynamicBytes>() {}));
        return new RemoteFunctionCall<Tuple12<BigInteger, byte[], BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, byte[], BigInteger, BigInteger, List<String>, byte[]>>(function,
                new Callable<Tuple12<BigInteger, byte[], BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, byte[], BigInteger, BigInteger, List<String>, byte[]>>() {
                    @Override
                    public Tuple12<BigInteger, byte[], BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, byte[], BigInteger, BigInteger, List<String>, byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple12<BigInteger, byte[], BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, byte[], BigInteger, BigInteger, List<String>, byte[]>(
                                (BigInteger) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (byte[]) results.get(7).getValue(), 
                                (BigInteger) results.get(8).getValue(), 
                                (BigInteger) results.get(9).getValue(), 
                                convertToNative((List<Address>) results.get(10).getValue()), 
                                (byte[]) results.get(11).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple4<BigInteger, byte[], byte[], String>> viewContributionABILegacy(byte[] _taskid, String _worker) {
        final Function function = new Function(FUNC_VIEWCONTRIBUTIONABILEGACY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_taskid), 
                new org.web3j.abi.datatypes.Address(_worker)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}));
        return new RemoteFunctionCall<Tuple4<BigInteger, byte[], byte[], String>>(function,
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

    public RemoteFunctionCall<Tuple3<String, String, BigInteger>> viewCategoryABILegacy(BigInteger _catid) {
        final Function function = new Function(FUNC_VIEWCATEGORYABILEGACY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_catid)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple3<String, String, BigInteger>>(function,
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

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class TaskInitializeEventResponse extends BaseEventResponse {
        public byte[] taskid;

        public String workerpool;
    }

    public static class TaskContributeEventResponse extends BaseEventResponse {
        public byte[] taskid;

        public String worker;

        public byte[] hash;
    }

    public static class TaskConsensusEventResponse extends BaseEventResponse {
        public byte[] taskid;

        public byte[] consensus;
    }

    public static class TaskRevealEventResponse extends BaseEventResponse {
        public byte[] taskid;

        public String worker;

        public byte[] digest;
    }

    public static class TaskReopenEventResponse extends BaseEventResponse {
        public byte[] taskid;
    }

    public static class TaskFinalizeEventResponse extends BaseEventResponse {
        public byte[] taskid;

        public byte[] results;
    }

    public static class TaskClaimedEventResponse extends BaseEventResponse {
        public byte[] taskid;
    }

    public static class AccurateContributionEventResponse extends BaseEventResponse {
        public String worker;

        public byte[] taskid;
    }

    public static class FaultyContributionEventResponse extends BaseEventResponse {
        public String worker;

        public byte[] taskid;
    }
}
