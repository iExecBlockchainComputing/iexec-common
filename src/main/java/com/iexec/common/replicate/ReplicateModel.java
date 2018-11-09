package com.iexec.common.replicate;

import com.iexec.common.dapp.DappType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplicateModel {

    private String taskId;
    private byte[] chainTaskId;
    private String workerAddress;
    private DappType dappType;
    private String dappName;
    private String cmd;
    private ReplicateStatus replicateStatus;

}
