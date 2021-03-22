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

@Slf4j
public class EnvUtils {

    public static String getEnvVarOrExit(String envVarName) {
        String envVar = getEnvVar(envVarName);
        if (StringUtils.isBlank(envVar)) {
            log.error("Env var is blank, exiting [varName:{}]", envVarName);
            System.exit(1);
        }
        return envVar;
    }

    public static String getEnvVar(String envVarName) {
        String envVar = System.getenv(envVarName);
        return StringUtils.isNotBlank(envVar) ? envVar : "";
    }
}