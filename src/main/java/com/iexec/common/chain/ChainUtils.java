package com.iexec.common.chain;

import com.iexec.common.contract.generated.App;
import com.iexec.common.contract.generated.IexecClerkABILegacy;
import com.iexec.common.contract.generated.IexecHubABILegacy;
import com.iexec.common.utils.BytesUtils;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.Arrays;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.ens.EnsResolutionException;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tuples.generated.Tuple9;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import static com.iexec.common.chain.ChainApp.stringParamsToChainAppParams;
import static com.iexec.common.chain.ChainDeal.stringParamsToList;
import static com.iexec.common.contract.generated.IexecHubABILegacy.*;

@Slf4j
public class ChainUtils {

    private ChainUtils() {
        throw new UnsupportedOperationException();
    }

    public static Web3j getWeb3j(String chainNodeAddress) {
        Web3j web3j = Web3j.build(new HttpService(chainNodeAddress));
        ExceptionInInitializerError exceptionInInitializerError = new ExceptionInInitializerError("Failed to connect to ethereum node " + chainNodeAddress);
        try {
            log.info("Connected to Ethereum node [node:{}]", web3j.web3ClientVersion().send().getWeb3ClientVersion());
            if (web3j.web3ClientVersion().send().getWeb3ClientVersion() == null) {
                throw exceptionInInitializerError;
            }
        } catch (IOException e) {
            throw exceptionInInitializerError;
        }
        return web3j;
    }

    public static IexecHubABILegacy loadHubContract(Credentials credentials, Web3j web3j, String iexecHubAddress) {
        ExceptionInInitializerError exceptionInInitializerError = new ExceptionInInitializerError("Failed to load IexecHub contract from address " + iexecHubAddress);

        if (iexecHubAddress != null && !iexecHubAddress.isEmpty()) {
            try {
                return IexecHubABILegacy.load(
                        iexecHubAddress, web3j, credentials, getContractGasProvider());
            } catch (EnsResolutionException e) {
                throw exceptionInInitializerError;
            }
        } else {
            throw exceptionInInitializerError;
        }
    }

    public static IexecClerkABILegacy loadClerkContract(Credentials credentials, Web3j web3j, String iexecHubAddress) {
        IexecHubABILegacy iexecHubABILegacy = loadHubContract(credentials, web3j, iexecHubAddress);
        ExceptionInInitializerError exceptionInInitializerError = new ExceptionInInitializerError("Failed to load IexecClerk contract from Hub address " + iexecHubAddress);
        try {
            String addressClerk = iexecHubABILegacy.iexecclerk().send();
            if (addressClerk == null || addressClerk.isEmpty()) {
                throw exceptionInInitializerError;
            }
            return IexecClerkABILegacy.load(addressClerk, web3j, credentials, getContractGasProvider());
        } catch (Exception e) {
            log.error("Failed to load clerk");
            return null;
        }
    }

    public static App loadDappContract(Credentials credentials, Web3j web3j, String appAddress) {
        ExceptionInInitializerError exceptionInInitializerError = new ExceptionInInitializerError("Failed to load Dapp contract address " + appAddress);
        try {
            if (appAddress == null || appAddress.isEmpty()) {
                throw exceptionInInitializerError;
            }

            return App.load(appAddress, web3j, credentials, new DefaultGasProvider());
        } catch (Exception e) {
            log.error("Failed to load chainApp [address:{}]", appAddress);
        }
        return null;
    }

