/*
 * Copyright 2020-2023 IEXEC BLOCKCHAIN TECH
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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.math.BigInteger;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonDeserialize(builder = WorkerpoolOrder.WorkerpoolOrderBuilder.class)
public class WorkerpoolOrder extends Order {

    String workerpool;
    BigInteger workerpoolprice;
    BigInteger trust;
    BigInteger category;
    String apprestrict;
    String datasetrestrict;
    String requesterrestrict;

    @Builder
    WorkerpoolOrder(
            String workerpool, BigInteger workerpoolprice, BigInteger volume,
            String tag, BigInteger trust, BigInteger category,
            String salt, String sign, String apprestrict,
            String datasetrestrict, String requesterrestrict) {
        super(volume, tag, salt, sign);
        this.workerpool = workerpool;
        this.workerpoolprice = workerpoolprice;
        this.trust = trust;
        this.category = category;
        this.apprestrict = toLowerCase(apprestrict);
        this.datasetrestrict = toLowerCase(datasetrestrict);
        this.requesterrestrict = toLowerCase(requesterrestrict);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class WorkerpoolOrderBuilder{}

    @Override
    public WorkerpoolOrder withSignature(String signature) {
        return new WorkerpoolOrder(
                this.workerpool, this.workerpoolprice,
                this.volume, this.tag, this.trust, this.category, this.salt, signature,
                this.apprestrict, this.datasetrestrict, this.requesterrestrict
        );
    }
}
