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

import com.iexec.common.utils.WaitUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.function.Function;

import static com.iexec.common.chain.ChainUtils.weiToEth;
import static com.iexec.common.contract.generated.AppRegistry.FUNC_CREATEAPP;
import static com.iexec.common.contract.generated.DatasetRegistry.FUNC_CREATEDATASET;
import static com.iexec.common.contract.generated.IexecHubContract.*;
import static com.iexec.common.contract.generated.WorkerpoolRegistry.FUNC_CREATEWORKERPOOL;

@Slf4j
public abstract class Web3jAbstractService {

    private final static long GAS_LIMIT_CAP = 500000;
    private final float gasPriceMultiplier;
    private final long gasPriceCap;
    private final boolean isSidechain;
    private String chainNodeAddress;
    private Web3j web3j;

    public Web3jAbstractService(String chainNodeAddress,
                                float gasPriceMultiplier,
                                long gasPriceCap,
                                boolean isSidechain) {
        this.chainNodeAddress = chainNodeAddress;
        this.gasPriceMultiplier = gasPriceMultiplier;
        this.gasPriceCap = gasPriceCap;
        this.isSidechain = isSidechain;

        this.getWeb3j(true); //let's check eth node connection at boot
    }

    public static BigInteger getMaxTxCost(long gasPriceCap) {
        return BigInteger.valueOf(GAS_LIMIT_CAP * gasPriceCap);
    }

