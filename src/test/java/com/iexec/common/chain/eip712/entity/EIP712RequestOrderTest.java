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

package com.iexec.common.chain.eip712.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iexec.commons.poco.eip712.EIP712Domain;
import com.iexec.commons.poco.order.RequestOrder;
import com.iexec.commons.poco.utils.BytesUtils;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;

import java.io.IOException;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

class EIP712RequestOrderTest {

    private ObjectMapper mapper = new ObjectMapper();
    private static final EIP712Domain DOMAIN = new EIP712Domain(133, "0x3eca1B216A7DF1C7689aEb259fFB83ADFB894E7f");

    private static final RequestOrder REQUEST_ORDER = RequestOrder.builder()
            .app("0x6709CAe77CDa2cbA8Cb90A4F5a4eFfb5c8Fe8367")
            .appmaxprice(BigInteger.ZERO)
            .dataset(BytesUtils.EMPTY_ADDRESS)
            .datasetmaxprice(BigInteger.ZERO)
            .workerpool("0x506fA5EaCa52B5d2F133452a45FFA68aD1CfB3C5")
            .workerpoolmaxprice(BigInteger.ZERO)
            .requester("0x1ec09e1782a43a770d54e813379c730e0b29ad4b")
            .volume(BigInteger.ONE)
            .tag(BytesUtils.toByte32HexString(0x1)) // any tag here
            .category(BigInteger.ZERO)
            .trust(BigInteger.ZERO)
            .beneficiary(BytesUtils.EMPTY_ADDRESS)
            .callback(BytesUtils.EMPTY_ADDRESS)
            .params("{\"iexec_tee_post_compute_fingerprint\":\"76bfdee97e692b729e989694f3a566cf0e1de95fc456ff5ee88c75b1cb865e33|1eb627c1c94bbca03178b099b13fb4d1|13076027fc67accba753a3ed2edf03227dfd013b450d68833a5589ec44132100\",\"iexec_tee_post_compute_image\":\"iexechub/tee-worker-post-compute:1.0.0\",\"iexec_result_storage_provider\":\"ipfs\",\"iexec_result_storage_proxy\":\"https://result.viviani.iex.ec\",\"iexec_result_encryption\":false,\"iexec_input_files\":[],\"iexec_args\":\"Alice\"}")
            .salt("0xee5c64cd59eaa084f59dbaa8f20b87260c4d6ac35c83214da657681bfe4e7632")
            .build();

    @Test
    void shouldSerializeAndDeserialize() throws JsonProcessingException {
        EIP712RequestOrder eip712RequestOrder = new EIP712RequestOrder(DOMAIN, REQUEST_ORDER);
        String jsonString = mapper.writeValueAsString(eip712RequestOrder);
        EIP712RequestOrder deserializedEip712RequestOrder = mapper.readValue(jsonString, EIP712RequestOrder.class);
        assertThat(deserializedEip712RequestOrder).usingRecursiveComparison().isEqualTo(eip712RequestOrder);
    }

    /**
     * Expected signature string could also be found with:
     * <p>
     * iexec order sign --request --chain 133 \
     * --keystoredir /home/$USER/iexecdev/iexec-common/src/test/resources/ \
     * --wallet-file wallet.json --password whatever
     * <p>
     * Note: Don't forget to update salt
     */
    @Test
    void signRequestOrderEIP712() {
        EIP712RequestOrder eip712RequestOrder = new EIP712RequestOrder(DOMAIN, REQUEST_ORDER);

        String signatureString = eip712RequestOrder.signMessage(getWallet());
        assertThat(signatureString)
                .isEqualTo("0xe4085c70e1d543daf0433d9b7a15f10679befb65dc33c3eeb284dee1ba409f724ce8223a262c8eeb2d3f4f3cc44c2c5d06192ab1d74b3554904425f6f5f8c4cc1c");
    }

    private ECKeyPair getWallet() {
        try {
            return WalletUtils.loadCredentials(
                    "whatever",
                    "./src/test/resources/wallet.json")
                    .getEcKeyPair();
        } catch (IOException | CipherException e) {
            e.printStackTrace();
        }
        return null;
    }

}
