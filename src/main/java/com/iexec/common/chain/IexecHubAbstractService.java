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

package com.iexec.common.chain;

import com.iexec.common.contract.IexecHubSmartContractValidator;
import com.iexec.common.contract.generated.*;
import com.iexec.common.task.TaskDescription;
import com.iexec.common.utils.BytesUtils;
import com.iexec.common.utils.Retryer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.crypto.Credentials;
import org.web3j.ens.EnsResolutionException;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tuples.generated.Tuple9;
import org.web3j.tx.ChainIdLong;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

import static com.iexec.common.chain.ChainContributionStatus.CONTRIBUTED;
import static com.iexec.common.chain.ChainContributionStatus.REVEALED;
import static com.iexec.common.contract.generated.IexecHubContract.*;
import static com.iexec.common.tee.TeeEnclaveConfiguration.buildEnclaveConfigurationFromJsonString;
import static com.iexec.common.utils.BytesUtils.isNonZeroedBytes32;
import static org.web3j.protocol.core.JsonRpc2_0Web3j.DEFAULT_BLOCK_TIME;
import static org.web3j.tx.TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH;


/*
 * Contracts (located at *.contract.generated) which are used in this service are generated from:
 * - https://github.com/iExecBlockchainComputing/PoCo-dev
 * - @ commit c989a8d03410c0cc6c67f7b6a56ef891fc3f964c (HEAD, tag: v5.1.0, origin/v5, origin/HEAD, v5)
 * */
@Slf4j
public abstract class IexecHubAbstractService {

    public static final String PENDING_RECEIPT_STATUS = "pending";
    private final Credentials credentials;
    private final String iexecHubAddress;
    private final Web3jAbstractService web3jAbstractService;
    private long maxNbOfPeriodsForConsensus;
    private final Duration blockTime;
    private final int nbBlocksToWaitPerRetry;
    private final int retryDelay;// ms
    private final int maxRetries;
    // /!\ TODO remove expired task descriptions
    private final Map<String, TaskDescription> taskDescriptions = new HashMap<>();

    public IexecHubAbstractService(Credentials credentials,
                                   Web3jAbstractService web3jAbstractService,
                                   String iexecHubAddress) {
        this(credentials, web3jAbstractService, iexecHubAddress, Duration.ofMillis(DEFAULT_BLOCK_TIME), 6, 3);
    }

    @Deprecated
    public IexecHubAbstractService(Credentials credentials,
                                   Web3jAbstractService web3jAbstractService,
                                   String iexecHubAddress,
                                   int nbBlocksToWaitPerRetry,
                                   int maxRetries) {
        this(credentials, web3jAbstractService, iexecHubAddress, Duration.ofMillis(DEFAULT_BLOCK_TIME), nbBlocksToWaitPerRetry, maxRetries);
    }

    /**
     * Base constructor for the IexecHubAbstractService
     * @param credentials credentials for sending transaction
     * @param web3jAbstractService custom web3j service
     * @param iexecHubAddress address of the iExec Hub contract
     * @param blockTime block time as a duration
     * @param nbBlocksToWaitPerRetry nb block to wait per retry
     * @param maxRetries maximum reties
     */
    public IexecHubAbstractService(Credentials credentials,
                                   Web3jAbstractService web3jAbstractService,
                                   String iexecHubAddress,
                                   Duration blockTime,
                                   int nbBlocksToWaitPerRetry,
                                   int maxRetries) {
        this.credentials = credentials;
        this.web3jAbstractService = web3jAbstractService;
        this.iexecHubAddress = iexecHubAddress;
        this.nbBlocksToWaitPerRetry = nbBlocksToWaitPerRetry;
        if (blockTime == null || blockTime.toMillis() <= 0) {
            log.warn("Block time value is incorrect, using default value "
                            + "[blockTime:{}, DEFAULT_BLOCK_TIME:{}]",
                    blockTime, DEFAULT_BLOCK_TIME);
            this.blockTime = Duration.ofMillis(DEFAULT_BLOCK_TIME);
        } else {
            this.blockTime = blockTime;
        }
        this.retryDelay = nbBlocksToWaitPerRetry * (int)this.blockTime.toMillis();
        this.maxRetries = maxRetries;

        String hubAddress = getHubContract().getContractAddress();
        log.info("Abstract IexecHubService initialized (iexec proxy address) " +
                "[hubAddress:{}]", hubAddress);
        setMaxNbOfPeriodsForConsensus();
    }

    @PostConstruct
    private void validateRemoteIexecHubSmartContract() {
        if (!new IexecHubSmartContractValidator().validate(getHubContract())) {
            throw new IllegalArgumentException(
                    "IexecHub smart contract validation failed."
            );
        }
    }


    private static int scoreToWeight(int workerScore) {
        return Math.max(workerScore / 3, 3) - 1;
    }

    /*
     * We wan't a fresh new instance of IexecHubContract on each call in order to get
     * the last ContractGasProvider which depends on the gas price of the network
     */
    public IexecHubContract getHubContract(ContractGasProvider contractGasProvider) {
        return getHubContract(contractGasProvider,
                ChainIdLong.NONE,
                (int)blockTime.toMillis(),
                DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH);
    }

    public IexecHubContract getHubContract(ContractGasProvider contractGasProvider,
                                           long chainId) {
        return getHubContract(contractGasProvider,
                chainId,
                (int)blockTime.toMillis(),
                DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH);
    }

