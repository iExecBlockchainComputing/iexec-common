package com.iexec.common.task;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private List<String> inputFiles;
    private boolean isCallbackRequested;
    private boolean isResultEncryption;
    private String resultStorageProvider;
    private String resultStorageProxy;
    private String teePostComputeImage;
    private String teePostComputeFingerprint;

}
