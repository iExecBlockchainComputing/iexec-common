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

import com.iexec.common.chain.ChainDeal;
import com.iexec.common.dapp.DappType;
import com.iexec.common.tee.TeeEnclaveConfiguration;
import com.iexec.common.tee.TeeEnclaveProvider;
import com.iexec.common.tee.TeeUtils;
import com.iexec.common.utils.BytesUtils;
import com.iexec.common.utils.MultiAddressHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

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
    private String appAddress;
    private TeeEnclaveConfiguration appEnclaveConfiguration;
    private String cmd;
    private long maxExecutionTime;
    private boolean isTeeTask;
    private TeeEnclaveProvider teeEnclaveProvider;
    private int botIndex;
    private int botSize;
    private int botFirstIndex;
    private boolean developerLoggerEnabled;
    private String datasetAddress;
    private String datasetUri;
    private String datasetName;
    private String datasetChecksum;
    private List<String> inputFiles;
    private boolean isResultEncryption;
    private String resultStorageProvider;
    private String resultStorageProxy;
    private Map<String, String> secrets;
    private String teePostComputeImage;
    private String teePostComputeFingerprint;

    /**
     * Check if this task includes a dataset or not. The task is considered
     * as including a dataset only if all fields of the dataset are non-empty,
     * non-null values. The stack should ignore datasets with missing
     * information since they, inevitably, break the workflow. In the case
     * where those datasets are ignored, the worker will contribute an
     * application error caused by the missing dataset file.
     * 
     * @return true if all dataset fields are all non-null,
     * non-empty values, false otherwise.
     */
    public boolean containsDataset() {
        return !StringUtils.isEmpty(datasetAddress) &&
                !datasetAddress.equals(BytesUtils.EMPTY_ADDRESS) &&
                !StringUtils.isEmpty(datasetUri) &&
                !StringUtils.isEmpty(datasetChecksum) &&
                !StringUtils.isEmpty(datasetName);
    }

    /**
     * Check if a callback is requested for this task. 
     * 
     * @return true if a callback address is found in the deal, false otherwise.
     */
    public boolean containsCallback() {
        return !StringUtils.isEmpty(getCallback()) &&
                !getCallback().equals(BytesUtils.EMPTY_ADDRESS);
    }

    /**
     * Check if this task includes some input files.
     * 
     * @return true if at least one input file is present, false otherwise
     */
    public boolean containsInputFiles() {
        return inputFiles != null && !inputFiles.isEmpty();
    }

    /**
     * Check if this task contains a post-compute image uri and its fingerprint.
     * 
     * @return true if and only if both image uri and fingerprint are present,
     * false otherwise
     */
    public boolean containsPostCompute() {
        return !StringUtils.isEmpty(teePostComputeImage) &&
                !StringUtils.isEmpty(teePostComputeFingerprint);
    }

    public String getAppCommand() {
        String appArgs = appEnclaveConfiguration.getEntrypoint();
        if (!StringUtils.isEmpty(cmd)) {
            appArgs = appArgs + " " + cmd;
        }
        return appArgs;
    }

    /**
     * Create a {@link TaskDescription} from the provided chain deal. This method
     * if preferred to constructors or the builder method.
     * 
     * @param chainTaskId
     * @param taskIdx
     * @param chainDeal
     * @return the created taskDescription
     */
    public static TaskDescription toTaskDescription(String chainTaskId,
                                                    int taskIdx,
                                                    ChainDeal chainDeal) {
        if (chainDeal == null) {
            return null;
        }
        String datasetAddress = "";
        String datasetUri = "";
        String datasetName = "";
        String datasetChecksum = "";
        if (chainDeal.containsDataset()) {
            datasetAddress = chainDeal.getChainDataset().getChainDatasetId();
            datasetUri = MultiAddressHelper.convertToURI(
                            chainDeal.getChainDataset().getUri());
            datasetName = chainDeal.getChainDataset().getName();
            datasetChecksum = chainDeal.getChainDataset().getChecksum();
        }
        final String tag = chainDeal.getTag();
        return TaskDescription.builder()
                .chainTaskId(chainTaskId)
                .requester(chainDeal
                        .getRequester())
                .beneficiary(chainDeal
                        .getBeneficiary())
                .callback(chainDeal
                        .getCallback())
                .appType(DappType.DOCKER)
                .appUri(BytesUtils.hexStringToAscii(chainDeal.getChainApp()
                        .getUri()))
                .appAddress(chainDeal.getChainApp().getChainAppId())
                .appEnclaveConfiguration(chainDeal.getChainApp()
                        .getEnclaveConfiguration())
                .cmd(chainDeal.getParams()
                        .getIexecArgs())
                .inputFiles(chainDeal.getParams()
                        .getIexecInputFiles())
                .maxExecutionTime(chainDeal.getChainCategory()
                        .getMaxExecutionTime())
                .isTeeTask(TeeUtils
                        .isTeeTag(tag))
                .teeEnclaveProvider(TeeUtils
                        .getTeeEnclaveProvider(tag))
                .developerLoggerEnabled(chainDeal.getParams()
                        .isIexecDeveloperLoggerEnabled())
                .isResultEncryption(chainDeal.getParams()
                        .isIexecResultEncryption())
                .resultStorageProvider(chainDeal.getParams()
                        .getIexecResultStorageProvider())
                .resultStorageProxy(chainDeal.getParams()
                        .getIexecResultStorageProxy())
                .secrets(chainDeal.getParams()
                        .getIexecSecrets())
                .teePostComputeImage(chainDeal.getParams()
                        .getIexecTeePostComputeImage())
                .teePostComputeFingerprint(chainDeal.getParams()
                        .getIexecTeePostComputeFingerprint())
                .datasetAddress(datasetAddress)
                .datasetUri(datasetUri)
                .datasetName(datasetName)
                .datasetChecksum(datasetChecksum)
                .botSize(chainDeal
                        .getBotSize().intValue())
                .botFirstIndex(chainDeal
                        .getBotFirst().intValue())
                .botIndex(taskIdx)
                .build();
    }
}
