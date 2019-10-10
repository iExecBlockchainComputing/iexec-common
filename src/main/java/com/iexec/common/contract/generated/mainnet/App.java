package com.iexec.common.contract.generated.mainnet;

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
 * The git commit sha for this version is dc6ef27883744930d5b1c145ca7240f4ece1f217 (git tag v3.0.35)
 *
 * <p>Generated with web3j version 4.5.5.
 */
@SuppressWarnings("rawtypes")
public class App extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b50604051610809380380610809833981018060405260c081101561003357600080fd5b81516020830180519193928301929164010000000081111561005457600080fd5b8201602081018481111561006757600080fd5b815164010000000081118282018710171561008157600080fd5b5050929190602001805164010000000081111561009d57600080fd5b820160208101848111156100b057600080fd5b81516401000000008111828201871017156100ca57600080fd5b505092919060200180516401000000008111156100e657600080fd5b820160208101848111156100f957600080fd5b815164010000000081118282018710171561011357600080fd5b5050602082015160409092018051919492939164010000000081111561013857600080fd5b8201602081018481111561014b57600080fd5b815164010000000081118282018710171561016557600080fd5b505060008054600160a060020a0319163317808255604051929550600160a060020a0316935091506000805160206107e9833981519152908290a36101b286640100000000610212810204565b84516101c590600190602088019061026e565b5083516101d990600290602087019061026e565b5082516101ed90600390602086019061026e565b506004829055805161020690600590602084019061026e565b50505050505050610309565b600160a060020a03811661022557600080fd5b60008054604051600160a060020a03808516939216916000805160206107e983398151915291a360008054600160a060020a031916600160a060020a0392909216919091179055565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106102af57805160ff19168380011785556102dc565b828001600101855582156102dc579182015b828111156102dc5782518255916020019190600101906102c1565b506102e89291506102ec565b5090565b61030691905b808211156102e857600081556001016102f2565b90565b6104d1806103186000396000f3fe608060405234801561001057600080fd5b50600436106100b0576000357c0100000000000000000000000000000000000000000000000000000000900480638da5cb5b116100835780638da5cb5b1461015e5780638f32d59b1461018f578063e30d26a8146101ab578063f2fde38b146101b3578063f8c2ceb3146101e6576100b0565b8063358982a3146100b557806339e75d4514610132578063715018a61461013a57806384aaf12e14610144575b600080fd5b6100bd6101ee565b6040805160208082528351818301528351919283929083019185019080838360005b838110156100f75781810151838201526020016100df565b50505050905090810190601f1680156101245780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6100bd61027b565b6101426102d6565b005b61014c61034b565b60408051918252519081900360200190f35b610166610351565b6040805173ffffffffffffffffffffffffffffffffffffffff9092168252519081900360200190f35b61019761036d565b604080519115158252519081900360200190f35b6100bd61038b565b610142600480360360208110156101c957600080fd5b503573ffffffffffffffffffffffffffffffffffffffff166103e6565b6100bd61044d565b60018054604080516020600284861615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156102735780601f1061024857610100808354040283529160200191610273565b820191906000526020600020905b81548152906001019060200180831161025657829003601f168201915b505050505081565b6003805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156102735780601f1061024857610100808354040283529160200191610273565b6102de61036d565b6102e757600080fd5b6000805460405173ffffffffffffffffffffffffffffffffffffffff909116907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908390a36000805473ffffffffffffffffffffffffffffffffffffffff19169055565b60045481565b60005473ffffffffffffffffffffffffffffffffffffffff1690565b60005473ffffffffffffffffffffffffffffffffffffffff16331490565b6005805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156102735780601f1061024857610100808354040283529160200191610273565b604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152600860248201527f64697361626c6564000000000000000000000000000000000000000000000000604482015290519081900360640190fd5b6002805460408051602060018416156101000260001901909316849004601f810184900484028201840190925281815292918301828280156102735780601f106102485761010080835404028352916020019161027356fea165627a7a7230582010b569b8a246d618c27da2a63e748c4cd66311b5e7d5591d13fe1ca0a03abebb00298be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0";

    public static final String FUNC_M_APPNAME = "m_appName";

    public static final String FUNC_M_APPMULTIADDR = "m_appMultiaddr";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_M_APPCHECKSUM = "m_appChecksum";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_ISOWNER = "isOwner";

    public static final String FUNC_M_APPMRENCLAVE = "m_appMREnclave";

    public static final String FUNC_M_APPTYPE = "m_appType";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected App(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected App(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected App(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected App(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> m_appName() {
        final Function function = new Function(FUNC_M_APPNAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<byte[]> m_appMultiaddr() {
        final Function function = new Function(FUNC_M_APPMULTIADDR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final Function function = new Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<byte[]> m_appChecksum() {
        final Function function = new Function(FUNC_M_APPCHECKSUM, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> isOwner() {
        final Function function = new Function(FUNC_ISOWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<byte[]> m_appMREnclave() {
        final Function function = new Function(FUNC_M_APPMRENCLAVE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<String> m_appType() {
        final Function function = new Function(FUNC_M_APPTYPE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String param0) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static App load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new App(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static App load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new App(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static App load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new App(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static App load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new App(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<App> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _appOwner, String _appName, String _appType, byte[] _appMultiaddr, byte[] _appChecksum, byte[] _appMREnclave) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_appOwner), 
                new org.web3j.abi.datatypes.Utf8String(_appName), 
                new org.web3j.abi.datatypes.Utf8String(_appType), 
                new org.web3j.abi.datatypes.DynamicBytes(_appMultiaddr), 
                new org.web3j.abi.datatypes.generated.Bytes32(_appChecksum), 
                new org.web3j.abi.datatypes.DynamicBytes(_appMREnclave)));
        return deployRemoteCall(App.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<App> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _appOwner, String _appName, String _appType, byte[] _appMultiaddr, byte[] _appChecksum, byte[] _appMREnclave) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_appOwner), 
                new org.web3j.abi.datatypes.Utf8String(_appName), 
                new org.web3j.abi.datatypes.Utf8String(_appType), 
                new org.web3j.abi.datatypes.DynamicBytes(_appMultiaddr), 
                new org.web3j.abi.datatypes.generated.Bytes32(_appChecksum), 
                new org.web3j.abi.datatypes.DynamicBytes(_appMREnclave)));
        return deployRemoteCall(App.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<App> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _appOwner, String _appName, String _appType, byte[] _appMultiaddr, byte[] _appChecksum, byte[] _appMREnclave) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_appOwner), 
                new org.web3j.abi.datatypes.Utf8String(_appName), 
                new org.web3j.abi.datatypes.Utf8String(_appType), 
                new org.web3j.abi.datatypes.DynamicBytes(_appMultiaddr), 
                new org.web3j.abi.datatypes.generated.Bytes32(_appChecksum), 
                new org.web3j.abi.datatypes.DynamicBytes(_appMREnclave)));
        return deployRemoteCall(App.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<App> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _appOwner, String _appName, String _appType, byte[] _appMultiaddr, byte[] _appChecksum, byte[] _appMREnclave) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_appOwner), 
                new org.web3j.abi.datatypes.Utf8String(_appName), 
                new org.web3j.abi.datatypes.Utf8String(_appType), 
                new org.web3j.abi.datatypes.DynamicBytes(_appMultiaddr), 
                new org.web3j.abi.datatypes.generated.Bytes32(_appChecksum), 
                new org.web3j.abi.datatypes.DynamicBytes(_appMREnclave)));
        return deployRemoteCall(App.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
