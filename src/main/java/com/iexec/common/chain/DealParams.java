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

package com.iexec.common.chain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DealParams {

    public static final String IPFS_RESULT_STORAGE_PROVIDER = "ipfs";
    public static final String DROPBOX_RESULT_STORAGE_PROVIDER = "dropbox";

    // Note to dev: the naming of the variables in the json file is important since it will be stored on-chain
    @JsonProperty("iexec_args")
    private String iexecArgs;

    @JsonProperty("iexec_input_files")
    private List<String> iexecInputFiles;

    @JsonProperty("iexec_developer_logger")
    private boolean iexecDeveloperLoggerEnabled;

    @JsonProperty("iexec_result_encryption")
    private boolean iexecResultEncryption;

    @JsonProperty("iexec_result_storage_provider")
    private String iexecResultStorageProvider;

    @JsonProperty("iexec_result_storage_proxy")
    private String iexecResultStorageProxy;

    //Should be set by SDK
    @JsonProperty("iexec_tee_post_compute_image")
    private String iexecTeePostComputeImage;

    //Should be set by SDK
    @JsonProperty("iexec_tee_post_compute_fingerprint")
    private String iexecTeePostComputeFingerprint;


}
