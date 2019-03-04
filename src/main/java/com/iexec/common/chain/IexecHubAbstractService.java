package com.iexec.common.chain;

import com.iexec.common.contract.generated.App;
import com.iexec.common.contract.generated.Dataset;
import com.iexec.common.contract.generated.IexecClerkABILegacy;
import com.iexec.common.contract.generated.IexecHubABILegacy;
import com.iexec.common.utils.BytesUtils;
import lombok.extern.slf4j.Slf4j;
import org.web3j.crypto.Credentials;
import org.web3j.ens.EnsResolutionException;
import org.web3j.protocol.Web3j;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tuples.generated.Tuple9;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.Optional;

import static com.iexec.common.chain.ChainDeal.stringParamsToList;

@Slf4j
public abstract class IexecHubAbstractService {

    private final Credentials credentials;
    private final Web3j web3j;
    private final String iexecHubAddress;
    private final Web3jAbstractService web3jAbstractService;

    public IexecHubAbstractService(Credentials credentials,
                                   Web3jAbstractService web3jAbstractService,
                                   String iexecHubAddress) {
        this.credentials = credentials;
        this.web3jAbstractService = web3jAbstractService;
        this.iexecHubAddress = iexecHubAddress;
        web3j = web3jAbstractService.getWeb3j();

        String hubAddress = getHubContract(new DefaultGasProvider()).getContractAddress();
        String clerkAddress = getClerkContract(new DefaultGasProvider()).getContractAddress();
        log.info("Abstract IexecHubService initialized [hubAddress:{}, clerkAddress:{}]", hubAddress, clerkAddress);
    }

    /*
     * We wan't a fresh new instance of IexecHubABILegacy on each call in order to get
     * the last ContractGasProvider which depends on the gas price of the network
     */
    public IexecHubABILegacy getHubContract(ContractGasProvider contractGasProvider) {
        ExceptionInInitializerError exceptionInInitializerError = new ExceptionInInitializerError("Failed to load IexecHub contract from address " + iexecHubAddress);

        if (iexecHubAddress != null && !iexecHubAddress.isEmpty()) {
            try {
                return IexecHubABILegacy.load(
                        iexecHubAddress, web3j, credentials, contractGasProvider);
            } catch (EnsResolutionException e) {
                throw exceptionInInitializerError;
            }
        } else {
            throw exceptionInInitializerError;
        }
    }

    public IexecClerkABILegacy getClerkContract(ContractGasProvider contractGasProvider) {
        IexecHubABILegacy iexecHubABILegacy = getHubContract(contractGasProvider);
        ExceptionInInitializerError exceptionInInitializerError = new ExceptionInInitializerError("Failed to load IexecClerk contract from Hub address " + iexecHubAddress);
        try {
            String addressClerk = iexecHubABILegacy.iexecclerk().send();
            if (addressClerk == null || addressClerk.isEmpty()) {
                throw exceptionInInitializerError;
            }
            return IexecClerkABILegacy.load(addressClerk, web3j, credentials, contractGasProvider);
        } catch (Exception e) {
            log.error("Failed to load clerk [error:{}]", e.getMessage());
            return null;
        }
    }

