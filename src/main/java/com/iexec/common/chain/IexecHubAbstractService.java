package com.iexec.common.chain;

import com.iexec.common.contract.generated.*;
import com.iexec.common.dapp.DappType;
import com.iexec.common.task.TaskDescription;
import com.iexec.common.tee.TeeUtils;
import com.iexec.common.utils.BytesUtils;
import com.iexec.common.utils.MultiAddressHelper;
import lombok.extern.slf4j.Slf4j;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.datatypes.Event;
import org.web3j.crypto.Credentials;
import org.web3j.ens.EnsResolutionException;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tuples.generated.Tuple9;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.Optional;
import java.util.function.BiFunction;

import static com.iexec.common.chain.ChainDeal.stringToDealParams;
import static com.iexec.common.contract.generated.IexecHubABILegacy.*;

@Slf4j
public abstract class IexecHubAbstractService {

    public static final String PENDING_RECEIPT_STATUS = "pending";
    private final Credentials credentials;
    private final String iexecHubAddress;
    private final Web3jAbstractService web3jAbstractService;

    public IexecHubAbstractService(Credentials credentials,
                                   Web3jAbstractService web3jAbstractService,
                                   String iexecHubAddress) {
        this.credentials = credentials;
        this.web3jAbstractService = web3jAbstractService;
        this.iexecHubAddress = iexecHubAddress;

        String hubAddress = getHubContract().getContractAddress();
        String clerkAddress = getClerkContract().getContractAddress();
        log.info("Abstract IexecHubService initialized [hubAddress:{}, clerkAddress:{}]", hubAddress, clerkAddress);
    }

