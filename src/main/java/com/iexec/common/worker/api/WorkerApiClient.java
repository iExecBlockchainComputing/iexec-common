/*
 * Copyright 2022 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.worker.api;

import com.iexec.common.replicate.ReplicateStatusCause;
import com.iexec.common.result.ComputedFile;
import feign.FeignException;
import feign.Param;
import feign.RequestLine;

//TODO Migrate someday to iexec-worker-library
public interface WorkerApiClient {

    @RequestLine("POST /compute/pre/{chainTaskId}/exit")
    void sendExitCauseForPreComputeStage(@Param String chainTaskId,
                                         ReplicateStatusCause replicateStatusCause)
            throws FeignException;

    @RequestLine("POST /compute/post/{chainTaskId}/exit")
    void sendExitCauseForPostComputeStage(@Param String chainTaskId,
                                          ReplicateStatusCause replicateStatusCause)
            throws FeignException;

    @RequestLine("POST /compute/post/{chainTaskId}/computed")
    String sendComputedFileForTee(@Param String chainTaskId,
                                  @Param ComputedFile computedFile)
            throws FeignException;

}
