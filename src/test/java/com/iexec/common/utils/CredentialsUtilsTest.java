/*
 * Copyright 2021 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CredentialsUtilsTest {

    @Test
    void shouldGetAddress() {
        String privateKey = "0x0010000000000000000000000000000000000000000000000000000000000001";
        assertEquals("0xae2e9def8b48ba414fc57614f4683f008572226c",
                CredentialsUtils.getAddress(privateKey));
    }

    @Test
    void shouldNotGetAddressSinceWrongLength() {
        assertTrue(CredentialsUtils.getAddress("0x1").isEmpty());
    }

}