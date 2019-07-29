package com.iexec.common.contract.generated;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
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
 * <p>Generated with web3j version 4.3.0.
 *
 * The git commit sha for this version is dc6ef27883744930d5b1c145ca7240f4ece1f217 (git tag v3.0.35)
 */
public class Dataset extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b5060405161066f38038061066f8339810180604052608081101561003357600080fd5b81516020830180519193928301929164010000000081111561005457600080fd5b8201602081018481111561006757600080fd5b815164010000000081118282018710171561008157600080fd5b5050929190602001805164010000000081111561009d57600080fd5b820160208101848111156100b057600080fd5b81516401000000008111828201871017156100ca57600080fd5b505060209091015160008054600160a060020a0319163317808255604051939550919350600160a060020a03919091169160008051602061064f833981519152908290a361012084640100000000610154810204565b82516101339060019060208601906101b0565b5081516101479060029060208501906101b0565b506003555061024b915050565b600160a060020a03811661016757600080fd5b60008054604051600160a060020a038085169392169160008051602061064f83398151915291a360008054600160a060020a031916600160a060020a0392909216919091179055565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106101f157805160ff191683800117855561021e565b8280016001018555821561021e579182015b8281111561021e578251825591602001919060010190610203565b5061022a92915061022e565b5090565b61024891905b8082111561022a5760008155600101610234565b90565b6103f58061025a6000396000f3fe608060405234801561001057600080fd5b506004361061009a576000357c0100000000000000000000000000000000000000000000000000000000900480638da5cb5b116100785780638da5cb5b146101405780638f32d59b14610171578063a61ca6c51461018d578063f2fde38b146101955761009a565b80630847c4311461009f5780631ba99d7e1461011c578063715018a614610136575b600080fd5b6100a76101c8565b6040805160208082528351818301528351919283929083019185019080838360005b838110156100e15781810151838201526020016100c9565b50505050905090810190601f16801561010e5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b610124610255565b60408051918252519081900360200190f35b61013e61025b565b005b6101486102d0565b6040805173ffffffffffffffffffffffffffffffffffffffff9092168252519081900360200190f35b6101796102ec565b604080519115158252519081900360200190f35b6100a761030a565b61013e600480360360208110156101ab57600080fd5b503573ffffffffffffffffffffffffffffffffffffffff16610362565b60018054604080516020600284861615610100026000190190941693909304601f8101849004840282018401909252818152929183018282801561024d5780601f106102225761010080835404028352916020019161024d565b820191906000526020600020905b81548152906001019060200180831161023057829003601f168201915b505050505081565b60035481565b6102636102ec565b61026c57600080fd5b6000805460405173ffffffffffffffffffffffffffffffffffffffff909116907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908390a36000805473ffffffffffffffffffffffffffffffffffffffff19169055565b60005473ffffffffffffffffffffffffffffffffffffffff1690565b60005473ffffffffffffffffffffffffffffffffffffffff16331490565b6002805460408051602060018416156101000260001901909316849004601f8101849004840282018401909252818152929183018282801561024d5780601f106102225761010080835404028352916020019161024d565b604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152600860248201527f64697361626c6564000000000000000000000000000000000000000000000000604482015290519081900360640190fdfea165627a7a72305820308e45023d29aa500c6f60ed4a996424e39b658ec351591ac3def7528b78213b00298be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0";

    public static final String FUNC_M_DATASETNAME = "m_datasetName";

    public static final String FUNC_M_DATASETCHECKSUM = "m_datasetChecksum";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_ISOWNER = "isOwner";

    public static final String FUNC_M_DATASETMULTIADDR = "m_datasetMultiaddr";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected Dataset(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Dataset(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Dataset(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Dataset(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> m_datasetName() {
        final Function function = new Function(FUNC_M_DATASETNAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<byte[]> m_datasetChecksum() {
        final Function function = new Function(FUNC_M_DATASETCHECKSUM, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<TransactionReceipt> renounceOwnership() {
        final Function function = new Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Boolean> isOwner() {
        final Function function = new Function(FUNC_ISOWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<byte[]> m_datasetMultiaddr() {
        final Function function = new Function(FUNC_M_DATASETMULTIADDR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
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
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, OwnershipTransferredEventResponse>() {
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

    public RemoteCall<TransactionReceipt> transferOwnership(String param0) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Dataset load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Dataset(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Dataset load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Dataset(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Dataset load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Dataset(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Dataset load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Dataset(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Dataset> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _datasetOwner, String _datasetName, byte[] _datasetMultiaddr, byte[] _datasetChecksum) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_datasetOwner), 
                new org.web3j.abi.datatypes.Utf8String(_datasetName), 
                new org.web3j.abi.datatypes.DynamicBytes(_datasetMultiaddr), 
                new org.web3j.abi.datatypes.generated.Bytes32(_datasetChecksum)));
        return deployRemoteCall(Dataset.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Dataset> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _datasetOwner, String _datasetName, byte[] _datasetMultiaddr, byte[] _datasetChecksum) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_datasetOwner), 
                new org.web3j.abi.datatypes.Utf8String(_datasetName), 
                new org.web3j.abi.datatypes.DynamicBytes(_datasetMultiaddr), 
                new org.web3j.abi.datatypes.generated.Bytes32(_datasetChecksum)));
        return deployRemoteCall(Dataset.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Dataset> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _datasetOwner, String _datasetName, byte[] _datasetMultiaddr, byte[] _datasetChecksum) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_datasetOwner), 
                new org.web3j.abi.datatypes.Utf8String(_datasetName), 
                new org.web3j.abi.datatypes.DynamicBytes(_datasetMultiaddr), 
                new org.web3j.abi.datatypes.generated.Bytes32(_datasetChecksum)));
        return deployRemoteCall(Dataset.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Dataset> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _datasetOwner, String _datasetName, byte[] _datasetMultiaddr, byte[] _datasetChecksum) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_datasetOwner), 
                new org.web3j.abi.datatypes.Utf8String(_datasetName), 
                new org.web3j.abi.datatypes.DynamicBytes(_datasetMultiaddr), 
                new org.web3j.abi.datatypes.generated.Bytes32(_datasetChecksum)));
        return deployRemoteCall(Dataset.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class OwnershipTransferredEventResponse {
        public Log log;

        public String previousOwner;

        public String newOwner;
    }
}
