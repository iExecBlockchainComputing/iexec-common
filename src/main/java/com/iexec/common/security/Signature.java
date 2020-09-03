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

package com.iexec.common.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iexec.common.utils.BytesUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.util.Arrays;
import org.web3j.crypto.Sign;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Signature {

    // this value is the hexadecimal string of the signature
    // it is the concatenation of the R, S and V values of the signature.
    private String value;

    public Signature(byte[] sign) {
        this.value = BytesUtils.bytesToString(sign);
    }

    public Signature(byte[] r, byte[] s, byte[] v) {
        this(Arrays.concatenate(r, s, v));
    }

    public Signature(Sign.SignatureData sign) {
        this(sign.getR(), sign.getS(), sign.getV());
    }

    @JsonIgnore
    public byte[] getR(){
        return Arrays.copyOfRange(BytesUtils.stringToBytes(value), 0, 32);
    }

    @JsonIgnore
    public byte[] getS(){
        return Arrays.copyOfRange(BytesUtils.stringToBytes(value), 32, 64);
    }

    @JsonIgnore
    public byte[] getV(){
        return new byte[]{BytesUtils.stringToBytes(value)[64]};
    }
}
