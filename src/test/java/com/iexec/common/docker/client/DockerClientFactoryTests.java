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

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DockerClientFactoryTests {

    /**
     * Prevents regression
     */
    @Test
    public void shouldCreateOnlyOneClientInstanceWithoutCredentials() {
        assertThat(DockerClientFactory.get() == DockerClientFactory.get())
                .isTrue();
    }

    @Test
    public void shouldCreateClientWithAuthConfigCredentials() {
        String username = "username";
        String password = "password";

        DockerClientInstance dockerClientInstance = DockerClientFactory
                .get(username, password);

        assertThat(dockerClientInstance.getClient().authConfig().getUsername())
                .isEqualTo(username);
        assertThat(dockerClientInstance.getClient().authConfig().getPassword())
                .isEqualTo(password);
    }
}