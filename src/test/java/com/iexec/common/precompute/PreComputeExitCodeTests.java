package com.iexec.common.precompute;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PreComputeExitCodeTests {

    @Test
    public void contains() {
        assertThat(PreComputeExitCode.contains(-1)).isTrue();
        assertThat(PreComputeExitCode.contains(0)).isTrue();
        assertThat(PreComputeExitCode.contains(64)).isTrue();
        assertThat(PreComputeExitCode.contains(70)).isTrue();
        assertThat(PreComputeExitCode.contains(-3)).isFalse();
        assertThat(PreComputeExitCode.contains(3)).isFalse();
        assertThat(PreComputeExitCode.contains(71)).isFalse();
        assertThat(PreComputeExitCode.contains(9999)).isFalse();
    }

    @Test
    public void nameOf() {
        assertThat(PreComputeExitCode.nameOf(0))
                .isEqualTo(PreComputeExitCode.SUCCESS);
        assertThat(PreComputeExitCode.nameOf(-1))
                .isEqualTo(PreComputeExitCode.UNKNOWN_ERROR);
        assertThat(PreComputeExitCode.nameOf(64))
                .isEqualTo(PreComputeExitCode.EMPTY_REQUIRED_ENV_VAR);
        assertThat(PreComputeExitCode.nameOf(70))
                .isEqualTo(PreComputeExitCode.DATASET_DECRYPTION_ERROR);
        assertThat(PreComputeExitCode.nameOf(71)).isNull();
        assertThat(PreComputeExitCode.nameOf(999999)).isNull();
    }
}