    private static int scoreToWeight(int workerScore) {
        return Math.max(workerScore / 3, 3) - 1;
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
                        iexecHubAddress, web3jAbstractService.getWeb3j(), credentials, contractGasProvider);
            } catch (EnsResolutionException e) {
                throw exceptionInInitializerError;
            }
        } else {
            throw exceptionInInitializerError;
        }
    }

    /*
     * This method should only be used for reading
     */
    public IexecHubABILegacy getHubContract() {
        return getHubContract(new DefaultGasProvider());
    }

    public IexecClerkABILegacy getClerkContract(ContractGasProvider contractGasProvider) {
        IexecHubABILegacy iexecHubABILegacy = getHubContract(contractGasProvider);
        ExceptionInInitializerError exceptionInInitializerError = new ExceptionInInitializerError("Failed to load IexecClerk contract from Hub address " + iexecHubAddress);
        try {
            String addressClerk = iexecHubABILegacy.iexecclerk().send();
            if (addressClerk == null || addressClerk.isEmpty()) {
                throw exceptionInInitializerError;
            }
            return IexecClerkABILegacy.load(addressClerk, web3jAbstractService.getWeb3j(), credentials, contractGasProvider);
        } catch (Exception e) {
            log.error("Failed to load clerk [error:{}]", e.getMessage());
            return null;
        }
    }

    public IexecClerkABILegacy getClerkContract() {
        return getClerkContract(new DefaultGasProvider());
    }

    public App getAppContract(String appAddress) {
        ExceptionInInitializerError exceptionInInitializerError = new ExceptionInInitializerError("Failed to load App contract address " + appAddress);
        try {
            if (appAddress == null || appAddress.isEmpty()) {
                throw exceptionInInitializerError;
            }

            return App.load(appAddress, web3jAbstractService.getWeb3j(), credentials, new DefaultGasProvider());
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

            return Dataset.load(datasetAddress, web3jAbstractService.getWeb3j(), credentials, new DefaultGasProvider());
        } catch (Exception e) {
            log.error("Failed to load chainDataset [address:{}]", datasetAddress);
        }
        return null;
    }

    public Optional<String> getTaskBeneficiary(String chainTaskId, Integer chainId) {
        Optional<ChainTask> chainTask = getChainTask(chainTaskId);
        if (!chainTask.isPresent()) {
            return Optional.empty();
        }
        Optional<ChainDeal> optionalChainDeal = getChainDeal(chainTask.get().getDealid());
        return optionalChainDeal.map(chainDeal -> chainDeal.getBeneficiary().toLowerCase());
    }

    public boolean isPublicResult(String chainTaskId, Integer chainId) {
        Optional<String> beneficiary = getTaskBeneficiary(chainTaskId, chainId);
        if (!beneficiary.isPresent()) {
            log.error("Failed to get beneficiary for isPublicResult() method [chainTaskId:{}]", chainTaskId);
            return false;
        }
        return beneficiary.get().equals(BytesUtils.EMPTY_ADDRESS);
    }

    public String getTaskResults(String chainTaskId, Integer chainId) {
        Optional<ChainTask> chainTask = getChainTask(chainTaskId);
        if (!chainTask.isPresent()) {
            return "";
        }
        return chainTask.get().getResults();
    }

    public Optional<ChainDeal> getChainDeal(String chainDealId) {
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
                    .params(stringToDealParams(dealPt2.getValue6()))
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
            return Optional.of(ChainTask.tuple2ChainTask(getHubContract().viewTaskABILegacy(BytesUtils.stringToBytes(chainTaskId)).send()));
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
                    getHubContract().viewContributionABILegacy(BytesUtils.stringToBytes(chainTaskId), workerAddress).send()));
        } catch (Exception e) {
            log.error("Failed to get ChainContribution [chainTaskId:{}, workerAddress:{}]", chainTaskId, workerAddress);
        }
        return Optional.empty();
    }

    public Optional<ChainCategory> getChainCategory(long id) {
        try {
            Tuple3<String, String, BigInteger> category = getHubContract().viewCategoryABILegacy(BigInteger.valueOf(id)).send();
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
                        .owner(dataset.owner().send())
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

    public Optional<Integer> getWorkerScore(String address) {
        if (address != null && !address.isEmpty()) {
            try {
                BigInteger workerScore = getHubContract().viewScore(address).send();
                return Optional.of(workerScore.intValue());
            } catch (Exception e) {
                log.error("Failed to getWorkerScore [address:{}]", address);
            }
        }
        return Optional.empty();
    }

    public int getWorkerWeight(String address) {
        Optional<Integer> workerScore = getWorkerScore(address);
        if (!workerScore.isPresent()) {
            return 0;
        }
        int weight = scoreToWeight(workerScore.get());
        log.info("Get worker weight [address:{}, score:{}, weight:{}]", address, workerScore.get(), weight);
        return weight;
    }

    public Ownable getOwnableContract(String address) {
        ExceptionInInitializerError exceptionInInitializerError = new ExceptionInInitializerError("Failed to load Ownable contract " + address);
        try {
            if (address == null || address.isEmpty()) {
                throw exceptionInInitializerError;
            }

            return Ownable.load(address, web3jAbstractService.getWeb3j(), credentials, new DefaultGasProvider());
        } catch (Exception e) {
            log.error("Failed to load Ownable [address:{}]", address);
        }
        return null;
    }

    public String getOwner(String address) {
        Ownable ownableContract = getOwnableContract(address);

        if (ownableContract != null) {
            try {
                return ownableContract.owner().send();
            } catch (Exception e) {
                log.error("Failed to get owner [address:{}]", address);
            }
        }
        return "";
    }

    public long getMaxNbOfPeriodsForConsensus() {
        try {
            return getHubContract().CONSENSUS_DURATION_RATIO().send().longValue();
        } catch (Exception e) {
            log.error("Failed to getMaxNbOfPeriodsForConsensus");
        }
        return 0;
    }

    public boolean hasEnoughGas(String address) {
        return web3jAbstractService.hasEnoughGas(address);
    }


    protected boolean isStatusValidOnChainAfterPendingReceipt(String chainTaskId, ChainStatus onchainStatus,
                                                              BiFunction<String, ChainStatus, Boolean> isStatusValidOnChainFunction) {
        long maxWaitingTime = web3jAbstractService.getMaxWaitingTimeWhenPendingReceipt();
        log.info("Waiting for on-chain status after pending receipt [chainTaskId:{}, status:{}, maxWaitingTime:{}]",
                chainTaskId, onchainStatus, maxWaitingTime);

        final long startTime = System.currentTimeMillis();
        long duration = 0;
        while (duration < maxWaitingTime) {
            try {
                if (isStatusValidOnChainFunction.apply(chainTaskId, onchainStatus)) {
                    return true;
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.error("Error in checking the latest block number");
            }
            duration = System.currentTimeMillis() - startTime;
        }

        log.error("Timeout reached after waiting for on-chain status [chainTaskId:{}, maxWaitingTime:{}]",
                chainTaskId, maxWaitingTime);
        return false;
    }

    public Optional<TaskDescription> getTaskDescriptionFromChain(String chainTaskId) {

        Optional<ChainTask> optionalChainTask = getChainTask(chainTaskId);
        if (!optionalChainTask.isPresent()) {
            log.info("Failed to get TaskDescription, ChainTask error  [chainTaskId:{}]", chainTaskId);
            return Optional.empty();
        }

        ChainTask chainTask = optionalChainTask.get();

        Optional<ChainDeal> optionalChainDeal = getChainDeal(chainTask.getDealid());
        if (!optionalChainDeal.isPresent()) {
            log.info("Failed to get TaskDescription, ChainDeal error  [chainTaskId:{}]", chainTaskId);
            return Optional.empty();
        }

        ChainDeal chainDeal = optionalChainDeal.get();

        String datasetURI = chainDeal.getChainDataset() != null ? MultiAddressHelper.convertToURI(chainDeal.getChainDataset().getUri()) : "";

        return Optional.of(TaskDescription.builder()
                .chainTaskId(chainTaskId)
                .requester(chainDeal.getRequester())
                .beneficiary(chainDeal.getBeneficiary())
                .appType(DappType.DOCKER)
                .appUri(BytesUtils.hexStringToAscii(chainDeal.getChainApp().getUri()))
                .cmd(chainDeal.getParams().getIexecArgs())
                .inputFiles(chainDeal.getParams().getIexecInputFiles())
                .maxExecutionTime(chainDeal.getChainCategory().getMaxExecutionTime())
                .isTeeTask(TeeUtils.isTeeTag(chainDeal.getTag()))
                .developerLoggerEnabled(chainDeal.getParams().isIexecDeveloperLoggerEnabled())
                .resultStorageProvider(chainDeal.getParams().getIexecResultStorageProvider())
                .resultEncryption(chainDeal.getParams().getIexecResultEncryption())
                .datasetUri(datasetURI)
                .botSize(chainDeal.botSize.intValue())
                .botFirstIndex(chainDeal.botFirst.intValue())
                .botIndex(chainTask.getIdx())
                .build());
    }

    public boolean isTeeTask(String chainTaskId) {
        Optional<TaskDescription> oTaskDescription = getTaskDescriptionFromChain(chainTaskId);

        if (!oTaskDescription.isPresent()) {
            log.error("Couldn't get task description from chain [chainTaskId:{}]", chainTaskId);
            return false;
        }

        return oTaskDescription.get().isTeeTask();
    }

    public ChainReceipt getContributionBlock(String chainTaskId, String workerWallet, long fromBlock) {
        long latestBlock = web3jAbstractService.getLatestBlockNumber();
        if (fromBlock > latestBlock) {
            return ChainReceipt.builder().build();
        }

        IexecHubABILegacy hub = getHubContract();
        EthFilter ethFilter = createContributeEthFilter(fromBlock, latestBlock);

        // filter only taskContribute events for the chainTaskId and the worker's wallet
        // and retrieve the block number of the event
        return hub.taskContributeEventFlowable(ethFilter)
                .filter(eventResponse ->
                        chainTaskId.equals(BytesUtils.bytesToString(eventResponse.taskid)) &&
                                workerWallet.equals(eventResponse.worker)
                )
                .map(eventResponse -> ChainReceipt.builder()
                        .blockNumber(eventResponse.log.getBlockNumber().longValue())
                        .txHash(eventResponse.log.getTransactionHash())
                        .build())
                .blockingFirst();
    }

    public ChainReceipt getConsensusBlock(String chainTaskId, long fromBlock) {
        long latestBlock = web3jAbstractService.getLatestBlockNumber();
        if (fromBlock > latestBlock) {
            return ChainReceipt.builder().build();
        }
        IexecHubABILegacy hub = getHubContract();
        EthFilter ethFilter = createConsensusEthFilter(fromBlock, latestBlock);

        // filter only taskConsensus events for the chainTaskId (there should be only one)
        // and retrieve the block number of the event
        return hub.taskConsensusEventFlowable(ethFilter)
                .filter(eventResponse -> chainTaskId.equals(BytesUtils.bytesToString(eventResponse.taskid)))
                .map(eventResponse -> ChainReceipt.builder()
                        .blockNumber(eventResponse.log.getBlockNumber().longValue())
                        .txHash(eventResponse.log.getTransactionHash())
                        .build())
                .blockingFirst();
    }

    public ChainReceipt getRevealBlock(String chainTaskId, String workerWallet, long fromBlock) {
        long latestBlock = web3jAbstractService.getLatestBlockNumber();
        if (fromBlock > latestBlock) {
            return ChainReceipt.builder().build();
        }

        IexecHubABILegacy hub = getHubContract();
        EthFilter ethFilter = createRevealEthFilter(fromBlock, latestBlock);

        // filter only taskReveal events for the chainTaskId and the worker's wallet
        // and retrieve the block number of the event
        return hub.taskRevealEventFlowable(ethFilter)
                .filter(eventResponse ->
                        chainTaskId.equals(BytesUtils.bytesToString(eventResponse.taskid)) &&
                                workerWallet.equals(eventResponse.worker)
                )
                .map(eventResponse -> ChainReceipt.builder()
                        .blockNumber(eventResponse.log.getBlockNumber().longValue())
                        .txHash(eventResponse.log.getTransactionHash())
                        .build())
                .blockingFirst();
    }

    private EthFilter createContributeEthFilter(long fromBlock, long toBlock) {
        return createEthFilter(fromBlock, toBlock, TASKCONTRIBUTE_EVENT);
    }

    private EthFilter createConsensusEthFilter(long fromBlock, long toBlock) {
        return createEthFilter(fromBlock, toBlock, TASKCONSENSUS_EVENT);
    }

    private EthFilter createRevealEthFilter(long fromBlock, long toBlock) {
        return createEthFilter(fromBlock, toBlock, TASKREVEAL_EVENT);
    }

    private EthFilter createEthFilter(long fromBlock, long toBlock, Event event) {
        IexecHubABILegacy hub = getHubContract();
        DefaultBlockParameter startBlock = DefaultBlockParameter.valueOf(BigInteger.valueOf(fromBlock));
        DefaultBlockParameter endBlock = DefaultBlockParameter.valueOf(BigInteger.valueOf(toBlock));

        // define the filter
        EthFilter ethFilter = new EthFilter(
                startBlock,
                endBlock,
                hub.getContractAddress()
        );
        ethFilter.addSingleTopic(EventEncoder.encode(event));

        return ethFilter;
    }
}
