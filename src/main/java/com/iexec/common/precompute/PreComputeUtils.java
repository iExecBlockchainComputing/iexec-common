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

package com.iexec.common.precompute;

import java.io.File;

public class PreComputeUtils {

    /**
     * /!\ If you change those values please update
     * the palaemon config file.
     */

    public static final String IEXEC_DATASET_KEY_PROPERTY = "IEXEC_DATASET_KEY";
    public static final String IEXEC_DATASET_CHECKSUM_PROPERTY = "IEXEC_DATASET_CHECKSUM";

    public static final String IEXEC_PRE_COMPUTE_IN_PROPERTY = "IEXEC_PRE_COMPUTE_IN";
    public static final String SLASH_PRE_COMPUTE_IN = File.separator + "pre-compute-in";

    public static final String IEXEC_PRE_COMPUTE_OUT_PROPERTY = "IEXEC_PRE_COMPUTE_OUT";
    // pre-compute writes its output in compute stage's input (/iexec_in).
}
