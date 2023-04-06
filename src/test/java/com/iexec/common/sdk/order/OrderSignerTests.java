/*
 * Copyright 2023 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.sdk.order;

import com.iexec.common.sdk.order.payload.AppOrder;
import com.iexec.common.sdk.order.payload.DatasetOrder;
import com.iexec.common.sdk.order.payload.RequestOrder;
import com.iexec.common.sdk.order.payload.WorkerpoolOrder;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderSignerTests {

    private OrderSigner orderSigner;

    @BeforeEach
    @SneakyThrows
    public void init() {
        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
        orderSigner = new OrderSigner(65535, "0x1", ecKeyPair);
    }

    @Test
    void shouldNotSignNullOrders() {
        assertThat(orderSigner.signAppOrder(null)).isNull();
        assertThat(orderSigner.signDatasetOrder(null)).isNull();
        assertThat(orderSigner.signRequestOrder(null)).isNull();
        assertThat(orderSigner.signWorkerpoolOrder(null)).isNull();
    }

    @Test
    void shouldNotSignEmptyOrders() {
        AppOrder appOrder = AppOrder.builder().build();
        assertThatThrownBy(() -> orderSigner.signAppOrder(appOrder))
                .isInstanceOf(NullPointerException.class);
        DatasetOrder datasetOrder = DatasetOrder.builder().build();
        assertThatThrownBy(() -> orderSigner.signDatasetOrder(datasetOrder))
                .isInstanceOf(NullPointerException.class);
        RequestOrder requestOrder = RequestOrder.builder().build();
        assertThatThrownBy(() -> orderSigner.signRequestOrder(requestOrder))
                .isInstanceOf(NullPointerException.class);
        WorkerpoolOrder workerpoolOrder = WorkerpoolOrder.builder().build();
        assertThatThrownBy(() -> orderSigner.signWorkerpoolOrder(workerpoolOrder))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldSignAppOrder() {
        AppOrder appOrder = AppOrder
                .builder()
                .app("")
                .appprice(BigInteger.TEN)
                .volume(BigInteger.ONE)
                .tag("")
                .salt("")
                .sign("")
                .datasetrestrict("")
                .requesterrestrict("")
                .workerpoolrestrict("")
                .build();
        assertThat(orderSigner.signAppOrder(appOrder)).isNotNull();
    }

    @Test
    void shouldSignDatasetOrder() {
        DatasetOrder datasetOrder = DatasetOrder
                .builder()
                .dataset("")
                .datasetprice(BigInteger.TEN)
                .volume(BigInteger.ONE)
                .tag("")
                .salt("")
                .sign("")
                .apprestrict("")
                .requesterrestrict("")
                .workerpoolrestrict("")
                .build();
        assertThat(orderSigner.signDatasetOrder(datasetOrder)).isNotNull();
    }

    @Test
    void shouldSignRequestOrder() {
        RequestOrder requestOrder = RequestOrder
                .builder()
                .app("")
                .appmaxprice(BigInteger.TEN)
                .dataset("")
                .datasetmaxprice(BigInteger.TEN)
                .workerpool("")
                .workerpoolmaxprice(BigInteger.TEN)
                .requester("")
                .volume(BigInteger.ONE)
                .tag("")
                .category(BigInteger.ZERO)
                .trust(BigInteger.ONE)
                .beneficiary("")
                .callback("")
                .params("")
                .salt("")
                .sign("")
                .build();
        assertThat(orderSigner.signRequestOrder(requestOrder)).isNotNull();
    }

    @Test
    void shouldSignWorkerpoolOrder() {
        WorkerpoolOrder workerpoolOrder = WorkerpoolOrder
                .builder()
                .workerpool("")
                .workerpoolprice(BigInteger.TEN)
                .volume(BigInteger.ONE)
                .tag("")
                .trust(BigInteger.ONE)
                .category(BigInteger.ZERO)
                .salt("")
                .sign("")
                .apprestrict("")
                .datasetrestrict("")
                .requesterrestrict("")
                .build();
        assertThat(orderSigner.signWorkerpoolOrder(workerpoolOrder)).isNotNull();
    }

}
