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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iexec.common.contract.generated.IexecLibOrders_v5;
import com.iexec.common.tee.TeeUtils;
import com.iexec.common.utils.BytesUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class DatasetOrderTest {

    @Test
    public void getDatasetOrder() throws JsonProcessingException {
        DatasetOrder datasetOrder = DatasetOrder.builder()
                .dataset(BytesUtils.EMPTY_ADDRESS)
                .price(BigInteger.valueOf(0))
                .volume(BigInteger.valueOf(1))
                .tag(TeeUtils.TEE_TAG)
                .apprestrict(BytesUtils.EMPTY_ADDRESS)
                .workerpoolrestrict(BytesUtils.EMPTY_ADDRESS)
                .requesterrestrict(BytesUtils.EMPTY_ADDRESS)
                .salt(BytesUtils.EMPTY_HEXASTRING_64)
                .build();

        String datasetOrderAsString =
                new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(datasetOrder);

        IexecLibOrders_v5.DatasetOrder generatedDatasetOrder = new IexecLibOrders_v5.DatasetOrder(
                BytesUtils.EMPTY_ADDRESS,
                BigInteger.valueOf(0),
                BigInteger.valueOf(1),
                BytesUtils.stringToBytes(TeeUtils.TEE_TAG),
                BytesUtils.EMPTY_ADDRESS,
                BytesUtils.EMPTY_ADDRESS,
                BytesUtils.EMPTY_ADDRESS,
                BytesUtils.stringToBytes(BytesUtils.EMPTY_HEXASTRING_64),
                null);

        Assertions.assertThat(datasetOrder.getDataset())
                .isEqualTo(generatedDatasetOrder.dataset);
        Assertions.assertThat(datasetOrder.getDatasetprice())
                .isEqualTo(generatedDatasetOrder.datasetprice);
        Assertions.assertThat(datasetOrder.getVolume())
                .isEqualTo(generatedDatasetOrder.volume);
        Assertions.assertThat(datasetOrder.getTag())
                .isEqualTo(BytesUtils.bytesToString(generatedDatasetOrder.tag));
        Assertions.assertThat(datasetOrder.getApprestrict())
                .isEqualTo(generatedDatasetOrder.apprestrict);
        Assertions.assertThat(datasetOrder.getWorkerpoolrestrict())
                .isEqualTo(generatedDatasetOrder.workerpoolrestrict);
        Assertions.assertThat(datasetOrder.getRequesterrestrict())
                .isEqualTo(generatedDatasetOrder.requesterrestrict);
        Assertions.assertThat(datasetOrder.getSalt())
                .isEqualTo(BytesUtils.bytesToString(generatedDatasetOrder.salt));
    }
}