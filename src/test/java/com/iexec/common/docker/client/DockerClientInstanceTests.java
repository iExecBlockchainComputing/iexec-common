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

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.Device;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.transport.DockerHttpClient;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;
import com.iexec.common.docker.DockerRunRequest;
import com.iexec.common.sgx.SgxDriverMode;
import com.iexec.common.utils.ArgsUtils;
import com.iexec.common.utils.IexecFileHelper;
import com.iexec.common.utils.SgxUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Spy;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Slf4j
@Tag("slow")
class DockerClientInstanceTests extends AbstractDockerTests {

    //classic
    private static final String DOCKER_IO_CLASSIC_IMAGE = "docker.io/alpine/socat:latest";
    private static final String SHORT_CLASSIC_IMAGE = "alpine/socat:latest";
    //library
    private static final String DOCKER_IO_LIBRARY_IMAGE = "docker.io/library/alpine:latest";
    private static final String SHORT_LIBRARY_IMAGE = "library/alpine:latest";
    private static final String VERY_SHORT_LIBRARY_IMAGE = "alpine:latest";
    // deprecated
    private static final String DOCKER_COM_CLASSIC_IMAGE = "registry.hub.docker.com/alpine/socat:latest";
    // other
    private static final String ALPINE_BLABLA = "alpine:blabla";
    private static final String BLABLA_LATEST = "blabla:latest";
    private static final String DOCKERHUB_USERNAME_ENV_NAME = "DOCKER_IO_USER";
    private static final String DOCKERHUB_PASSWORD_ENV_NAME = "DOCKER_IO_PASSWORD";
    private static final String PRIVATE_IMAGE_NAME = "iexechub/private-image:alpine-3.13";
    private static final String DEVICE_PATH_IN_CONTAINER = "/dev/some-device-in-container";
    private static final String DEVICE_PATH_ON_HOST = "/dev/some-device-on-host";

    private static final List<String> usedRandomNames = new ArrayList<>();
    private static final List<String> usedImages = List.of(
            DOCKER_IO_CLASSIC_IMAGE, SHORT_CLASSIC_IMAGE, DOCKER_IO_LIBRARY_IMAGE,
            SHORT_LIBRARY_IMAGE, VERY_SHORT_LIBRARY_IMAGE, DOCKER_COM_CLASSIC_IMAGE,
            ALPINE_LATEST);

    @Spy
    private DockerClient spiedClient = dockerClientInstance.getClient();

    private DockerClient corruptedClient = getCorruptedDockerClient();

    @BeforeAll
    static void beforeAll() {
        usedImages.forEach(imageName -> new DockerClientInstance().pullImage(imageName));
    }

    @AfterAll
    static void afterAll() {
        DockerClientInstance instance = new DockerClientInstance();
        // clean containers
        usedRandomNames.forEach(instance::stopAndRemoveContainer);
        // clean networks
        usedRandomNames.forEach(instance::removeNetwork);
        instance.removeNetwork(DOCKER_NETWORK);
        // clean docker images
        usedImages.forEach(instance::removeImage);
    }

    //region DockerClientInstance
    @Test
    void shouldCreateUnauthenticatedClientWithDefaultRegistry() {
        DockerClientInstance instance = new DockerClientInstance();
        assertThat(instance.getClient().authConfig().getRegistryAddress())
                .isEqualTo(DockerClientInstance.DEFAULT_DOCKER_REGISTRY);
        assertThat(instance.getClient().authConfig().getPassword()).isNull();
    }

    @Test
    void shouldCreateUnauthenticatedClientWithCustomRegistry() {
        String registryAddress = "registryAddress";
        DockerClientInstance instance = new DockerClientInstance(registryAddress);
        assertThat(instance.getClient().authConfig().getRegistryAddress())
                .isEqualTo(registryAddress);
        assertThat(instance.getClient().authConfig().getPassword()).isNull();
    }

    @Test
    void shouldGetAuthenticatedClientWithDockerIoRegistry() {
        String dockerIoUsername = getEnvValue(DOCKERHUB_USERNAME_ENV_NAME);
        String dockerIoPassword = getEnvValue(DOCKERHUB_PASSWORD_ENV_NAME);
        DockerClientInstance instance = new DockerClientInstance(
                DockerClientInstance.DEFAULT_DOCKER_REGISTRY,
                dockerIoUsername, dockerIoPassword);
        assertThat(instance.getClient().authConfig().getRegistryAddress())
                .isEqualTo(DockerClientInstance.DEFAULT_DOCKER_REGISTRY);
        assertThat(instance.getClient().authConfig().getUsername())
                .isEqualTo(dockerIoUsername);
        assertThat(instance.getClient().authConfig().getPassword())
                .isEqualTo(dockerIoPassword);
    }
    //endregion

