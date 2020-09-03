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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SignatureTests {

    private String validSignature = "0x1b0b90d9f17a30d42492c8a2f98a24374600729a98d4e0b663a44ed48b589cab0e445eec300245e590150c7d88340d902c27e0d8673f3257cb8393f647d6c75c1b";

    @Test
    public void shouldBeValid() {
        Signature sign = new Signature(validSignature);
        Signature sign2 = new Signature(sign.getR(), sign.getS(), sign.getV());

        assertEquals(sign, sign2);
    }

}
