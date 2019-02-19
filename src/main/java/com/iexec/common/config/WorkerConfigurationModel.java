package com.iexec.common.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerConfigurationModel {

    private String name;
    private String walletAddress;
    private String os;
    private String cpu;
    private int cpuNb;
    private int memorySize;
    private boolean sgxEnabled;
}

