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

package com.iexec.common.task;

import com.iexec.common.dapp.DappType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDescription {

    private String chainTaskId;
    private String requester;
    private String beneficiary;
    private String callback;
    private DappType appType;
    private String appUri;
    private String cmd;
    private long maxExecutionTime;
    private boolean isTeeTask;
    private int botIndex;
    private int botSize;
    private int botFirstIndex;
    private boolean developerLoggerEnabled;
    private String datasetUri;
    private String datasetChecksum;
    private List<String> inputFiles;
    private boolean isCallbackRequested;
    private boolean isResultEncryption;
    private String resultStorageProvider;
    private String resultStorageProxy;
    private String teePostComputeImage;
    private String teePostComputeFingerprint;

}
