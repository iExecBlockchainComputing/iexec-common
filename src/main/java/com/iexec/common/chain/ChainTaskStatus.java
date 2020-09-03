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

import java.math.BigInteger;

public enum ChainTaskStatus implements ChainStatus {
    UNSET,     // Work order not yet initialized (invalid address)
    ACTIVE,    // Marketed to constributions are open
    REVEALING, // Starting consensus reveal
    COMPLETED, // Concensus achieved
    FAILLED;    // Failed consensus

    public static ChainTaskStatus getValue(int i) {
        return ChainTaskStatus.values()[i];
    }

    public static ChainTaskStatus getValue(BigInteger i) {
        return ChainTaskStatus.values()[i.intValue()];
    }

}
