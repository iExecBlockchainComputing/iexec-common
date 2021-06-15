/*
 * Copyright 2020 IEXEC BLOCKCHAIN TECH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iexec.common.docker.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("slow")
public class DockerClientFactoryTests {

    private static final String DOCKER_IO_USER = "DOCKER_IO_USER";
    private static final String DOCKER_IO_PASSWORD = "DOCKER_IO_PASSWORD";

    @BeforeEach
    public void beforeEach() {
        DockerClientFactory.purgeClients();
    }

    @Test
    public void shouldGetTheSameUnauthenticatedClientInstanceWithDefaultRegistry() throws Exception {
        DockerClientInstance instance1 = DockerClientFactory.getDockerClientInstance();
        DockerClientInstance instance2 = DockerClientFactory.getDockerClientInstance();
        assertThat(instance1 == instance2).isTrue();
    }

    @Test
    public void shouldGetTheSameUnauthenticatedClientInstanceWithCustomRegistry() throws Exception {
        String registryAddress = "registryAddress";
        DockerClientInstance instance1 = DockerClientFactory.getDockerClientInstance(registryAddress);
        DockerClientInstance instance2 = DockerClientFactory.getDockerClientInstance(registryAddress);
        assertThat(instance1 == instance2).isTrue();
    }

    @Test
    public void shouldGetTheSameAuthenticatedClient() throws Exception {
        String dockerIoUsername = getEnvValue(DOCKER_IO_USER);
        String dockerIoPassword = getEnvValue(DOCKER_IO_PASSWORD);
        DockerClientInstance instance1 = DockerClientFactory.getDockerClientInstance(
                DockerClientInstance.DEFAULT_DOCKER_REGISTRY, dockerIoUsername, dockerIoPassword);
        DockerClientInstance instance2 = DockerClientFactory.getDockerClientInstance(
                DockerClientInstance.DEFAULT_DOCKER_REGISTRY, dockerIoUsername, dockerIoPassword);
        assertThat(instance1 == instance2).isTrue();
    }

    private String getEnvValue(String envVarName) {
        return System.getenv(envVarName) != null ?
                //Intellij envvar injection
                System.getenv(envVarName) :
                //gradle test -DdockerhubPassword=xxx
                System.getProperty(envVarName);
    }
}