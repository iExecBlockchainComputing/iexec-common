package com.iexec.common.contract.generated;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
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
 * <p>Generated with web3j version 4.1.1.
 */
public class SMSDirectory extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b506103c0806100206000396000f3fe608060405234801561001057600080fd5b5060043610610052577c01000000000000000000000000000000000000000000000000000000006000350463307a7a5a81146100575780635061e52d146100f2575b600080fd5b61007d6004803603602081101561006d57600080fd5b5035600160a060020a0316610174565b6040805160208082528351818301528351919283929083019185019080838360005b838110156100b757818101518382015260200161009f565b50505050905090810190601f1680156100e45780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6101726004803603604081101561010857600080fd5b600160a060020a03823516919081019060408101602082013564010000000081111561013357600080fd5b82018360208201111561014557600080fd5b8035906020019184600183028401116401000000008311171561016757600080fd5b50909250905061021d565b005b600160a060020a0381166000908152602081815260409182902080548351601f60026000196101006001861615020190931692909204918201849004840281018401909452808452606093928301828280156102115780601f106101e657610100808354040283529160200191610211565b820191906000526020600020905b8154815290600101906020018083116101f457829003601f168201915b50505050509050919050565b600160a060020a0383163314806102c5575033600160a060020a031683600160a060020a0316638da5cb5b6040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040160206040518083038186803b15801561028e57600080fd5b505afa1580156102a2573d6000803e3d6000fd5b505050506040513d60208110156102b857600080fd5b5051600160a060020a0316145b15156102d057600080fd5b600160a060020a03831660009081526020819052604090206102f39083836102f9565b50505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061033a5782800160ff19823516178555610367565b82800160010185558215610367579182015b8281111561036757823582559160200191906001019061034c565b50610373929150610377565b5090565b61039191905b80821115610373576000815560010161037d565b9056fea165627a7a723058205d7b70521bd0a7eabd3ebf779d036478d7c4632e46c03135006826668fb0ff080029";

    public static final String FUNC_GETSMS = "getSMS";

    public static final String FUNC_SETSMS = "setSMS";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected SMSDirectory(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SMSDirectory(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SMSDirectory(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SMSDirectory(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<byte[]> getSMS(String _ressource) {
        final Function function = new Function(FUNC_GETSMS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_ressource)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<TransactionReceipt> setSMS(String _ressource, byte[] _sms) {
        final Function function = new Function(
                FUNC_SETSMS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_ressource), 
                new org.web3j.abi.datatypes.DynamicBytes(_sms)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static SMSDirectory load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SMSDirectory(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SMSDirectory load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SMSDirectory(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SMSDirectory load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SMSDirectory(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SMSDirectory load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SMSDirectory(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SMSDirectory> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SMSDirectory.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<SMSDirectory> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SMSDirectory.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SMSDirectory> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SMSDirectory.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SMSDirectory> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SMSDirectory.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }
}
