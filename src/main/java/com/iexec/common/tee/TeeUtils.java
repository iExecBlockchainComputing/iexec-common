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

public class TeeUtils {

    public static final String TEE_TAG = "0x0000000000000000000000000000000000000000000000000000000000000001";

    private TeeUtils() {
        throw new UnsupportedOperationException();
    }

    //TODO : xor instead of equals
    public static boolean isTeeTag(String tag){
        return tag != null && tag.equals(TEE_TAG);
    }

    public static boolean isTeeChallenge(String challenge){
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
        return isYes!= null && isYes.equals("yes");
    }

}
