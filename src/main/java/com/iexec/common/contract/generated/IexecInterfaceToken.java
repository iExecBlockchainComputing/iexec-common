package com.iexec.common.contract.generated;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
 * <p>Generated with web3j version 4.7.0.
 */
@SuppressWarnings("rawtypes")
public class IexecInterfaceToken extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_UNISWAPV2ROUTER = "UniswapV2Router";

    public static final String FUNC_ALLOWANCE = "allowance";

    public static final String FUNC_APPREGISTRY = "appregistry";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_APPROVEANDCALL = "approveAndCall";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BROADCASTAPPORDER = "broadcastAppOrder";

    public static final String FUNC_BROADCASTDATASETORDER = "broadcastDatasetOrder";

    public static final String FUNC_BROADCASTREQUESTORDER = "broadcastRequestOrder";

    public static final String FUNC_BROADCASTWORKERPOOLORDER = "broadcastWorkerpoolOrder";

    public static final String FUNC_CALLBACKGAS = "callbackgas";

    public static final String FUNC_CLAIM = "claim";

    public static final String FUNC_CLAIMARRAY = "claimArray";

    public static final String FUNC_CONFIGURE = "configure";

    public static final String FUNC_CONTRIBUTE = "contribute";

    public static final String FUNC_CONTRIBUTEANDFINALIZE = "contributeAndFinalize";

    public static final String FUNC_CONTRIBUTION_DEADLINE_RATIO = "contribution_deadline_ratio";

    public static final String FUNC_COUNTCATEGORY = "countCategory";

    public static final String FUNC_CREATECATEGORY = "createCategory";

    public static final String FUNC_DATASETREGISTRY = "datasetregistry";

    public static final String FUNC_DECIMALS = "decimals";

    public static final String FUNC_DECREASEALLOWANCE = "decreaseAllowance";

    public static final String FUNC_DEPOSIT = "deposit";

    public static final String FUNC_DEPOSITETH = "depositEth";

    public static final String FUNC_DEPOSITETHFOR = "depositEthFor";

    public static final String FUNC_DEPOSITFOR = "depositFor";

    public static final String FUNC_DEPOSITFORARRAY = "depositForArray";

    public static final String FUNC_DOMAIN = "domain";

    public static final String FUNC_EIP712DOMAIN_SEPARATOR = "eip712domain_separator";

    public static final String FUNC_ESTIMATEDEPOSITETHSENT = "estimateDepositEthSent";

    public static final String FUNC_ESTIMATEDEPOSITTOKENWANTED = "estimateDepositTokenWanted";

    public static final String FUNC_ESTIMATEWITHDRAWETHWANTED = "estimateWithdrawEthWanted";

    public static final String FUNC_ESTIMATEWITHDRAWTOKENSENT = "estimateWithdrawTokenSent";

    public static final String FUNC_FINAL_DEADLINE_RATIO = "final_deadline_ratio";

    public static final String FUNC_FINALIZE = "finalize";

    public static final String FUNC_FROZENOF = "frozenOf";

    public static final String FUNC_GROUPMEMBER_PURPOSE = "groupmember_purpose";

    public static final String FUNC_IMPORTSCORE = "importScore";

    public static final String FUNC_INCREASEALLOWANCE = "increaseAllowance";

    public static final String FUNC_INITIALIZE = "initialize";

    public static final String FUNC_INITIALIZEANDCLAIMARRAY = "initializeAndClaimArray";

    public static final String FUNC_INITIALIZEARRAY = "initializeArray";

    public static final String FUNC_KITTY_ADDRESS = "kitty_address";

    public static final String FUNC_KITTY_MIN = "kitty_min";

    public static final String FUNC_KITTY_RATIO = "kitty_ratio";

    public static final String FUNC_MANAGEAPPORDER = "manageAppOrder";

    public static final String FUNC_MANAGEDATASETORDER = "manageDatasetOrder";

    public static final String FUNC_MANAGEREQUESTORDER = "manageRequestOrder";

    public static final String FUNC_MANAGEWORKERPOOLORDER = "manageWorkerpoolOrder";

    public static final String FUNC_MATCHORDERS = "matchOrders";

    public static final String FUNC_MATCHORDERSWITHETH = "matchOrdersWithEth";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_RECEIVEAPPROVAL = "receiveApproval";

    public static final String FUNC_RECOVER = "recover";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_REOPEN = "reopen";

    public static final String FUNC_REQUESTTOKEN = "requestToken";

    public static final String FUNC_REQUESTTOKENFOR = "requestTokenFor";

    public static final String FUNC_RESULTFOR = "resultFor";

    public static final String FUNC_REVEAL = "reveal";

    public static final String FUNC_REVEAL_DEADLINE_RATIO = "reveal_deadline_ratio";

    public static final String FUNC_SAFEDEPOSITETH = "safeDepositEth";

    public static final String FUNC_SAFEDEPOSITETHFOR = "safeDepositEthFor";

    public static final String FUNC_SAFEWITHDRAWETH = "safeWithdrawEth";

    public static final String FUNC_SAFEWITHDRAWETHTO = "safeWithdrawEthTo";

    public static final String FUNC_SETCALLBACKGAS = "setCallbackGas";

    public static final String FUNC_SETNAME = "setName";

    public static final String FUNC_SETTEEBROKER = "setTeeBroker";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TEEBROKER = "teebroker";

    public static final String FUNC_TOKEN = "token";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_UPDATEDOMAINSEPARATOR = "updateDomainSeparator";

    public static final String FUNC_VERIFYPRESIGNATURE = "verifyPresignature";

    public static final String FUNC_VERIFYPRESIGNATUREORSIGNATURE = "verifyPresignatureOrSignature";

    public static final String FUNC_VERIFYSIGNATURE = "verifySignature";

    public static final String FUNC_VIEWACCOUNT = "viewAccount";

    public static final String FUNC_VIEWCATEGORY = "viewCategory";

    public static final String FUNC_VIEWCONSUMED = "viewConsumed";

    public static final String FUNC_VIEWCONTRIBUTION = "viewContribution";

    public static final String FUNC_VIEWDEAL = "viewDeal";

    public static final String FUNC_VIEWPRESIGNED = "viewPresigned";

    public static final String FUNC_VIEWSCORE = "viewScore";

    public static final String FUNC_VIEWTASK = "viewTask";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final String FUNC_WITHDRAWETH = "withdrawEth";

    public static final String FUNC_WITHDRAWETHTO = "withdrawEthTo";

    public static final String FUNC_WITHDRAWTO = "withdrawTo";

    public static final String FUNC_WORKERPOOL_STAKE_RATIO = "workerpool_stake_ratio";

    public static final String FUNC_WORKERPOOLREGISTRY = "workerpoolregistry";

    public static final Event ACCURATECONTRIBUTION_EVENT = new Event("AccurateContribution", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event APPROVAL_EVENT = new Event("Approval", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event BROADCASTAPPORDER_EVENT = new Event("BroadcastAppOrder", 
            Arrays.<TypeReference<?>>asList(new TypeReference<AppOrder>() {}));
    ;

    public static final Event BROADCASTDATASETORDER_EVENT = new Event("BroadcastDatasetOrder", 
            Arrays.<TypeReference<?>>asList(new TypeReference<DatasetOrder>() {}));
    ;

    public static final Event BROADCASTREQUESTORDER_EVENT = new Event("BroadcastRequestOrder", 
            Arrays.<TypeReference<?>>asList(new TypeReference<RequestOrder>() {}));
    ;

    public static final Event BROADCASTWORKERPOOLORDER_EVENT = new Event("BroadcastWorkerpoolOrder", 
            Arrays.<TypeReference<?>>asList(new TypeReference<WorkerpoolOrder>() {}));
    ;

    public static final Event CLOSEDAPPORDER_EVENT = new Event("ClosedAppOrder", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final Event CLOSEDDATASETORDER_EVENT = new Event("ClosedDatasetOrder", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final Event CLOSEDREQUESTORDER_EVENT = new Event("ClosedRequestOrder", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final Event CLOSEDWORKERPOOLORDER_EVENT = new Event("ClosedWorkerpoolOrder", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final Event CREATECATEGORY_EVENT = new Event("CreateCategory", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event FAULTYCONTRIBUTION_EVENT = new Event("FaultyContribution", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event LOCK_EVENT = new Event("Lock", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ORDERSMATCHED_EVENT = new Event("OrdersMatched", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event REWARD_EVENT = new Event("Reward", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event SCHEDULERNOTICE_EVENT = new Event("SchedulerNotice", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event SEIZE_EVENT = new Event("Seize", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event SIGNEDAPPORDER_EVENT = new Event("SignedAppOrder", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final Event SIGNEDDATASETORDER_EVENT = new Event("SignedDatasetOrder", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final Event SIGNEDREQUESTORDER_EVENT = new Event("SignedRequestOrder", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final Event SIGNEDWORKERPOOLORDER_EVENT = new Event("SignedWorkerpoolOrder", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final Event TASKCLAIMED_EVENT = new Event("TaskClaimed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event TASKCONSENSUS_EVENT = new Event("TaskConsensus", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event TASKCONTRIBUTE_EVENT = new Event("TaskContribute", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event TASKFINALIZE_EVENT = new Event("TaskFinalize", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event TASKINITIALIZE_EVENT = new Event("TaskInitialize", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event TASKREOPEN_EVENT = new Event("TaskReopen", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event TASKREVEAL_EVENT = new Event("TaskReveal", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event UNLOCK_EVENT = new Event("Unlock", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected IexecInterfaceToken(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IexecInterfaceToken(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IexecInterfaceToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IexecInterfaceToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
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
        return web3j.ethLogFlowable(filter).map(new Function<Log, AccurateContributionEventResponse>() {
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

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVAL_EVENT, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventFlowable(filter);
    }

    public List<BroadcastAppOrderEventResponse> getBroadcastAppOrderEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BROADCASTAPPORDER_EVENT, transactionReceipt);
        ArrayList<BroadcastAppOrderEventResponse> responses = new ArrayList<BroadcastAppOrderEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BroadcastAppOrderEventResponse typedResponse = new BroadcastAppOrderEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.apporder = (AppOrder) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<BroadcastAppOrderEventResponse> broadcastAppOrderEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, BroadcastAppOrderEventResponse>() {
            @Override
            public BroadcastAppOrderEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BROADCASTAPPORDER_EVENT, log);
                BroadcastAppOrderEventResponse typedResponse = new BroadcastAppOrderEventResponse();
                typedResponse.log = log;
                typedResponse.apporder = (AppOrder) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public Flowable<BroadcastAppOrderEventResponse> broadcastAppOrderEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BROADCASTAPPORDER_EVENT));
        return broadcastAppOrderEventFlowable(filter);
    }

    public List<BroadcastDatasetOrderEventResponse> getBroadcastDatasetOrderEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BROADCASTDATASETORDER_EVENT, transactionReceipt);
        ArrayList<BroadcastDatasetOrderEventResponse> responses = new ArrayList<BroadcastDatasetOrderEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BroadcastDatasetOrderEventResponse typedResponse = new BroadcastDatasetOrderEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.datasetorder = (DatasetOrder) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<BroadcastDatasetOrderEventResponse> broadcastDatasetOrderEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, BroadcastDatasetOrderEventResponse>() {
            @Override
            public BroadcastDatasetOrderEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BROADCASTDATASETORDER_EVENT, log);
                BroadcastDatasetOrderEventResponse typedResponse = new BroadcastDatasetOrderEventResponse();
                typedResponse.log = log;
                typedResponse.datasetorder = (DatasetOrder) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public Flowable<BroadcastDatasetOrderEventResponse> broadcastDatasetOrderEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BROADCASTDATASETORDER_EVENT));
        return broadcastDatasetOrderEventFlowable(filter);
    }

    public List<BroadcastRequestOrderEventResponse> getBroadcastRequestOrderEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BROADCASTREQUESTORDER_EVENT, transactionReceipt);
        ArrayList<BroadcastRequestOrderEventResponse> responses = new ArrayList<BroadcastRequestOrderEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BroadcastRequestOrderEventResponse typedResponse = new BroadcastRequestOrderEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.requestorder = (RequestOrder) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<BroadcastRequestOrderEventResponse> broadcastRequestOrderEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, BroadcastRequestOrderEventResponse>() {
            @Override
            public BroadcastRequestOrderEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BROADCASTREQUESTORDER_EVENT, log);
                BroadcastRequestOrderEventResponse typedResponse = new BroadcastRequestOrderEventResponse();
                typedResponse.log = log;
                typedResponse.requestorder = (RequestOrder) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public Flowable<BroadcastRequestOrderEventResponse> broadcastRequestOrderEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BROADCASTREQUESTORDER_EVENT));
        return broadcastRequestOrderEventFlowable(filter);
    }

    public List<BroadcastWorkerpoolOrderEventResponse> getBroadcastWorkerpoolOrderEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BROADCASTWORKERPOOLORDER_EVENT, transactionReceipt);
        ArrayList<BroadcastWorkerpoolOrderEventResponse> responses = new ArrayList<BroadcastWorkerpoolOrderEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BroadcastWorkerpoolOrderEventResponse typedResponse = new BroadcastWorkerpoolOrderEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.workerpoolorder = (WorkerpoolOrder) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<BroadcastWorkerpoolOrderEventResponse> broadcastWorkerpoolOrderEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, BroadcastWorkerpoolOrderEventResponse>() {
            @Override
            public BroadcastWorkerpoolOrderEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BROADCASTWORKERPOOLORDER_EVENT, log);
                BroadcastWorkerpoolOrderEventResponse typedResponse = new BroadcastWorkerpoolOrderEventResponse();
                typedResponse.log = log;
                typedResponse.workerpoolorder = (WorkerpoolOrder) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public Flowable<BroadcastWorkerpoolOrderEventResponse> broadcastWorkerpoolOrderEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BROADCASTWORKERPOOLORDER_EVENT));
        return broadcastWorkerpoolOrderEventFlowable(filter);
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
        return web3j.ethLogFlowable(filter).map(new Function<Log, ClosedAppOrderEventResponse>() {
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
        return web3j.ethLogFlowable(filter).map(new Function<Log, ClosedDatasetOrderEventResponse>() {
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
        return web3j.ethLogFlowable(filter).map(new Function<Log, ClosedRequestOrderEventResponse>() {
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
        return web3j.ethLogFlowable(filter).map(new Function<Log, ClosedWorkerpoolOrderEventResponse>() {
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

    public List<CreateCategoryEventResponse> getCreateCategoryEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CREATECATEGORY_EVENT, transactionReceipt);
        ArrayList<CreateCategoryEventResponse> responses = new ArrayList<CreateCategoryEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CreateCategoryEventResponse typedResponse = new CreateCategoryEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.catid = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.description = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.workClockTimeRef = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CreateCategoryEventResponse> createCategoryEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CreateCategoryEventResponse>() {
            @Override
            public CreateCategoryEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CREATECATEGORY_EVENT, log);
                CreateCategoryEventResponse typedResponse = new CreateCategoryEventResponse();
                typedResponse.log = log;
                typedResponse.catid = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.name = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.description = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.workClockTimeRef = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CreateCategoryEventResponse> createCategoryEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CREATECATEGORY_EVENT));
        return createCategoryEventFlowable(filter);
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
        return web3j.ethLogFlowable(filter).map(new Function<Log, FaultyContributionEventResponse>() {
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

    public List<LockEventResponse> getLockEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(LOCK_EVENT, transactionReceipt);
        ArrayList<LockEventResponse> responses = new ArrayList<LockEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LockEventResponse typedResponse = new LockEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LockEventResponse> lockEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LockEventResponse>() {
            @Override
            public LockEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(LOCK_EVENT, log);
                LockEventResponse typedResponse = new LockEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LockEventResponse> lockEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOCK_EVENT));
        return lockEventFlowable(filter);
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
        return web3j.ethLogFlowable(filter).map(new Function<Log, OrdersMatchedEventResponse>() {
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

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public List<RewardEventResponse> getRewardEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REWARD_EVENT, transactionReceipt);
        ArrayList<RewardEventResponse> responses = new ArrayList<RewardEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RewardEventResponse typedResponse = new RewardEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.ref = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RewardEventResponse> rewardEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RewardEventResponse>() {
            @Override
            public RewardEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REWARD_EVENT, log);
                RewardEventResponse typedResponse = new RewardEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.ref = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RewardEventResponse> rewardEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REWARD_EVENT));
        return rewardEventFlowable(filter);
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
        return web3j.ethLogFlowable(filter).map(new Function<Log, SchedulerNoticeEventResponse>() {
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

    public List<SeizeEventResponse> getSeizeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SEIZE_EVENT, transactionReceipt);
        ArrayList<SeizeEventResponse> responses = new ArrayList<SeizeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SeizeEventResponse typedResponse = new SeizeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.ref = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SeizeEventResponse> seizeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, SeizeEventResponse>() {
            @Override
            public SeizeEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SEIZE_EVENT, log);
                SeizeEventResponse typedResponse = new SeizeEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.ref = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SeizeEventResponse> seizeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SEIZE_EVENT));
        return seizeEventFlowable(filter);
    }

    public List<SignedAppOrderEventResponse> getSignedAppOrderEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SIGNEDAPPORDER_EVENT, transactionReceipt);
        ArrayList<SignedAppOrderEventResponse> responses = new ArrayList<SignedAppOrderEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SignedAppOrderEventResponse typedResponse = new SignedAppOrderEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.appHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SignedAppOrderEventResponse> signedAppOrderEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, SignedAppOrderEventResponse>() {
            @Override
            public SignedAppOrderEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SIGNEDAPPORDER_EVENT, log);
                SignedAppOrderEventResponse typedResponse = new SignedAppOrderEventResponse();
                typedResponse.log = log;
                typedResponse.appHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SignedAppOrderEventResponse> signedAppOrderEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SIGNEDAPPORDER_EVENT));
        return signedAppOrderEventFlowable(filter);
    }

    public List<SignedDatasetOrderEventResponse> getSignedDatasetOrderEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SIGNEDDATASETORDER_EVENT, transactionReceipt);
        ArrayList<SignedDatasetOrderEventResponse> responses = new ArrayList<SignedDatasetOrderEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SignedDatasetOrderEventResponse typedResponse = new SignedDatasetOrderEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.datasetHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SignedDatasetOrderEventResponse> signedDatasetOrderEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, SignedDatasetOrderEventResponse>() {
            @Override
            public SignedDatasetOrderEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SIGNEDDATASETORDER_EVENT, log);
                SignedDatasetOrderEventResponse typedResponse = new SignedDatasetOrderEventResponse();
                typedResponse.log = log;
                typedResponse.datasetHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SignedDatasetOrderEventResponse> signedDatasetOrderEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SIGNEDDATASETORDER_EVENT));
        return signedDatasetOrderEventFlowable(filter);
    }

    public List<SignedRequestOrderEventResponse> getSignedRequestOrderEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SIGNEDREQUESTORDER_EVENT, transactionReceipt);
        ArrayList<SignedRequestOrderEventResponse> responses = new ArrayList<SignedRequestOrderEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SignedRequestOrderEventResponse typedResponse = new SignedRequestOrderEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.requestHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SignedRequestOrderEventResponse> signedRequestOrderEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, SignedRequestOrderEventResponse>() {
            @Override
            public SignedRequestOrderEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SIGNEDREQUESTORDER_EVENT, log);
                SignedRequestOrderEventResponse typedResponse = new SignedRequestOrderEventResponse();
                typedResponse.log = log;
                typedResponse.requestHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SignedRequestOrderEventResponse> signedRequestOrderEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SIGNEDREQUESTORDER_EVENT));
        return signedRequestOrderEventFlowable(filter);
    }

    public List<SignedWorkerpoolOrderEventResponse> getSignedWorkerpoolOrderEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SIGNEDWORKERPOOLORDER_EVENT, transactionReceipt);
        ArrayList<SignedWorkerpoolOrderEventResponse> responses = new ArrayList<SignedWorkerpoolOrderEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SignedWorkerpoolOrderEventResponse typedResponse = new SignedWorkerpoolOrderEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.workerpoolHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SignedWorkerpoolOrderEventResponse> signedWorkerpoolOrderEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, SignedWorkerpoolOrderEventResponse>() {
            @Override
            public SignedWorkerpoolOrderEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SIGNEDWORKERPOOLORDER_EVENT, log);
                SignedWorkerpoolOrderEventResponse typedResponse = new SignedWorkerpoolOrderEventResponse();
                typedResponse.log = log;
                typedResponse.workerpoolHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SignedWorkerpoolOrderEventResponse> signedWorkerpoolOrderEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SIGNEDWORKERPOOLORDER_EVENT));
        return signedWorkerpoolOrderEventFlowable(filter);
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
        return web3j.ethLogFlowable(filter).map(new Function<Log, TaskClaimedEventResponse>() {
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
        return web3j.ethLogFlowable(filter).map(new Function<Log, TaskConsensusEventResponse>() {
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
        return web3j.ethLogFlowable(filter).map(new Function<Log, TaskContributeEventResponse>() {
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
        return web3j.ethLogFlowable(filter).map(new Function<Log, TaskFinalizeEventResponse>() {
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
        return web3j.ethLogFlowable(filter).map(new Function<Log, TaskInitializeEventResponse>() {
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
        return web3j.ethLogFlowable(filter).map(new Function<Log, TaskReopenEventResponse>() {
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
        return web3j.ethLogFlowable(filter).map(new Function<Log, TaskRevealEventResponse>() {
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

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public List<UnlockEventResponse> getUnlockEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(UNLOCK_EVENT, transactionReceipt);
        ArrayList<UnlockEventResponse> responses = new ArrayList<UnlockEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UnlockEventResponse typedResponse = new UnlockEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UnlockEventResponse> unlockEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, UnlockEventResponse>() {
            @Override
            public UnlockEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(UNLOCK_EVENT, log);
                UnlockEventResponse typedResponse = new UnlockEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<UnlockEventResponse> unlockEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UNLOCK_EVENT));
        return unlockEventFlowable(filter);
    }

    public RemoteFunctionCall<String> UniswapV2Router() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_UNISWAPV2ROUTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> allowance(String param0, String param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ALLOWANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.Address(160, param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> appregistry() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_APPREGISTRY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> approve(String param0, BigInteger param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> approveAndCall(String param0, BigInteger param1, byte[] param2) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_APPROVEANDCALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1), 
                new org.web3j.abi.datatypes.DynamicBytes(param2)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> broadcastAppOrder(AppOrder param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BROADCASTAPPORDER, 
                Arrays.<Type>asList(param0), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> broadcastDatasetOrder(DatasetOrder param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BROADCASTDATASETORDER, 
                Arrays.<Type>asList(param0), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> broadcastRequestOrder(RequestOrder param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BROADCASTREQUESTORDER, 
                Arrays.<Type>asList(param0), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> broadcastWorkerpoolOrder(WorkerpoolOrder param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BROADCASTWORKERPOOLORDER, 
                Arrays.<Type>asList(param0), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> callbackgas() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CALLBACKGAS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> claim(byte[] param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLAIM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> claimArray(List<byte[]> param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLAIMARRAY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(param0, org.web3j.abi.datatypes.generated.Bytes32.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> configure(String param0, String param1, String param2, BigInteger param3, String param4, String param5, String param6, String param7) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CONFIGURE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.Utf8String(param1), 
                new org.web3j.abi.datatypes.Utf8String(param2), 
                new org.web3j.abi.datatypes.generated.Uint8(param3), 
                new org.web3j.abi.datatypes.Address(160, param4), 
                new org.web3j.abi.datatypes.Address(160, param5), 
                new org.web3j.abi.datatypes.Address(160, param6), 
                new org.web3j.abi.datatypes.Address(160, param7)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> contribute(byte[] param0, byte[] param1, byte[] param2, String param3, byte[] param4, byte[] param5) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CONTRIBUTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0), 
                new org.web3j.abi.datatypes.generated.Bytes32(param1), 
                new org.web3j.abi.datatypes.generated.Bytes32(param2), 
                new org.web3j.abi.datatypes.Address(160, param3), 
                new org.web3j.abi.datatypes.DynamicBytes(param4), 
                new org.web3j.abi.datatypes.DynamicBytes(param5)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> contributeAndFinalize(byte[] param0, byte[] param1, byte[] param2, byte[] param3, String param4, byte[] param5, byte[] param6) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CONTRIBUTEANDFINALIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0), 
                new org.web3j.abi.datatypes.generated.Bytes32(param1), 
                new org.web3j.abi.datatypes.DynamicBytes(param2), 
                new org.web3j.abi.datatypes.DynamicBytes(param3), 
                new org.web3j.abi.datatypes.Address(160, param4), 
                new org.web3j.abi.datatypes.DynamicBytes(param5), 
                new org.web3j.abi.datatypes.DynamicBytes(param6)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> contribution_deadline_ratio() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CONTRIBUTION_DEADLINE_RATIO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> countCategory() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_COUNTCATEGORY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> createCategory(String param0, String param1, BigInteger param2) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CREATECATEGORY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0), 
                new org.web3j.abi.datatypes.Utf8String(param1), 
                new org.web3j.abi.datatypes.generated.Uint256(param2)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> datasetregistry() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DATASETREGISTRY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> decimals() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DECIMALS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> decreaseAllowance(String param0, BigInteger param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DECREASEALLOWANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> deposit(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> depositEth() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSITETH, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> depositEthFor(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSITETHFOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> depositFor(BigInteger param0, String param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSITFOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.Address(160, param1)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> depositForArray(List<BigInteger> param0, List<String> param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSITFORARRAY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(param0, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(param1, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<EIP712Domain> domain() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DOMAIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<EIP712Domain>() {}));
        return executeRemoteCallSingleValueReturn(function, EIP712Domain.class);
    }

    public RemoteFunctionCall<byte[]> eip712domain_separator() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_EIP712DOMAIN_SEPARATOR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<BigInteger> estimateDepositEthSent(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ESTIMATEDEPOSITETHSENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> estimateDepositTokenWanted(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ESTIMATEDEPOSITTOKENWANTED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> estimateWithdrawEthWanted(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ESTIMATEWITHDRAWETHWANTED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> estimateWithdrawTokenSent(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ESTIMATEWITHDRAWTOKENSENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> final_deadline_ratio() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_FINAL_DEADLINE_RATIO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> finalize(byte[] param0, byte[] param1, byte[] param2) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FINALIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0), 
                new org.web3j.abi.datatypes.DynamicBytes(param1), 
                new org.web3j.abi.datatypes.DynamicBytes(param2)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> frozenOf(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_FROZENOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> groupmember_purpose() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GROUPMEMBER_PURPOSE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> importScore(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_IMPORTSCORE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> increaseAllowance(String param0, BigInteger param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INCREASEALLOWANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> initialize(byte[] param0, BigInteger param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INITIALIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> initializeAndClaimArray(List<byte[]> param0, List<BigInteger> param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INITIALIZEANDCLAIMARRAY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(param0, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(param1, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> initializeArray(List<byte[]> param0, List<BigInteger> param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INITIALIZEARRAY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(param0, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(param1, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> kitty_address() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_KITTY_ADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> kitty_min() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_KITTY_MIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> kitty_ratio() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_KITTY_RATIO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> manageAppOrder(AppOrderOperation param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MANAGEAPPORDER, 
                Arrays.<Type>asList(param0), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> manageDatasetOrder(DatasetOrderOperation param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MANAGEDATASETORDER, 
                Arrays.<Type>asList(param0), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> manageRequestOrder(RequestOrderOperation param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MANAGEREQUESTORDER, 
                Arrays.<Type>asList(param0), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> manageWorkerpoolOrder(WorkerpoolOrderOperation param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MANAGEWORKERPOOLORDER, 
                Arrays.<Type>asList(param0), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> matchOrders(AppOrder param0, DatasetOrder param1, WorkerpoolOrder param2, RequestOrder param3) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MATCHORDERS, 
                Arrays.<Type>asList(param0, 
                param1, 
                param2, 
                param3), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> matchOrdersWithEth(AppOrder param0, DatasetOrder param1, WorkerpoolOrder param2, RequestOrder param3) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MATCHORDERSWITHETH, 
                Arrays.<Type>asList(param0, 
                param1, 
                param2, 
                param3), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> name() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> receiveApproval(String param0, BigInteger param1, String param2, byte[] param3) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RECEIVEAPPROVAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1), 
                new org.web3j.abi.datatypes.Address(160, param2), 
                new org.web3j.abi.datatypes.DynamicBytes(param3)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> recover() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RECOVER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> reopen(byte[] param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REOPEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> requestToken(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REQUESTTOKEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> requestTokenFor(BigInteger param0, String param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REQUESTTOKENFOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.Address(160, param1)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<byte[]> resultFor(byte[] param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_RESULTFOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> reveal(byte[] param0, byte[] param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REVEAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0), 
                new org.web3j.abi.datatypes.generated.Bytes32(param1)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> reveal_deadline_ratio() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REVEAL_DEADLINE_RATIO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> safeDepositEth(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SAFEDEPOSITETH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> safeDepositEthFor(BigInteger param0, String param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SAFEDEPOSITETHFOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.Address(160, param1)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> safeWithdrawEth(BigInteger param0, BigInteger param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SAFEWITHDRAWETH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> safeWithdrawEthTo(BigInteger param0, BigInteger param1, String param2) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SAFEWITHDRAWETHTO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1), 
                new org.web3j.abi.datatypes.Address(160, param2)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setCallbackGas(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETCALLBACKGAS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setName(String ens, String name) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETNAME, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, ens), 
                new org.web3j.abi.datatypes.Utf8String(name)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setTeeBroker(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETTEEBROKER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> symbol() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> teebroker() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TEEBROKER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> token() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOKEN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> totalSupply() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transfer(String param0, BigInteger param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferFrom(String param0, String param1, BigInteger param2) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.Address(160, param1), 
                new org.web3j.abi.datatypes.generated.Uint256(param2)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateDomainSeparator() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATEDOMAINSEPARATOR, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> verifyPresignature(String param0, byte[] param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VERIFYPRESIGNATURE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.generated.Bytes32(param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> verifyPresignatureOrSignature(String param0, byte[] param1, byte[] param2) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VERIFYPRESIGNATUREORSIGNATURE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.generated.Bytes32(param1), 
                new org.web3j.abi.datatypes.DynamicBytes(param2)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> verifySignature(String param0, byte[] param1, byte[] param2) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VERIFYSIGNATURE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.generated.Bytes32(param1), 
                new org.web3j.abi.datatypes.DynamicBytes(param2)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Account> viewAccount(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VIEWACCOUNT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Account>() {}));
        return executeRemoteCallSingleValueReturn(function, Account.class);
    }

    public RemoteFunctionCall<Category> viewCategory(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VIEWCATEGORY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Category>() {}));
        return executeRemoteCallSingleValueReturn(function, Category.class);
    }

    public RemoteFunctionCall<BigInteger> viewConsumed(byte[] param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VIEWCONSUMED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Contribution> viewContribution(byte[] param0, String param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VIEWCONTRIBUTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0), 
                new org.web3j.abi.datatypes.Address(160, param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Contribution>() {}));
        return executeRemoteCallSingleValueReturn(function, Contribution.class);
    }

    public RemoteFunctionCall<Deal> viewDeal(byte[] param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VIEWDEAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Deal>() {}));
        return executeRemoteCallSingleValueReturn(function, Deal.class);
    }

    public RemoteFunctionCall<String> viewPresigned(byte[] param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VIEWPRESIGNED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> viewScore(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VIEWSCORE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Task> viewTask(byte[] param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VIEWTASK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Task>() {}));
        return executeRemoteCallSingleValueReturn(function, Task.class);
    }

    public RemoteFunctionCall<TransactionReceipt> withdraw(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawEth(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAWETH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawEthTo(BigInteger param0, String param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAWETHTO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.Address(160, param1)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawTo(BigInteger param0, String param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAWTO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.Address(160, param1)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> workerpool_stake_ratio() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_WORKERPOOL_STAKE_RATIO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> workerpoolregistry() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_WORKERPOOLREGISTRY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static IexecInterfaceToken load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IexecInterfaceToken(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IexecInterfaceToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IexecInterfaceToken(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IexecInterfaceToken load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IexecInterfaceToken(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IexecInterfaceToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IexecInterfaceToken(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class AppOrder extends DynamicStruct {
        public String app;

        public BigInteger appprice;

        public BigInteger volume;

        public byte[] tag;

        public String datasetrestrict;

        public String workerpoolrestrict;

        public String requesterrestrict;

        public byte[] salt;

        public byte[] sign;

        public AppOrder(String app, BigInteger appprice, BigInteger volume, byte[] tag, String datasetrestrict, String workerpoolrestrict, String requesterrestrict, byte[] salt, byte[] sign) {
            super(new org.web3j.abi.datatypes.Address(app),new org.web3j.abi.datatypes.generated.Uint256(appprice),new org.web3j.abi.datatypes.generated.Uint256(volume),new org.web3j.abi.datatypes.generated.Bytes32(tag),new org.web3j.abi.datatypes.Address(datasetrestrict),new org.web3j.abi.datatypes.Address(workerpoolrestrict),new org.web3j.abi.datatypes.Address(requesterrestrict),new org.web3j.abi.datatypes.generated.Bytes32(salt),new org.web3j.abi.datatypes.DynamicBytes(sign));
            this.app = app;
            this.appprice = appprice;
            this.volume = volume;
            this.tag = tag;
            this.datasetrestrict = datasetrestrict;
            this.workerpoolrestrict = workerpoolrestrict;
            this.requesterrestrict = requesterrestrict;
            this.salt = salt;
            this.sign = sign;
        }

        public AppOrder(Address app, Uint256 appprice, Uint256 volume, Bytes32 tag, Address datasetrestrict, Address workerpoolrestrict, Address requesterrestrict, Bytes32 salt, DynamicBytes sign) {
            super(app,appprice,volume,tag,datasetrestrict,workerpoolrestrict,requesterrestrict,salt,sign);
            this.app = app.getValue();
            this.appprice = appprice.getValue();
            this.volume = volume.getValue();
            this.tag = tag.getValue();
            this.datasetrestrict = datasetrestrict.getValue();
            this.workerpoolrestrict = workerpoolrestrict.getValue();
            this.requesterrestrict = requesterrestrict.getValue();
            this.salt = salt.getValue();
            this.sign = sign.getValue();
        }
    }

    public static class DatasetOrder extends DynamicStruct {
        public String dataset;

        public BigInteger datasetprice;

        public BigInteger volume;

        public byte[] tag;

        public String apprestrict;

        public String workerpoolrestrict;

        public String requesterrestrict;

        public byte[] salt;

        public byte[] sign;

        public DatasetOrder(String dataset, BigInteger datasetprice, BigInteger volume, byte[] tag, String apprestrict, String workerpoolrestrict, String requesterrestrict, byte[] salt, byte[] sign) {
            super(new org.web3j.abi.datatypes.Address(dataset),new org.web3j.abi.datatypes.generated.Uint256(datasetprice),new org.web3j.abi.datatypes.generated.Uint256(volume),new org.web3j.abi.datatypes.generated.Bytes32(tag),new org.web3j.abi.datatypes.Address(apprestrict),new org.web3j.abi.datatypes.Address(workerpoolrestrict),new org.web3j.abi.datatypes.Address(requesterrestrict),new org.web3j.abi.datatypes.generated.Bytes32(salt),new org.web3j.abi.datatypes.DynamicBytes(sign));
            this.dataset = dataset;
            this.datasetprice = datasetprice;
            this.volume = volume;
            this.tag = tag;
            this.apprestrict = apprestrict;
            this.workerpoolrestrict = workerpoolrestrict;
            this.requesterrestrict = requesterrestrict;
            this.salt = salt;
            this.sign = sign;
        }

        public DatasetOrder(Address dataset, Uint256 datasetprice, Uint256 volume, Bytes32 tag, Address apprestrict, Address workerpoolrestrict, Address requesterrestrict, Bytes32 salt, DynamicBytes sign) {
            super(dataset,datasetprice,volume,tag,apprestrict,workerpoolrestrict,requesterrestrict,salt,sign);
            this.dataset = dataset.getValue();
            this.datasetprice = datasetprice.getValue();
            this.volume = volume.getValue();
            this.tag = tag.getValue();
            this.apprestrict = apprestrict.getValue();
            this.workerpoolrestrict = workerpoolrestrict.getValue();
            this.requesterrestrict = requesterrestrict.getValue();
            this.salt = salt.getValue();
            this.sign = sign.getValue();
        }
    }

    public static class RequestOrder extends DynamicStruct {
        public String app;

        public BigInteger appmaxprice;

        public String dataset;

        public BigInteger datasetmaxprice;

        public String workerpool;

        public BigInteger workerpoolmaxprice;

        public String requester;

        public BigInteger volume;

        public byte[] tag;

        public BigInteger category;

        public BigInteger trust;

        public String beneficiary;

        public String callback;

        public String params;

        public byte[] salt;

        public byte[] sign;

        public RequestOrder(String app, BigInteger appmaxprice, String dataset, BigInteger datasetmaxprice, String workerpool, BigInteger workerpoolmaxprice, String requester, BigInteger volume, byte[] tag, BigInteger category, BigInteger trust, String beneficiary, String callback, String params, byte[] salt, byte[] sign) {
            super(new org.web3j.abi.datatypes.Address(app),new org.web3j.abi.datatypes.generated.Uint256(appmaxprice),new org.web3j.abi.datatypes.Address(dataset),new org.web3j.abi.datatypes.generated.Uint256(datasetmaxprice),new org.web3j.abi.datatypes.Address(workerpool),new org.web3j.abi.datatypes.generated.Uint256(workerpoolmaxprice),new org.web3j.abi.datatypes.Address(requester),new org.web3j.abi.datatypes.generated.Uint256(volume),new org.web3j.abi.datatypes.generated.Bytes32(tag),new org.web3j.abi.datatypes.generated.Uint256(category),new org.web3j.abi.datatypes.generated.Uint256(trust),new org.web3j.abi.datatypes.Address(beneficiary),new org.web3j.abi.datatypes.Address(callback),new org.web3j.abi.datatypes.Utf8String(params),new org.web3j.abi.datatypes.generated.Bytes32(salt),new org.web3j.abi.datatypes.DynamicBytes(sign));
            this.app = app;
            this.appmaxprice = appmaxprice;
            this.dataset = dataset;
            this.datasetmaxprice = datasetmaxprice;
            this.workerpool = workerpool;
            this.workerpoolmaxprice = workerpoolmaxprice;
            this.requester = requester;
            this.volume = volume;
            this.tag = tag;
            this.category = category;
            this.trust = trust;
            this.beneficiary = beneficiary;
            this.callback = callback;
            this.params = params;
            this.salt = salt;
            this.sign = sign;
        }

        public RequestOrder(Address app, Uint256 appmaxprice, Address dataset, Uint256 datasetmaxprice, Address workerpool, Uint256 workerpoolmaxprice, Address requester, Uint256 volume, Bytes32 tag, Uint256 category, Uint256 trust, Address beneficiary, Address callback, Utf8String params, Bytes32 salt, DynamicBytes sign) {
            super(app,appmaxprice,dataset,datasetmaxprice,workerpool,workerpoolmaxprice,requester,volume,tag,category,trust,beneficiary,callback,params,salt,sign);
            this.app = app.getValue();
            this.appmaxprice = appmaxprice.getValue();
            this.dataset = dataset.getValue();
            this.datasetmaxprice = datasetmaxprice.getValue();
            this.workerpool = workerpool.getValue();
            this.workerpoolmaxprice = workerpoolmaxprice.getValue();
            this.requester = requester.getValue();
            this.volume = volume.getValue();
            this.tag = tag.getValue();
            this.category = category.getValue();
            this.trust = trust.getValue();
            this.beneficiary = beneficiary.getValue();
            this.callback = callback.getValue();
            this.params = params.getValue();
            this.salt = salt.getValue();
            this.sign = sign.getValue();
        }
    }

    public static class WorkerpoolOrder extends DynamicStruct {
        public String workerpool;

        public BigInteger workerpoolprice;

        public BigInteger volume;

        public byte[] tag;

        public BigInteger category;

        public BigInteger trust;

        public String apprestrict;

        public String datasetrestrict;

        public String requesterrestrict;

        public byte[] salt;

        public byte[] sign;

        public WorkerpoolOrder(String workerpool, BigInteger workerpoolprice, BigInteger volume, byte[] tag, BigInteger category, BigInteger trust, String apprestrict, String datasetrestrict, String requesterrestrict, byte[] salt, byte[] sign) {
            super(new org.web3j.abi.datatypes.Address(workerpool),new org.web3j.abi.datatypes.generated.Uint256(workerpoolprice),new org.web3j.abi.datatypes.generated.Uint256(volume),new org.web3j.abi.datatypes.generated.Bytes32(tag),new org.web3j.abi.datatypes.generated.Uint256(category),new org.web3j.abi.datatypes.generated.Uint256(trust),new org.web3j.abi.datatypes.Address(apprestrict),new org.web3j.abi.datatypes.Address(datasetrestrict),new org.web3j.abi.datatypes.Address(requesterrestrict),new org.web3j.abi.datatypes.generated.Bytes32(salt),new org.web3j.abi.datatypes.DynamicBytes(sign));
            this.workerpool = workerpool;
            this.workerpoolprice = workerpoolprice;
            this.volume = volume;
            this.tag = tag;
            this.category = category;
            this.trust = trust;
            this.apprestrict = apprestrict;
            this.datasetrestrict = datasetrestrict;
            this.requesterrestrict = requesterrestrict;
            this.salt = salt;
            this.sign = sign;
        }

        public WorkerpoolOrder(Address workerpool, Uint256 workerpoolprice, Uint256 volume, Bytes32 tag, Uint256 category, Uint256 trust, Address apprestrict, Address datasetrestrict, Address requesterrestrict, Bytes32 salt, DynamicBytes sign) {
            super(workerpool,workerpoolprice,volume,tag,category,trust,apprestrict,datasetrestrict,requesterrestrict,salt,sign);
            this.workerpool = workerpool.getValue();
            this.workerpoolprice = workerpoolprice.getValue();
            this.volume = volume.getValue();
            this.tag = tag.getValue();
            this.category = category.getValue();
            this.trust = trust.getValue();
            this.apprestrict = apprestrict.getValue();
            this.datasetrestrict = datasetrestrict.getValue();
            this.requesterrestrict = requesterrestrict.getValue();
            this.salt = salt.getValue();
            this.sign = sign.getValue();
        }
    }

    public static class EIP712Domain extends DynamicStruct {
        public String name;

        public String version;

        public BigInteger chainId;

        public String verifyingContract;

        public EIP712Domain(String name, String version, BigInteger chainId, String verifyingContract) {
            super(new org.web3j.abi.datatypes.Utf8String(name),new org.web3j.abi.datatypes.Utf8String(version),new org.web3j.abi.datatypes.generated.Uint256(chainId),new org.web3j.abi.datatypes.Address(verifyingContract));
            this.name = name;
            this.version = version;
            this.chainId = chainId;
            this.verifyingContract = verifyingContract;
        }

        public EIP712Domain(Utf8String name, Utf8String version, Uint256 chainId, Address verifyingContract) {
            super(name,version,chainId,verifyingContract);
            this.name = name.getValue();
            this.version = version.getValue();
            this.chainId = chainId.getValue();
            this.verifyingContract = verifyingContract.getValue();
        }
    }

    public static class Account extends StaticStruct {
        public BigInteger stake;

        public BigInteger locked;

        public Account(BigInteger stake, BigInteger locked) {
            super(new org.web3j.abi.datatypes.generated.Uint256(stake),new org.web3j.abi.datatypes.generated.Uint256(locked));
            this.stake = stake;
            this.locked = locked;
        }

        public Account(Uint256 stake, Uint256 locked) {
            super(stake,locked);
            this.stake = stake.getValue();
            this.locked = locked.getValue();
        }
    }

    public static class Category extends DynamicStruct {
        public String name;

        public String description;

        public BigInteger workClockTimeRef;

        public Category(String name, String description, BigInteger workClockTimeRef) {
            super(new org.web3j.abi.datatypes.Utf8String(name),new org.web3j.abi.datatypes.Utf8String(description),new org.web3j.abi.datatypes.generated.Uint256(workClockTimeRef));
            this.name = name;
            this.description = description;
            this.workClockTimeRef = workClockTimeRef;
        }

        public Category(Utf8String name, Utf8String description, Uint256 workClockTimeRef) {
            super(name,description,workClockTimeRef);
            this.name = name.getValue();
            this.description = description.getValue();
            this.workClockTimeRef = workClockTimeRef.getValue();
        }
    }

    public static class Contribution extends StaticStruct {
        public BigInteger status;

        public byte[] resultHash;

        public byte[] resultSeal;

        public String enclaveChallenge;

        public BigInteger weight;

        public Contribution(BigInteger status, byte[] resultHash, byte[] resultSeal, String enclaveChallenge, BigInteger weight) {
            super(new org.web3j.abi.datatypes.generated.Uint8(status),new org.web3j.abi.datatypes.generated.Bytes32(resultHash),new org.web3j.abi.datatypes.generated.Bytes32(resultSeal),new org.web3j.abi.datatypes.Address(enclaveChallenge),new org.web3j.abi.datatypes.generated.Uint256(weight));
            this.status = status;
            this.resultHash = resultHash;
            this.resultSeal = resultSeal;
            this.enclaveChallenge = enclaveChallenge;
            this.weight = weight;
        }

        public Contribution(Uint8 status, Bytes32 resultHash, Bytes32 resultSeal, Address enclaveChallenge, Uint256 weight) {
            super(status,resultHash,resultSeal,enclaveChallenge,weight);
            this.status = status.getValue();
            this.resultHash = resultHash.getValue();
            this.resultSeal = resultSeal.getValue();
            this.enclaveChallenge = enclaveChallenge.getValue();
            this.weight = weight.getValue();
        }
    }

    public static class Resource extends StaticStruct {
        public String pointer;

        public String owner;

        public BigInteger price;

        public Resource(String pointer, String owner, BigInteger price) {
            super(new org.web3j.abi.datatypes.Address(pointer),new org.web3j.abi.datatypes.Address(owner),new org.web3j.abi.datatypes.generated.Uint256(price));
            this.pointer = pointer;
            this.owner = owner;
            this.price = price;
        }

        public Resource(Address pointer, Address owner, Uint256 price) {
            super(pointer,owner,price);
            this.pointer = pointer.getValue();
            this.owner = owner.getValue();
            this.price = price.getValue();
        }
    }

    public static class Task extends DynamicStruct {
        public BigInteger status;

        public byte[] dealid;

        public BigInteger idx;

        public BigInteger timeref;

        public BigInteger contributionDeadline;

        public BigInteger revealDeadline;

        public BigInteger finalDeadline;

        public byte[] consensusValue;

        public BigInteger revealCounter;

        public BigInteger winnerCounter;

        public List<Address> contributors;

        public byte[] resultDigest;

        public byte[] results;

        public BigInteger resultsTimestamp;

        public byte[] resultsCallback;

        //REMOVED REQUIRED BUT FAILING CONSTRUCTOR

        public Task(Uint8 status, Bytes32 dealid, Uint256 idx, Uint256 timeref, Uint256 contributionDeadline, Uint256 revealDeadline, Uint256 finalDeadline, Bytes32 consensusValue, Uint256 revealCounter, Uint256 winnerCounter, DynamicArray<Address> contributors, Bytes32 resultDigest, DynamicBytes results, Uint256 resultsTimestamp, DynamicBytes resultsCallback) {
            super(status,dealid,idx,timeref,contributionDeadline,revealDeadline,finalDeadline,consensusValue,revealCounter,winnerCounter,contributors,resultDigest,results,resultsTimestamp,resultsCallback);
            this.status = status.getValue();
            this.dealid = dealid.getValue();
            this.idx = idx.getValue();
            this.timeref = timeref.getValue();
            this.contributionDeadline = contributionDeadline.getValue();
            this.revealDeadline = revealDeadline.getValue();
            this.finalDeadline = finalDeadline.getValue();
            this.consensusValue = consensusValue.getValue();
            this.revealCounter = revealCounter.getValue();
            this.winnerCounter = winnerCounter.getValue();
            this.contributors = contributors.getValue();
            this.resultDigest = resultDigest.getValue();
            this.results = results.getValue();
            this.resultsTimestamp = resultsTimestamp.getValue();
            this.resultsCallback = resultsCallback.getValue();
        }
    }

    public static class AppOrderOperation extends DynamicStruct {
        public AppOrder order;

        public BigInteger operation;

        public byte[] sign;

        public AppOrderOperation(AppOrder order, BigInteger operation, byte[] sign) {
            super(order,new org.web3j.abi.datatypes.generated.Uint8(operation),new org.web3j.abi.datatypes.DynamicBytes(sign));
            this.order = order;
            this.operation = operation;
            this.sign = sign;
        }

        public AppOrderOperation(AppOrder order, Uint8 operation, DynamicBytes sign) {
            super(order,operation,sign);
            this.order = order;
            this.operation = operation.getValue();
            this.sign = sign.getValue();
        }
    }

    public static class DatasetOrderOperation extends DynamicStruct {
        public DatasetOrder order;

        public BigInteger operation;

        public byte[] sign;

        public DatasetOrderOperation(DatasetOrder order, BigInteger operation, byte[] sign) {
            super(order,new org.web3j.abi.datatypes.generated.Uint8(operation),new org.web3j.abi.datatypes.DynamicBytes(sign));
            this.order = order;
            this.operation = operation;
            this.sign = sign;
        }

        public DatasetOrderOperation(DatasetOrder order, Uint8 operation, DynamicBytes sign) {
            super(order,operation,sign);
            this.order = order;
            this.operation = operation.getValue();
            this.sign = sign.getValue();
        }
    }

    public static class RequestOrderOperation extends DynamicStruct {
        public RequestOrder order;

        public BigInteger operation;

        public byte[] sign;

        public RequestOrderOperation(RequestOrder order, BigInteger operation, byte[] sign) {
            super(order,new org.web3j.abi.datatypes.generated.Uint8(operation),new org.web3j.abi.datatypes.DynamicBytes(sign));
            this.order = order;
            this.operation = operation;
            this.sign = sign;
        }

        public RequestOrderOperation(RequestOrder order, Uint8 operation, DynamicBytes sign) {
            super(order,operation,sign);
            this.order = order;
            this.operation = operation.getValue();
            this.sign = sign.getValue();
        }
    }

    public static class WorkerpoolOrderOperation extends DynamicStruct {
        public WorkerpoolOrder order;

        public BigInteger operation;

        public byte[] sign;

        public WorkerpoolOrderOperation(WorkerpoolOrder order, BigInteger operation, byte[] sign) {
            super(order,new org.web3j.abi.datatypes.generated.Uint8(operation),new org.web3j.abi.datatypes.DynamicBytes(sign));
            this.order = order;
            this.operation = operation;
            this.sign = sign;
        }

        public WorkerpoolOrderOperation(WorkerpoolOrder order, Uint8 operation, DynamicBytes sign) {
            super(order,operation,sign);
            this.order = order;
            this.operation = operation.getValue();
            this.sign = sign.getValue();
        }
    }

    public static class Deal extends DynamicStruct {
        public Resource app;

        public Resource dataset;

        public Resource workerpool;

        public BigInteger trust;

        public BigInteger category;

        public byte[] tag;

        public String requester;

        public String beneficiary;

        public String callback;

        public String params;

        public BigInteger startTime;

        public BigInteger botFirst;

        public BigInteger botSize;

        public BigInteger workerStake;

        public BigInteger schedulerRewardRatio;

        public Deal(Resource app, Resource dataset, Resource workerpool, BigInteger trust, BigInteger category, byte[] tag, String requester, String beneficiary, String callback, String params, BigInteger startTime, BigInteger botFirst, BigInteger botSize, BigInteger workerStake, BigInteger schedulerRewardRatio) {
            super(app,dataset,workerpool,new org.web3j.abi.datatypes.generated.Uint256(trust),new org.web3j.abi.datatypes.generated.Uint256(category),new org.web3j.abi.datatypes.generated.Bytes32(tag),new org.web3j.abi.datatypes.Address(requester),new org.web3j.abi.datatypes.Address(beneficiary),new org.web3j.abi.datatypes.Address(callback),new org.web3j.abi.datatypes.Utf8String(params),new org.web3j.abi.datatypes.generated.Uint256(startTime),new org.web3j.abi.datatypes.generated.Uint256(botFirst),new org.web3j.abi.datatypes.generated.Uint256(botSize),new org.web3j.abi.datatypes.generated.Uint256(workerStake),new org.web3j.abi.datatypes.generated.Uint256(schedulerRewardRatio));
            this.app = app;
            this.dataset = dataset;
            this.workerpool = workerpool;
            this.trust = trust;
            this.category = category;
            this.tag = tag;
            this.requester = requester;
            this.beneficiary = beneficiary;
            this.callback = callback;
            this.params = params;
            this.startTime = startTime;
            this.botFirst = botFirst;
            this.botSize = botSize;
            this.workerStake = workerStake;
            this.schedulerRewardRatio = schedulerRewardRatio;
        }

        public Deal(Resource app, Resource dataset, Resource workerpool, Uint256 trust, Uint256 category, Bytes32 tag, Address requester, Address beneficiary, Address callback, Utf8String params, Uint256 startTime, Uint256 botFirst, Uint256 botSize, Uint256 workerStake, Uint256 schedulerRewardRatio) {
            super(app,dataset,workerpool,trust,category,tag,requester,beneficiary,callback,params,startTime,botFirst,botSize,workerStake,schedulerRewardRatio);
            this.app = app;
            this.dataset = dataset;
            this.workerpool = workerpool;
            this.trust = trust.getValue();
            this.category = category.getValue();
            this.tag = tag.getValue();
            this.requester = requester.getValue();
            this.beneficiary = beneficiary.getValue();
            this.callback = callback.getValue();
            this.params = params.getValue();
            this.startTime = startTime.getValue();
            this.botFirst = botFirst.getValue();
            this.botSize = botSize.getValue();
            this.workerStake = workerStake.getValue();
            this.schedulerRewardRatio = schedulerRewardRatio.getValue();
        }
    }

    public static class AccurateContributionEventResponse extends BaseEventResponse {
        public String worker;

        public byte[] taskid;
    }

    public static class ApprovalEventResponse extends BaseEventResponse {
        public String owner;

        public String spender;

        public BigInteger value;
    }

    public static class BroadcastAppOrderEventResponse extends BaseEventResponse {
        public AppOrder apporder;
    }

    public static class BroadcastDatasetOrderEventResponse extends BaseEventResponse {
        public DatasetOrder datasetorder;
    }

    public static class BroadcastRequestOrderEventResponse extends BaseEventResponse {
        public RequestOrder requestorder;
    }

    public static class BroadcastWorkerpoolOrderEventResponse extends BaseEventResponse {
        public WorkerpoolOrder workerpoolorder;
    }

    public static class ClosedAppOrderEventResponse extends BaseEventResponse {
        public byte[] appHash;
    }

    public static class ClosedDatasetOrderEventResponse extends BaseEventResponse {
        public byte[] datasetHash;
    }

    public static class ClosedRequestOrderEventResponse extends BaseEventResponse {
        public byte[] requestHash;
    }

    public static class ClosedWorkerpoolOrderEventResponse extends BaseEventResponse {
        public byte[] workerpoolHash;
    }

    public static class CreateCategoryEventResponse extends BaseEventResponse {
        public BigInteger catid;

        public String name;

        public String description;

        public BigInteger workClockTimeRef;
    }

    public static class FaultyContributionEventResponse extends BaseEventResponse {
        public String worker;

        public byte[] taskid;
    }

    public static class LockEventResponse extends BaseEventResponse {
        public String owner;

        public BigInteger amount;
    }

    public static class OrdersMatchedEventResponse extends BaseEventResponse {
        public byte[] dealid;

        public byte[] appHash;

        public byte[] datasetHash;

        public byte[] workerpoolHash;

        public byte[] requestHash;

        public BigInteger volume;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class RewardEventResponse extends BaseEventResponse {
        public String owner;

        public BigInteger amount;

        public byte[] ref;
    }

    public static class SchedulerNoticeEventResponse extends BaseEventResponse {
        public String workerpool;

        public byte[] dealid;
    }

    public static class SeizeEventResponse extends BaseEventResponse {
        public String owner;

        public BigInteger amount;

        public byte[] ref;
    }

    public static class SignedAppOrderEventResponse extends BaseEventResponse {
        public byte[] appHash;
    }

    public static class SignedDatasetOrderEventResponse extends BaseEventResponse {
        public byte[] datasetHash;
    }

    public static class SignedRequestOrderEventResponse extends BaseEventResponse {
        public byte[] requestHash;
    }

    public static class SignedWorkerpoolOrderEventResponse extends BaseEventResponse {
        public byte[] workerpoolHash;
    }

    public static class TaskClaimedEventResponse extends BaseEventResponse {
        public byte[] taskid;
    }

    public static class TaskConsensusEventResponse extends BaseEventResponse {
        public byte[] taskid;

        public byte[] consensus;
    }

    public static class TaskContributeEventResponse extends BaseEventResponse {
        public byte[] taskid;

        public String worker;

        public byte[] hash;
    }

    public static class TaskFinalizeEventResponse extends BaseEventResponse {
        public byte[] taskid;

        public byte[] results;
    }

    public static class TaskInitializeEventResponse extends BaseEventResponse {
        public byte[] taskid;

        public String workerpool;
    }

    public static class TaskReopenEventResponse extends BaseEventResponse {
        public byte[] taskid;
    }

    public static class TaskRevealEventResponse extends BaseEventResponse {
        public byte[] taskid;

        public String worker;

        public byte[] digest;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger value;
    }

    public static class UnlockEventResponse extends BaseEventResponse {
        public String owner;

        public BigInteger amount;
    }
}
