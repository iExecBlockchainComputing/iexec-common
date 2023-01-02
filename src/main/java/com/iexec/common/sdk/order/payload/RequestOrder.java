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

import com.iexec.common.chain.DealParams;
import lombok.*;

import java.math.BigInteger;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class RequestOrder extends Order {

    private String app;
    private BigInteger appmaxprice;
    private String dataset;
    private BigInteger datasetmaxprice;
    private String workerpool;
    private BigInteger workerpoolmaxprice;
    private String requester;
    private BigInteger category;
    private BigInteger trust;
    private String beneficiary;
    private String callback;
    private String params;

    @Builder
    public RequestOrder(String app, BigInteger appmaxprice, String dataset, BigInteger datasetmaxprice, String workerpool, BigInteger workerpoolmaxprice, String requester, BigInteger volume, String tag, BigInteger category, BigInteger trust, String beneficiary, String callback, String params, String salt, String sign) {
        super(volume, tag, salt, sign);
        setApp(app);
        this.appmaxprice = appmaxprice;
        setDataset(dataset);
        this.datasetmaxprice = datasetmaxprice;
        setWorkerpool(workerpool);
        this.workerpoolmaxprice = workerpoolmaxprice;
        setRequester(requester);
        this.category = category;
        this.trust = trust;
        setBeneficiary(beneficiary);
        setCallback(callback);
        this.params = params;
    }

    /**
     * @deprecated Use {@link DealParams#toJsonString()} instead.
     * @param params Deal parameters to write on chain
     * @return JSON string that will be written on chain
     */
    @Deprecated
    public static String toStringParams(DealParams params) {
        return params.toJsonString();
    }

    public void setApp(String app) {
        this.app = toLowerCase(app);
    }

    public void setDataset(String dataset) {
        this.dataset = toLowerCase(dataset);
    }

    public void setWorkerpool(String workerpool) {
        this.workerpool = toLowerCase(workerpool);
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = toLowerCase(beneficiary);
    }

    public void setCallback(String callback) {
        this.callback = toLowerCase(callback);
    }

    public void setRequester(String requester) {
        this.requester = toLowerCase(requester);
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
}