    /**
     * Get an IexecHubContract instance.
     *
     * @param contractGasProvider gas provider, useful for sending txs
     * @param chainId chain ID for EIP155 protection
     * @param watchFrequency frequency for getting tx receipt
     * @param watchAttempts number of attempts to get tx receipt
     * @return an IexecHubContract instance
     */
    public IexecHubContract getHubContract(ContractGasProvider contractGasProvider,
                                           long chainId,
                                           int watchFrequency,
                                           int watchAttempts) {
        ExceptionInInitializerError exceptionInInitializerError =
                new ExceptionInInitializerError("Failed to load IexecHub " +
                        "contract from address " + iexecHubAddress);

        if (iexecHubAddress != null && !iexecHubAddress.isEmpty()) {
            try {
                return IexecHubContract.load(iexecHubAddress,
                        web3jAbstractService.getWeb3j(),
                        new RawTransactionManager(web3jAbstractService.getWeb3j(),
                                credentials,
                                chainId,
                                watchAttempts,
                                watchFrequency),
                        contractGasProvider);
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
    public IexecHubContract getHubContract() {
        return getHubContract(new DefaultGasProvider());
    }

    // TODO move workerpool methods to their own class (e.g.: WorkerpoolManager)

    // ######################
    // #    Workerpool      #
    // ######################

    public Workerpool getWorkerpoolContract(String workerpoolAddress) {
        ExceptionInInitializerError exceptionInInitializerError =
                new ExceptionInInitializerError("Failed to load Workerpool " +
                        "contract address " + workerpoolAddress);
        try {
            if (workerpoolAddress == null || workerpoolAddress.isEmpty()) {
                throw exceptionInInitializerError;
            }

            return Workerpool.load(workerpoolAddress,
                    web3jAbstractService.getWeb3j(),
                    credentials,
                    new DefaultGasProvider());
        } catch (Exception e) {
            log.error("Failed to load chainWorkerpool [address:{}]", workerpoolAddress, e);
        }
        return null;
    }

    public WorkerpoolRegistry getWorkerpoolRegistryContract(ContractGasProvider contractGasProvider) {
        String workerpoolRegistryAddress = "";
        ExceptionInInitializerError exceptionInInitializerError =
                new ExceptionInInitializerError("Failed to load WorkerpoolRegistry contract");
        try {
            workerpoolRegistryAddress = getHubContract().workerpoolregistry().send();
            if (workerpoolRegistryAddress == null || workerpoolRegistryAddress.isEmpty()) {
                throw exceptionInInitializerError;
            }
            return WorkerpoolRegistry.load(workerpoolRegistryAddress,
                    web3jAbstractService.getWeb3j(),
                    credentials,
                    contractGasProvider);
        } catch (Exception e) {
            log.error("Failed to load WorkerpoolRegistry contract [address:{}]",
                    workerpoolRegistryAddress, e);
        }
        return null;
    }

    /**
     * This method allows to create a new workerpool on iExec
     * <p>
     * Note: Workerpool is an ERC721. We use the Transfer event sent in the
     * ERC721 mint method to retrieve workerpool address
     * tokenId is the generic form of workerpoolAddress
     *
     * @param name workerpool name
     * @param secondsTimeout await workerpool deployment for couple seconds
     * @param secondsPollingInterval check if workerpool is deployed every couple seconds
     * @return workerpool address (e.g.: 0x95ba540ca3c2dfd52a7e487a03e1358dfe9441ce)
     */
    public String createWorkerpool(String name, int secondsTimeout, int secondsPollingInterval) {
        String owner = credentials.getAddress();
        final String paramsPrinter = " [owner:{}, name:{}]";

        if (StringUtils.isEmpty(owner) || StringUtils.isEmpty(name)) {
            log.error("Non empty inputs are required" + paramsPrinter, owner, name);
            return "";
        }

        WorkerpoolRegistry workerpoolRegistry =
                getWorkerpoolRegistryContract(web3jAbstractService.getWritingContractGasProvider());
        if (workerpoolRegistry == null) {
            log.error("Failed to get workerpoolRegistry" + paramsPrinter, owner, name);
            return "";
        }

        RemoteCall<TransactionReceipt> createWorkerpoolCall = workerpoolRegistry
                .createWorkerpool(owner, name);

        TransactionReceipt createWorkerpoolReceipt;
        try {
            createWorkerpoolReceipt = createWorkerpoolCall.send();
        } catch (Exception e) {
            log.error("Failed to send createWorkerpool transaction" + paramsPrinter,
                    owner, name, e);
            return "";
        }

        if (!createWorkerpoolReceipt.isStatusOK()) {
            log.error("Bad response status for createWorkerpool transaction" + paramsPrinter,
                    owner, name);
            return "";
        }

        String workerpoolAddress = workerpoolRegistry.getTransferEvents(createWorkerpoolReceipt)
                .stream()
                .findFirst()
                .map(event -> event.tokenId) // workerpool is an ERC721
                .map(Address::new)
                .map(Address::toString)
                .orElse("");

        if (StringUtils.isEmpty(workerpoolAddress)) {
            log.error("Failed to extract workerpool address" + paramsPrinter,
                    owner, name);
            return "";
        }

        //tx hash can be null, manually verifying contract is deployed
        Callable<Optional<ChainWorkerpool>> isDeployedWorkerpool = () -> {
            log.info("Waiting for contract deployment" + paramsPrinter,
                    owner, name);
            return getChainWorkerpool(getWorkerpoolContract(workerpoolAddress));
        };

        try {
            Awaitility.await()
                    .atMost(secondsTimeout, TimeUnit.SECONDS)
                    .pollInterval(secondsPollingInterval, TimeUnit.SECONDS)
                    .until(isDeployedWorkerpool, Optional::isPresent);
        } catch (ConditionTimeoutException e) {
            log.error("Reached timeout when waiting for contract deployment"
                    + paramsPrinter, owner, name, e);
            return "";
        }
        return workerpoolAddress;
    }

    /**
     * Default method for creating workerpool
     *
     * @param name workerpool name
     * @return workerpool address (e.g.: 0x95ba540ca3c2dfd52a7e487a03e1358dfe9441ce)
     */
    public String createWorkerpool(String name) {
        return createWorkerpool(name, 10 * 60, 5);
    }

    /**
     * This method to predict workerpool address without deploying it
     *
     * @param owner workerpool owner
     * @param name workerpool name
     * @return workerpool address (e.g.: 0x95ba540ca3c2dfd52a7e487a03e1358dfe9441ce)
     */
    public String predictWorkerpool(String owner, String name) {
        final String paramsPrinter = " [owner:{}, name:{}]";

        if (StringUtils.isEmpty(owner) || StringUtils.isEmpty(name)) {
            log.error("Non empty inputs are required" + paramsPrinter, owner, name);
            return "";
        }

        WorkerpoolRegistry workerpoolRegistry =
                getWorkerpoolRegistryContract(web3jAbstractService.getReadingContractGasProvider());
        if (workerpoolRegistry == null) {
            log.error("Failed to get workerpoolRegistry" + paramsPrinter, owner, name);
            return null;
        }

        RemoteFunctionCall<String> call = workerpoolRegistry.predictWorkerpool(owner,name);
        String address = "";
        try {
            address = call.send();
        } catch (Exception e) {
            log.error("Failed to get predictWorkerpool" + paramsPrinter, owner, name, e);
        }
        return address;
    }

    // TODO move app methods to its own class (e.g.: AppManager)

    // ######################
    // #        App         #
    // ######################

    public App getAppContract(String appAddress) {
        ExceptionInInitializerError exceptionInInitializerError =
                new ExceptionInInitializerError("Failed to load App " +
                        "contract address " + appAddress);
        try {
            if (appAddress == null || appAddress.isEmpty()) {
                throw exceptionInInitializerError;
            }

            return App.load(appAddress,
                    web3jAbstractService.getWeb3j(),
                    credentials,
                    new DefaultGasProvider());
        } catch (Exception e) {
            log.error("Failed to load chainApp [address:{}]", appAddress, e);
        }
        return null;
    }

    public AppRegistry getAppRegistryContract(ContractGasProvider contractGasProvider) {
        String appRegistryAddress = "";
        ExceptionInInitializerError exceptionInInitializerError =
                new ExceptionInInitializerError("Failed to load AppRegistry contract");
        try {
            appRegistryAddress = getHubContract().appregistry().send();
            if (appRegistryAddress == null || appRegistryAddress.isEmpty()) {
                throw exceptionInInitializerError;
            }
            return AppRegistry.load(appRegistryAddress,
                    web3jAbstractService.getWeb3j(),
                    credentials,
                    contractGasProvider);
        } catch (Exception e) {
            log.error("Failed to load AppRegistry contract [address:{}]",
                    appRegistryAddress, e);
        }
        return null;
    }

    /**
     * This method allows to create a new app on iExec
     * <p>
     * Note: App is an ERC721. We use the Transfer event sent in the
     * ERC721 mint method to retrieve app address
     * tokenId is the generic form of appAddress
     *
     * @param name app name
     * @param multiAddress app url
     * @param type app type
     * @param checksum app sha256 checksum
     * @param mrEnclave app mrEnclave
     * @param secondsTimeout await app deployment for couple seconds
     * @param secondsPollingInterval check if app is deployed every couple seconds
     * @return app address (e.g.: 0x95ba540ca3c2dfd52a7e487a03e1358dfe9441ce)
     */
    public String createApp(String name, String multiAddress, String type,
            String checksum, String mrEnclave, int secondsTimeout, int secondsPollingInterval) {
        String owner = credentials.getAddress();
        final String paramsPrinter = " [owner:{}, name:{}]";

        if (StringUtils.isEmpty(owner) || StringUtils.isEmpty(name)) {
            log.error("Non empty inputs are required" + paramsPrinter, owner, name);
            return "";
        }

        AppRegistry appRegistry =
                getAppRegistryContract(web3jAbstractService.getWritingContractGasProvider());
        if (appRegistry == null) {
            log.error("Failed to get appRegistry" + paramsPrinter, owner, name);
            return "";
        }

        RemoteCall<TransactionReceipt> createAppCall = appRegistry
                .createApp(owner, name, type,
                        multiAddress.getBytes(StandardCharsets.UTF_8),
                        BytesUtils.hexStringToBytes32(checksum),
                        mrEnclave.getBytes(StandardCharsets.UTF_8));

        TransactionReceipt createAppReceipt;
        try {
            createAppReceipt = createAppCall.send();
        } catch (Exception e) {
            log.error("Failed to send createApp transaction" + paramsPrinter,
                    owner, name, e);
            return "";
        }

        if (!createAppReceipt.isStatusOK()) {
            log.error("Bad response status for createApp transaction" + paramsPrinter,
                    owner, name);
            return "";
        }

        String appAddress = appRegistry.getTransferEvents(createAppReceipt)
                .stream()
                .findFirst()
                .map(event -> event.tokenId) // app is an ERC721
                .map(Address::new)
                .map(Address::toString)
                .orElse("");

        if (StringUtils.isEmpty(appAddress)) {
            log.error("Failed to extract app address" + paramsPrinter,
                    owner, name);
            return "";
        }

        //tx hash can be null, manually verifying contract is deployed
        Callable<Optional<ChainApp>> isDeployedApp = () -> {
            log.info("Waiting for contract deployment" + paramsPrinter,
                    owner, name);
            return getChainApp(getAppContract(appAddress));
        };

        try {
            Awaitility.await()
                    .atMost(secondsTimeout, TimeUnit.SECONDS)
                    .pollInterval(secondsPollingInterval, TimeUnit.SECONDS)
                    .until(isDeployedApp, Optional::isPresent);
        } catch (ConditionTimeoutException e) {
            log.error("Reached timeout when waiting for contract deployment"
                    + paramsPrinter, owner, name, e);
            return "";
        }
        return appAddress;
    }

    /**
     * Default method for creating app
     *
     * @param name app name
     * @param multiAddress app url
     * @param type app type
     * @param checksum app sha256 checksum
     * @param mrEnclave app mrEnclave
     * @return app address (e.g.: 0x95ba540ca3c2dfd52a7e487a03e1358dfe9441ce)
     */
    public String createApp(String name, String multiAddress, String type,
            String checksum, String mrEnclave) {
        return createApp(name, multiAddress, type, checksum, mrEnclave, 10 * 60, 5);
    }

    /**
     * This method to predict app address without deploying it
     *
     * @param owner app owner
     * @param name app name
     * @param multiAddress app url
     * @param checksum app sha256 checksum
     * @return app address (e.g.: 0x95ba540ca3c2dfd52a7e487a03e1358dfe9441ce)
     */
    public String predictApp(String owner, String name, String multiAddress, String type,
            String checksum, String mrEnclave) {
        final String paramsPrinter = " [owner:{}, name:{}, multiAddress:{}, checksum:{}]";

        if (StringUtils.isEmpty(owner) || StringUtils.isEmpty(name)
                || StringUtils.isEmpty(multiAddress) || StringUtils.isEmpty(checksum)) {
            log.error("Non empty inputs are required" + paramsPrinter,
                    owner, name, multiAddress, checksum);
            return "";
        }

        AppRegistry appRegistry =
                getAppRegistryContract(web3jAbstractService.getReadingContractGasProvider());
        if (appRegistry == null) {
            log.error("Failed to get appRegistry" + paramsPrinter,
                    owner, name, multiAddress, checksum);
            return null;
        }

        RemoteFunctionCall<String> call = appRegistry.predictApp(owner, name, type,
                multiAddress.getBytes(StandardCharsets.UTF_8),
                BytesUtils.hexStringToBytes32(checksum),
                mrEnclave.getBytes(StandardCharsets.UTF_8));
        String address = "";
        try {
            address = call.send();
        } catch (Exception e) {
            log.error("Failed to get predictApp" + paramsPrinter,
                    owner, name, multiAddress, checksum, e);
        }
        return address;
    }

    // TODO move dataset methods to its own class (e.g.: DatasetManager)

    // ######################
    // #      Dataset       #
    // ######################

    public Dataset getDatasetContract(String datasetAddress) {
        ExceptionInInitializerError exceptionInInitializerError =
                new ExceptionInInitializerError("Failed to load Dataset " +
                        "contract address " + datasetAddress);
        try {
            if (datasetAddress == null || datasetAddress.isEmpty()) {
                throw exceptionInInitializerError;
            }

            return Dataset.load(datasetAddress,
                    web3jAbstractService.getWeb3j(),
                    credentials,
                    new DefaultGasProvider());
        } catch (Exception e) {
            log.error("Failed to load chainDataset [address:{}]", datasetAddress, e);
        }
        return null;
    }

    public DatasetRegistry getDatasetRegistryContract(ContractGasProvider contractGasProvider) {
        String datasetRegistryAddress = "";
        ExceptionInInitializerError exceptionInInitializerError =
                new ExceptionInInitializerError("Failed to load DatasetRegistry contract");
        try {
            datasetRegistryAddress = getHubContract().datasetregistry().send();
            if (datasetRegistryAddress == null || datasetRegistryAddress.isEmpty()) {
                throw exceptionInInitializerError;
            }
            return DatasetRegistry.load(datasetRegistryAddress,
                    web3jAbstractService.getWeb3j(),
                    credentials,
                    contractGasProvider);
        } catch (Exception e) {
            log.error("Failed to load DatasetRegistry contract [address:{}]",
                    datasetRegistryAddress, e);
        }
        return null;
    }

    /**
     * This method allows to create a new dataset on iExec
     * <p>
     * Note: Dataset is an ERC721. We use the Transfer event sent in the
     * ERC721 mint method to retrieve dataset address
     * tokenId is the generic form of datasetAddress
     *
     * @param name dataset name
     * @param multiAddress dataset url
     * @param checksum dataset sha256 checksum
     * @param secondsTimeout await dataset deployment for couple seconds
     * @param secondsPollingInterval check if dataset is deployed every couple seconds
     * @return dataset address (e.g.: 0x95ba540ca3c2dfd52a7e487a03e1358dfe9441ce)
     */
    public String createDataset(String name, String multiAddress, String checksum,
                                int secondsTimeout, int secondsPollingInterval) {
        String owner = credentials.getAddress();
        final String paramsPrinter = " [owner:{}, name:{}, multiAddress:{}, checksum:{}]";

        if (StringUtils.isEmpty(owner) || StringUtils.isEmpty(name)
                || StringUtils.isEmpty(multiAddress) || StringUtils.isEmpty(checksum)) {
            log.error("Non empty inputs are required" + paramsPrinter,
                    owner, name, multiAddress, checksum);
            return "";
        }

        DatasetRegistry datasetRegistry =
                getDatasetRegistryContract(web3jAbstractService.getWritingContractGasProvider());
        if (datasetRegistry == null) {
            log.error("Failed to get datasetRegistry" + paramsPrinter,
                    owner, name, multiAddress, checksum);
            return "";
        }

        RemoteCall<TransactionReceipt> createDatasetCall = datasetRegistry
                .createDataset(
                        owner,
                        name,
                        multiAddress.getBytes(StandardCharsets.UTF_8),
                        BytesUtils.hexStringToBytes32(checksum));

        TransactionReceipt createDatasetReceipt;
        try {
            createDatasetReceipt = createDatasetCall.send();
        } catch (Exception e) {
            log.error("Failed to send createDataset transaction" + paramsPrinter,
                    owner, name, multiAddress, checksum, e);
            return "";
        }

        if (!createDatasetReceipt.isStatusOK()) {
            log.error("Bad response status for createDataset transaction" + paramsPrinter,
                    owner, name, multiAddress, checksum);
            return "";
        }

        String datasetAddress = datasetRegistry.getTransferEvents(createDatasetReceipt)
                .stream()
                .findFirst()
                .map(event -> event.tokenId) // dataset is an ERC721
                .map(Address::new)
                .map(Address::toString)
                .orElse("");

        if (StringUtils.isEmpty(datasetAddress)) {
            log.error("Failed to extract dataset address" + paramsPrinter,
                    owner, name, multiAddress, checksum);
            return "";
        }

        //tx hash can be null, manually verifying contract is deployed
        Callable<Optional<ChainDataset>> isDeployedDataset = () -> {
            log.info("Waiting for contract deployment" + paramsPrinter,
                    owner, name, multiAddress, checksum);
            return getChainDataset(getDatasetContract(datasetAddress));
        };

        try {
            Awaitility.await()
                    .atMost(secondsTimeout, TimeUnit.SECONDS)
                    .pollInterval(secondsPollingInterval, TimeUnit.SECONDS)
                    .until(isDeployedDataset, Optional::isPresent);
        } catch (ConditionTimeoutException e) {
            log.error("Reached timeout when waiting for contract deployment"
                    + paramsPrinter, owner, name, multiAddress, checksum, e);
            return "";
        }
        return datasetAddress;
    }

    /**
     * Default method for creating dataset
     *
     * @param name dataset name
     * @param multiAddress dataset url
     * @param checksum dataset sha256 checksum
     * @return dataset address (e.g.: 0x95ba540ca3c2dfd52a7e487a03e1358dfe9441ce)
     */
    public String createDataset(String name, String multiAddress, String checksum) {
        return createDataset(name, multiAddress, checksum, 10 * 60, 5);
    }

    /**
     * This method to predict dataset address without deploying it
     *
     * @param owner dataset owner
     * @param name dataset name
     * @param multiAddress dataset url
     * @param checksum dataset sha256 checksum
     * @return dataset address (e.g.: 0x95ba540ca3c2dfd52a7e487a03e1358dfe9441ce)
     */
    public String predictDataset(String owner, String name, String multiAddress,
                                 String checksum) {
        final String paramsPrinter = " [owner:{}, name:{}, multiAddress:{}, checksum:{}]";

        if (StringUtils.isEmpty(owner) || StringUtils.isEmpty(name)
                || StringUtils.isEmpty(multiAddress) || StringUtils.isEmpty(checksum)) {
            log.error("Non empty inputs are required" + paramsPrinter,
                    owner, name, multiAddress, checksum);
            return "";
        }

        DatasetRegistry datasetRegistry =
                getDatasetRegistryContract(web3jAbstractService.getReadingContractGasProvider());
        if (datasetRegistry == null) {
            log.error("Failed to get datasetRegistry" + paramsPrinter,
                    owner, name, multiAddress, checksum);
            return null;
        }

        RemoteFunctionCall<String> call = datasetRegistry
                .predictDataset(owner,
                        name,
                        multiAddress.getBytes(StandardCharsets.UTF_8),
                        BytesUtils.hexStringToBytes32(checksum));
        String address = "";
        try {
            address = call.send();
        } catch (Exception e) {
            log.error("Failed to get predictDataset" + paramsPrinter,
                    owner, name, multiAddress, checksum, e);
        }
        return address;
    }

    public Optional<String> getTaskBeneficiary(String chainTaskId, Integer chainId) {
        Optional<ChainTask> chainTask = getChainTask(chainTaskId);
        if (chainTask.isEmpty()) {
            log.error("Failed to get Task Beneficiary as ChainTask not found" +
                    " [chainTaskId:{}]", chainTaskId);
            return Optional.empty();
        }
        Optional<ChainDeal> optionalChainDeal = getChainDeal(chainTask.get().getDealid());
        return optionalChainDeal.map(chainDeal -> chainDeal.getBeneficiary().toLowerCase());
    }

    public boolean isPublicResult(String chainTaskId, Integer chainId) {
        Optional<String> beneficiary = getTaskBeneficiary(chainTaskId, chainId);
        if (beneficiary.isEmpty()) {
            log.error("Failed to get beneficiary for isPublicResult() method" +
                    " [chainTaskId:{}]", chainTaskId);
            return false;
        }
        return beneficiary.get().equals(BytesUtils.EMPTY_ADDRESS);
    }

    public String getTaskResults(String chainTaskId, Integer chainId) {
        Optional<ChainTask> chainTask = getChainTask(chainTaskId);
        if (chainTask.isEmpty()) {
            log.error("Failed to get Task Results as ChainTask not found" +
                    " [chainTaskId:{}]", chainTaskId);
            return "";
        }
        return chainTask.get().getResults();
    }

    /**
     * Retrieves on-chain deal with a retryer
     *
     * @param chainDealId deal ID
     * @param retryDelay delay between retries in ms
     * @param maxRetry number of maximum retry
     * @return optional ChainDeal
     */
    public Optional<ChainDeal> repeatGetChainDeal(String chainDealId,
                                                  int retryDelay,
                                                  int maxRetry) {
        return new Retryer<Optional<ChainDeal>>()
                .repeatCall(() -> getChainDeal(chainDealId),
                        Optional::isEmpty,
                        retryDelay, maxRetry,
                        String.format("getChainDeal(chainDealId) " +
                                "[chainDealId:%s]", chainDealId));
    }

    /**
     * Retrieves on-chain deal with its blockchain ID
     *
     * Note:
     * If `start time` is invalid, it is likely a blockchain issue. In this case,
     * in order to protect workflows based on top of it, the deal won't be
     * accessible from this method
     *
     * @param chainDealId blockchain ID of the deal (e.g: 0x123..abc)
     * @return deal object
     */
    public Optional<ChainDeal> getChainDeal(String chainDealId) {
        IexecHubContract iexecHub = getHubContract(new DefaultGasProvider());

        byte[] chainDealIdBytes = BytesUtils.stringToBytes(chainDealId);
        try {
            Tuple9<String, String, BigInteger, String, String, BigInteger, String, String, BigInteger> dealPt1 =
                    iexecHub.viewDealABILegacy_pt1(chainDealIdBytes).send();
            Tuple6<BigInteger, byte[], String, String, String, String> dealPt2 =
                    iexecHub.viewDealABILegacy_pt2(chainDealIdBytes).send();
            Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> config =
                    iexecHub.viewConfigABILegacy(chainDealIdBytes).send();


            String appAddress = dealPt1.component1();
            String datasetAddress = dealPt1.component4();
            BigInteger categoryId = config.component1();

            Optional<ChainApp> chainApp = getChainApp(getAppContract(appAddress));
            if (chainApp.isEmpty()) {
                return Optional.empty();
            }
            ChainApp app = chainApp.get();
            Optional<ChainCategory> chainCategory = getChainCategory(categoryId.longValue());
            if (chainCategory.isEmpty()) {
                return Optional.empty();
            }
            ChainCategory category = chainCategory.get();
            Optional<ChainDataset> chainDataset =
                    getChainDataset(getDatasetContract(datasetAddress));
            ChainDataset dataset = chainDataset.orElse(null);

            ChainDeal chainDeal = ChainDeal.parts2ChainDeal(chainDealId, dealPt1, dealPt2, config, app, category, dataset);

            if (chainDeal.getStartTime() == null
                    || chainDeal.getStartTime().longValue() <= 0) {
                log.error("Deal start time should be greater than zero (likely a " +
                                "blockchain issue) [chainDealId:{}, startTime:{}]",
                        chainDealId, chainDeal.getStartTime());
                return Optional.empty();
            }
            return Optional.of(chainDeal);
        } catch (Exception e) {
            log.error("Failed to get ChainDeal [chainDealId:{}]", chainDealId, e);
        }
        return Optional.empty();
    }

    /**
     * Retrieve on-chain task with a retryer
     *
     * @param chainTaskId task ID
     * @param retryDelay delay between retries in ms
     * @param maxRetry number of maximum retry
     * @return optional ChainTask
     */
    public Optional<ChainTask> repeatGetChainTask(String chainTaskId,
                                                  int retryDelay,
                                                  int maxRetry) {
        return new Retryer<Optional<ChainTask>>()
                .repeatCall(() -> getChainTask(chainTaskId),
                        Optional::isEmpty,
                        retryDelay, maxRetry,
                        String.format("getChainTask(chainTaskId) " +
                                "[chainTaskId:%s]", chainTaskId));
    }

    public Optional<ChainTask> getChainTask(String chainTaskId) {
        try {
            ChainTask chainTask = ChainTask.tuple2ChainTask(getHubContract()
                    .viewTaskABILegacy(BytesUtils.stringToBytes(chainTaskId)).send());
            String chainDealId = chainTask.getDealid();
            if (isNonZeroedBytes32(chainDealId)){
                return Optional.of(chainTask);
            } else {
                log.debug("Failed to get consistent ChainTask [chainTaskId:{}]",
                        chainTaskId);
            }
        } catch (Exception e) {
            log.error("Failed to get ChainTask [chainTaskId:{}]", chainTaskId, e);
        }
        return Optional.empty();
    }

    public Optional<ChainAccount> getChainAccount(String walletAddress) {
        try {
            return Optional.of(ChainAccount.tuple2Account(getHubContract(new DefaultGasProvider())
                    .viewAccountABILegacy(walletAddress).send()));
        } catch (Exception e) {
            log.error("Failed to get ChainAccount [walletAddress:{}]", walletAddress, e);
        }
        return Optional.empty();
    }

    public Optional<ChainContribution> getChainContribution(String chainTaskId,
                                                            String workerAddress) {
        try {
            return Optional.of(ChainContribution.tuple2Contribution(getHubContract()
                    .viewContributionABILegacy(BytesUtils
                            .stringToBytes(chainTaskId), workerAddress).send()));
        } catch (Exception e) {
            log.error("Failed to get ChainContribution [chainTaskId:{}" +
                    ", workerAddress:{}]", chainTaskId, workerAddress, e);
        }
        return Optional.empty();
    }

    /**
     * Retrieves on-chain category with its blockchain ID
     *
     * Note:
     * If `max execution time` is invalid, it is likely a blockchain issue.
     * In this case,in order to protect workflows based on top of it, the category
     * won't be accessible from this method
     *
     * @param id blockchain ID of the category (e.g: 0x123..abc)
     * @return category object
     */
    public Optional<ChainCategory> getChainCategory(long id) {
        try {
            Tuple3<String, String, BigInteger> category = getHubContract()
                    .viewCategoryABILegacy(BigInteger.valueOf(id)).send();
            ChainCategory chainCategory = ChainCategory.tuple2ChainCategory(id,
                    category.component1(),
                    category.component2(),
                    category.component3()
            );
            if (chainCategory.getMaxExecutionTime() <= 0) {
                log.error("Category max execution time should be greater than zero " +
                        "(likely a blockchain issue) [categoryId:{}, maxExecutionTime:{}]",
                        id, chainCategory.getMaxExecutionTime());
                return Optional.empty();
            }
            return Optional.of(chainCategory);
        } catch (Exception e) {
            log.error("Failed to get ChainCategory [id:{}]", id, e);
        }
        return Optional.empty();
    }

    public Optional<ChainWorkerpool> getChainWorkerpool(Workerpool workerpool) {
        if (workerpool != null && !workerpool.getContractAddress().equals(BytesUtils.EMPTY_ADDRESS)) {
            try {
                return Optional.of(ChainWorkerpool.builder()
                        .chainWorkerpoolId(workerpool.getContractAddress())
                        .owner(workerpool.owner().send())
                        .description(workerpool.m_workerpoolDescription().send())
                        .build());
            } catch (Exception e) {
                log.error("Failed to get ChainDataset [chainDatasetId:{}]",
                        workerpool.getContractAddress(), e);
            }
        }
        return Optional.empty();
    }

    public Optional<ChainApp> getChainApp(App app) {
        if (app == null ||
                StringUtils.isEmpty(app.getContractAddress()) ||
                app.getContractAddress().equals(BytesUtils.EMPTY_ADDRESS)) {
            return Optional.empty();
        }
        ChainApp chainApp;
        try {
            chainApp = ChainApp.builder()
                    .chainAppId(app.getContractAddress())
                    .name(app.m_appName().send())
                    .type(app.m_appType().send())
                    .uri(BytesUtils.bytesToString(app.m_appMultiaddr().send()))
                    .checksum(BytesUtils.bytesToString(app.m_appChecksum().send()))
                    .build();
        } catch (Exception e) {
            log.error("Failed to get chain app [chainAppId:{}]",
                    app.getContractAddress(), e);
            return Optional.empty();
        }
        String mrEnclave;
        try {
            mrEnclave = new String(app.m_appMREnclave().send());
        } catch (Exception e) {
            log.error("Failed to get chain app mrenclave [chainAppId:{}]",
                    app.getContractAddress(), e);
            return Optional.empty();
        }
        if (StringUtils.isEmpty(mrEnclave)) {
            // Standard application
            return Optional.of(chainApp);
        }
        try {
            chainApp.setEnclaveConfiguration(
                    buildEnclaveConfigurationFromJsonString(mrEnclave));
        } catch (Exception e) {
            log.error("Failed to get tee chain app enclave configuration " +
                    "[chainAppId:{}, mrEnclave:{}]", app.getContractAddress(),
                    mrEnclave, e);
            return Optional.empty();
        }
        return Optional.of(chainApp);
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
                log.error("Failed to get ChainDataset [chainDatasetId:{}]",
                        dataset.getContractAddress(), e);
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
                log.error("Failed to getWorkerScore [address:{}]", address, e);
            }
        }
        return Optional.empty();
    }

    public int getWorkerWeight(String address) {
        Optional<Integer> workerScore = getWorkerScore(address);
        if (workerScore.isEmpty()) {
            return 0;
        }
        int weight = scoreToWeight(workerScore.get());
        log.info("Get worker weight [address:{}, score:{}, weight:{}]",
                address, workerScore.get(), weight);
        return weight;
    }

    public Ownable getOwnableContract(String address) {
        ExceptionInInitializerError exceptionInInitializerError =
                new ExceptionInInitializerError("Failed to load Ownable " +
                        "contract " + address);
        try {
            if (address == null || address.isEmpty()) {
                throw exceptionInInitializerError;
            }

            return Ownable.load(address,
                    web3jAbstractService.getWeb3j(),
                    credentials,
                    new DefaultGasProvider());
        } catch (Exception e) {
            log.error("Failed to load Ownable [address:{}]", address, e);
        }
        return null;
    }

    public String getOwner(String address) {
        Ownable ownableContract = getOwnableContract(address);

        if (ownableContract != null) {
            try {
                return ownableContract.owner().send();
            } catch (Exception e) {
                log.error("Failed to get owner [address:{}]", address, e);
            }
        }
        return "";
    }

    /**
     * get the value of MaxNbOfPeriodsForConsensus
     * written onchain.
     *
     * @return the value found onchain or -1 if
     * we could not read it.
     */
    public long getMaxNbOfPeriodsForConsensus() {
        if (maxNbOfPeriodsForConsensus == -1) {
            setMaxNbOfPeriodsForConsensus();
        }
        return maxNbOfPeriodsForConsensus;
    }

    private void setMaxNbOfPeriodsForConsensus() {
        try {
            this.maxNbOfPeriodsForConsensus = getHubContract()
                    .contribution_deadline_ratio().send().longValue();
        } catch (Exception e) {
            log.error("Failed to get maxNbOfPeriodsForConsensus from the chain", e);
            this.maxNbOfPeriodsForConsensus = -1;
        }
    }

    public boolean hasEnoughGas(String address) {
        return web3jAbstractService.hasEnoughGas(address);
    }


    protected boolean isStatusValidOnChainAfterPendingReceipt(String chainTaskId,
                                                              ChainStatus onchainStatus,
                                                              BiFunction<String, ChainStatus, Boolean> isStatusValidOnChainFunction) {
        long maxWaitingTime = web3jAbstractService.getMaxWaitingTimeWhenPendingReceipt();
        log.info("Waiting for on-chain status after pending receipt " +
                        "[chainTaskId:{}, status:{}, maxWaitingTime:{}]",
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
                log.error("Error in checking the latest block number", e);
            }
            duration = System.currentTimeMillis() - startTime;
        }

        log.error("Timeout reached after waiting for on-chain status " +
                        "[chainTaskId:{}, maxWaitingTime:{}]",
                chainTaskId, maxWaitingTime);
        return false;
    }

