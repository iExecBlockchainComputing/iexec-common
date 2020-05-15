package com.iexec.common.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iexec.common.utils.FileHelper;
import com.iexec.common.utils.IexecFileHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComputedFile {

    @JsonProperty("deterministic-output")
    private String deterministicOutput;

    @JsonProperty("callback-data")
    private String callbackData;

    @JsonProperty("task-id")
    private String taskId;

    @JsonProperty("result-digest")
    private String resultDigest;

    @JsonProperty("enclave-signature")
    private String enclaveSignature;

    public static void main(String[] args) {
        String s = "{\"deterministic-output\":\"/scone/iexec_out/result.txt\",\"callback-data\":null,\"task-id\":\"0x20b09b5ee85e7964c42f2c7f299a614f3074703cf7186eac89dd2841e3c89364\",\"result-digest\":\"0x0574679b6715d15adf266f6a1faae523a93ca2c38c6bb1be8ab7d0d7a0cabf16\",\"enclave-signature\":\"0x0c99adff5e232452d885063f38d675cee4f37c9abc0602a859d276a0d8b632250e9afd6e4eabf3dc938a710cac9ebf46f95eb393218227662488f0d3cbffab921b\"}";
        FileHelper.writeFile("/tmp/computed.json", s.getBytes());

        ComputedFile computedFile = IexecFileHelper.readComputedFile("aa", "/tmp");
        System.out.println(computedFile);
    }

}