    //region isImagePresent
    @Test
    void shouldFindImagePresent() {
        assertThat(dockerClientInstance.isImagePresent(ALPINE_LATEST)).isTrue();
    }

    @Test
    void shouldNotFindImagePresent() {
        assertThat(dockerClientInstance.isImagePresent(getRandomString())).isFalse();
    }

    @Test
    void shouldNotFindImagePresentSinceEmptyName() {
        assertThat(dockerClientInstance.isImagePresent("")).isFalse();
    }


    @Test
    void shouldNotFindImagePresentSinceDockerCmdException() {
        assertThat(corruptClientInstance.isImagePresent("")).isFalse();
    }
    //endregion

    //region pullImage
    @Test
    void shouldPullImage() {
        dockerClientInstance.removeImage(ALPINE_LATEST);
        assertThat(dockerClientInstance.pullImage(ALPINE_LATEST)).isTrue();
        assertThat(dockerClientInstance.isImagePresent(ALPINE_LATEST)).isTrue();
    }

    @Test
    void shouldPullImageWithExplicitTimeout() {
        dockerClientInstance.removeImage(ALPINE_LATEST);
        assertThat(dockerClientInstance.pullImage(ALPINE_LATEST, Duration.of(3, ChronoUnit.MINUTES))).isTrue();
        assertThat(dockerClientInstance.isImagePresent(ALPINE_LATEST)).isTrue();
    }

    @Test
    void shouldNotPullImageSinceTimeout() {
        dockerClientInstance.removeImage(ALPINE_LATEST);
        assertThat(dockerClientInstance.pullImage(ALPINE_LATEST, Duration.of(1, ChronoUnit.SECONDS))).isFalse();
        assertThat(dockerClientInstance.isImagePresent(ALPINE_LATEST)).isFalse();
        dockerClientInstance.pullImage(ALPINE_LATEST);
    }

    @Test
    void shouldNotPullImageSinceNoTag() {
        assertThat(dockerClientInstance.pullImage("alpine")).isFalse();
    }

    @Test
    void shouldNotPullImageSinceEmptyImageName() {
        assertThat(dockerClientInstance.pullImage("")).isFalse();
    }

    @Test
    void shouldNotPullImageSinceEmptyNameButPresentTag() {
        assertThat(dockerClientInstance.pullImage(":latest")).isFalse();
    }

    @Test
    void shouldNotPullImageSincePresentNameButEmptyTag() {
        assertThat(dockerClientInstance.pullImage("blabla:")).isFalse();
    }

    @Test
    void shouldNotPullImageSinceWrongName() {
        assertThat(dockerClientInstance.pullImage(BLABLA_LATEST)).isFalse();
    }

    @Test
    void shouldNotPullImageSinceWrongTag() {
        assertThat(dockerClientInstance.pullImage(ALPINE_BLABLA)).isFalse();
    }

    @Test
    void shouldNotPullImageSinceDockerCmdException() {
        assertThat(corruptClientInstance.pullImage(getRandomString())).isFalse();
    }

    /**
     * Try to pull a private image from iexechub, require valid login and permissions.
     * The test will fail if Docker Hub credentials are missing or invalid.
     */
    @Test
    void shouldPullPrivateImage() {
        String username = getEnvValue(DOCKERHUB_USERNAME_ENV_NAME);
        String password = getEnvValue(DOCKERHUB_PASSWORD_ENV_NAME);
        // Get an authenticated docker client
        DockerClientInstance authClientInstance =
                new DockerClientInstance(DockerClientInstance.DEFAULT_DOCKER_REGISTRY,
                        username, password);
        // clean to avoid previous tests collisions
        authClientInstance.removeImage(PRIVATE_IMAGE_NAME);
        // pull image and check
        assertThat(authClientInstance.pullImage(PRIVATE_IMAGE_NAME)).isTrue();
        // clean
        authClientInstance.removeImage(PRIVATE_IMAGE_NAME);
    }
    //endregion

    //region getImageId
    @Test
    void shouldGetImageId() {
        dockerClientInstance.pullImage(ALPINE_LATEST);
        assertThat(dockerClientInstance.getImageId(ALPINE_LATEST)).isNotEmpty();
    }

