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
import com.github.dockerjava.api.exception.DockerException;
import com.github.dockerjava.api.model.Device;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.transport.DockerHttpClient;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;
import com.iexec.common.docker.DockerLogs;
import com.iexec.common.docker.DockerRunFinalStatus;
import com.iexec.common.docker.DockerRunRequest;
import com.iexec.common.docker.DockerRunResponse;
import com.iexec.common.sgx.SgxDriverMode;
import com.iexec.common.utils.ArgsUtils;
import com.iexec.common.utils.IexecFileHelper;
import com.iexec.common.utils.SgxUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Tag("slow")
class DockerClientInstanceTests {

    private static final String CHAIN_TASK_ID = "chainTaskId";
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
    private static final String ALPINE_LATEST = "alpine:latest";
    private static final String ALPINE_BLABLA = "alpine:blabla";
    private static final String BLABLA_LATEST = "blabla:latest";
    private static final String CMD = "cmd";
    private static final List<String> ENV = List.of("FOO=bar");
    private static final String DOCKERHUB_USERNAME_ENV_NAME = "DOCKER_IO_USER";
    private static final String DOCKERHUB_PASSWORD_ENV_NAME = "DOCKER_IO_PASSWORD";
    private static final String PRIVATE_IMAGE_NAME = "iexechub/private-image:alpine-3.13";
    private static final String DOCKER_NETWORK = "dockerTestsNetwork";
    private static final String DEVICE_PATH_IN_CONTAINER = "/dev/some-device-in-container";
    private static final String DEVICE_PATH_ON_HOST = "/dev/some-device-on-host";
    private static final String SLASH_TMP = "/tmp";

    private static final List<String> usedRandomNames = new ArrayList<>();
    private static final List<String> usedImages = List.of(
            DOCKER_IO_CLASSIC_IMAGE, SHORT_CLASSIC_IMAGE, DOCKER_IO_LIBRARY_IMAGE,
            SHORT_LIBRARY_IMAGE, VERY_SHORT_LIBRARY_IMAGE, DOCKER_COM_CLASSIC_IMAGE,
            ALPINE_LATEST);


    @Spy
    private DockerClientInstance dockerClientInstance = new DockerClientInstance();

    @Spy
    private DockerClient spiedClient = dockerClientInstance.getClient();

    private DockerClient corruptedClient = getCorruptedDockerClient();

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    static void beforeAll() {
        usedImages.forEach(imageName -> new DockerClientInstance().pullImage(imageName));
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Cleaning after all tests");
        DockerClientInstance instance = new DockerClientInstance();
        // clean containers
        usedRandomNames.forEach(instance::stopAndRemoveContainer);
        // clean networks
        usedRandomNames.forEach(instance::removeNetwork);
        instance.removeNetwork(DOCKER_NETWORK);
        // clean docker images
        usedImages.forEach(instance::removeImage);
    }

    DockerRunRequest getDefaultDockerRunRequest(SgxDriverMode sgxDriverMode) {
        return DockerRunRequest.builder()
                .containerName(getRandomString())
                .chainTaskId(CHAIN_TASK_ID)
                .imageUri(ALPINE_LATEST)
                .cmd(CMD)
                .env(ENV)
                .sgxDriverMode(sgxDriverMode)
                .containerPort(1000)
                .binds(Collections.singletonList(IexecFileHelper.SLASH_IEXEC_IN +
                        ":" + IexecFileHelper.SLASH_IEXEC_OUT))
                .maxExecutionTime(500000)
                .dockerNetwork(DOCKER_NETWORK)
                .workingDir(SLASH_TMP)
                .build();
    }

    /**
     * new instance
     */

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

    /**
     * This test is temporarily disabled because of this error:
     * toomanyrequests: too many failed login attempts for
     * username or IP address.
     */
    @Test
    @Disabled("toomanyrequests: too many failed login attempts for username or IP address")
    void shouldFailToAuthenticateClientToRegistry() {
        DockerException e = assertThrows(DockerException.class, () -> DockerClientFactory
                .getDockerClientInstance(DockerClientInstance.DEFAULT_DOCKER_REGISTRY, "badUsername", "badPassword"));
        assertThat(e.getHttpStatus()).isEqualTo(401);
    }

