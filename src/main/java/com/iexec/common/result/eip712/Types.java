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

package com.iexec.common.result.eip712;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Types {

    @JsonProperty("EIP712Domain")
    private List<TypeParam> domainTypeParams = null;
    @JsonProperty("Challenge")
    private List<TypeParam> challengeTypeParams = null;

    public static String typeParamsToString(List<TypeParam> typeParams) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < typeParams.size(); i++) {
            s.append(typeParams.get(i).getType()).append(" ").append(typeParams.get(i).getName());
            if (i <= typeParams.size() - 2) {
                s.append(",");
            }
        }
        return s.toString();
    }

    @JsonIgnore
    public List<TypeParam> getDomainTypeParams() {
        return domainTypeParams;
    }

    @JsonIgnore
    public List<TypeParam> getChallengeTypeParams() {
        return challengeTypeParams;
    }
}