    @Test
    void shouldGetImageIdWithDockerIoClassicImage() {
        String image = DOCKER_IO_CLASSIC_IMAGE;
        dockerClientInstance.pullImage(image);
        assertThat(dockerClientInstance.getImageId(image)).isNotEmpty();
    }

    @Test
    void shouldGetImageIdWithShortClassicImage() {
        String image = SHORT_CLASSIC_IMAGE;
        dockerClientInstance.pullImage(image);
        assertThat(dockerClientInstance.getImageId(image)).isNotEmpty();
    }

    @Test
    void shouldGetImageIdWithDockerIoLibraryImage() {
        String image = DOCKER_IO_LIBRARY_IMAGE;
        dockerClientInstance.pullImage(image);
        assertThat(dockerClientInstance.getImageId(image)).isNotEmpty();
    }

    @Test
    void shouldGetImageIdWithShortLibraryImage() {
        String image = SHORT_LIBRARY_IMAGE;
        dockerClientInstance.pullImage(image);
        assertThat(dockerClientInstance.getImageId(image)).isNotEmpty();
    }

    @Test
    void shouldGetImageIdWithVeryShortLibraryImage() {
        String image = VERY_SHORT_LIBRARY_IMAGE;
        dockerClientInstance.pullImage(image);
        assertThat(dockerClientInstance.getImageId(image)).isNotEmpty();
    }

    @Test
    void shouldGetImageIdWithClassicDockerComImage() {
        String image = DOCKER_COM_CLASSIC_IMAGE;
        dockerClientInstance.pullImage(image);
        String imageId = dockerClientInstance.getImageId(image);
        assertThat(imageId).isNotEmpty();
    }

    @Test
    void shouldNotGetImageIdSinceEmptyName() {
        assertThat(dockerClientInstance.getImageId("")).isEmpty();
    }

    @Test
    void shouldNotGetImageId() {
        assertThat(dockerClientInstance.getImageId(BLABLA_LATEST)).isEmpty();
    }

    @Test
    void shouldNotGetImageIdSinceDockerCmdException() {
        assertThat(corruptClientInstance.getImageId(getRandomString())).isEmpty();
    }
    //endregion

    //region sanitizeImageName
    @Test
    void shouldGetSanitizedImageWithDockerIoClassicImage() {
        assertThat(dockerClientInstance.sanitizeImageName(DOCKER_IO_CLASSIC_IMAGE))
                .isEqualTo("alpine/socat:latest");
    }

    @Test
    void shouldGetSanitizedImageWithDockerIoLibraryImage() {
        assertThat(dockerClientInstance.sanitizeImageName(DOCKER_IO_LIBRARY_IMAGE))
                .isEqualTo("alpine:latest");
    }

    @Test
    void shouldGetSanitizedImageWithShortLibraryImage() {
        assertThat(dockerClientInstance.sanitizeImageName(SHORT_LIBRARY_IMAGE))
                .isEqualTo("alpine:latest");
    }

    @Test
    void shouldGetSanitizedImageIfShortNameLibraryName() {
        assertThat(dockerClientInstance.sanitizeImageName(VERY_SHORT_LIBRARY_IMAGE))
                .isEqualTo("alpine:latest");
    }

    @Test
    void shouldDoNothingTOSanitizeImageWithDockerComClassicImage() {
        assertThat(dockerClientInstance.sanitizeImageName(DOCKER_COM_CLASSIC_IMAGE))
                .isEqualTo(DOCKER_COM_CLASSIC_IMAGE);
    }

    @Test
    void shouldDoNothingForSanitizedImage() {
        String image = "nexus.iex.ec/some-app:latest";
        assertThat(dockerClientInstance.sanitizeImageName(image))
                .isEqualTo(image);
    }
    //endregion

    //region removeImage
    @Test
    void shouldRemoveImage() {
        dockerClientInstance.pullImage(DOCKER_IO_CLASSIC_IMAGE);
        assertThat(dockerClientInstance.removeImage(DOCKER_IO_CLASSIC_IMAGE)).isTrue();
    }

    @Test
    void shouldNotRemoveImageByIdSinceEmptyName() {
        assertThat(dockerClientInstance.removeImage("")).isFalse();
    }

    @Test
    void shouldNotRemoveImageByIdSinceDockerCmdException() {
        assertThat(corruptClientInstance.removeImage(ALPINE_LATEST)).isFalse();
    }
    //endregion

