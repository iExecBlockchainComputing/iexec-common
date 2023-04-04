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
@JsonDeserialize(builder = DatasetOrder.DatasetOrderBuilder.class)
public class DatasetOrder extends Order {

    String dataset;
    BigInteger datasetprice;
    String apprestrict;
    String workerpoolrestrict;
    String requesterrestrict;

    @Builder
    DatasetOrder(
            String dataset, BigInteger datasetprice, BigInteger volume, String tag,
            String salt, String sign, String apprestrict, String workerpoolrestrict,
            String requesterrestrict) {
        super(volume, tag, salt, sign);
        this.dataset = dataset;
        this.datasetprice = datasetprice;
        this.apprestrict = toLowerCase(apprestrict);
        this.workerpoolrestrict = toLowerCase(workerpoolrestrict);
        this.requesterrestrict = toLowerCase(requesterrestrict);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class DatasetOrderBuilder{}

    public DatasetOrder withSignature(String signature) {
        return new DatasetOrder(
                this.dataset, this.datasetprice,
                this.volume, this.tag, this.salt, signature,
                this.apprestrict, this.workerpoolrestrict, this.requesterrestrict
        );
    }
}
