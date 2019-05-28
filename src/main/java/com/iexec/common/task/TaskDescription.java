package com.iexec.common.task;

import com.iexec.common.dapp.DappType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDescription {

    private String chainTaskId;
    private DappType appType;
    private String appUri;
    private String cmd;
    private long maxExecutionTime;
    private boolean isTrustedExecution;
    private String datasetUri;
}
