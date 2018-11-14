package com.iexec.common.chain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.web3j.crypto.Sign;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContributionAuthorization {

    private String workerWallet;
    private String taskId;
    private String enclave;
    private Sign.SignatureData sign;
}
