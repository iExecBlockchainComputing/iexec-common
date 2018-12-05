package com.iexec.common.chain;

import com.iexec.common.contract.generated.App;
import com.iexec.common.contract.generated.IexecClerkABILegacy;
import com.iexec.common.contract.generated.IexecHubABILegacy;
import com.iexec.common.utils.BytesUtils;
import lombok.extern.slf4j.Slf4j;
import org.web3j.crypto.Credentials;
import org.web3j.ens.EnsResolutionException;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tuples.generated.Tuple9;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;

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
                IexecHubABILegacy iexecHubABILegacy = IexecHubABILegacy.load(
                        iexecHubAddress, web3j, credentials, new ContractGasProvider() {
                            @Override
                            public BigInteger getGasPrice(String s) {
                                return BigInteger.valueOf(22000000000L);
                            }

                            @Override
                            public BigInteger getGasPrice() {
                                return BigInteger.valueOf(22000000000L);
                            }

                            @Override
                            public BigInteger getGasLimit(String s) {
                                return BigInteger.valueOf(8000000L);
                            }

                            @Override
                            public BigInteger getGasLimit() {
                                return BigInteger.valueOf(8000000L);
                            }
                        });

                log.info("Loaded contract IexecHub [address:{}] ", iexecHubAddress);
                return iexecHubABILegacy;
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

            IexecClerkABILegacy iexecClerkABILegacy = IexecClerkABILegacy.load(addressClerk, web3j, credentials, new DefaultGasProvider());
            log.info("Loaded contract IexecClerkLegacy [address:{}] ", addressClerk);
            return iexecClerkABILegacy;
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

            App app = App.load(appAddress, web3j, credentials, new DefaultGasProvider());
            log.info("Loaded contract app [address:{}] ", appAddress);
            return app;
        } catch (Exception e) {
            log.error("Failed get ChainApp [address:{}]", appAddress);
        }
        return null;
    }

    public static Optional<ChainDeal> getChainDeal(IexecClerkABILegacy iexecClerk, String chainDealId) {
        byte[] chainDealIdBytes = BytesUtils.stringToBytes(chainDealId);
        try {
            Tuple9<String, String, BigInteger, String, String, BigInteger, String, String, BigInteger> dealPt1 =
                    iexecClerk.viewDealABILegacy_pt1(chainDealIdBytes).send();
            Tuple6<BigInteger, BigInteger, String, String, String, String> dealPt2 =
                    iexecClerk.viewDealABILegacy_pt2(chainDealIdBytes).send();
            Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> config =
                    iexecClerk.viewConfigABILegacy(chainDealIdBytes).send();

            return Optional.of(ChainDeal.builder()
                    .dappPointer(dealPt1.getValue1())
                    .dappOwner(dealPt1.getValue2())
                    .dappPrice(dealPt1.getValue3())
                    .dataPointer(dealPt1.getValue4())
                    .dataOwner(dealPt1.getValue5())
                    .dataPrice(dealPt1.getValue6())
                    .poolPointer(dealPt1.getValue7())
                    .poolOwner(dealPt1.getValue8())
                    .poolPrice(dealPt1.getValue9())
                    .trust(dealPt2.getValue1())
                    .tag(dealPt2.getValue2())
                    .requester(dealPt2.getValue3())
                    .beneficiary(dealPt2.getValue4())
                    .callback(dealPt2.getValue5())
                    .params(dealPt2.getValue6())
                    .category(config.getValue1())
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
}
