package com.iexec.common.precompute;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PreComputeExitCodeTests {

    @Test
    public void contains() {
        assertThat(PreComputeExitCode.contains(-1)).isTrue();
        assertThat(PreComputeExitCode.contains(0)).isTrue();
        assertThat(PreComputeExitCode.contains(-3)).isFalse();
        assertThat(PreComputeExitCode.contains(3)).isFalse();
        assertThat(PreComputeExitCode.contains(64)).isTrue();
        assertThat(PreComputeExitCode.contains(70)).isTrue();
        assertThat(PreComputeExitCode.contains(71)).isFalse();
        assertThat(PreComputeExitCode.contains(9999)).isFalse();
    }

    @Test
    public void values() {
        assertThat(PreComputeExitCode.UNKNOWN_ERROR.value()).isEqualTo(-1);
        assertThat(PreComputeExitCode.SUCCESS.value()).isEqualTo(0);
        assertThat(PreComputeExitCode.EMPTY_REQUIRED_ENV_VAR.value()).isEqualTo(64);
        assertThat(PreComputeExitCode.OUTPUT_FOLDER_NOT_FOUND.value()).isEqualTo(65);
        assertThat(PreComputeExitCode.DATASET_DOWNLOAD_FAILED.value()).isEqualTo(66);
        assertThat(PreComputeExitCode.INVALID_DATASET_CHECKSUM.value()).isEqualTo(67);
        assertThat(PreComputeExitCode.INVALID_DATASET_KEY.value()).isEqualTo(68);
        assertThat(PreComputeExitCode.DATASET_DECRYPTION_FAILED.value()).isEqualTo(69);
        assertThat(PreComputeExitCode.WRITING_PLAIN_DATASET_FAILED.value()).isEqualTo(70);
    }
}
