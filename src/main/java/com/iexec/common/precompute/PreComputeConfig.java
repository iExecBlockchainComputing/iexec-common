package com.iexec.common.precompute;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@NoArgsConstructor
public class PreComputeConfig {

    private String image;
    private String fingerprint;
    private String heapSize;

    public PreComputeConfig(String image, String fingerprint, String heapSize) {
        if (StringUtils.isEmpty(image)) {
            throw new IllegalStateException("No pre-compute image is provided");
        }
        if (StringUtils.isEmpty(fingerprint)) {
            throw new IllegalStateException("No pre-compute fingerprint is provided");
        }
        if (StringUtils.isEmpty(heapSize)) {
            throw new IllegalStateException("No pre-compute heap size is provided");
        }
        this.image = image;
        this.fingerprint = fingerprint;
        this.heapSize = heapSize;
    }
}