    public App getAppContract(String appAddress) {
        ExceptionInInitializerError exceptionInInitializerError = new ExceptionInInitializerError("Failed to load App contract address " + appAddress);
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

    public Dataset getDatasetContract(String datasetAddress) {
        ExceptionInInitializerError exceptionInInitializerError = new ExceptionInInitializerError("Failed to load Dataset contract address " + datasetAddress);
        try {
            if (datasetAddress == null || datasetAddress.isEmpty()) {
                throw exceptionInInitializerError;
            }

            return Dataset.load(datasetAddress, web3j, credentials, new DefaultGasProvider());
        } catch (Exception e) {
            log.error("Failed to load chainDataset [address:{}]", datasetAddress);
        }
        return null;
    }

    public Optional<ChainDeal> getChainDeal(String chainDealId) {
        IexecHubABILegacy iexecHub = getHubContract(new DefaultGasProvider());
        IexecClerkABILegacy iexecClerk = getClerkContract(new DefaultGasProvider());

        byte[] chainDealIdBytes = BytesUtils.stringToBytes(chainDealId);
        try {
            Tuple9<String, String, BigInteger, String, String, BigInteger, String, String, BigInteger> dealPt1 =
                    iexecClerk.viewDealABILegacy_pt1(chainDealIdBytes).send();
            Tuple6<BigInteger, byte[], String, String, String, String> dealPt2 =
                    iexecClerk.viewDealABILegacy_pt2(chainDealIdBytes).send();
            Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> config =
                    iexecClerk.viewConfigABILegacy(chainDealIdBytes).send();


            String appAddress = dealPt1.getValue1();
            String datasetAddress = dealPt1.getValue4();
            BigInteger categoryId = config.getValue1();

            Optional<ChainApp> chainApp = getChainApp(getAppContract(appAddress));
            if (!chainApp.isPresent()) {
                return Optional.empty();
            }
            Optional<ChainCategory> chainCategory = getChainCategory(categoryId.longValue());
            if (!chainCategory.isPresent()) {
                return Optional.empty();
            }
            Optional<ChainDataset> chainDataset = getChainDataset(getDatasetContract(datasetAddress));

            return Optional.of(ChainDeal.builder()
                    .chainDealId(chainDealId)
                    .chainApp(chainApp.get())
                    .dappOwner(dealPt1.getValue2())
                    .dappPrice(dealPt1.getValue3())
                    .chainDataset(chainDataset.orElse(null))
                    .dataOwner(dealPt1.getValue5())
                    .dataPrice(dealPt1.getValue6())
                    .poolPointer(dealPt1.getValue7())
                    .poolOwner(dealPt1.getValue8())
                    .poolPrice(dealPt1.getValue9())
                    .trust(dealPt2.getValue1())
                    .tag(BytesUtils.bytesToString(dealPt2.getValue2()))
                    .requester(dealPt2.getValue3())
                    .beneficiary(dealPt2.getValue4())
                    .callback(dealPt2.getValue5())
                    .params(stringParamsToList(dealPt2.getValue6()))
                    .chainCategory(chainCategory.get())
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

    public Optional<ChainTask> getChainTask(String chainTaskId) {
        try {
            return Optional.of(ChainTask.tuple2ChainTask(getHubContract(new DefaultGasProvider()).viewTaskABILegacy(BytesUtils.stringToBytes(chainTaskId)).send()));
        } catch (Exception e) {
            log.error("Failed to get ChainTask [chainTaskId:{}]", chainTaskId);
        }
        return Optional.empty();
    }

    public Optional<ChainAccount> getChainAccount(String walletAddress) {
        try {
            return Optional.of(ChainAccount.tuple2Account(getClerkContract(new DefaultGasProvider()).viewAccountABILegacy(walletAddress).send()));
        } catch (Exception e) {
            log.info("Failed to get ChainAccount");
        }
        return Optional.empty();
    }

    public Optional<ChainContribution> getChainContribution(String chainTaskId, String workerAddress) {
        try {
            return Optional.of(ChainContribution.tuple2Contribution(
                    getHubContract(new DefaultGasProvider()).viewContributionABILegacy(BytesUtils.stringToBytes(chainTaskId), workerAddress).send()));
        } catch (Exception e) {
            log.error("Failed to get ChainContribution [chainTaskId:{}, workerAddress:{}]", chainTaskId, workerAddress);
        }
        return Optional.empty();
    }

    public Optional<ChainCategory> getChainCategory(long id) {
        try {
            Tuple3<String, String, BigInteger> category = getHubContract(new DefaultGasProvider()).viewCategoryABILegacy(BigInteger.valueOf(id)).send();
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

    public Optional<ChainApp> getChainApp(App app) {
        if (app != null && !app.getContractAddress().equals(BytesUtils.EMPTY_ADDRESS)) {
            try {
                return Optional.of(ChainApp.builder()
                        .chainAppId(app.getContractAddress())
                        .name(app.m_appName().send())
                        .type(app.m_appType().send())
                        .uri(BytesUtils.bytesToString(app.m_appMultiaddr().send()))
                        .checksum(BytesUtils.bytesToString(app.m_appChecksum().send()))
                        .build());
            } catch (Exception e) {
                log.error("Failed to get ChainApp [chainAppId:{}]", app.getContractAddress());
            }
        }
        return Optional.empty();
    }

    public Optional<ChainDataset> getChainDataset(Dataset dataset) {
        if (dataset != null && !dataset.getContractAddress().equals(BytesUtils.EMPTY_ADDRESS)) {
            try {
                return Optional.of(ChainDataset.builder()
                        .chainDatasetId(dataset.getContractAddress())
                        .owner(dataset.m_owner().send())
                        .name(dataset.m_datasetName().send())
                        .uri(BytesUtils.bytesToString(dataset.m_datasetMultiaddr().send()))
                        .checksum(BytesUtils.bytesToString(dataset.m_datasetChecksum().send()))
                        .build());
            } catch (Exception e) {
                log.info("Failed to get ChainDataset [chainDatasetId:{}]", dataset.getContractAddress());
            }
        }
        return Optional.empty();
    }

    public long getMaxNbOfPeriodsForConsensus() {
        try {
            return getHubContract(new DefaultGasProvider()).CONSENSUS_DURATION_RATIO().send().longValue();
        } catch (Exception e) {
            log.error("Failed to getMaxNbOfPeriodsForConsensus");
        }
        return 0;
    }

    public boolean hasEnoughGas(String address) {
        return web3jAbstractService.hasEnoughGas(address);
    }

}
