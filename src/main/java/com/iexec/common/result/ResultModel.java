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
import com.iexec.commons.poco.utils.BytesUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonDeserialize(builder = ResultModel.ResultModelBuilder.class)
public class ResultModel {

    @Builder.Default
    private final String chainTaskId = BytesUtils.EMPTY_ADDRESS;
    private final String image;
    private final String cmd;
    @Builder.Default
    private final byte[] zip = new byte[0];
    private final String deterministHash;
    @Builder.Default
    private final String enclaveSignature = BytesUtils.EMPTY_ADDRESS;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ResultModelBuilder {
    }

}
