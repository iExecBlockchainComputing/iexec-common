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