    /*
     * Behaves as a cache to avoid always calling blockchain to retrieve task description
     *
     */
    public TaskDescription getTaskDescription(String chainTaskId) {
        if(!taskDescriptions.containsKey(chainTaskId)) {
            repeatGetTaskDescriptionFromChain(chainTaskId, retryDelay, maxRetries)
                    .ifPresent(taskDescription ->
                            taskDescriptions.putIfAbsent(chainTaskId, taskDescription));
        }
        return taskDescriptions.get(chainTaskId);
    }

    public Optional<TaskDescription> getTaskDescriptionFromChain(String chainTaskId) {
        return repeatGetTaskDescriptionFromChain(chainTaskId, retryDelay, 0);
    }

    public Optional<TaskDescription> repeatGetTaskDescriptionFromChain(String chainTaskId,
                                                                     int retryDelay,
                                                                     int maxRetry) {
        if (retryDelay == 0){
            return Optional.empty();
        }
        Optional<ChainTask> optionalChainTask =
                repeatGetChainTask(chainTaskId, retryDelay, maxRetry);
        if (optionalChainTask.isEmpty()) {
            log.info("Failed to get TaskDescription, ChainTask error " +
                    "[chainTaskId:{}]", chainTaskId);
            return Optional.empty();
        }
        ChainTask chainTask = optionalChainTask.get();

        Optional<ChainDeal> optionalChainDeal =
                repeatGetChainDeal(chainTask.getDealid(), retryDelay, maxRetry);
        if (optionalChainDeal.isEmpty()) {
            log.info("Failed to get TaskDescription, ChainDeal error " +
                    "[chainTaskId:{}]", chainTaskId);
            return Optional.empty();
        }
        ChainDeal chainDeal = optionalChainDeal.get();

        TaskDescription taskDescription =
                TaskDescription.toTaskDescription(chainTaskId,
                        chainTask.getIdx(),
                        chainDeal);
        return taskDescription != null ? Optional.of(taskDescription) : Optional.empty();
    }

