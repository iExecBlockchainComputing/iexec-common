package com.iexec.common.chain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContributionAuthorization {

    private String workerWallet;
    private String chainTaskId;
    private String enclave;

    private byte signV;
    private byte[] signR;
    private byte[] signS;
}
