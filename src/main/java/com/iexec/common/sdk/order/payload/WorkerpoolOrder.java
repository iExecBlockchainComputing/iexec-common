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

package com.iexec.common.sdk.order.payload;

import lombok.*;

import java.math.BigInteger;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class WorkerpoolOrder extends Order {

    private String workerpool;
    private BigInteger workerpoolprice;
    private BigInteger trust;
    private BigInteger category;
    private String apprestrict;
    private String datasetrestrict;
    private String requesterrestrict;

    @Builder
    public WorkerpoolOrder(
            String workerpool, BigInteger price, BigInteger volume,
            String tag, BigInteger trust, BigInteger category,
            String salt, String sign, String apprestrict,
            String datasetrestrict, String requesterrestrict) {
        super(volume, tag, salt, sign);
        this.workerpool = workerpool;
        this.workerpoolprice = price;
        this.trust = trust;
        this.category = category;
        setApprestrict(apprestrict);
        setDatasetrestrict(datasetrestrict);
        setRequesterrestrict(requesterrestrict);
    }

    public void setApprestrict(String apprestrict) {
        this.apprestrict = toLowerCase(apprestrict);
    }

    public void setDatasetrestrict(String datasetrestrict) {
        this.datasetrestrict = toLowerCase(datasetrestrict);
    }

    public void setRequesterrestrict(String requesterrestrict) {
        this.requesterrestrict = toLowerCase(requesterrestrict);
    }
}
