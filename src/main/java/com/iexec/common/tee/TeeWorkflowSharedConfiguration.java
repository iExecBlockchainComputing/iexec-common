package com.iexec.common.tee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeeWorkflowSharedConfiguration {

    String preComputeImage;
    String preComputeHeapSize;
    String postComputeImage;
    String postComputeHeapSize;
}
