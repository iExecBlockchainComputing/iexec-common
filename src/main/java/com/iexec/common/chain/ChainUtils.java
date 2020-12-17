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

import com.iexec.common.utils.BytesUtils;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.Arrays;
import org.web3j.crypto.Hash;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;

@Slf4j
public class ChainUtils {

    private ChainUtils() {
        throw new UnsupportedOperationException();
    }

    public static String generateChainTaskId(String dealId, int taskIndex) {
        byte[] dealIdBytes32 = BytesUtils.stringToBytes(dealId);
        if (dealIdBytes32.length != 32) {
            return null;
        }
        BigInteger taskIndexBigInt = BigInteger.valueOf(taskIndex);
        byte[] taskIndexBytes32 = Numeric.toBytesPadded(taskIndexBigInt, 32);
        if (taskIndexBytes32.length != 32) {
            return null;
        }
        //concatenate bytes with same size only
        byte[] concatenate = Arrays.concatenate(dealIdBytes32, taskIndexBytes32);
        return Hash.sha3(BytesUtils.bytesToString(concatenate));
    }


    public static BigDecimal weiToEth(BigInteger weiAmount) {
        return Convert.fromWei(weiAmount.toString(), Convert.Unit.ETHER);
    }

    public static ChainReceipt buildChainReceipt(Log chainResponseLog, String chainTaskId, long lastBlock) {
        if (chainResponseLog == null) {
            log.error("Transaction log received but was null [chainTaskId:{}]", chainTaskId);
            return null;
        }

        BigInteger txBlockNumber = chainResponseLog.getBlockNumber();
        String txHash = chainResponseLog.getTransactionHash();

        ChainReceipt.ChainReceiptBuilder builder = ChainReceipt.builder();
        // it seems response.log.getBlockNumber() could be null (issue in https://github.com/web3j/web3j should be opened)
        if (txHash != null) {
            builder.txHash(txHash);
        }

        if (txBlockNumber != null) {
            builder.blockNumber(txBlockNumber.longValue());
        } else {
            log.warn("Transaction log received but blockNumber is null inside (lastBlock will be used instead) "
                    + "[chainTaskId:{}, receiptLog:{}, lastBlock:{}]", chainTaskId, chainResponseLog.toString(), lastBlock);
            builder.blockNumber(lastBlock);
        }

        return builder.build();
    }

}
