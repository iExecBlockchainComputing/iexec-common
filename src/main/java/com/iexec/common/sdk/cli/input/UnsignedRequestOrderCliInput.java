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

package com.iexec.common.sdk.cli.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iexec.common.sdk.order.payload.RequestOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnsignedRequestOrderCliInput implements CliInput<UnsignedRequestOrderCliInput.UnsignedOrder> {


    @JsonProperty("order")
    private UnsignedOrder unsignedOrder;

    public UnsignedRequestOrderCliInput(RequestOrder unsignedRequestOrder) {
        this.unsignedOrder = new UnsignedOrder(unsignedRequestOrder);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnsignedOrder {

        @JsonProperty("requestorder")
        private RequestOrder requestorder;

    }

}