    public static Optional<ChainDeal> getChainDeal(Credentials credentials, Web3j web3j, String iexecHubAddress, String chainDealId) {
        IexecHubABILegacy iexecHub = loadHubContract(credentials, web3j, iexecHubAddress);
        IexecClerkABILegacy iexecClerk = loadClerkContract(credentials, web3j, iexecHubAddress);

        byte[] chainDealIdBytes = BytesUtils.stringToBytes(chainDealId);
        try {
            Tuple9<String, String, BigInteger, String, String, BigInteger, String, String, BigInteger> dealPt1 =
                    iexecClerk.viewDealABILegacy_pt1(chainDealIdBytes).send();
            Tuple6<BigInteger, BigInteger, String, String, String, String> dealPt2 =
                    iexecClerk.viewDealABILegacy_pt2(chainDealIdBytes).send();
            Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> config =
                    iexecClerk.viewConfigABILegacy(chainDealIdBytes).send();


            String appAddress = dealPt1.getValue1();
            BigInteger categoryId = config.getValue1();

            ChainApp chainApp = getChainApp(loadDappContract(credentials, web3j, appAddress)).get();
            ChainCategory chainCategory = getChainCategory(iexecHub, categoryId.longValue()).get();

            return Optional.of(ChainDeal.builder()
                    .chainDealId(chainDealId)
                    .chainApp(chainApp)
                    .dappOwner(dealPt1.getValue2())
                    .dappPrice(dealPt1.getValue3())
                    .dataPointer(dealPt1.getValue4())
                    .dataOwner(dealPt1.getValue5())
                    .dataPrice(dealPt1.getValue6())
                    .poolPointer(dealPt1.getValue7())
                    .poolOwner(dealPt1.getValue8())
                    .poolPrice(dealPt1.getValue9())
                    .trust(dealPt2.getValue1())
                    .tag(BytesUtils.bytesToString(dealPt2.getValue2().toByteArray())) //bigInteger.toByteArray().toHexString(); ex : 1 -> 0x1 : SGX TAG
                    .requester(dealPt2.getValue3())
                    .beneficiary(dealPt2.getValue4())
                    .callback(dealPt2.getValue5())
                    .params(stringParamsToList(dealPt2.getValue6()))
                    .chainCategory(chainCategory)
                    .startTime(config.getValue2())
                    .botFirst(config.getValue3())
                    .botSize(config.getValue4())
                    .workerStake(config.getValue5())
                    .schedulerRewardRatio(config.getValue6())
                    .build());
        } catch (Exception e) {
            log.error("Failed to get ChainDeal [chainDealId:{}]", chainDealId);
        }
        return Optional.empty();
    }

    public static Optional<ChainTask> getChainTask(IexecHubABILegacy iexecHub, String chainTaskId) {
        ChainTask chainTask = null;
        try {
            return Optional.of(ChainTask.tuple2ChainTask(iexecHub.viewTaskABILegacy(BytesUtils.stringToBytes(chainTaskId)).send()));
        } catch (Exception e) {
            log.error("Failed to get ChainTask [chainTaskId:{}]", chainTaskId);
        }
        return Optional.empty();
    }


    public static Optional<ChainAccount> getChainAccount(IexecClerkABILegacy iexecClerk, String walletAddress) {
        try {
            return Optional.of(ChainAccount.tuple2Account(iexecClerk.viewAccountABILegacy(walletAddress).send()));
        } catch (Exception e) {
            log.info("Failed to get ChainAccount");
        }
        return Optional.empty();
    }

    public static Optional<ChainContribution> getChainContribution(IexecHubABILegacy iexecHub, String chainTaskId, String workerAddress) {
        try {
            return Optional.of(ChainContribution.tuple2Contribution(
                    iexecHub.viewContributionABILegacy(BytesUtils.stringToBytes(chainTaskId), workerAddress).send()));
        } catch (Exception e) {
            log.error("Failed to get ChainContribution [chainTaskId:{}, workerAddress:{}]", chainTaskId, workerAddress);
        }
        return Optional.empty();
    }

    public static Optional<ChainCategory> getChainCategory(IexecHubABILegacy iexecHub, long id) {
        try {
            Tuple3<String, String, BigInteger> category = iexecHub.viewCategoryABILegacy(BigInteger.valueOf(id)).send();
            return Optional.of(ChainCategory.tuple2ChainCategory(id,
                    category.getValue1(),
                    category.getValue2(),
                    category.getValue3()
            ));
        } catch (Exception e) {
            log.error("Failed to get ChainCategory [id:{}]", id);
        }
        return Optional.empty();
    }

    public static Optional<ChainApp> getChainApp(App app) {
        try {
            return Optional.of(ChainApp.builder()
                    .chainAppId(app.getContractAddress())
                    .name(app.m_appName().send())
                    .params(stringParamsToChainAppParams(app.m_appParams().send()))
                    .hash(BytesUtils.bytesToString(app.m_appHash().send()))
                    .build());
        } catch (Exception e) {
            log.error("Failed to get ChainApp [chainAppId:{}]", app.getContractAddress());
        }
        return Optional.empty();
    }

    public static String generateChainTaskId(String dealId, BigInteger taskIndex) {
        byte[] dealIdBytes32 = BytesUtils.stringToBytes(dealId);
        if (dealIdBytes32.length != 32) {
            return null;
        }
        byte[] taskIndexBytes32 = Arrays.copyOf(taskIndex.toByteArray(), 32);
        if (taskIndexBytes32.length != 32) {
            return null;
        }
        //concatenate bytes with same size only
        byte[] concatenate = Arrays.concatenate(dealIdBytes32, taskIndexBytes32);
        return Hash.sha3(BytesUtils.bytesToString(concatenate));
    }

