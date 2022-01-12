package com.iexec.common.contract.generated;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
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
 * <p>Generated with web3j version 4.5.16.
 */
@SuppressWarnings("rawtypes")
public class Workerpool extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b50610809806100206000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c80638da5cb5b1161005b5780638da5cb5b146101ca578063b55e75af146101d2578063f199413c146101da578063f62d1888146101fd57610088565b80633121db1c1461008d578063584feb3a1461010f5780637b1039991461018c57806387639c68146101b0575b600080fd5b61010d600480360360408110156100a357600080fd5b6001600160a01b0382351691908101906040810160208201356401000000008111156100ce57600080fd5b8201836020820111156100e057600080fd5b8035906020019184600183028401116401000000008311171561010257600080fd5b5090925090506102a3565b005b610117610346565b6040805160208082528351818301528351919283929083019185019080838360005b83811015610151578181015183820152602001610139565b50505050905090810190601f16801561017e5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6101946103d3565b604080516001600160a01b039092168252519081900360200190f35b6101b86103e2565b60408051918252519081900360200190f35b6101946103e8565b6101b8610466565b61010d600480360360408110156101f057600080fd5b508035906020013561046c565b61010d6004803603602081101561021357600080fd5b81019060208101813564010000000081111561022e57600080fd5b82018360208201111561024057600080fd5b8035906020019184600183028401116401000000008311171561026257600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610530945050505050565b336102ac6103e8565b6001600160a01b031614610301576040805162461bcd60e51b815260206004820152601760248201527631b0b63632b91034b9903737ba103a34329037bbb732b960491b604482015290519081900360640190fd5b6103418383838080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525061055a92505050565b505050565b60018054604080516020600284861615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156103cb5780601f106103a0576101008083540402835291602001916103cb565b820191906000526020600020905b8154815290600101906020018083116103ae57829003601f168201915b505050505081565b6000546001600160a01b031681565b60035481565b60008054604080516331a9108f60e11b815230600482015290516001600160a01b0390921691636352211e91602480820192602092909190829003018186803b15801561043457600080fd5b505afa158015610448573d6000803e3d6000fd5b505050506040513d602081101561045e57600080fd5b505190505b90565b60025481565b336104756103e8565b6001600160a01b0316146104ca576040805162461bcd60e51b815260206004820152601760248201527631b0b63632b91034b9903737ba103a34329037bbb732b960491b604482015290519081900360640190fd5b60648111156104d857600080fd5b60025460035460408051928352602083018590528281019190915260608201839052517f61f37ef3a6d587ce2d6be79492e1fac570f2745787015db7ed95ad4397b05c1e9181900360800190a1600291909155600355565b610539336106c5565b805161054c90600190602084019061073b565b5050601e6002556001600355565b604080516302571be360e01b81527f91d1777781884d03a6757a803996e38de2a42967fb37eeaca72729271025a9e2600482015290516001600160a01b038416916302571be3916024808301926020929190829003018186803b1580156105c057600080fd5b505afa1580156105d4573d6000803e3d6000fd5b505050506040513d60208110156105ea57600080fd5b505160405163c47f002760e01b81526020600482018181528451602484015284516001600160a01b039094169363c47f002793869383926044909201919085019080838360005b83811015610649578181015183820152602001610631565b50505050905090810190601f1680156106765780820380516001836020036101000a031916815260200191505b5092505050602060405180830381600087803b15801561069557600080fd5b505af11580156106a9573d6000803e3d6000fd5b505050506040513d60208110156106bf57600080fd5b50505050565b6000546001600160a01b031615610719576040805162461bcd60e51b8152602060048201526013602482015272185b1c9958591e481a5b9a5d1a585b1a5e9959606a1b604482015290519081900360640190fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061077c57805160ff19168380011785556107a9565b828001600101855582156107a9579182015b828111156107a957825182559160200191906001019061078e565b506107b59291506107b9565b5090565b61046391905b808211156107b557600081556001016107bf56fea264697066735822122018fc57db17dda04ce8d113e03376f297c311647dfae2b7176bb7f7e53af0983164736f6c63430006060033";

    public static final String FUNC_M_SCHEDULERREWARDRATIOPOLICY = "m_schedulerRewardRatioPolicy";

    public static final String FUNC_M_WORKERSTAKERATIOPOLICY = "m_workerStakeRatioPolicy";

    public static final String FUNC_M_WORKERPOOLDESCRIPTION = "m_workerpoolDescription";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_REGISTRY = "registry";

    public static final String FUNC_SETNAME = "setName";

    public static final String FUNC_INITIALIZE = "initialize";

    public static final String FUNC_CHANGEPOLICY = "changePolicy";

    public static final Event POLICYUPDATE_EVENT = new Event("PolicyUpdate", 
            Arrays.asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<>();
    }

    @Deprecated
    protected Workerpool(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Workerpool(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Workerpool(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Workerpool(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<PolicyUpdateEventResponse> getPolicyUpdateEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(POLICYUPDATE_EVENT, transactionReceipt);
        ArrayList<PolicyUpdateEventResponse> responses = new ArrayList<>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PolicyUpdateEventResponse typedResponse = new PolicyUpdateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldWorkerStakeRatioPolicy = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newWorkerStakeRatioPolicy = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.oldSchedulerRewardRatioPolicy = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.newSchedulerRewardRatioPolicy = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PolicyUpdateEventResponse> policyUpdateEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<>() {
            @Override
            public PolicyUpdateEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(POLICYUPDATE_EVENT, log);
                PolicyUpdateEventResponse typedResponse = new PolicyUpdateEventResponse();
                typedResponse.log = log;
                typedResponse.oldWorkerStakeRatioPolicy = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.newWorkerStakeRatioPolicy = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.oldSchedulerRewardRatioPolicy = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.newSchedulerRewardRatioPolicy = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PolicyUpdateEventResponse> policyUpdateEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(POLICYUPDATE_EVENT));
        return policyUpdateEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> m_schedulerRewardRatioPolicy() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_M_SCHEDULERREWARDRATIOPOLICY,
                List.<Type>of(),
                List.<TypeReference<?>>of(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> m_workerStakeRatioPolicy() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_M_WORKERSTAKERATIOPOLICY,
                List.<Type>of(),
                List.<TypeReference<?>>of(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> m_workerpoolDescription() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_M_WORKERPOOLDESCRIPTION,
                List.<Type>of(),
                List.<TypeReference<?>>of(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER,
                List.<Type>of(),
                List.<TypeReference<?>>of(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> registry() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REGISTRY,
                List.<Type>of(),
                List.<TypeReference<?>>of(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setName(String _ens, String _name) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETNAME, 
                Arrays.asList(new org.web3j.abi.datatypes.Address(_ens),
                new org.web3j.abi.datatypes.Utf8String(_name)), 
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> initialize(String _workerpoolDescription) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INITIALIZE,
                List.<Type>of(new Utf8String(_workerpoolDescription)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> changePolicy(BigInteger _newWorkerStakeRatioPolicy, BigInteger _newSchedulerRewardRatioPolicy) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CHANGEPOLICY, 
                Arrays.asList(new org.web3j.abi.datatypes.generated.Uint256(_newWorkerStakeRatioPolicy),
                new org.web3j.abi.datatypes.generated.Uint256(_newSchedulerRewardRatioPolicy)), 
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Workerpool load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Workerpool(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Workerpool load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Workerpool(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Workerpool load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Workerpool(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Workerpool load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Workerpool(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Workerpool> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Workerpool.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Workerpool> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Workerpool.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Workerpool> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Workerpool.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Workerpool> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Workerpool.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class PolicyUpdateEventResponse extends BaseEventResponse {
        public BigInteger oldWorkerStakeRatioPolicy;

        public BigInteger newWorkerStakeRatioPolicy;

        public BigInteger oldSchedulerRewardRatioPolicy;

        public BigInteger newSchedulerRewardRatioPolicy;
    }
}
