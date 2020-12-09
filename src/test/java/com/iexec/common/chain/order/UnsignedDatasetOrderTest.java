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

package com.iexec.common.chain.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iexec.common.contract.generated.IexecLibOrders_v5;
import com.iexec.common.tee.TeeUtils;
import com.iexec.common.utils.BytesUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.math.BigInteger;

public class UnsignedDatasetOrderTest {

    @Test
    public void getUnsignedDatasetOrder() throws JsonProcessingException {
        String signature = BytesUtils.EMPTY_HEXASTRING_64;

        SignedDatasetOrder signedDatasetOrder = new SignedDatasetOrder(
                new DatasetOrder(
                        BytesUtils.EMPTY_ADDRESS,
                        BigInteger.valueOf(0),
                        BigInteger.valueOf(1),
                        TeeUtils.TEE_TAG,
                        BytesUtils.EMPTY_ADDRESS,
                        BytesUtils.EMPTY_ADDRESS,
                        BytesUtils.EMPTY_ADDRESS,
                        BytesUtils.EMPTY_HEXASTRING_64),
                signature);

        String signedDatasetOrderAsString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(signedDatasetOrder);

        System.out.println(signedDatasetOrderAsString);

        Assertions.assertThat(signedDatasetOrderAsString).isEqualTo("{\n" +
                "  \"dataset\" : \"0x0000000000000000000000000000000000000000\",\n" +
                "  \"datasetprice\" : 0,\n" +
                "  \"volume\" : 1,\n" +
                "  \"tag\" : \"0x0000000000000000000000000000000000000000000000000000000000000001\",\n" +
                "  \"apprestrict\" : \"0x0000000000000000000000000000000000000000\",\n" +
                "  \"workerpoolrestrict\" : \"0x0000000000000000000000000000000000000000\",\n" +
                "  \"requesterrestrict\" : \"0x0000000000000000000000000000000000000000\",\n" +
                "  \"salt\" : \"0x0000000000000000000000000000000000000000000000000000000000000000\",\n" +
                "  \"sign\" : \"0x0000000000000000000000000000000000000000000000000000000000000000\"\n" +
                "}");

        IexecLibOrders_v5.DatasetOrder generatedDatasetOrder = new IexecLibOrders_v5.DatasetOrder(
                BytesUtils.EMPTY_ADDRESS,
                BigInteger.valueOf(0),
                BigInteger.valueOf(1),
                BytesUtils.stringToBytes(TeeUtils.TEE_TAG),
                BytesUtils.EMPTY_ADDRESS,
                BytesUtils.EMPTY_ADDRESS,
                BytesUtils.EMPTY_ADDRESS,
                BytesUtils.stringToBytes(BytesUtils.EMPTY_HEXASTRING_64),
                BytesUtils.stringToBytes(BytesUtils.EMPTY_HEXASTRING_64));

        Assertions.assertThat(signedDatasetOrder.getAddress()).isEqualTo(generatedDatasetOrder.dataset);
        Assertions.assertThat(signedDatasetOrder.getPrice()).isEqualTo(generatedDatasetOrder.datasetprice);
        Assertions.assertThat(signedDatasetOrder.getVolume()).isEqualTo(generatedDatasetOrder.volume);
        Assertions.assertThat(signedDatasetOrder.getTag()).isEqualTo(BytesUtils.bytesToString(generatedDatasetOrder.tag));
        Assertions.assertThat(signedDatasetOrder.getAppRestrict()).isEqualTo(generatedDatasetOrder.apprestrict);
        Assertions.assertThat(signedDatasetOrder.getWorkerpoolRestrict()).isEqualTo(generatedDatasetOrder.workerpoolrestrict);
        Assertions.assertThat(signedDatasetOrder.getRequesterRestrict()).isEqualTo(generatedDatasetOrder.requesterrestrict);
        Assertions.assertThat(signedDatasetOrder.getSalt()).isEqualTo(BytesUtils.bytesToString(generatedDatasetOrder.salt));
        Assertions.assertThat(signedDatasetOrder.getSignature()).isEqualTo(BytesUtils.bytesToString(generatedDatasetOrder.sign));
    }
}