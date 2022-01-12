/*
 * Copyright 2020 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.contract.generated;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
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
public class App extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b50610a19806100206000396000f3fe608060405234801561001057600080fd5b50600436106100935760003560e01c806384aaf12e1161006657806384aaf12e146101c157806386c02c8f146101db5780638da5cb5b14610416578063e30d26a81461041e578063f8c2ceb31461042657610093565b80633121db1c14610098578063358982a31461011857806339e75d45146101955780637b1039991461019d575b600080fd5b610116600480360360408110156100ae57600080fd5b6001600160a01b038235169190810190604081016020820135600160201b8111156100d857600080fd5b8201836020820111156100ea57600080fd5b803590602001918460018302840111600160201b8311171561010b57600080fd5b50909250905061042e565b005b6101206104d7565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561015a578181015183820152602001610142565b50505050905090810190601f1680156101875780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b610120610564565b6101a56105bf565b604080516001600160a01b039092168252519081900360200190f35b6101c96105ce565b60408051918252519081900360200190f35b610116600480360360a08110156101f157600080fd5b810190602081018135600160201b81111561020b57600080fd5b82018360208201111561021d57600080fd5b803590602001918460018302840111600160201b8311171561023e57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561029057600080fd5b8201836020820111156102a257600080fd5b803590602001918460018302840111600160201b831117156102c357600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561031557600080fd5b82018360208201111561032757600080fd5b803590602001918460018302840111600160201b8311171561034857600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092958435959094909350604081019250602001359050600160201b8111156103a257600080fd5b8201836020820111156103b457600080fd5b803590602001918460018302840111600160201b831117156103d557600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506105d4945050505050565b6101a5610639565b6101206106b7565b610120610712565b33610437610639565b6001600160a01b031614610492576040805162461bcd60e51b815260206004820152601760248201527f63616c6c6572206973206e6f7420746865206f776e6572000000000000000000604482015290519081900360640190fd5b6104d28383838080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525061076a92505050565b505050565b60018054604080516020600284861615610100026000190190941693909304601f8101849004840282018401909252818152929183018282801561055c5780601f106105315761010080835404028352916020019161055c565b820191906000526020600020905b81548152906001019060200180831161053f57829003601f168201915b505050505081565b6003805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152929183018282801561055c5780601f106105315761010080835404028352916020019161055c565b6000546001600160a01b031681565b60045481565b6105dd336108d5565b84516105f090600190602088019061094b565b50835161060490600290602087019061094b565b50825161061890600390602086019061094b565b506004829055805161063190600590602084019061094b565b505050505050565b60008054604080516331a9108f60e11b815230600482015290516001600160a01b0390921691636352211e91602480820192602092909190829003018186803b15801561068557600080fd5b505afa158015610699573d6000803e3d6000fd5b505050506040513d60208110156106af57600080fd5b505190505b90565b6005805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152929183018282801561055c5780601f106105315761010080835404028352916020019161055c565b6002805460408051602060018416156101000260001901909316849004601f8101849004840282018401909252818152929183018282801561055c5780601f106105315761010080835404028352916020019161055c565b604080516302571be360e01b81527f91d1777781884d03a6757a803996e38de2a42967fb37eeaca72729271025a9e2600482015290516001600160a01b038416916302571be3916024808301926020929190829003018186803b1580156107d057600080fd5b505afa1580156107e4573d6000803e3d6000fd5b505050506040513d60208110156107fa57600080fd5b505160405163c47f002760e01b81526020600482018181528451602484015284516001600160a01b039094169363c47f002793869383926044909201919085019080838360005b83811015610859578181015183820152602001610841565b50505050905090810190601f1680156108865780820380516001836020036101000a031916815260200191505b5092505050602060405180830381600087803b1580156108a557600080fd5b505af11580156108b9573d6000803e3d6000fd5b505050506040513d60208110156108cf57600080fd5b50505050565b6000546001600160a01b031615610929576040805162461bcd60e51b8152602060048201526013602482015272185b1c9958591e481a5b9a5d1a585b1a5e9959606a1b604482015290519081900360640190fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061098c57805160ff19168380011785556109b9565b828001600101855582156109b9579182015b828111156109b957825182559160200191906001019061099e565b506109c59291506109c9565b5090565b6106b491905b808211156109c557600081556001016109cf56fea26469706673582212202c608028e7b360813229f9625eab7667f24b1287646c4519461e6f424260575c64736f6c63430006060033";

    public static final String FUNC_M_APPCHECKSUM = "m_appChecksum";

    public static final String FUNC_M_APPMRENCLAVE = "m_appMREnclave";

    public static final String FUNC_M_APPMULTIADDR = "m_appMultiaddr";

    public static final String FUNC_M_APPNAME = "m_appName";

    public static final String FUNC_M_APPTYPE = "m_appType";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_REGISTRY = "registry";

    public static final String FUNC_SETNAME = "setName";

    public static final String FUNC_INITIALIZE = "initialize";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<>();
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

    public RemoteFunctionCall<byte[]> m_appChecksum() {
        final Function function = new Function(FUNC_M_APPCHECKSUM,
                List.of(),
                List.of(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> m_appMREnclave() {
        final Function function = new Function(FUNC_M_APPMRENCLAVE,
                List.of(),
                List.of(new TypeReference<DynamicBytes>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> m_appMultiaddr() {
        final Function function = new Function(FUNC_M_APPMULTIADDR,
                List.of(),
                List.of(new TypeReference<DynamicBytes>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<String> m_appName() {
        final Function function = new Function(FUNC_M_APPNAME,
                List.of(),
                List.of(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> m_appType() {
        final Function function = new Function(FUNC_M_APPTYPE,
                List.of(),
                List.of(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER,
                List.of(),
                List.of(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> registry() {
        final Function function = new Function(FUNC_REGISTRY,
                List.of(),
                List.of(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setName(String _ens, String _name) {
        final Function function = new Function(
                FUNC_SETNAME, 
                Arrays.asList(new org.web3j.abi.datatypes.Address(_ens),
                new org.web3j.abi.datatypes.Utf8String(_name)), 
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> initialize(String _appName, String _appType, byte[] _appMultiaddr, byte[] _appChecksum, byte[] _appMREnclave) {
        final Function function = new Function(
                FUNC_INITIALIZE, 
                Arrays.asList(new org.web3j.abi.datatypes.Utf8String(_appName),
                new org.web3j.abi.datatypes.Utf8String(_appType), 
                new org.web3j.abi.datatypes.DynamicBytes(_appMultiaddr), 
                new org.web3j.abi.datatypes.generated.Bytes32(_appChecksum), 
                new org.web3j.abi.datatypes.DynamicBytes(_appMREnclave)), 
                Collections.emptyList());
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

    public static RemoteCall<App> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(App.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<App> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(App.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<App> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(App.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<App> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(App.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }
}
