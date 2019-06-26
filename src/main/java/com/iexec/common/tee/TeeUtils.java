package com.iexec.common.tee;

import com.iexec.common.utils.BytesUtils;

public class TeeUtils {

    private static final String TEE_TAG = "0x0000000000000000000000000000000000000000000000000000000000000001";

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

}
