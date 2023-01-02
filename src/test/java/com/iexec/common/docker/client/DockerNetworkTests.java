/*
 * Copyright 2022 IEXEC BLOCKCHAIN TECH
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

class DockerNetworkTests extends AbstractDockerTests {

    //region createNetwork
    @Test
    void shouldCreateNetwork() {
        String networkName = getRandomString();
        String networkId = dockerClientInstance.createNetwork(networkName);
        assertThat(networkId).isNotEmpty();
        assertThat(dockerClientInstance.isNetworkPresent(networkName)).isTrue();
        // cleaning
        dockerClientInstance.removeNetwork(networkName);
    }

    @Test
    void shouldNotCreateNetworkSinceEmptyName() {
        assertThat(dockerClientInstance.createNetwork("")).isEmpty();
    }

    @Test
    void shouldReturnExistingNetworkIdWenNetworkIsAlreadyPresent() {
        String networkName = getRandomString();
        dockerClientInstance.createNetwork(networkName);
        assertThat(dockerClientInstance.createNetwork(networkName)).isNotEmpty();
        assertThat(dockerClientInstance.isNetworkPresent(networkName)).isTrue();
        // cleaning
        dockerClientInstance.removeNetwork(networkName);
    }

    @Test
    void shouldNotCreateNetworkSinceDockerCmdException() {
        assertThat(corruptClientInstance.createNetwork(getRandomString())).isEmpty();
    }
    //endregion

    //region getNetworkId
    @Test
    void shouldGetNetworkId() {
        String networkName = getRandomString();
        String networkId = dockerClientInstance.createNetwork(networkName);
        assertThat(dockerClientInstance.getNetworkId(networkName)).isEqualTo(networkId);
        // cleaning
        dockerClientInstance.removeNetwork(networkName);
    }

    @Test
    void shouldNotGetNetworkIdSinceEmptyName() {
        assertThat(dockerClientInstance.getNetworkId("")).isEmpty();
    }

    @Test
    void shouldNotGetNetworkIdSinceDockerCmdException() {
        assertThat(corruptClientInstance.getNetworkId(getRandomString())).isEmpty();
    }
    //endregion

    //region isNetworkPresent
    @Test
    void shouldFindNetworkPresent() {
        String networkName = getRandomString();
        dockerClientInstance.createNetwork(networkName);
        assertThat(dockerClientInstance.isNetworkPresent(networkName)).isTrue();
        dockerClientInstance.removeNetwork(networkName);
    }

    @Test
    void shouldNotFindNetworkPresentSinceEmptyName() {
        assertThat(dockerClientInstance.isNetworkPresent("")).isFalse();
    }

    @Test
    void shouldNotFindNetworkPresentSinceDockerCmdException() {
        assertThat(corruptClientInstance.isNetworkPresent(getRandomString())).isFalse();
    }
    //endregion

    //region removeNetwork
    @Test
    void shouldRemoveNetwork() {
        String networkName = getRandomString();
        dockerClientInstance.createNetwork(networkName);
        assertThat(dockerClientInstance.isNetworkPresent(networkName)).isTrue();
        assertThat(dockerClientInstance.removeNetwork(networkName)).isTrue();
        assertThat(dockerClientInstance.isNetworkPresent(networkName)).isFalse();
    }

    @Test
    void shouldNotRemoveNetworkSinceEmptyId() {
        assertThat(dockerClientInstance.removeNetwork("")).isFalse();
    }

    @Test
    void shouldNotRemoveNetworkSinceNotFound() {
        String networkName = getRandomString();
        assertThat(dockerClientInstance.removeNetwork(networkName)).isFalse();
    }

    @Test
    void shouldNotRemoveNetworkSinceDockerCmdException() {
        String networkName = getRandomString();
        dockerClientInstance.createNetwork(networkName);
        assertThat(dockerClientInstance.isNetworkPresent(networkName)).isTrue();
        assertThat(corruptClientInstance.removeNetwork(networkName)).isFalse();
        dockerClientInstance.removeNetwork(networkName);
    }
    //endregion

}
