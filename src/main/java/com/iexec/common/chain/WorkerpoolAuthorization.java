package com.iexec.common.chain;

import com.iexec.common.security.Signature;
import com.iexec.common.utils.HashUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerpoolAuthorization {

    private String chainTaskId;
    private String workerWallet;
    private String enclaveChallenge;
    private Signature signature;

    public String getHash() {
        return HashUtils.concatenateAndHash(workerWallet, chainTaskId, enclaveChallenge);
    }
}