    public boolean isTeeTask(String chainTaskId) {
        Optional<TaskDescription> oTaskDescription =
                getTaskDescriptionFromChain(chainTaskId);

        if (oTaskDescription.isEmpty()) {
            log.error("Couldn't get task description from chain [chainTaskId:{}]",
                    chainTaskId);
            return false;
        }

        return oTaskDescription.get().isTeeTask();
    }

    public ChainReceipt getContributionBlock(String chainTaskId, String workerWallet,
                                             long fromBlock) {
        long latestBlock = web3jAbstractService.getLatestBlockNumber();
        if (fromBlock > latestBlock) {
            return ChainReceipt.builder().build();
        }

        IexecHubContract iexecHub = getHubContract();
        EthFilter ethFilter = createContributeEthFilter(fromBlock, latestBlock);

        // filter only taskContribute events for the chainTaskId and the worker's wallet
        // and retrieve the block number of the event
        return iexecHub.taskContributeEventFlowable(ethFilter)
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
        IexecHubContract iexecHub = getHubContract();
        EthFilter ethFilter = createConsensusEthFilter(fromBlock, latestBlock);

        // filter only taskConsensus events for the chainTaskId (there should be only one)
        // and retrieve the block number of the event
        return iexecHub.taskConsensusEventFlowable(ethFilter)
                .filter(eventResponse -> chainTaskId.equals(BytesUtils.bytesToString(eventResponse.taskid)))
                .map(eventResponse -> ChainReceipt.builder()
                        .blockNumber(eventResponse.log.getBlockNumber().longValue())
                        .txHash(eventResponse.log.getTransactionHash())
                        .build())
                .blockingFirst();
    }