    //region createContainer
    @Test
    void shouldCreateContainer() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerId = dockerClientInstance.createContainer(request);
        assertThat(containerId).isNotEmpty();
        // cleaning
        dockerClientInstance.removeContainer(request.getContainerName());
    }

    @Test
    void shouldNotCreateContainerSinceNoRequest() {
        assertThat(dockerClientInstance.createContainer(null)).isEmpty();
    }

    @Test
    void shouldNotCreateContainerSinceEmptyContainerName() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        request.setContainerName("");
        assertThat(dockerClientInstance.createContainer(request)).isEmpty();
    }

    @Test
    void shouldNotCreateContainerSinceEmptyImageUri() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        request.setImageUri("");
        assertThat(dockerClientInstance.createContainer(request)).isEmpty();
    }

    @Test
    void shouldNotCreateContainerSinceDockerCmdException() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        when(dockerClientInstance.getClient())
                .thenCallRealMethod() // isContainerPresent
                .thenCallRealMethod() // isNetworkPresent
                .thenCallRealMethod() // createNetwork
                .thenReturn(corruptedClient);
        assertThat(dockerClientInstance.createContainer(request)).isEmpty();
    }

    @Test
    void shouldCreateContainerAndRemoveExistingDuplicate() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        // create first container
        String container1Id = dockerClientInstance.createContainer(request);
        // create second container with same name (should replace previous one)
        String container2Id = dockerClientInstance.createContainer(request);
        assertThat(container2Id).isNotEmpty();
        assertThat(container2Id).isNotEqualTo(container1Id);
        // cleaning
        dockerClientInstance.removeContainer(request.getContainerName());
    }

    @Test
    void shouldNotCreateContainerSinceDuplicateIsPresent() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        // create first container
        String container1Id = dockerClientInstance.createContainer(request);
        // create second container with same name (should not replace previous one)
        String container2Id = dockerClientInstance.createContainer(request, false);
        assertThat(container1Id).isNotEmpty();
        assertThat(container2Id).isEmpty();
        // cleaning
        dockerClientInstance.removeContainer(request.getContainerName());
    }
    //endregion

    //region buildHostConfigFromRunRequest
    @Test
    void shouldBuildHostConfigFromRunRequest() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);

        HostConfig hostConfig =
                dockerClientInstance.buildHostConfigFromRunRequest(request);
        assertThat(hostConfig.getNetworkMode())
                .isEqualTo(DOCKER_NETWORK);
        assertThat((hostConfig.getBinds()[0].getPath()))
                .isEqualTo(IexecFileHelper.SLASH_IEXEC_IN);
        assertThat((hostConfig.getBinds()[0].getVolume().getPath()))
                .isEqualTo(IexecFileHelper.SLASH_IEXEC_OUT);
        assertThat(hostConfig.getDevices()).isEmpty();
    }

    @Test
    void shouldBuildHostConfigWithDeviceFromRunRequest() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        request.setDevices(List.of(new Device("", DEVICE_PATH_IN_CONTAINER, DEVICE_PATH_ON_HOST)));

        HostConfig hostConfig =
                dockerClientInstance.buildHostConfigFromRunRequest(request);
        assertThat(hostConfig.getNetworkMode())
                .isEqualTo(DOCKER_NETWORK);
        assertThat((hostConfig.getBinds()[0].getPath()))
                .isEqualTo(IexecFileHelper.SLASH_IEXEC_IN);
        assertThat((hostConfig.getBinds()[0].getVolume().getPath()))
                .isEqualTo(IexecFileHelper.SLASH_IEXEC_OUT);
        assertThat(hostConfig.getDevices()).isNotNull();
        assertThat(hostConfig.getDevices()[0].getPathInContainer())
                .isEqualTo(DEVICE_PATH_IN_CONTAINER);
        assertThat(hostConfig.getDevices()[0].getPathOnHost())
                .isEqualTo(DEVICE_PATH_ON_HOST);
    }

    @Test
    void shouldBuildHostConfigWithSgxDeviceFromRunRequest() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.LEGACY);

        HostConfig hostConfig =
                dockerClientInstance.buildHostConfigFromRunRequest(request);
        assertThat(hostConfig.getNetworkMode())
                .isEqualTo(DOCKER_NETWORK);
        assertThat((hostConfig.getBinds()[0].getPath()))
                .isEqualTo(IexecFileHelper.SLASH_IEXEC_IN);
        assertThat((hostConfig.getBinds()[0].getVolume().getPath()))
                .isEqualTo(IexecFileHelper.SLASH_IEXEC_OUT);
        assertThat(hostConfig.getDevices()[0].getcGroupPermissions())
                .isEqualTo(SgxUtils.SGX_CGROUP_PERMISSIONS);
        assertThat(hostConfig.getDevices()[0].getPathInContainer())
                .isEqualTo("/dev/isgx");
        assertThat(hostConfig.getDevices()[0].getPathOnHost())
                .isEqualTo("/dev/isgx");
    }

    @Test
    void shouldNotBuildHostConfigFromRunRequestSinceNoRequest() {
        HostConfig hostConfig =
                dockerClientInstance.buildHostConfigFromRunRequest(null);
        assertThat(hostConfig).isNull();
    }
    //endregion

    //region createContainerCmd
    @Test
    void shouldBuildCreateContainerCmdFromRunRequest() {
        CreateContainerCmd createContainerCmd = dockerClientInstance.getClient()
                .createContainerCmd("repo/image:tag");
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        request.setCmd("");
        request.setEnv(null);
        request.setContainerPort(0);

        Optional<CreateContainerCmd> oActualCreateContainerCmd =
                dockerClientInstance.buildCreateContainerCmdFromRunRequest(request,
                        createContainerCmd);
        assertThat(oActualCreateContainerCmd).isPresent();
        CreateContainerCmd actualCreateContainerCmd = oActualCreateContainerCmd.get();
        assertThat(actualCreateContainerCmd.getName())
                .isEqualTo(request.getContainerName());
        assertThat(actualCreateContainerCmd.getHostConfig())
                .isEqualTo(dockerClientInstance.buildHostConfigFromRunRequest(request));
        assertThat(actualCreateContainerCmd.getCmd()).isNull();
        assertThat(actualCreateContainerCmd.getEnv()).isNull();
        assertThat(actualCreateContainerCmd.getExposedPorts()).isEmpty();
    }

    @Test
    void shouldBuildCreateContainerCmdFromRunRequestWithFullParams() {
        CreateContainerCmd createContainerCmd = dockerClientInstance.getClient()
                .createContainerCmd("repo/image:tag");
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);

        Optional<CreateContainerCmd> oActualCreateContainerCmd =
                dockerClientInstance.buildCreateContainerCmdFromRunRequest(request,
                        createContainerCmd);
        assertThat(oActualCreateContainerCmd).isPresent();
        CreateContainerCmd actualCreateContainerCmd = oActualCreateContainerCmd.get();
        assertThat(actualCreateContainerCmd.getName())
                .isEqualTo(request.getContainerName());
        assertThat(actualCreateContainerCmd.getHostConfig())
                .isEqualTo(dockerClientInstance.buildHostConfigFromRunRequest(request));
        assertThat(actualCreateContainerCmd.getCmd())
                .isEqualTo(ArgsUtils.stringArgsToArrayArgs(request.getCmd()));
        assertThat(actualCreateContainerCmd.getEnv()).isNotNull();
        assertThat(Arrays.asList(actualCreateContainerCmd.getEnv()))
                .isEqualTo(request.getEnv());
        assertThat(actualCreateContainerCmd.getExposedPorts()).isNotNull();
        assertThat(actualCreateContainerCmd.getExposedPorts()[0].getPort())
                .isEqualTo(1000);
        assertThat(actualCreateContainerCmd.getWorkingDir()).isEqualTo(SLASH_TMP);
    }

    @Test
    void shouldNotBuildCreateContainerCmdFromRunRequestSinceNoRequest() {
        Optional<CreateContainerCmd> actualCreateContainerCmd =
                dockerClientInstance.buildCreateContainerCmdFromRunRequest(
                        getDefaultDockerRunRequest(SgxDriverMode.NONE),
                        null);
        assertThat(actualCreateContainerCmd).isEmpty();
    }

    @Test
    void shouldNotBuildCreateContainerCmdFromRunRequestSinceNoCreateContainerCmd() {
        Optional<CreateContainerCmd> actualCreateContainerCmd =
                dockerClientInstance.buildCreateContainerCmdFromRunRequest(
                        null,
                        dockerClientInstance.getClient()
                                .createContainerCmd("repo/image:tag")
                );
        assertThat(actualCreateContainerCmd).isEmpty();
    }
    //endregion

    //region isContainerPresent
    @Test
    void shouldIsContainerPresentBeTrue() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        dockerClientInstance.createContainer(request);

        boolean isPresent = dockerClientInstance
                .isContainerPresent(request.getContainerName());
        assertThat(isPresent).isTrue();
        // cleaning
        dockerClientInstance.removeContainer(request.getContainerName());
    }
    //endregion

    //region isContainerActive
    @Test
    void shouldIsContainerActiveBeTrue() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        request.setCmd("sh -c 'sleep 10 && echo Hello from Docker alpine!'");
        dockerClientInstance.createContainer(request);
        boolean isStarted = dockerClientInstance.startContainer(request.getContainerName());
        assertThat(isStarted).isTrue();

        boolean isActive = dockerClientInstance
                .isContainerActive(request.getContainerName());
        assertThat(isActive).isTrue();
        // cleaning
        dockerClientInstance.stopAndRemoveContainer(request.getContainerName());
    }

    @Test
    void shouldIsContainerActiveBeFalseSinceContainerIsNotRunning() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        dockerClientInstance.createContainer(request);
        // Container is not running or restarting

        boolean isActive = dockerClientInstance
                .isContainerActive(request.getContainerName());
        assertThat(isActive).isFalse();
        // cleaning
        dockerClientInstance.removeContainer(request.getContainerName());
    }
    //endregion

    //region getContainerName
    @Test
    void shouldGetContainerName() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerId = dockerClientInstance.createContainer(request);

        assertThat(dockerClientInstance.getContainerName(containerId))
                .isEqualTo(request.getContainerName());

        // cleaning
        dockerClientInstance.removeContainer(request.getContainerName());
    }

    @Test
    void shouldNotGetContainerNameSinceEmptyId() {
        assertThat(dockerClientInstance.getContainerName("")).isEmpty();
    }

    @Test
    void shouldNotGetContainerNameSinceNoContainer() {
        assertThat(dockerClientInstance.getContainerName(getRandomString())).isEmpty();
    }

    @Test
    void shouldNotGetContainerNameSinceDockerCmdException() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerId = dockerClientInstance.createContainer(request);
        assertThat(corruptClientInstance.getContainerName(containerId)).isEmpty();
        dockerClientInstance.removeContainer(request.getContainerName());
    }
    //endregion

    //region getContainerId
    @Test
    void shouldGetContainerId() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        pullImageIfNecessary();
        String expectedId = dockerClientInstance.createContainer(request);

        String containerId =
                dockerClientInstance.getContainerId(request.getContainerName());
        assertThat(containerId).isNotEmpty();
        assertThat(containerId).isEqualTo(expectedId);

        // cleaning
        dockerClientInstance.removeContainer(request.getContainerName());
    }

    @Test
    void shouldNotGetContainerIdSinceEmptyId() {
        assertThat(dockerClientInstance.getContainerId("")).isEmpty();
    }

    @Test
    void shouldNotGetContainerIdSinceDockerCmdException() {
        assertThat(corruptClientInstance.getContainerId(getRandomString())).isEmpty();
    }
    //endregion

    //region getContainerStatus
    @Test
    void shouldGetContainerStatus() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        pullImageIfNecessary();
        dockerClientInstance.createContainer(request);

        assertThat(dockerClientInstance.getContainerStatus(request.getContainerName()))
                .isEqualTo(DockerClientInstance.CREATED_STATUS);

        // cleaning
        dockerClientInstance.removeContainer(request.getContainerName());
    }

    @Test
    void shouldNotGetContainerStatusSinceEmptyId() {
        assertThat(dockerClientInstance.getContainerStatus("")).isEmpty();
    }

    @Test
    void shouldNotGetContainerStatusSinceDockerCmdException() {
        assertThat(corruptClientInstance.getContainerStatus(getRandomString())).isEmpty();
    }
    //endregion

    //region startContainer
    @Test
    void shouldStartContainer() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 1 && echo Hello from Docker alpine!'");
        pullImageIfNecessary();
        dockerClientInstance.createContainer(request);

        assertThat(dockerClientInstance.startContainer(containerName)).isTrue();
        assertThat(dockerClientInstance.getContainerStatus(containerName))
                .isEqualTo(DockerClientInstance.RUNNING_STATUS);

        // cleaning
        dockerClientInstance.stopContainer(containerName);
        dockerClientInstance.removeContainer(containerName);
    }

    @Test
    void shouldNotStartContainerNameSinceEmptyId() {
        assertThat(dockerClientInstance.startContainer("")).isFalse();
    }

    @Test
    void shouldNotStartContainerSinceDockerCmdException() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 1 && echo Hello from Docker alpine!'");
        dockerClientInstance.createContainer(request);
        assertThat(corruptClientInstance.startContainer(containerName)).isFalse();

        // cleaning
        dockerClientInstance.stopContainer(containerName);
        dockerClientInstance.removeContainer(containerName);
    }
    //endregion

    //region waitContainerUntilExitOrTimeout
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {""})
    void shouldNotWaitContainerUntilExitOrTimeoutSinceBlankContainerName(String containerName) {
        final String message = assertThrows(IllegalArgumentException.class, () -> dockerClientInstance.waitContainerUntilExitOrTimeout(containerName, null))
                .getMessage();
        assertEquals("Container name cannot be blank", message);
    }

    @Test
    void shouldNotWaitContainerUntilExitOrTimeoutSinceNoTimeout() {
        final String message = assertThrows(IllegalArgumentException.class, () -> dockerClientInstance.waitContainerUntilExitOrTimeout("dummyContainerName", null))
                .getMessage();
        assertEquals("Timeout date cannot be null", message);
    }

    @Test
    void shouldTimeoutAfterWaitContainerUntilExitOrTimeout() throws TimeoutException {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 30 && echo Hello from Docker alpine!'");
        pullImageIfNecessary();
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(containerName);
        assertThat(dockerClientInstance.getContainerStatus(containerName))
                .isEqualTo(DockerClientInstance.RUNNING_STATUS);
        Date before = new Date();

        assertThrows(TimeoutException.class, () -> dockerClientInstance.waitContainerUntilExitOrTimeout(containerName,
                Instant.now().plusSeconds(5)));
        assertThat(dockerClientInstance.getContainerStatus(containerName))
                .isEqualTo(DockerClientInstance.RUNNING_STATUS);
        assertThat(new Date().getTime() - before.getTime()).isGreaterThan(1000);
        // cleaning
        dockerClientInstance.stopContainer(containerName);
        dockerClientInstance.removeContainer(containerName);
    }

    @Test
    void shouldWaitContainerUntilExitOrTimeoutSinceExited() throws TimeoutException {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 1 && echo Hello from Docker alpine!'");
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(containerName);
        assertThat(dockerClientInstance.getContainerStatus(containerName))
                .isEqualTo(DockerClientInstance.RUNNING_STATUS);
        dockerClientInstance.waitContainerUntilExitOrTimeout(containerName,
                Instant.now().plusMillis(3000));
        assertThat(dockerClientInstance.getContainerStatus(containerName))
                .isEqualTo(DockerClientInstance.EXITED_STATUS);

        // cleaning
        dockerClientInstance.stopContainer(containerName);
        dockerClientInstance.removeContainer(containerName);
    }
    //endregion

    //region getContainerExitCode
    @Test
    void shouldGetContainerExitCode() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        dockerClientInstance.createContainer(request);
        int exitCode = dockerClientInstance
                .getContainerExitCode(request.getContainerName());
        assertThat(exitCode).isEqualTo(0);
        dockerClientInstance.removeContainer(request.getContainerName());
    }

    @Test
    void shouldNotGetContainerExitCodeSinceDockerCmdException() {
        assertThat(corruptClientInstance.getContainerExitCode(getRandomString()))
                .isEqualTo(-1);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {""})
    void shouldNotGetContainerExitCodeSinceBlankContainerName(String containerName) {
        final String message = assertThrows(IllegalArgumentException.class, () -> dockerClientInstance.getContainerExitCode(containerName))
                .getMessage();
        assertEquals("Container name cannot be blank", message);
    }
    //endregion

    //region stopContainer
    @Test
    void shouldStopContainer() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 10 && echo Hello from Docker alpine!'");
        pullImageIfNecessary();
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(containerName);
        assertThat(dockerClientInstance.getContainerStatus(containerName))
                .isEqualTo(DockerClientInstance.RUNNING_STATUS);

        boolean isStopped = dockerClientInstance.stopContainer(containerName);
        assertThat(isStopped).isTrue();
        assertThat(dockerClientInstance.getContainerStatus(containerName))
                .isEqualTo(DockerClientInstance.EXITED_STATUS);
        verify(dockerClientInstance, atLeastOnce()).isContainerPresent(containerName);
        verify(dockerClientInstance).isContainerActive(containerName);
        // cleaning
        dockerClientInstance.removeContainer(containerName);
    }

    @Test
    void shouldReturnTrueWhenContainerIsNotActive() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 10 && echo Hello from Docker alpine!'");
        pullImageIfNecessary();
        dockerClientInstance.createContainer(request);
        assertThat(dockerClientInstance.getContainerStatus(containerName))
                .isEqualTo(DockerClientInstance.CREATED_STATUS);
        // Use spied client to verify method calls
        when(dockerClientInstance.getClient()).thenReturn(spiedClient);

        boolean isStopped = dockerClientInstance.stopContainer(containerName);
        assertThat(isStopped).isTrue();
        verify(dockerClientInstance, atLeastOnce()).isContainerPresent(containerName);
        verify(dockerClientInstance).isContainerActive(containerName);
        verify(spiedClient, never()).stopContainerCmd(anyString());
        // cleaning
        dockerClientInstance.removeContainer(containerName);
    }

    @Test
    void shouldNotStopContainerSinceEmptyId() {
        assertThat(dockerClientInstance.stopContainer("")).isFalse();
    }

    @Test
    void shouldNotStopContainerSinceNotFound() {
        String containerName = "not-found";
        boolean isStopped = dockerClientInstance.stopContainer(containerName);
        assertThat(isStopped).isFalse();
        verify(dockerClientInstance, atLeastOnce()).isContainerPresent(containerName);
        verify(dockerClientInstance, never()).isContainerActive(containerName);
    }

    @Test
    void shouldNotStopContainerSinceDockerCmdException() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 10 && echo Hello from Docker alpine!'");
        pullImageIfNecessary();
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(containerName);
        when(dockerClientInstance.getClient())
                .thenCallRealMethod()        // isContainerPresent
                .thenCallRealMethod()        // getContainerStatus
                .thenReturn(corruptedClient) // stopContainerCmd
                .thenCallRealMethod();       // stopAndRemoveContainer
        assertThat(dockerClientInstance.stopContainer(containerName)).isFalse();
        // clean
        dockerClientInstance.stopAndRemoveContainer(containerName);
    }
    //endregion

    //region removeContainer
    @Test
    void shouldRemoveContainer() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 10 && echo Hello from Docker alpine!'");
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(containerName);
        dockerClientInstance.stopContainer(containerName);

        assertThat(dockerClientInstance.removeContainer(containerName)).isTrue();
    }

    @Test
    void shouldNotRemoveContainerSinceEmptyId() {
        assertThat(dockerClientInstance.removeContainer("")).isFalse();
    }

    @Test
    void shouldNotRemoveContainerSinceRunning() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 5 && echo Hello from Docker alpine!'");
        pullImageIfNecessary();
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(containerName);

        assertThat(dockerClientInstance.getContainerStatus(containerName))
                .isEqualTo(DockerClientInstance.RUNNING_STATUS);
        assertThat(dockerClientInstance.removeContainer(containerName)).isFalse();
        // cleaning
        dockerClientInstance.stopAndRemoveContainer(containerName);
    }

    @Test
    void shouldNotRemoveContainerSinceDockerCmdException() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 5 && echo Hello from Docker alpine!'");
        pullImageIfNecessary();
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(containerName);
        when(dockerClientInstance.getClient())
                .thenCallRealMethod()        // isContainerPresent
                .thenReturn(corruptedClient) // removeContainerCmd
                .thenCallRealMethod();       // stopAndRemoveContainer

        assertThat(dockerClientInstance.removeContainer(containerName)).isFalse();
        // clean
        dockerClientInstance.stopAndRemoveContainer(containerName);
    }
    //endregion

    // tools

    @Override
    String getRandomString() {
        String random = RandomStringUtils.randomAlphanumeric(20);
        usedRandomNames.add(random);
        return random;
    }

    private DockerClient getCorruptedDockerClient() {
        DockerClientConfig config =
                DefaultDockerClientConfig.createDefaultConfigBuilder()
                        .withDockerHost("tcp://localhost:11111")
                        .build();
        DockerHttpClient httpClient = new ZerodepDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .build();
        return DockerClientImpl.getInstance(config, httpClient);
    }

    private void pullImageIfNecessary() {
        if (dockerClientInstance.getImageId(ALPINE_LATEST).isEmpty()){
            dockerClientInstance.pullImage(ALPINE_LATEST);
        }
    }
}
