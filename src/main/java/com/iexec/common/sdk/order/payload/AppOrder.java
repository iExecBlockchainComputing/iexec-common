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
@JsonDeserialize(builder = AppOrder.AppOrderBuilder.class)
public class AppOrder extends Order {

    String app;
    BigInteger appprice;
    String datasetrestrict;
    String workerpoolrestrict;
    String requesterrestrict;

    @Builder
    AppOrder(
            String app,
            BigInteger appprice,
            BigInteger volume,
            String tag,
            String salt,
            String sign,
            String datasetrestrict,
            String workerpoolrestrict,
            String requesterrestrict) {
        super(volume, tag, salt, sign);
        this.app = app;
        this.appprice = appprice;
        this.datasetrestrict = toLowerCase(datasetrestrict);
        this.workerpoolrestrict = toLowerCase(workerpoolrestrict);
        this.requesterrestrict = toLowerCase(requesterrestrict);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class AppOrderBuilder{}

    @Override
    public AppOrder withSignature(String signature) {
        return new AppOrder(
                this.app, this.appprice,
                this.volume, this.tag, this.salt, signature,
                this.datasetrestrict, this.workerpoolrestrict, this.requesterrestrict
        );
    }
}