    public ChainReceipt getRevealBlock(String chainTaskId, String workerWallet,
                                       long fromBlock) {
        long latestBlock = web3jAbstractService.getLatestBlockNumber();
        if (fromBlock > latestBlock) {
            return ChainReceipt.builder().build();
        }

        IexecHubContract iexecHub = getHubContract();
        EthFilter ethFilter = createRevealEthFilter(fromBlock, latestBlock);

        // filter only taskReveal events for the chainTaskId and the worker's wallet
        // and retrieve the block number of the event
        return iexecHub.taskRevealEventFlowable(ethFilter)
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
        IexecHubContract iexecHub = getHubContract();
        DefaultBlockParameter startBlock =
                DefaultBlockParameter.valueOf(BigInteger.valueOf(fromBlock));
        DefaultBlockParameter endBlock =
                DefaultBlockParameter.valueOf(BigInteger.valueOf(toBlock));

        // define the filter
        EthFilter ethFilter = new EthFilter(
                startBlock,
                endBlock,
                iexecHub.getContractAddress()
        );
        ethFilter.addSingleTopic(EventEncoder.encode(event));

        return ethFilter;
    }

    public boolean repeatIsContributedTrue(String chainTaskId, String walletAddress) {
        return web3jAbstractService.repeatCheck(nbBlocksToWaitPerRetry, maxRetries,
                "isContributedTrue", this::isContributedTrue, chainTaskId, walletAddress);
    }

