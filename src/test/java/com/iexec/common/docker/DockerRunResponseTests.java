package com.iexec.common.docker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class DockerRunResponseTests {
    @Test
    void shouldBeSuccessful() {
        final DockerRunResponse response = DockerRunResponse.builder().finalStatus(DockerRunFinalStatus.SUCCESS).build();
        Assertions.assertTrue(response.isSuccessful());
    }

    @ParameterizedTest
    @EnumSource(value = DockerRunFinalStatus.class, names = {"FAILED", "TIMEOUT"})
    void shouldBeFailed(DockerRunFinalStatus status) {
        final DockerRunResponse response = DockerRunResponse.builder().finalStatus(status).build();
        Assertions.assertFalse(response.isSuccessful());
    }
}