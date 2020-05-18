package com.iexec.common.contribution;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contribution {

    private String chainTaskId;

    private String resultDigest;

    private String resultHash;

    private String resultSeal;

    private String enclaveSignature;

    private String workerPoolSignature;

    private String enclaveChallenge;


}
