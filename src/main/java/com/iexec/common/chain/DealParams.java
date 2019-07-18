package com.iexec.common.chain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DealParams {

    // Note to dev: the naming of the variables in the json file is important since it will be stored on-chain
    @JsonProperty("iexec_args")
    private String iexecArgs;

    @JsonProperty("iexec_input_files")
    private List<String> iexecInputFiles;
}
