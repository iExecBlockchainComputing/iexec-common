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

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.web3j.crypto.Credentials;

@Slf4j
public class CredentialsUtils {

    private CredentialsUtils() {
        throw new UnsupportedOperationException();
    }

    public static String getAddress(String privateKey) {
        if (BytesUtils.isNonZeroedHexStringWithPrefixAndProperBytesSize(privateKey,
                BytesUtils.BYTES_32_SIZE)) {
            return Credentials.create(privateKey).getAddress();
        }
        log.error("Cannot get address from private key [privateKeyLength:{}]",
                StringUtils.isNotEmpty(privateKey) ? privateKey.length() : 0);
        return "";
    }

}
