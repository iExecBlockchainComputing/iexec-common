package com.iexec.common.contract.generated;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import org.web3j.abi.FunctionEncoder;
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
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 * Poco-dev: commit 0aa794bd1040a5308142c87ad78e1d3f9a17a9cb
 * <p>Generated with web3j version 4.1.1.
 */
public class App extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b50604051610754380380610754833981018060405261003291908101906101bb565b60008054600160a060020a031916600160a060020a03881617905584516100609060019060208801906100ad565b5083516100749060029060208701906100ad565b5082516100889060039060208601906100ad565b50600482905580516100a19060059060208401906100ad565b50505050505050610338565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106100ee57805160ff191683800117855561011b565b8280016001018555821561011b579182015b8281111561011b578251825591602001919060010190610100565b5061012792915061012b565b5090565b61014591905b808211156101275760008155600101610131565b90565b600061015482516102f7565b9392505050565b60006101548251610145565b6000601f8201831361017857600080fd5b815161018b610186826102c4565b61029e565b915080825260208301602083018583830111156101a757600080fd5b6101b2838284610308565b50505092915050565b60008060008060008060c087890312156101d457600080fd5b60006101e08989610148565b96505060208701516001604060020a038111156101fc57600080fd5b61020889828a01610167565b95505060408701516001604060020a0381111561022457600080fd5b61023089828a01610167565b94505060608701516001604060020a0381111561024c57600080fd5b61025889828a01610167565b935050608061026989828a0161015b565b92505060a08701516001604060020a0381111561028557600080fd5b61029189828a01610167565b9150509295509295509295565b6040518181016001604060020a03811182821017156102bc57600080fd5b604052919050565b60006001604060020a038211156102da57600080fd5b506020601f91909101601f19160190565b600160a060020a031690565b6000610302826102eb565b92915050565b60005b8381101561032357818101518382015260200161030b565b83811115610332576000848401525b50505050565b61040d806103476000396000f3fe6080604052600436106100775763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663358982a3811461007c57806339e75d45146100a757806384aaf12e146100bc578063deff41c1146100de578063e30d26a814610100578063f8c2ceb314610115575b600080fd5b34801561008857600080fd5b5061009161012a565b60405161009e9190610356565b60405180910390f35b3480156100b357600080fd5b506100916101b7565b3480156100c857600080fd5b506100d1610212565b60405161009e9190610348565b3480156100ea57600080fd5b506100f3610218565b60405161009e9190610334565b34801561010c57600080fd5b50610091610234565b34801561012157600080fd5b5061009161028f565b60018054604080516020600284861615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156101af5780601f10610184576101008083540402835291602001916101af565b820191906000526020600020905b81548152906001019060200180831161019257829003601f168201915b505050505081565b6003805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156101af5780601f10610184576101008083540402835291602001916101af565b60045481565b60005473ffffffffffffffffffffffffffffffffffffffff1681565b6005805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156101af5780601f10610184576101008083540402835291602001916101af565b6002805460408051602060018416156101000260001901909316849004601f810184900484028201840190925281815292918301828280156101af5780601f10610184576101008083540402835291602001916101af565b6102f081610372565b82525050565b6102f08161037d565b600061030a8261036e565b80845261031e816020860160208601610399565b610327816103c9565b9093016020019392505050565b6020810161034282846102e7565b92915050565b6020810161034282846102f6565b6020808252810161036781846102ff565b9392505050565b5190565b600061034282610380565b90565b73ffffffffffffffffffffffffffffffffffffffff1690565b60005b838110156103b457818101518382015260200161039c565b838111156103c3576000848401525b50505050565b601f01601f19169056fea265627a7a723058205d17f6f994693bf7b1ea4328bced55df000b3e6aafe4fed9a4745907b93729816c6578706572696d656e74616cf50037";

    public static final String FUNC_M_APPNAME = "m_appName";

    public static final String FUNC_M_APPMULTIADDR = "m_appMultiaddr";

    public static final String FUNC_M_APPCHECKSUM = "m_appChecksum";

    public static final String FUNC_M_OWNER = "m_owner";

    public static final String FUNC_M_APPMRENCLAVE = "m_appMREnclave";

    public static final String FUNC_M_APPTYPE = "m_appType";

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

    public RemoteCall<String> m_appName() {
        final Function function = new Function(FUNC_M_APPNAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<byte[]> m_appMultiaddr() {
        final Function function = new Function(FUNC_M_APPMULTIADDR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<byte[]> m_appChecksum() {
        final Function function = new Function(FUNC_M_APPCHECKSUM, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<String> m_owner() {
        final Function function = new Function(FUNC_M_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<byte[]> m_appMREnclave() {
        final Function function = new Function(FUNC_M_APPMRENCLAVE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<String> m_appType() {
        final Function function = new Function(FUNC_M_APPTYPE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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
}
