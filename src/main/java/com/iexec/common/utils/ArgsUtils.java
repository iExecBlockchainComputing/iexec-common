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
import org.apache.maven.shared.utils.cli.CommandLineException;
import org.apache.maven.shared.utils.cli.CommandLineUtils;

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