    /**
     * docker volume
     */

    // createVolume

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
        useCorruptedDockerClient();
        assertThat(dockerClientInstance.createVolume(getRandomString())).isFalse();
    }

    // isVolumePresent

    @Test
    void shouldFindVolumePresent() {
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
        useCorruptedDockerClient();
        assertThat(dockerClientInstance.isVolumePresent(getRandomString())).isFalse();
    }

    // removeVolume

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
    void shouldNotRemoveVolumeSinceDockerCmdException() {
        String volumeName = getRandomString();
        dockerClientInstance.createVolume(volumeName);
        assertThat(dockerClientInstance.isVolumePresent(volumeName)).isTrue();
        
        when(dockerClientInstance.getClient())
                .thenCallRealMethod() // isVolumePresent returns true
                .thenReturn(corruptedClient);

        assertThat(dockerClientInstance.removeVolume(volumeName)).isFalse();

        useRealDockerClient();     
        dockerClientInstance.removeVolume(volumeName);
    }

    /**
     * docker network
     */

    // createNetwork

    @Test
    void shouldCreateNetwork() {
        String networkName = getRandomString();
        assertThat(dockerClientInstance.createNetwork(networkName)).isNotEmpty();
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
        String networkId = dockerClientInstance.createNetwork(networkName);
        assertThat(dockerClientInstance.createNetwork(networkName)).isEqualTo(networkId);
        // cleaning
        dockerClientInstance.removeNetwork(networkName);
    }

    @Test
    void shouldNotCreateNetworkSinceDockerCmdException() {
        useCorruptedDockerClient();
        assertThat(dockerClientInstance.createNetwork(getRandomString())).isEmpty();
    }

    // getNetworkId

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
        useCorruptedDockerClient();
        assertThat(dockerClientInstance.getNetworkId(getRandomString())).isEmpty();
    }

    // isNetworkPresent

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
        useCorruptedDockerClient();
        assertThat(dockerClientInstance.isNetworkPresent(getRandomString())).isFalse();
        
    }

    // removeNetwork

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
    void shouldNotRemoveNetworkSinceDockerCmdException() {
        String networkName = getRandomString();
        dockerClientInstance.createNetwork(networkName);
        assertThat(dockerClientInstance.isNetworkPresent(networkName)).isTrue();

        when(dockerClientInstance.getClient())
                .thenCallRealMethod() // isVolumePresent returns true
                .thenReturn(corruptedClient);

        assertThat(dockerClientInstance.removeNetwork(networkName)).isFalse();
        
        useRealDockerClient();
        dockerClientInstance.removeNetwork(networkName);
    }

    /**
     * docker image
     */

    // isImagePresent

    @Test
    void shouldFindImagePresent() {
        dockerClientInstance.pullImage(ALPINE_LATEST);
        assertThat(dockerClientInstance.isImagePresent(ALPINE_LATEST)).isTrue();
        dockerClientInstance.removeImage(ALPINE_LATEST);
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
        useCorruptedDockerClient();
        assertThat(dockerClientInstance.isImagePresent("")).isFalse();
    }

    // pull image

    @Test
    void shouldPullImage() {
        assertThat(dockerClientInstance.pullImage(ALPINE_LATEST)).isTrue();
        assertThat(dockerClientInstance.isImagePresent(ALPINE_LATEST)).isTrue();
        dockerClientInstance.removeImage(ALPINE_LATEST);
    }

    @Test
    void shouldPullImageWithExplicitTimeout() {
        assertThat(dockerClientInstance.pullImage(ALPINE_LATEST, Duration.of(3, ChronoUnit.MINUTES))).isTrue();
        assertThat(dockerClientInstance.isImagePresent(ALPINE_LATEST)).isTrue();
        dockerClientInstance.removeImage(ALPINE_LATEST);
    }

    @Test
    void shouldNotPullImageSinceTimeout() {
        dockerClientInstance.removeImage(ALPINE_LATEST);
        assertThat(dockerClientInstance.pullImage(ALPINE_LATEST, Duration.of(1, ChronoUnit.SECONDS))).isFalse();
        assertThat(dockerClientInstance.isImagePresent(ALPINE_LATEST)).isFalse();
        dockerClientInstance.removeImage(ALPINE_LATEST);
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
        useCorruptedDockerClient();
        assertThat(dockerClientInstance.pullImage(getRandomString())).isFalse();
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

    private String getEnvValue(String envVarName) {
        return System.getenv(envVarName) != null ?
                //Intellij envvar injection
                System.getenv(envVarName) :
                //gradle test -DdockerhubPassword=xxx
                System.getProperty(envVarName);
    }

    // getImageId

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
        useCorruptedDockerClient();
        assertThat(dockerClientInstance.getImageId(getRandomString())).isEmpty();
    }

    // sanitizeImageName

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

    // Remove image

    @Test
    void shouldRemoveImage() {
        dockerClientInstance.pullImage(DOCKER_IO_CLASSIC_IMAGE);
        assertThat(dockerClientInstance.removeImage(DOCKER_IO_CLASSIC_IMAGE)).isTrue();
    }

    @Test
    void shouldRemoveImageByIdSinceEmptyName() {
        assertThat(dockerClientInstance.removeImage("")).isFalse();
    }

    @Test
    void shouldNotRemoveImageByIdSinceDockerCmdException() {
        dockerClientInstance.pullImage(ALPINE_LATEST);
        dockerClientInstance.getImageId(ALPINE_LATEST);

        useCorruptedDockerClient();
        assertThat(dockerClientInstance.removeImage(ALPINE_LATEST)).isFalse();

        // cleaning
        dockerClientInstance.removeImage(ALPINE_LATEST);
    }

    /**
     * docker container
     */

    // docker run

    @Test
    void shouldRunSuccessfullyAndWaitForContainerToFinish() throws TimeoutException {
        DockerRunRequest dockerRunRequest = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        dockerRunRequest.setMaxExecutionTime(5000); // 5s
        String msg = "Hello world!";
        dockerRunRequest.setCmd("sh -c 'sleep 2 && echo " + msg + "'");
        String containerName = dockerRunRequest.getContainerName();

        DockerRunResponse dockerRunResponse =
                dockerClientInstance.run(dockerRunRequest);

        assertThat(dockerRunResponse).isNotNull();
        assertThat(dockerRunResponse.getFinalStatus()).isEqualTo(DockerRunFinalStatus.SUCCESS);
        assertThat(dockerRunResponse.getContainerExitCode()).isZero();
        assertThat(dockerRunResponse.getStdout().trim()).isEqualTo(msg);
        assertThat(dockerRunResponse.getStderr()).isEmpty();
        verify(dockerClientInstance).createContainer(dockerRunRequest);
        verify(dockerClientInstance).startContainer(containerName);
        verify(dockerClientInstance)
                .waitContainerUntilExitOrTimeout(eq(containerName), any());
        verify(dockerClientInstance, never()).stopContainer(containerName);
        verify(dockerClientInstance).getContainerLogs(containerName);
        verify(dockerClientInstance).removeContainer(containerName);
    }

    @Test
    void shouldRunSuccessfullyAndNotWaitForTimeout() throws TimeoutException {
        DockerRunRequest dockerRunRequest = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        dockerRunRequest.setMaxExecutionTime(0); // detached mode // can be -1
        dockerRunRequest.setCmd("sh -c 'sleep 30'");
        String containerName = dockerRunRequest.getContainerName();

        DockerRunResponse dockerRunResponse =
                dockerClientInstance.run(dockerRunRequest);

        assertThat(dockerRunResponse).isNotNull();
        assertThat(dockerRunResponse.getFinalStatus()).isEqualTo(DockerRunFinalStatus.SUCCESS);
        assertThat(dockerRunResponse.getContainerExitCode()).isEqualTo(-1);
        assertThat(dockerRunResponse.getStdout()).isEmpty();
        assertThat(dockerRunResponse.getStderr()).isEmpty();
        verify(dockerClientInstance).createContainer(dockerRunRequest);
        verify(dockerClientInstance).startContainer(containerName);
        verify(dockerClientInstance, never())
                .waitContainerUntilExitOrTimeout(eq(containerName), any());
        verify(dockerClientInstance, never()).stopContainer(containerName);
        verify(dockerClientInstance, never()).getContainerLogs(containerName);
        verify(dockerClientInstance, never()).removeContainer(containerName);
        // clean
        dockerClientInstance.stopAndRemoveContainer(containerName);
    }

    @Test
    void shouldRunAndReturnFailureInStderrSinceBadCmd() throws TimeoutException {
        DockerRunRequest dockerRunRequest = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        dockerRunRequest.setMaxExecutionTime(5000); // 5s
        dockerRunRequest.setCmd("sh -c 'someBadCmd'");
        String containerName = dockerRunRequest.getContainerName();

        DockerRunResponse dockerRunResponse =
                dockerClientInstance.run(dockerRunRequest);

        System.out.println(dockerRunResponse);
        assertThat(dockerRunResponse).isNotNull();
        assertThat(dockerRunResponse.getFinalStatus()).isEqualTo(DockerRunFinalStatus.FAILED);
        assertThat(dockerRunResponse.getContainerExitCode()).isNotZero();
        assertThat(dockerRunResponse.getStdout()).isEmpty();
        assertThat(dockerRunResponse.getStderr()).isNotEmpty();
        verify(dockerClientInstance).createContainer(dockerRunRequest);
        verify(dockerClientInstance).startContainer(containerName);
        verify(dockerClientInstance)
                .waitContainerUntilExitOrTimeout(eq(containerName), any());
        verify(dockerClientInstance, never()).stopContainer(containerName);
        verify(dockerClientInstance).getContainerLogs(containerName);
        verify(dockerClientInstance).removeContainer(containerName);
    }

    @Test
    void shouldRunAndReturnFailureAndLogsSinceTimeout() throws TimeoutException {
        DockerRunRequest dockerRunRequest = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        dockerRunRequest.setMaxExecutionTime(5000); // 5s
        String msg1 = "First message";
        String msg2 = "Second message";
        String cmd = String.format("sh -c 'echo %s && sleep 10 && echo %s'", msg1, msg2);
        dockerRunRequest.setCmd(cmd);
        String containerName = dockerRunRequest.getContainerName();

        DockerRunResponse dockerRunResponse =
                dockerClientInstance.run(dockerRunRequest);

        System.out.println(dockerRunResponse);
        assertThat(dockerRunResponse).isNotNull();
        assertThat(dockerRunResponse.getFinalStatus()).isEqualTo(DockerRunFinalStatus.TIMEOUT);
        assertThat(dockerRunResponse.getContainerExitCode()).isEqualTo(-1);
        assertThat(dockerRunResponse.getStdout().trim()).isEqualTo(msg1);
        assertThat(dockerRunResponse.getStderr()).isEmpty();
        verify(dockerClientInstance).createContainer(dockerRunRequest);
        verify(dockerClientInstance).startContainer(containerName);
        verify(dockerClientInstance)
                .waitContainerUntilExitOrTimeout(eq(containerName), any());
        verify(dockerClientInstance).stopContainer(containerName);
        verify(dockerClientInstance).getContainerLogs(containerName);
        verify(dockerClientInstance).removeContainer(containerName);
    }

    @Test
    void shouldReturnFailureSinceCantCreateContainer() throws TimeoutException {
        DockerRunRequest dockerRunRequest = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        dockerRunRequest.setMaxExecutionTime(5000); // 5s
        String msg = "Hello world!";
        dockerRunRequest.setCmd("sh -c 'sleep 2 && echo " + msg + "'");
        String containerName = dockerRunRequest.getContainerName();
        doReturn("").when(dockerClientInstance).createContainer(dockerRunRequest);

        DockerRunResponse dockerRunResponse =
                dockerClientInstance.run(dockerRunRequest);

        System.out.println(dockerRunResponse);
        assertThat(dockerRunResponse).isNotNull();
        assertThat(dockerRunResponse.getFinalStatus()).isEqualTo(DockerRunFinalStatus.FAILED);
        assertThat(dockerRunResponse.getContainerExitCode()).isEqualTo(-1);
        assertThat(dockerRunResponse.getStdout()).isEmpty();
        assertThat(dockerRunResponse.getStderr()).isEmpty();
        verify(dockerClientInstance).createContainer(dockerRunRequest);
        verify(dockerClientInstance, never()).startContainer(containerName);
        verify(dockerClientInstance, never())
                .waitContainerUntilExitOrTimeout(eq(containerName), any());
        verify(dockerClientInstance, never()).stopContainer(containerName);
        verify(dockerClientInstance, never()).getContainerLogs(containerName);
        verify(dockerClientInstance, never()).removeContainer(containerName);
    }

    @Test
    void shouldReturnFailureSinceCantStartContainer() throws TimeoutException {
        DockerRunRequest dockerRunRequest = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        dockerRunRequest.setMaxExecutionTime(5000); // 5s
        String msg = "Hello world!";
        dockerRunRequest.setCmd("sh -c 'sleep 2 && echo " + msg + "'");
        String containerName = dockerRunRequest.getContainerName();
        doReturn(false).when(dockerClientInstance).startContainer(containerName);

        DockerRunResponse dockerRunResponse =
                dockerClientInstance.run(dockerRunRequest);

        System.out.println(dockerRunResponse);
        assertThat(dockerRunResponse).isNotNull();
        assertThat(dockerRunResponse.getFinalStatus()).isEqualTo(DockerRunFinalStatus.FAILED);
        assertThat(dockerRunResponse.getContainerExitCode()).isEqualTo(-1);
        assertThat(dockerRunResponse.getStdout()).isEmpty();
        assertThat(dockerRunResponse.getStderr()).isEmpty();
        verify(dockerClientInstance).createContainer(dockerRunRequest);
        verify(dockerClientInstance).startContainer(containerName);
        verify(dockerClientInstance, never())
                .waitContainerUntilExitOrTimeout(eq(containerName), any());
        verify(dockerClientInstance, never()).stopContainer(containerName);
        verify(dockerClientInstance, never()).getContainerLogs(containerName);
        verify(dockerClientInstance).removeContainer(containerName);
    }

    @Test
    void shouldReturnFailureSinceCantStopContainer() throws TimeoutException {
        DockerRunRequest dockerRunRequest = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        dockerRunRequest.setMaxExecutionTime(5000); // 5s
        String msg = "Hello world!";
        dockerRunRequest.setCmd("sh -c 'sleep 10 && echo " + msg + "'");
        String containerName = dockerRunRequest.getContainerName();
        doReturn(false).when(dockerClientInstance).stopContainer(containerName);

        DockerRunResponse dockerRunResponse =
                dockerClientInstance.run(dockerRunRequest);

        System.out.println(dockerRunResponse);
        assertThat(dockerRunResponse).isNotNull();
        assertThat(dockerRunResponse.getFinalStatus()).isEqualTo(DockerRunFinalStatus.TIMEOUT);
        assertThat(dockerRunResponse.getContainerExitCode()).isEqualTo(-1);
        assertThat(dockerRunResponse.getStdout()).isEmpty();
        assertThat(dockerRunResponse.getStderr()).isEmpty();
        verify(dockerClientInstance).createContainer(dockerRunRequest);
        verify(dockerClientInstance).startContainer(containerName);
        verify(dockerClientInstance)
                .waitContainerUntilExitOrTimeout(eq(containerName), any());
        verify(dockerClientInstance).stopContainer(containerName);
        verify(dockerClientInstance).getContainerLogs(containerName);
        verify(dockerClientInstance, never()).removeContainer(containerName);
        // clean
        dockerClientInstance.stopAndRemoveContainer(containerName);
    }

    @Test
    void shouldReturnSuccessButLogsSinceCantRemoveContainer() throws TimeoutException {
        DockerRunRequest dockerRunRequest = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        dockerRunRequest.setMaxExecutionTime(5000); // 5s
        String msg = "Hello world!";
        dockerRunRequest.setCmd("sh -c 'sleep 2 && echo " + msg + "'");
        String containerName = dockerRunRequest.getContainerName();
        doReturn(false).when(dockerClientInstance).removeContainer(containerName);

        DockerRunResponse dockerRunResponse =
                dockerClientInstance.run(dockerRunRequest);

        System.out.println(dockerRunResponse);
        assertThat(dockerRunResponse).isNotNull();
        assertThat(dockerRunResponse.getFinalStatus()).isEqualTo(DockerRunFinalStatus.SUCCESS);
        assertThat(dockerRunResponse.getContainerExitCode()).isZero();
        assertThat(dockerRunResponse.getStdout().trim()).isEqualTo(msg);
        assertThat(dockerRunResponse.getStderr()).isEmpty();
        verify(dockerClientInstance).createContainer(dockerRunRequest);
        verify(dockerClientInstance).startContainer(containerName);
        verify(dockerClientInstance)
                .waitContainerUntilExitOrTimeout(eq(containerName), any());
        verify(dockerClientInstance, never()).stopContainer(containerName);
        verify(dockerClientInstance).getContainerLogs(containerName);
        verify(dockerClientInstance).removeContainer(containerName);
        // clean
        dockerClientInstance.stopAndRemoveContainer(containerName);
    }

    // createContainer

    @Test
    void shouldCreateContainer() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        dockerClientInstance.pullImage(request.getImageUri());
        String containerId = dockerClientInstance.createContainer(request);
        assertThat(containerId).isNotEmpty();
        // cleaning
        dockerClientInstance.removeContainer(request.getContainerName());
        dockerClientInstance.removeImage(request.getImageUri());
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
        dockerClientInstance.pullImage(request.getImageUri());
        // create first container
        String container1Id = dockerClientInstance.createContainer(request);
        // create second container with same name (should replace previous one)
        String container2Id = dockerClientInstance.createContainer(request);
        assertThat(container2Id).isNotEmpty();
        assertThat(container2Id).isNotEqualTo(container1Id);
        // cleaning
        dockerClientInstance.removeContainer(container2Id);
        dockerClientInstance.removeImage(request.getImageUri());
    }

    @Test
    void shouldNotCreateContainerSinceDuplicateIsPresent() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        dockerClientInstance.pullImage(request.getImageUri());
        // create first container
        String container1Id = dockerClientInstance.createContainer(request);
        // create second container with same name (should not replace previous one)
        String container2Id = dockerClientInstance.createContainer(request, false);
        assertThat(container1Id).isNotEmpty();
        assertThat(container2Id).isEmpty();
        // cleaning
        dockerClientInstance.removeContainer(container1Id);
        dockerClientInstance.removeImage(request.getImageUri());
    }

    // buildHostConfigFromRunRequest

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

    //#region isContainerPresent()

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

    //#endregion

    //#region isContainerActive()

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

    //#endregion

    // getContainerName

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

        useCorruptedDockerClient();
        assertThat(dockerClientInstance.getContainerName(containerId)).isEmpty();

        // cleaning
        useRealDockerClient();
        dockerClientInstance.removeContainer(request.getContainerName());
    }

    // getContainerId

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
        useCorruptedDockerClient();
        assertThat(dockerClientInstance.getContainerId(getRandomString())).isEmpty();
    }

    // getContainerStatus

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
        useCorruptedDockerClient();
        assertThat(dockerClientInstance.getContainerStatus(getRandomString())).isEmpty();
    }

    // start container

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

        useCorruptedDockerClient();
        assertThat(dockerClientInstance.startContainer(containerName)).isFalse();

        // cleaning
        useRealDockerClient();
        dockerClientInstance.stopContainer(containerName);
        dockerClientInstance.removeContainer(containerName);
    }

    // waitContainerUntilExitOrTimeout

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

    // getContainerExitCode

    @Test
    void shouldGetContainerExitCode() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        dockerClientInstance.createContainer(request);
        int exitCode = dockerClientInstance
                .getContainerExitCode(request.getContainerName());
        assertThat(exitCode).isEqualTo(0);
    }

    @Test
    void shouldNotGetContainerExitCodeSinceDockerCmdException() {
        useCorruptedDockerClient();
        assertThat(dockerClientInstance.getContainerExitCode(getRandomString()))
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

    // getContainerLogs

    @Test
    void shouldGetContainerLogsSinceStdout() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        request.setCmd("sh -c 'echo Hello from Docker alpine!'");
        pullImageIfNecessary();
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(request.getContainerName());

        Optional<DockerLogs> containerLogs =
                dockerClientInstance.getContainerLogs(request.getContainerName());
        assertThat(containerLogs).isPresent();
        assertThat(containerLogs.get().getStdout()).contains("Hello from " +
                "Docker alpine!");
        assertThat(containerLogs.get().getStderr()).isEmpty();

        // cleaning
        dockerClientInstance.stopContainer(request.getContainerName());
        dockerClientInstance.removeContainer(request.getContainerName());
    }

    @Test
    void shouldGetContainerLogsSinceStderr() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        request.setCmd("sh -c 'echo Hello from Docker alpine! >&2'");
        pullImageIfNecessary();
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(request.getContainerName());

        Optional<DockerLogs> containerLogs =
                dockerClientInstance.getContainerLogs(request.getContainerName());
        assertThat(containerLogs).isPresent();
        assertThat(containerLogs.get().getStdout()).isEmpty();
        assertThat(containerLogs.get().getStderr()).contains("Hello from " +
                "Docker alpine!");

        // cleaning
        dockerClientInstance.stopContainer(request.getContainerName());
        dockerClientInstance.removeContainer(request.getContainerName());
    }

    @Test
    void shouldNotGetContainerLogsSinceEmptyId() {
        assertThat(dockerClientInstance.getContainerLogs("")).isEmpty();
    }

    @Test
    void shouldNotGetContainerLogsSinceDockerCmdException() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        dockerClientInstance.createContainer(request);
        when(dockerClientInstance.getClient())
                .thenCallRealMethod() // isContainerPresent
                .thenReturn(corruptedClient);
        assertThat(dockerClientInstance.getContainerLogs(request.getContainerName()))
                .isEmpty();
    }

    // stopContainer

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
                .thenCallRealMethod() // isContainerPresent
                .thenCallRealMethod() // getContainerStatus
                .thenReturn(corruptedClient);
        assertThat(dockerClientInstance.stopContainer(containerName)).isFalse();
        // clean
        dockerClientInstance.stopAndRemoveContainer(containerName);
    }

    // removeContainer

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
                .thenCallRealMethod() // isContainerPresent
                .thenReturn(corruptedClient);

        assertThat(dockerClientInstance.removeContainer(containerName)).isFalse();
        // clean
        dockerClientInstance.stopAndRemoveContainer(containerName);
    }

    // exec

    @Test
    void shouldExecuteCommandInContainer() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 10'");
        String msg = "Hello from Docker alpine!";
        String cmd = "echo " + msg;
        pullImageIfNecessary();
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(containerName);

        Optional<DockerLogs> logs = 
                dockerClientInstance.exec(containerName, "sh", "-c", cmd);
        assertThat(logs.isPresent()).isTrue();
        assertThat(logs.get().getStdout().trim()).isEqualTo(msg);
        // clean
        dockerClientInstance.stopAndRemoveContainer(containerName);
    }

    @Test
    void shouldNotExecuteCommandSinceContainerNotFound() {
        // no container created
        Optional<DockerLogs> logs = 
                dockerClientInstance.exec(getRandomString(), "sh", "-c", "ls");
        assertThat(logs).isEmpty();
    }

    @Test
    void shouldNotExecuteCommandSinceDockerException() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 10'");
        String msg = "Hello from Docker alpine!";
        String cmd = "echo " + msg;
        pullImageIfNecessary();
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(containerName);
        when(dockerClientInstance.getClient())
                .thenCallRealMethod() // isContainerPresent
                .thenCallRealMethod() // create command
                .thenReturn(corruptedClient);

        Optional<DockerLogs> logs = 
                dockerClientInstance.exec(containerName, "sh", "-c", cmd);
        assertThat(logs).isEmpty();
        // clean
        dockerClientInstance.stopAndRemoveContainer(containerName);
    }

    // tools

    private String getRandomString() {
        String random = RandomStringUtils.randomAlphanumeric(20);
        usedRandomNames.add(random);
        return random;
    }

    private void useRealDockerClient() {
        when(dockerClientInstance.getClient()).thenCallRealMethod();
    }

    private void useCorruptedDockerClient() {
        when(dockerClientInstance.getClient()).thenReturn(corruptedClient);
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
