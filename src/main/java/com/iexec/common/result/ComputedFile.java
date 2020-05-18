package com.iexec.common.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComputedFile {

    @JsonProperty("deterministic-output-path")
    private String deterministicOutputPath;

    @JsonProperty("callback-data")
    private String callbackData;

    @JsonProperty("task-id")
    private String taskId;

    @JsonProperty("result-digest")
    private String resultDigest;

    @JsonProperty("enclave-signature")
    private String enclaveSignature;

}
