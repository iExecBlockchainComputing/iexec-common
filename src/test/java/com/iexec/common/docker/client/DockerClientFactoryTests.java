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

import com.github.dockerjava.api.exception.DockerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("slow")
public class DockerClientFactoryTests {

    private String dockerIoUsername = getEnvValue("DOCKER_IO_USER");
    private String dockerIoPassword = getEnvValue("DOCKER_IO_PASSWORD");
    private String nexusRegistry = getEnvValue("NEXUS_REGISTRY");
    private String nexusUsername = getEnvValue("NEXUS_USER");
    private String nexusPassword = getEnvValue("NEXUS_PASSWORD");

    @BeforeEach
    public void beforeEach() {
        DockerClientFactory.purgeClients();
    }

    @Test
    public void shouldGetUnauthenticatedClient() throws Exception {
        DockerClientInstance instance1 = DockerClientFactory.getDockerClientInstance();
        DockerClientInstance instance2 = DockerClientFactory.getDockerClientInstance();
        assertThat(instance1 == instance2).isTrue();
    }

    @Test
    public void shouldGetAuthenticatedClientWithDefaultRegistry() throws Exception {
        DockerClientInstance instance1 = DockerClientFactory
                .getDockerClientInstance(dockerIoUsername, dockerIoPassword);
        DockerClientInstance instance2 = DockerClientFactory
                .getDockerClientInstance(dockerIoUsername, dockerIoPassword);
        assertThat(instance1 == instance2).isTrue();
        assertThat(instance1.getClient().authConfig().getUsername())
                .isEqualTo(dockerIoUsername);
        assertThat(instance1.getClient().authConfig().getPassword())
                .isEqualTo(dockerIoPassword);
        System.out.println(instance1.getClient().authConfig());
    }

    @Test
    public void shouldGetAuthenticatedClientWithCustomRegistry() throws Exception {
        DockerClientInstance instance1 = DockerClientFactory
                .getDockerClientInstance(nexusRegistry, nexusUsername, nexusPassword);
        DockerClientInstance instance2 = DockerClientFactory
                .getDockerClientInstance(nexusRegistry, nexusUsername, nexusPassword);
        assertThat(instance1 == instance2).isTrue();
        assertThat(instance1.getClient().authConfig().getRegistryAddress())
                .isEqualTo(nexusRegistry);
        assertThat(instance1.getClient().authConfig().getUsername())
                .isEqualTo(nexusUsername);
        assertThat(instance1.getClient().authConfig().getPassword())
                .isEqualTo(nexusPassword);
    }

    @Test
    public void shouldFailtoAuthenticateClientWithDefaultRegistry() {
        DockerException e = assertThrows(DockerException.class, () -> DockerClientFactory
                .getDockerClientInstance("badUsername", "badPassword"));
        assertThat(e.getHttpStatus()).isEqualTo(401);
    }

    @Test
    public void shouldFailtoAuthenticateClientWithCustomRegistry() {
        DockerException e = assertThrows(DockerException.class, () -> DockerClientFactory
                .getDockerClientInstance(nexusRegistry, nexusUsername, "badPassword"));
        assertThat(e.getHttpStatus()).isEqualTo(401);
    }

    private String getEnvValue(String envVarName) {
        return System.getenv(envVarName) != null ?
                //Intellij envvar injection
                System.getenv(envVarName) :
                //gradle test -DdockerhubPassword=xxx
                System.getProperty(envVarName);
    }
}