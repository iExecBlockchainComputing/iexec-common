/*
 * Copyright 2020-2024 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.result;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.web3j.utils.Numeric;

@Data
@Builder
@AllArgsConstructor
@JsonDeserialize(builder = ResultModel.ResultModelBuilder.class)
public class ResultModel {

    //TODO move this to commons-poco
    public static final String EMPTY_CHAIN_ID = Numeric.toHexString(new byte[32]);
    // An ethereum signature is 65 bytes long with R, S and V parts (32 bytes + 32 bytes + 1 byte)
    public static final String EMPTY_WEB3_SIG = Numeric.toHexString(new byte[65]);

    @Builder.Default
    private final String chainTaskId = EMPTY_CHAIN_ID;
    private final String image;
    private final String cmd;
    @Builder.Default
    private final byte[] zip = new byte[0];
    private final String deterministHash;
    @Builder.Default
    private final String enclaveSignature = EMPTY_WEB3_SIG;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ResultModelBuilder {
    }

}
