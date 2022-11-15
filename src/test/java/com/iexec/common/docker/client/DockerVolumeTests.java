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

class DockerVolumeTests extends AbstractDockerTests {

    //region createVolume
    @Test
    void shouldCreateVolume() {
        String volumeName = getRandomString();
        assertThat(dockerClientInstance.isVolumePresent(volumeName)).isFalse();
        assertThat(dockerClientInstance.createVolume(volumeName)).isTrue();
        // cleaning
        dockerClientInstance.removeVolume(volumeName);
    }

    @Test
    void shouldNotCreateVolumeSinceEmptyName() {
        assertThat(dockerClientInstance.createVolume("")).isFalse();
    }

    @Test
    void shouldReturnTrueSinceVolumeAlreadyPresent() {
        String volumeName = getRandomString();
        assertThat(dockerClientInstance.createVolume(volumeName)).isTrue();
        assertThat(dockerClientInstance.createVolume(volumeName)).isTrue();
        // cleaning
        dockerClientInstance.removeVolume(volumeName);
    }

    @Test
    void shouldNotCreateVolumeSinceDockerCmdException() {
        assertThat(corruptClientInstance.createVolume(getRandomString())).isFalse();
    }
    //endregion

    //region isVolumePresent
    @Test
    void ShouldFindVolumePresent() {
        String volumeName = getRandomString();
        dockerClientInstance.createVolume(volumeName);
        assertThat(dockerClientInstance.isVolumePresent(volumeName)).isTrue();
        // cleaning
        dockerClientInstance.removeVolume(volumeName);
    }

    @Test
    void shouldNotFindVolumePresentSinceEmptyName() {
        assertThat(dockerClientInstance.isVolumePresent("")).isFalse();
    }

    @Test
    void shouldNotFindVolumePresentSinceDockerCmdException() {
        assertThat(corruptClientInstance.isVolumePresent(getRandomString())).isFalse();
    }
    //endregion

    //region removeVolume
    @Test
    void shouldRemoveVolume() {
        String volumeName = getRandomString();
        dockerClientInstance.createVolume(volumeName);
        assertThat(dockerClientInstance.removeVolume(volumeName)).isTrue();
        assertThat(dockerClientInstance.isVolumePresent(volumeName)).isFalse();
    }

    @Test
    void shouldNotRemoveVolumeSinceEmptyName() {
        assertThat(dockerClientInstance.removeVolume("")).isFalse();
    }

    @Test
    void shouldNotRemoveVolumeSinceNotFound() {
        String volumeName = getRandomString();
        assertThat(dockerClientInstance.removeVolume(volumeName)).isFalse();
    }

    @Test
    void shouldNotRemoveVolumeSinceDockerCmdException() {
        String volumeName = getRandomString();
        dockerClientInstance.createVolume(volumeName);
        assertThat(dockerClientInstance.isVolumePresent(volumeName)).isTrue();
        assertThat(corruptClientInstance.removeVolume(volumeName)).isFalse();
        dockerClientInstance.removeVolume(volumeName);
    }
    //endregion

}
