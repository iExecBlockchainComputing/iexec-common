package com.iexec.common.tee;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TeeUtils {

    private TeeUtils() {
        throw new UnsupportedOperationException();
    }


    //TODO : xor instead of equals
    public static boolean isTrustedExecutionTag(String tag){
        return tag != null && tag.equals("0x01");
    }

}
