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
public class Dataset extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b50610806806100206000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c80637b1039991161005b5780637b1039991461019b5780638c2b1e2f146101bf5780638da5cb5b146102ee578063a61ca6c5146102f65761007d565b80630847c431146100825780631ba99d7e146100ff5780633121db1c14610119575b600080fd5b61008a6102fe565b6040805160208082528351818301528351919283929083019185019080838360005b838110156100c45781810151838201526020016100ac565b50505050905090810190601f1680156100f15780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61010761038b565b60408051918252519081900360200190f35b6101996004803603604081101561012f57600080fd5b6001600160a01b03823516919081019060408101602082013564010000000081111561015a57600080fd5b82018360208201111561016c57600080fd5b8035906020019184600183028401116401000000008311171561018e57600080fd5b509092509050610391565b005b6101a361043a565b604080516001600160a01b039092168252519081900360200190f35b610199600480360360608110156101d557600080fd5b8101906020810181356401000000008111156101f057600080fd5b82018360208201111561020257600080fd5b8035906020019184600183028401116401000000008311171561022457600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929594936020810193503591505064010000000081111561027757600080fd5b82018360208201111561028957600080fd5b803590602001918460018302840111640100000000831117156102ab57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295505091359250610449915050565b6101a3610481565b61008a6104ff565b60018054604080516020600284861615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156103835780601f1061035857610100808354040283529160200191610383565b820191906000526020600020905b81548152906001019060200180831161036657829003601f168201915b505050505081565b60035481565b3361039a610481565b6001600160a01b0316146103f5576040805162461bcd60e51b815260206004820152601760248201527f63616c6c6572206973206e6f7420746865206f776e6572000000000000000000604482015290519081900360640190fd5b6104358383838080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525061055792505050565b505050565b6000546001600160a01b031681565b610452336106c2565b8251610465906001906020860190610738565b508151610479906002906020850190610738565b506003555050565b60008054604080516331a9108f60e11b815230600482015290516001600160a01b0390921691636352211e91602480820192602092909190829003018186803b1580156104cd57600080fd5b505afa1580156104e1573d6000803e3d6000fd5b505050506040513d60208110156104f757600080fd5b505190505b90565b6002805460408051602060018416156101000260001901909316849004601f810184900484028201840190925281815292918301828280156103835780601f1061035857610100808354040283529160200191610383565b604080516302571be360e01b81527f91d1777781884d03a6757a803996e38de2a42967fb37eeaca72729271025a9e2600482015290516001600160a01b038416916302571be3916024808301926020929190829003018186803b1580156105bd57600080fd5b505afa1580156105d1573d6000803e3d6000fd5b505050506040513d60208110156105e757600080fd5b505160405163c47f002760e01b81526020600482018181528451602484015284516001600160a01b039094169363c47f002793869383926044909201919085019080838360005b8381101561064657818101518382015260200161062e565b50505050905090810190601f1680156106735780820380516001836020036101000a031916815260200191505b5092505050602060405180830381600087803b15801561069257600080fd5b505af11580156106a6573d6000803e3d6000fd5b505050506040513d60208110156106bc57600080fd5b50505050565b6000546001600160a01b031615610716576040805162461bcd60e51b8152602060048201526013602482015272185b1c9958591e481a5b9a5d1a585b1a5e9959606a1b604482015290519081900360640190fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061077957805160ff19168380011785556107a6565b828001600101855582156107a6579182015b828111156107a657825182559160200191906001019061078b565b506107b29291506107b6565b5090565b6104fc91905b808211156107b257600081556001016107bc56fea26469706673582212205a97b2fc595c24ec15f1c328b4ffef569dda7a95d444ccbd5d419390d6f7171d64736f6c63430006060033";

    public static final String FUNC_M_DATASETCHECKSUM = "m_datasetChecksum";

    public static final String FUNC_M_DATASETMULTIADDR = "m_datasetMultiaddr";

    public static final String FUNC_M_DATASETNAME = "m_datasetName";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_REGISTRY = "registry";

    public static final String FUNC_SETNAME = "setName";

    public static final String FUNC_INITIALIZE = "initialize";

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

    public RemoteFunctionCall<byte[]> m_datasetChecksum() {
        final Function function = new Function(FUNC_M_DATASETCHECKSUM, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> m_datasetMultiaddr() {
        final Function function = new Function(FUNC_M_DATASETMULTIADDR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<String> m_datasetName() {
        final Function function = new Function(FUNC_M_DATASETNAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> registry() {
        final Function function = new Function(FUNC_REGISTRY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setName(String _ens, String _name) {
        final Function function = new Function(
                FUNC_SETNAME, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_ens), 
                new org.web3j.abi.datatypes.Utf8String(_name)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> initialize(String _datasetName, byte[] _datasetMultiaddr, byte[] _datasetChecksum) {
        final Function function = new Function(
                FUNC_INITIALIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_datasetName), 
                new org.web3j.abi.datatypes.DynamicBytes(_datasetMultiaddr), 
                new org.web3j.abi.datatypes.generated.Bytes32(_datasetChecksum)), 
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

    public static RemoteCall<Dataset> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Dataset.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Dataset> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Dataset.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Dataset> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Dataset.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Dataset> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Dataset.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }
}