    public boolean repeatIsRevealedTrue(String chainTaskId, String walletAddress) {
        return web3jAbstractService.repeatCheck(nbBlocksToWaitPerRetry, maxRetries,
                "isRevealedTrue", this::isRevealedTrue, chainTaskId, walletAddress);
    }

    private boolean isContributedTrue(String... args) {
        return this.isStatusTrueOnChain(args[0], args[1], CONTRIBUTED);
    }

    private boolean isRevealedTrue(String... args) {
        return this.isStatusTrueOnChain(args[0], args[1], REVEALED);
    }

    public boolean isStatusTrueOnChain(String chainTaskId, String walletAddress,
                                       ChainContributionStatus wishedStatus) {
        Optional<ChainContribution> optional =
                getChainContribution(chainTaskId, walletAddress);
        if (optional.isEmpty()) {
            return false;
        }

        ChainContribution chainContribution = optional.get();
        ChainContributionStatus chainStatus = chainContribution.getStatus();
        switch (wishedStatus) {
            case CONTRIBUTED:
                // has at least contributed
                return chainStatus.equals(CONTRIBUTED) || chainStatus.equals(REVEALED);
            case REVEALED:
                // has at least revealed
                return chainStatus.equals(REVEALED);
            default:
                return false;
        }
    }
}
