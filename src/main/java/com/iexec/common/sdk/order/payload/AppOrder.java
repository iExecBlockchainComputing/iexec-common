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
public class AppOrder extends Order {

    private String app;
    private BigInteger appprice;
    private String datasetrestrict;
    private String workerpoolrestrict;
    private String requesterrestrict;

    @Builder
    public AppOrder(String app, BigInteger price, BigInteger volume, String tag,
                    String salt, String sign, String datasetrestrict, String workerpoolrestrict,
                    String requesterrestrict) {
        super(volume, tag, salt, sign);
        this.app = app;
        this.appprice = price;
        setDatasetrestrict(datasetrestrict);
        setWorkerpoolrestrict(workerpoolrestrict);
        setRequesterrestrict(requesterrestrict);
    }

    public void setDatasetrestrict(String datasetrestrict) {
        this.datasetrestrict = toLowerCase(datasetrestrict);
    }

    public void setWorkerpoolrestrict(String workerpoolrestrict) {
        this.workerpoolrestrict = toLowerCase(workerpoolrestrict);
    }

    public void setRequesterrestrict(String requesterrestrict) {
        this.requesterrestrict = toLowerCase(requesterrestrict);
    }
}
