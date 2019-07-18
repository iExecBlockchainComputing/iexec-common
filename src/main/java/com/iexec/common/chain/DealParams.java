package com.iexec.common.chain;

import io.reactivex.annotations.Nullable;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DealParams {

    // Note to dev: the naming of the variables here is important since it will be stored on-chain
    // that is why the names are not java compliant (use of underscore and no upper case)
    private String iexec_args;

    @Nullable
    private List<String> iexec_input_files;
}
