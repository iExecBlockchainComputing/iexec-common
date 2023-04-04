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
import java.util.Objects;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonDeserialize(builder = RequestOrder.RequestOrderBuilder.class)
public class RequestOrder extends Order {

    String app;
    BigInteger appmaxprice;
    String dataset;
    BigInteger datasetmaxprice;
    String workerpool;
    BigInteger workerpoolmaxprice;
    String requester;
    BigInteger category;
    BigInteger trust;
    String beneficiary;
    String callback;
    String params;

    @Builder
    RequestOrder(String app, BigInteger appmaxprice, String dataset, BigInteger datasetmaxprice, String workerpool, BigInteger workerpoolmaxprice, String requester, BigInteger volume, String tag, BigInteger category, BigInteger trust, String beneficiary, String callback, String params, String salt, String sign) {
        super(volume, tag, salt, sign);
        this.app = toLowerCase(app);
        this.appmaxprice = appmaxprice;
        this.dataset = toLowerCase(dataset);
        this.datasetmaxprice = datasetmaxprice;
        this.workerpool = toLowerCase(workerpool);
        this.workerpoolmaxprice = workerpoolmaxprice;
        this.requester = toLowerCase(requester);
        this.category = category;
        this.trust = trust;
        this.beneficiary = toLowerCase(beneficiary);
        this.callback = toLowerCase(callback);
        this.params = params;
    }

    public boolean equalsExcludedSaltSignAndParams(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestOrder that = (RequestOrder) o;
        return Objects.equals(app, that.app) &&
                Objects.equals(appmaxprice, that.appmaxprice) &&
                Objects.equals(dataset, that.dataset) &&
                Objects.equals(datasetmaxprice, that.datasetmaxprice) &&
                Objects.equals(workerpool, that.workerpool) &&
                Objects.equals(workerpoolmaxprice, that.workerpoolmaxprice) &&
                Objects.equals(volume, that.volume) &&
                Objects.equals(category, that.category) &&
                Objects.equals(trust, that.trust) &&
                Objects.equals(tag, that.tag) &&
                Objects.equals(beneficiary, that.beneficiary) &&
                Objects.equals(callback, that.callback) &&
                //Objects.equals(params, that.params) &&
                Objects.equals(requester, that.requester);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class RequestOrderBuilder{}

    @Override
    public RequestOrder withSignature(String signature) {
        return new RequestOrder(
                this.app, this.appmaxprice,
                this.dataset, this.datasetmaxprice,
                this.workerpool, this.workerpoolmaxprice,
                this.requester, this.volume, this.tag, this.category,
                this.trust, this.beneficiary, this.callback, this.params, this.salt, signature
        );
    }
}
