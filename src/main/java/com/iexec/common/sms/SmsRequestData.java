package com.iexec.common.sms;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsRequestData {

    @JsonProperty("taskid") private String chainTaskId;
    @JsonProperty("worker") private String workerAddress;
    @JsonProperty("enclave") private String enclaveChallenge;
    @JsonProperty("sign") private String coreSignature;
    @JsonProperty("workersign") private String workerSignature;
}