    public static Optional<BigInteger> getBalance(Web3j web3j, String address) {
        try {
            return Optional.of(web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().getBalance());
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public static ContractGasProvider getContractGasProvider() {
        return new ContractGasProvider() {
            private final long DEFAULT_GAS_LIMIT = 500000;
            private final long DEFAULT_GAS_PRICE = 22000000000L;

            @Override
            public BigInteger getGasPrice(String s) {
                return BigInteger.valueOf(DEFAULT_GAS_PRICE);
            }

            @Override
            public BigInteger getGasPrice() {
                return BigInteger.valueOf(DEFAULT_GAS_PRICE);
            }

            @Override
            public BigInteger getGasLimit(String functionName) {
                long gasLimit;
                switch (functionName) {
                    case FUNC_INITIALIZE:
                        gasLimit = 300000;//seen 176340
                        break;
                    case FUNC_CONTRIBUTEABILEGACY:
                        gasLimit = 500000;//seen 333541
                        break;
                    case FUNC_REVEAL:
                        gasLimit = 100000;//seen 56333
                        break;
                    case FUNC_FINALIZE:
                        gasLimit = 3000000;//seen 175369 (242641 in reopen case)
                        break;
                    case FUNC_REOPEN:
                        gasLimit = 500000;//seen 43721
                        break;
                    default:
                        gasLimit = DEFAULT_GAS_LIMIT;
                }
                return BigInteger.valueOf(gasLimit);
            }

            @Override
            public BigInteger getGasLimit() {
                return BigInteger.valueOf(DEFAULT_GAS_LIMIT);
            }
        };
    }

    public static BigInteger getMaxTxCost() {
        return getContractGasProvider().getGasLimit().multiply(getContractGasProvider().getGasPrice());
    }

    public static BigDecimal weiToEth(BigInteger weiAmount) {
        return Convert.fromWei(weiAmount.toString(), Convert.Unit.ETHER);
    }

    public static boolean hasEnoughGas(Web3j web3j, String address) {
        Optional<BigInteger> optionalBalance = getBalance(web3j, address);
        if (!optionalBalance.isPresent()){
            return false;
        }

        BigInteger weiBalance = optionalBalance.get();
        BigInteger estimateTxNb = weiBalance.divide(getMaxTxCost());
        BigDecimal balanceToShow = weiToEth(weiBalance);

        if (estimateTxNb.compareTo(BigInteger.ONE) < 0) {
            log.error("ETH balance is empty, please refill gas now [balance:{}, estimateTxNb:{}]", balanceToShow, estimateTxNb);
            return false;
        } else if(estimateTxNb.compareTo(BigInteger.TEN) < 0){
            log.warn("ETH balance very low, should refill gas now [balance:{}, estimateTxNb:{}]", balanceToShow, estimateTxNb);
        } else {
            log.info("ETH balance is fine [balance:{}, estimateTxNb:{}]", balanceToShow, estimateTxNb);
        }
        return true;
    }

    public static ChainReceipt buildChainReceipt(Log chainResponseLog, String chainTaskId) {
        return buildChainReceipt(chainResponseLog, chainTaskId, 0);
    }

    public static ChainReceipt buildChainReceipt(Log chainResponseLog, String chainTaskId, long lastBlock) {
        if (chainResponseLog == null) {
            log.error("Transaction log received but was null [chainTaskId:{}]", chainTaskId);
            return ChainReceipt.builder().build();
        }

        BigInteger block = chainResponseLog.getBlockNumber();
        String txHash = chainResponseLog.getTransactionHash();

        // it seems response.log.getBlockNumber() could be null (issue in https://github.com/web3j/web3j should be opened)
        if (block == null && txHash == null) {
            log.error("Transaction log received but blockNumber and txHash were both null inside "
                    + "[chainTaskId:{}, action:{} receiptLog:{}, lastBlock:{}]", chainTaskId, chainResponseLog.toString(), lastBlock);

            return ChainReceipt.builder().build();
        }

        long blockNumber = block != null ? block.longValue() : lastBlock;

        return ChainReceipt.builder()
                .blockNumber(blockNumber)
                .txHash(txHash)
                .build();
    }

}
