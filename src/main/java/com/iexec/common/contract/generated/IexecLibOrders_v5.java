package com.iexec.common.contract.generated;

import java.math.BigInteger;
import java.util.List;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
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
public class IexecLibOrders_v5 extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_APPORDEROPERATION_TYPEHASH = "APPORDEROPERATION_TYPEHASH";

    public static final String FUNC_APPORDER_TYPEHASH = "APPORDER_TYPEHASH";

    public static final String FUNC_DATASETORDEROPERATION_TYPEHASH = "DATASETORDEROPERATION_TYPEHASH";

    public static final String FUNC_DATASETORDER_TYPEHASH = "DATASETORDER_TYPEHASH";

    public static final String FUNC_EIP712DOMAIN_TYPEHASH = "EIP712DOMAIN_TYPEHASH";

    public static final String FUNC_REQUESTORDEROPERATION_TYPEHASH = "REQUESTORDEROPERATION_TYPEHASH";

    public static final String FUNC_REQUESTORDER_TYPEHASH = "REQUESTORDER_TYPEHASH";

    public static final String FUNC_WORKERPOOLORDEROPERATION_TYPEHASH = "WORKERPOOLORDEROPERATION_TYPEHASH";

    public static final String FUNC_WORKERPOOLORDER_TYPEHASH = "WORKERPOOLORDER_TYPEHASH";

    public static final String FUNC_hash = "hash";

    @Deprecated
    protected IexecLibOrders_v5(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IexecLibOrders_v5(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IexecLibOrders_v5(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IexecLibOrders_v5(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<byte[]> APPORDEROPERATION_TYPEHASH() {
        final Function function = new Function(FUNC_APPORDEROPERATION_TYPEHASH,
                List.<Type>of(),
                List.<TypeReference<?>>of(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> APPORDER_TYPEHASH() {
        final Function function = new Function(FUNC_APPORDER_TYPEHASH,
                List.<Type>of(),
                List.<TypeReference<?>>of(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> DATASETORDEROPERATION_TYPEHASH() {
        final Function function = new Function(FUNC_DATASETORDEROPERATION_TYPEHASH,
                List.<Type>of(),
                List.<TypeReference<?>>of(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> DATASETORDER_TYPEHASH() {
        final Function function = new Function(FUNC_DATASETORDER_TYPEHASH,
                List.<Type>of(),
                List.<TypeReference<?>>of(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> EIP712DOMAIN_TYPEHASH() {
        final Function function = new Function(FUNC_EIP712DOMAIN_TYPEHASH,
                List.<Type>of(),
                List.<TypeReference<?>>of(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> REQUESTORDEROPERATION_TYPEHASH() {
        final Function function = new Function(FUNC_REQUESTORDEROPERATION_TYPEHASH,
                List.<Type>of(),
                List.<TypeReference<?>>of(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> REQUESTORDER_TYPEHASH() {
        final Function function = new Function(FUNC_REQUESTORDER_TYPEHASH,
                List.<Type>of(),
                List.<TypeReference<?>>of(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> WORKERPOOLORDEROPERATION_TYPEHASH() {
        final Function function = new Function(FUNC_WORKERPOOLORDEROPERATION_TYPEHASH,
                List.<Type>of(),
                List.<TypeReference<?>>of(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> WORKERPOOLORDER_TYPEHASH() {
        final Function function = new Function(FUNC_WORKERPOOLORDER_TYPEHASH,
                List.<Type>of(),
                List.<TypeReference<?>>of(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> hash(DatasetOrder _datasetorder) {
        final Function function = new Function(FUNC_hash,
                List.<Type>of(_datasetorder),
                List.<TypeReference<?>>of(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> hash(DatasetOrderOperation _datasetorderoperation) {
        final Function function = new Function(FUNC_hash,
                List.<Type>of(_datasetorderoperation),
                List.<TypeReference<?>>of(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> hash(WorkerpoolOrderOperation _workerpoolorderoperation) {
        final Function function = new Function(FUNC_hash,
                List.<Type>of(_workerpoolorderoperation),
                List.<TypeReference<?>>of(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> hash(AppOrderOperation _apporderoperation) {
        final Function function = new Function(FUNC_hash,
                List.<Type>of(_apporderoperation),
                List.<TypeReference<?>>of(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> hash(EIP712Domain _domain) {
        final Function function = new Function(FUNC_hash,
                List.<Type>of(_domain),
                List.<TypeReference<?>>of(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> hash(AppOrder _apporder) {
        final Function function = new Function(FUNC_hash,
                List.<Type>of(_apporder),
                List.<TypeReference<?>>of(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> hash(RequestOrder _requestorder) {
        final Function function = new Function(FUNC_hash,
                List.<Type>of(_requestorder),
                List.<TypeReference<?>>of(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> hash(WorkerpoolOrder _workerpoolorder) {
        final Function function = new Function(FUNC_hash,
                List.<Type>of(_workerpoolorder),
                List.<TypeReference<?>>of(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    @Deprecated
    public static IexecLibOrders_v5 load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IexecLibOrders_v5(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IexecLibOrders_v5 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IexecLibOrders_v5(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IexecLibOrders_v5 load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IexecLibOrders_v5(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IexecLibOrders_v5 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IexecLibOrders_v5(contractAddress, web3j, transactionManager, contractGasProvider);
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

    public static class DatasetOrderOperation extends DynamicStruct {
        public DatasetOrder order;

        public byte[] sign;

        public DatasetOrderOperation(DatasetOrder order, byte[] sign) {
            super(order,new org.web3j.abi.datatypes.DynamicBytes(sign));
            this.order = order;
            this.sign = sign;
        }

        public DatasetOrderOperation(DatasetOrder order, DynamicBytes sign) {
            super(order,sign);
            this.order = order;
            this.sign = sign.getValue();
        }
    }

    public static class WorkerpoolOrderOperation extends DynamicStruct {
        public WorkerpoolOrder order;

        public byte[] sign;

        public WorkerpoolOrderOperation(WorkerpoolOrder order, byte[] sign) {
            super(order,new org.web3j.abi.datatypes.DynamicBytes(sign));
            this.order = order;
            this.sign = sign;
        }

        public WorkerpoolOrderOperation(WorkerpoolOrder order, DynamicBytes sign) {
            super(order,sign);
            this.order = order;
            this.sign = sign.getValue();
        }
    }

    public static class AppOrderOperation extends DynamicStruct {
        public AppOrder order;

        public byte[] sign;

        public AppOrderOperation(AppOrder order, byte[] sign) {
            super(order,new org.web3j.abi.datatypes.DynamicBytes(sign));
            this.order = order;
            this.sign = sign;
        }

        public AppOrderOperation(AppOrder order, DynamicBytes sign) {
            super(order,sign);
            this.order = order;
            this.sign = sign.getValue();
        }
    }
}
