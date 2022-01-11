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

package com.iexec.common.chain.eip712;

import com.iexec.common.utils.BytesUtils;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;

/**
 * See https://medium.com/metamask/eip712-is-coming-what-to-expect-and-how-to-use-it-bb92fd1a7a26
 */
public class EIP712Utils {

    private EIP712Utils() {
        throw new UnsupportedOperationException();
    }

    public static String encodeData(Object param) {
        if (param.getClass().equals(String.class)) {
            if (Numeric.containsHexPrefix((String) param)) {
                return encodeHexString((String) param); // 0x < bytes32
            } else {
                return encodeUTF8String((String) param); // ascii
            }
        } else if (param.getClass().equals(Long.class)) {
            return encodeLong((Long) param);
        } else if (param.getClass().equals(BigInteger.class)) {
            return encodeBigInteger((BigInteger) param);
        } else if (param.getClass().equals(byte[].class)) {
            return encodeByteArray((byte[]) param);
        }
        return "";
    }

    static String encodeUTF8String(String string) {
        return Hash.sha3String(string);
    }

    static String encodeHexString(String hexString) {
        if (!BytesUtils.isHexString(hexString)) {
            return "";
        }
        if (BytesUtils.stringToBytes(hexString).length > 32) {
            return "";
        }
        return Numeric.toHexString(Numeric.toBytesPadded(Numeric.toBigInt(hexString), 32));
    }

    static String encodeLong(Long longValue) {
        return Numeric.toHexString(Numeric.toBytesPadded(BigInteger.valueOf(longValue), 32));
    }

    static String encodeBigInteger(BigInteger bigInteger) {
        return Numeric.toHexString(Numeric.toBytesPadded(bigInteger, 32));
    }

    static String encodeByteArray(byte[] byteArray) {
        if (!BytesUtils.isBytes32(BytesUtils.bytesToString(byteArray))) {
            return "";
        }
        return Numeric.toHexString(byteArray);
    }

    /*
     * REMINDER : Arrays.concatenate(byteArray) similar to Numeric.hexStringToByteArray("0x1901")
     *
     * abi.encode should take bytes32 (padding could be required) while abi.encodePacked takes 0x12+1a2b
     *
     * System.out.println(Numeric.toHexString(Arrays.concatenate(Numeric.hexStringToByteArray("1901"),
     * Numeric.hexStringToByteArray(domainSeparator) ,
     * Numeric.hexStringToByteArray(messageHash))).equals(Numeric.toHexString(Numeric.hexStringToByteArray(
     * "0x1901" + Numeric.cleanHexPrefix(domainSeparator) + Numeric.cleanHexPrefix(messageHash)))));
     *
     */
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

}