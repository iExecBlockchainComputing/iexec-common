package com.iexec.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.maven.shared.utils.cli.CommandLineException;
import org.apache.maven.shared.utils.cli.CommandLineUtils;
import org.bouncycastle.util.Arrays;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import java.nio.charset.StandardCharsets;

@Slf4j
public class ArgsUtils {

    private ArgsUtils() {
        throw new UnsupportedOperationException();
    }

    /*
     * stringArgsToArrayArgs("1st_param 2nd_param '3rd param' \"4th param\"") gives
     * ["1st_param", "2nd_param", "3rd param", "4th param"]
     * */
    public static String[] stringArgsToArrayArgs(String args) {
        try {
            return CommandLineUtils.translateCommandline(args);
        } catch (CommandLineException e) {
            log.error("Failed to translate string args to array [args:{}]", args);
        }
        return null;
    }
}
