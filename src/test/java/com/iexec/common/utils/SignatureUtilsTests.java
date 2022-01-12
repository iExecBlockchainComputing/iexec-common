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

package com.iexec.common.utils;

import com.iexec.common.security.Signature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.iexec.common.utils.SignatureUtils.isExpectedSignerOnSignedMessageHash;
import static com.iexec.common.utils.SignatureUtils.signMessageHashAndGetSignature;

class SignatureUtilsTests {

    @Test
    void shouldMatchExpectedSigner(){
        String messageHash = "0xf0cea2ffdb802c106aef2a032b01c7d271a454473709016c2e2c406097acdfd3";
        String privateKey = "0x6dacd24b3d49d0c50c555aa728c60a57aa08beb363e3a90cce2e4e5d327c6ee2";
        String address = CredentialsUtils.getAddress(privateKey);

        Signature signature = signMessageHashAndGetSignature(messageHash, privateKey);

        boolean isExpectedSigner = isExpectedSignerOnSignedMessageHash(messageHash, signature, address);

        Assertions.assertTrue(isExpectedSigner);
    }

}
