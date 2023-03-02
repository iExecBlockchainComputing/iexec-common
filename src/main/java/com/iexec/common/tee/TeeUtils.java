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

package com.iexec.common.tee;

import com.iexec.common.utils.BytesUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Map;

public class TeeUtils {

    public static final int TEE_SCONE_BITS = 0b0011;
    public static final int TEE_GRAMINE_BITS = 0b0101;
    private static final Map<Integer, TeeFramework> TEE_BITS_TO_FRAMEWORK = Map.of(
            TEE_SCONE_BITS, TeeFramework.SCONE,
            TEE_GRAMINE_BITS, TeeFramework.GRAMINE
    );
    public static final String TEE_SCONE_ONLY_TAG = BytesUtils.toByte32HexString(TEE_SCONE_BITS);
    public static final String TEE_GRAMINE_ONLY_TAG = BytesUtils.toByte32HexString(TEE_GRAMINE_BITS);
    private static final int TEE_RUNTIME_FRAMEWORK_MASK = 0b1111; //last nibble (4 bits)

    private TeeUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Check if hexTag asks for a known TEE runtime framework.
     *
     * @param hexTag tag of the deal
     * @return true if a known TEE runtime framework is requested
     */
    public static boolean isTeeTag(String hexTag) {
        return hasTeeSconeInTag(hexTag) || hasTeeGramineInTag(hexTag);
    }

    /**
     * Check if tag asks for Scone TEE runtime framework.
     *
     * @param hexTag tag of the deal
     * @return true if Scone TEE runtime framework is requested
     */
    public static boolean hasTeeSconeInTag(String hexTag) {
        return hasTeeRuntimeFrameworkBitsInTag(TEE_SCONE_BITS, hexTag);
    }

    /**
     * Check if tag asks for Gramine TEE runtime framework.
     *
     * @param hexTag tag of the deal
     * @return true if Gramine TEE runtime framework is requested
     */
    public static boolean hasTeeGramineInTag(String hexTag) {
        return hasTeeRuntimeFrameworkBitsInTag(TEE_GRAMINE_BITS, hexTag);
    }

    /**
     * Check if some bits are set on the TEE runtime framework range.
     *
     * @param expectedBits some bits expected to be in the tag
     * @param hexTag       tag of the deal
     * @return true if bits are set
     */
    static boolean hasTeeRuntimeFrameworkBitsInTag(int expectedBits, String hexTag) {
        return hexTag != null && Numeric.toBigInt(hexTag)
                .and(BigInteger.valueOf(TEE_RUNTIME_FRAMEWORK_MASK))
                .equals(BigInteger.valueOf(expectedBits));
    }

    /**
     * Returns TEE framework matching given {@code hexTag}.
     *
     * @param hexTag tag of the deal
     * @return {@link TeeFramework} matching given {@code hexTag}
     * or {@literal null} if tag is not a TEE tag or if there is no match.
     */
    public static TeeFramework getTeeFramework(String hexTag) {
        for (Map.Entry<Integer, TeeFramework> teeFramework: TEE_BITS_TO_FRAMEWORK.entrySet()) {
            if (hasTeeRuntimeFrameworkBitsInTag(teeFramework.getKey(), hexTag)) {
                return teeFramework.getValue();
            }
        }
        //TODO add TeeFramework.UNDEFINED
        return null;
    }

    public static boolean isTeeChallenge(String challenge) {
        return challenge != null && !challenge.equals(BytesUtils.EMPTY_ADDRESS);
    }

    /*
     * CAS does not accept boolean in yml (Failed to generateSecureSession)
     * We need to convert boolean into String
     * */
    public static String booleanToYesNo(boolean isTrue) {
        return BooleanUtils.toStringYesNo(isTrue);
    }

    public static boolean booleanFromYesNo(String isYes) {
        return isYes != null && isYes.equals("yes");
    }

}
