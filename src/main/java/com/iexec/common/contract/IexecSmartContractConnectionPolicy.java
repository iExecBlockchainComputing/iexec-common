/*
 * Copyright 2021 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.contract;

import com.iexec.common.contract.generated.IexecHubContract;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;

@Slf4j
public class IexecSmartContractConnectionPolicy {
    private final Integer expectedFinalDeadlineRatio;

    public IexecSmartContractConnectionPolicy(Integer expectedFinalDeadlineRatio) {
        this.expectedFinalDeadlineRatio = expectedFinalDeadlineRatio;
    }

    public void checkSmartContractConnection(IexecHubContract contract) {
        final BigInteger finalDeadlineRatio;
        final String errorMessage =
                "Something went wrong with the chain configuration. "
                        + "Please check your configuration values.";
        try {
            finalDeadlineRatio = contract
                    .final_deadline_ratio()
                    .send();
        } catch (Exception e) {
            throw new IllegalArgumentException(errorMessage);
        }

        if (expectedFinalDeadlineRatio == null) {
            log.warn("Can't check final deadline ratio " +
                    "as no expected value is provided." +
                    "[actualValue: {}]", finalDeadlineRatio);
            return;
        }

        if (!finalDeadlineRatio.equals(BigInteger.valueOf(expectedFinalDeadlineRatio))) {
            log.error(errorMessage
                            + " [expectedFinalDeadlineRatio:{}, actual: {}]",
                    expectedFinalDeadlineRatio, finalDeadlineRatio
            );
            throw new IllegalArgumentException(errorMessage);
        } else {
            log.info("Connection to iExec smart contract has been established.");
        }
    }
}