    public Web3j getWeb3j(boolean shouldCheckConnection) {
        web3j = Web3j.build(new HttpService(chainNodeAddress));
        if (shouldCheckConnection) {
            try {
                if (web3j.web3ClientVersion().send().getWeb3ClientVersion() != null) {
                    log.info("Connected to Ethereum node [address:{}, version:{}]", chainNodeAddress, web3j.web3ClientVersion().send().getWeb3ClientVersion());
                    return web3j;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            int fewSeconds = 5;
            log.error("Failed to connect to ethereum node (will retry) [chainNodeAddress:{}, retryIn:{}]",
                    chainNodeAddress, fewSeconds);
            WaitUtils.sleep(fewSeconds);
            return getWeb3j(shouldCheckConnection);
        }
        return web3j;
    }

    public Web3j getWeb3j() {
        return getWeb3j(false);
    }

    public EthBlock.Block getLatestBlock() throws IOException {
        return getWeb3j().ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send().getBlock();
    }

    public long getLatestBlockNumber() {
        try {
            return getLatestBlock().getNumber().longValue();
        } catch (IOException e) {
            log.error("GetLastBlock failed");
        }
        return 0;
    }

    private EthBlock.Block getBlock(long blockNumber) throws IOException {
        return getWeb3j().ethGetBlockByNumber(DefaultBlockParameter.valueOf(BigInteger.valueOf(blockNumber)),
                false).send().getBlock();
    }

    // check if the blockNumber is already available for the scheduler
    // blockNumber is different than 0 only for status the require a check on the blockchain, so the scheduler should
    // already have this block, otherwise it should wait for a maximum of 10 blocks.
    public boolean isBlockAvailable(long blockNumber) {
        // if the blocknumer is already available then simply returns true
        if (blockNumber <= getLatestBlockNumber()) {
            return true;
        }

        // otherwise we wait for a maximum of 10 blocks to see if the block will be available
        try {
            long maxBlockNumber = blockNumber + 10;
            long currentBlockNumber = getLatestBlockNumber();
            while (currentBlockNumber <= maxBlockNumber) {
                if (blockNumber <= currentBlockNumber) {
                    return true;
                } else {
                    log.warn("Chain is NOT synchronized yet [blockNumber:{}, currentBlockNumber:{}]", blockNumber, currentBlockNumber);
                    Thread.sleep(500);
                }
                currentBlockNumber = getLatestBlockNumber();
            }
        } catch (InterruptedException e) {
            log.error("Error in checking the latest block number [execption:{}]", e.getMessage());
        }

        return false;
    }

    public long getMaxWaitingTimeWhenPendingReceipt() {
        long maxWaitingTime = 2 * 60 * 1000L; // 2min

        // max waiting Time should be roughly the time of 10 blocks
        try {
            EthBlock.Block latestBlock = getLatestBlock();

            long latestBlockNumber = latestBlock.getNumber().longValue();

            BigInteger latestBlockTimestamp = latestBlock.getTimestamp();
            BigInteger tenBlocksAgoTimestamp = getBlock(latestBlockNumber - 10).getTimestamp();

            maxWaitingTime = (latestBlockTimestamp.longValue() - tenBlocksAgoTimestamp.longValue()) * 1000;

            log.info(" [latestBlockTimestamp:{}, tenBlocksAgoTimestamp:{}, maxWaitingTime:{}]",
                    latestBlockTimestamp, tenBlocksAgoTimestamp, maxWaitingTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maxWaitingTime;
    }

    public long getAverageTimePerBlock() {//in ms
        long defaultTime = TransactionManager.DEFAULT_POLLING_FREQUENCY; // 15sec
        int NB_OF_BLOCKS = 10;

        try {
            EthBlock.Block latestBlock = getLatestBlock();

            long latestBlockNumber = latestBlock.getNumber().longValue();

            BigInteger latestBlockTimestamp = latestBlock.getTimestamp();
            BigInteger tenBlocksAgoTimestamp = getBlock(latestBlockNumber - NB_OF_BLOCKS).getTimestamp();

            defaultTime = ((latestBlockTimestamp.longValue() - tenBlocksAgoTimestamp.longValue()) / NB_OF_BLOCKS) * 1000L;
        } catch (IOException e) {
            log.error("Failed to getAverageTimePerBlock");
            e.printStackTrace();
        }
        return defaultTime;
    }

    public boolean hasEnoughGas(String address) {
        // if a sidechain is used, there is no need to check if the wallet has enough gas.
        // if mainnet is used, the check should be done.
        if (isSidechain) {
            return true;
        }

        Optional<BigInteger> optionalBalance = getBalance(address);
        if (!optionalBalance.isPresent()) {
            return false;
        }

        BigInteger weiBalance = optionalBalance.get();
        BigInteger estimateTxNb = weiBalance.divide(getMaxTxCost(gasPriceCap));
        BigDecimal balanceToShow = weiToEth(weiBalance);

        if (estimateTxNb.compareTo(BigInteger.ONE) < 0) {
            log.error("ETH balance is empty, please refill gas now [balance:{}, estimateTxNb:{}]", balanceToShow, estimateTxNb);
            return false;
        } else if (estimateTxNb.compareTo(BigInteger.TEN) < 0) {
            log.warn("ETH balance very low, should refill gas now [balance:{}, estimateTxNb:{}]", balanceToShow, estimateTxNb);
        } else {
            log.debug("ETH balance is fine [balance:{}, estimateTxNb:{}]", balanceToShow, estimateTxNb);
        }

        return true;
    }

    public Optional<BigInteger> getBalance(String address) {
        try {
            return Optional.of(getWeb3j().ethGetBalance(address, DefaultBlockParameterName.LATEST).send().getBalance());
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public Optional<BigInteger> getNetworkGasPrice() {
        try {
            BigInteger gasPrice = getWeb3j().ethGasPrice().send().getGasPrice();
            return Optional.of(gasPrice);
        } catch (IOException e) {
            log.error("getNetworkGasPrice failed");
            return Optional.empty();
        }
    }

    public BigInteger getUserGasPrice(float gasPriceMultiplier, long gasPriceCap) {
        Optional<BigInteger> networkGasPrice = getNetworkGasPrice();
        if (!networkGasPrice.isPresent()) {
            return BigInteger.valueOf(gasPriceCap);
        }
        long wishedGasPrice = (long) (networkGasPrice.get().floatValue() * gasPriceMultiplier);

        return BigInteger.valueOf(Math.min(wishedGasPrice, gasPriceCap));
    }

    /*
     * This is just a dummy stub for contract reader:
     * gas price & gas limit is not required when querying (read) an eth node
     *
     */
    public ContractGasProvider getReadingContractGasProvider() {
        return new ContractGasProvider() {
            @Override
            public BigInteger getGasPrice(String contractFunc) {
                return null;
            }

            @Override
            public BigInteger getGasPrice() {
                return null;
            }

            @Override
            public BigInteger getGasLimit(String contractFunc) {
                return null;
            }

            @Override
            public BigInteger getGasLimit() {
                return null;
            }
        };
    }

    public ContractGasProvider getWritingContractGasProvider() {
        return new ContractGasProvider() {

            @Override
            public BigInteger getGasPrice(String s) {
                return getUserGasPrice(gasPriceMultiplier, gasPriceCap);
            }

            @Override
            public BigInteger getGasPrice() {
                return getUserGasPrice(gasPriceMultiplier, gasPriceCap);
            }

            @Override
            public BigInteger getGasLimit(String functionName) {
                return getGasLimitForFunction(functionName);
            }

            @Override
            public BigInteger getGasLimit() {
                return BigInteger.valueOf(GAS_LIMIT_CAP);
            }
        };
    }

    @NotNull
    static BigInteger getGasLimitForFunction(String functionName) {
        long gasLimit;
        switch (functionName) {
            case FUNC_INITIALIZE:
                gasLimit = 300000;//seen 176340
                break;
            case FUNC_CONTRIBUTE:
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
            case FUNC_CREATEAPP:
                gasLimit = 700000;
                break;
            case FUNC_CREATEWORKERPOOL:
                gasLimit = 700000;
                break;
            case FUNC_CREATEDATASET:
                gasLimit = 700000;//seen 608878
                break;
            default:
                gasLimit = GAS_LIMIT_CAP;
        }
        return BigInteger.valueOf(gasLimit);
    }

    /*
     * Below method:
     *
     * - checks any function `boolean myMethod(String s1, String s2, ...)`
     * - waits a certain amount of time between checks (waits a certain number of blocks)
     * - stops checking after a certain number of tries
     *
     * */
    //TODO: Add a cache for getAverageTimePerBlock();
    public boolean repeatCheck(int nbBlocksToWaitPerTry, int maxTry, String logTag, Function<String[], Boolean> function, String... functionArgs) {
        if (maxTry < 1) {
            maxTry = 1;
        }

        if (nbBlocksToWaitPerTry < 1) {
            nbBlocksToWaitPerTry = 1;
        }

        long timePerBlock = this.getAverageTimePerBlock();
        long msToWait = nbBlocksToWaitPerTry * timePerBlock;

        int i = 0;
        while (i < maxTry) {
            if (function.apply(functionArgs)) {
                log.info("Verified check [try:{}, function:{}, args:{}, maxTry:{}, msToWait:{}, msPerBlock:{}]",
                        i + 1, logTag, functionArgs, maxTry, msToWait, timePerBlock);
                return true;
            }
            i++;
            WaitUtils.sleepMs(msToWait);
        }

        log.error("Still wrong check [function:{}, args:{}, maxTry:{}, msToWait:{}, msPerBlock:{}]",
                logTag, functionArgs, maxTry, msToWait, timePerBlock);
        return false;
    }
}
