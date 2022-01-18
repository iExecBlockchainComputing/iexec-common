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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MultiAddressHelperTests {

    @Test
    void shouldConvertToIPFSAddress() {
        String content = MultiAddressHelper.convertToURI("0xa5032212205d008f22f8f878fd58473a634cc73d41049fc0d19b81002eaae221ac5a446a3e");
        String expected = "https://gateway.ipfs.io/ipfs/QmUbh7ugQ9WVprTVYjzrCS4d9cCy73zUz4MMchsrqzzu1w";

        assertEquals(content, expected);

        content = MultiAddressHelper.convertToURI("0xa5032212207237a7f4091a74ae95514890fa86a2e8a36037c350ea71c06a34c8cbe25a5057");
        expected = "https://gateway.ipfs.io/ipfs/QmW2WQi7j6c7UgJTarActp7tDNikE4B2qXtFCfLPdsgaTQ";

        assertEquals(content, expected);
    }

    @Test
    void shouldConvertToHTTPAddress() {
        // this is hexa value of an HTTP address in String format, not in multiaddress format!
        String content = MultiAddressHelper.convertToURI("0x68747470733a2f2f676174657761792e697066732e696f2f697066732f516d5732575169376a36633755674a546172416374703774444e696b453442327158744643664c506473676154512f6361742e6a7067");
        String expected = "https://gateway.ipfs.io/ipfs/QmW2WQi7j6c7UgJTarActp7tDNikE4B2qXtFCfLPdsgaTQ/cat.jpg";

        assertEquals(content, expected);
    }
}
