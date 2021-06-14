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
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.iexec.common.docker.DockerLogs;
import com.iexec.common.docker.DockerRunRequest;
import com.iexec.common.docker.DockerRunResponse;
import com.iexec.common.utils.ArgsUtils;
import com.iexec.common.utils.IexecFileHelper;
import com.iexec.common.utils.SgxUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("slow")
public class DockerClientInstanceTests {

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
    private final static String DOCKERHUB_USERNAME_ENV_NAME = "dockerhubUsername";
    private final static String DOCKERHUB_PASSWORD_ENV_NAME = "dockerhubPassword";
    private final static String PRIVATE_IMAGE_NAME =
            "sconecuratedimages/iexec:runtime-scone-3.0.0-production";
    private final static String DOCKER_NETWORK = "dockerTestsNetwork";
    private static final String DEVICE_PATH_IN_CONTAINER = "/dev/some-device-in-container";
    private static final String DEVICE_PATH_ON_HOST = "/dev/some-device-on-host";
    private static final String SLASH_TMP = "/tmp";

    private static List<String> usedRandomNames = new ArrayList<>();
    private static List<String> usedImages = List.of(
            DOCKER_IO_CLASSIC_IMAGE, SHORT_CLASSIC_IMAGE, DOCKER_IO_LIBRARY_IMAGE,
            SHORT_LIBRARY_IMAGE, VERY_SHORT_LIBRARY_IMAGE, DOCKER_COM_CLASSIC_IMAGE,
            ALPINE_LATEST);


    @Spy
    private DockerClientInstance dockerClientInstance = DockerClientFactory.getDockerClientInstance();

    @Spy
    private DockerClient realClient = dockerClientInstance.getClient();

    private DockerClient corruptedClient = getCorruptedDockerClient();

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    public static void beforeAll() {
        usedImages.forEach(imageName ->
                DockerClientFactory.getDockerClientInstance().pullImage(imageName));
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("Cleaning after all tests");
        // clean containers
        usedRandomNames.forEach(name -> 
                DockerClientFactory.getDockerClientInstance().stopAndRemoveContainer(name));
        // clean networks
        usedRandomNames.forEach(name -> 
                DockerClientFactory.getDockerClientInstance().removeNetwork(name));
        DockerClientFactory.getDockerClientInstance().removeNetwork(DOCKER_NETWORK);
        // clean docker images
        usedImages.forEach(imageName ->
                DockerClientFactory.getDockerClientInstance().removeImage(imageName));
    }

    public DockerRunRequest getDefaultDockerRunRequest(boolean isSgx) {
        return DockerRunRequest.builder()
                .containerName(getRandomString())
                .chainTaskId(CHAIN_TASK_ID)
                .imageUri(ALPINE_LATEST)
                .cmd(CMD)
                .env(ENV)
                .isSgx(isSgx)
                .containerPort(1000)
                .binds(Collections.singletonList(IexecFileHelper.SLASH_IEXEC_IN +
                        ":" + IexecFileHelper.SLASH_IEXEC_OUT))
                .maxExecutionTime(500000)
                .dockerNetwork(DOCKER_NETWORK)
                .workingDir(SLASH_TMP)
                .build();
    }

    /**
     * docker volume
     */

    // createVolume

    @Test
    public void shouldCreateVolume() {
        String volumeName = getRandomString();
        assertThat(dockerClientInstance.isVolumePresent(volumeName)).isFalse();
        assertThat(dockerClientInstance.createVolume(volumeName)).isTrue();
        // cleaning
        dockerClientInstance.removeVolume(volumeName);
    }

    @Test
    public void shouldNotCreateVolumeSinceEmptyName() {
        assertThat(dockerClientInstance.createVolume("")).isFalse();
    }

    @Test
    public void shouldReturnTrueSinceVolumeAlreadyPresent() {
        String volumeName = getRandomString();
        assertThat(dockerClientInstance.createVolume(volumeName)).isTrue();
        assertThat(dockerClientInstance.createVolume(volumeName)).isTrue();
        // cleaning
        dockerClientInstance.removeVolume(volumeName);
    }

    @Test
    public void shouldNotCreateVolumeSinceDockerCmdException() {
        useCorruptedDockerClient();
        assertThat(dockerClientInstance.createVolume(getRandomString())).isFalse();
    }

    // isVolumePresent

    @Test
    public void ShouldFindVolumePresent() {
        String volumeName = getRandomString();
        dockerClientInstance.createVolume(volumeName);
        assertThat(dockerClientInstance.isVolumePresent(volumeName)).isTrue();
        // cleaning
        dockerClientInstance.removeVolume(volumeName);
    }

    @Test
    public void shouldNotFindVolumePresentSinceEmptyName() {
        assertThat(dockerClientInstance.isVolumePresent("")).isFalse();
    }

    @Test
    public void shouldNotFindVolumePresentSinceDockerCmdException() {
        useCorruptedDockerClient();
        assertThat(dockerClientInstance.isVolumePresent(getRandomString())).isFalse();
    }

    // removeVolume

    @Test
    public void shouldRemoveVolume() {
        String volumeName = getRandomString();
        dockerClientInstance.createVolume(volumeName);
        assertThat(dockerClientInstance.removeVolume(volumeName)).isTrue();
        assertThat(dockerClientInstance.isVolumePresent(volumeName)).isFalse();
    }

    @Test
    public void shouldNotRemoveVolumeSinceEmptyName() {
        assertThat(dockerClientInstance.removeVolume("")).isFalse();
    }

    @Test
    public void shouldNotRemoveVolumeSinceDockerCmdException() {
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
    public void shouldCreateNetwork() {
        String networkName = getRandomString();
        String networkId = dockerClientInstance.createNetwork(networkName);
        assertThat(networkId).isNotEmpty();
        assertThat(dockerClientInstance.isNetworkPresent(networkName)).isTrue();
        // cleaning
        dockerClientInstance.removeNetwork(networkId);
    }

    @Test
    public void shouldNotCreateNetworkSinceEmptyName() {
        assertThat(dockerClientInstance.createNetwork("")).isEmpty();
    }

    @Test
    public void shouldReturnExistingNetworkIdWenNetworkIsAlreadyPresent() {
        String networkName = getRandomString();
        String networkId = dockerClientInstance.createNetwork(networkName);
        assertThat(dockerClientInstance.createNetwork(networkName)).isNotEmpty();
        assertThat(dockerClientInstance.isNetworkPresent(networkName)).isTrue();
        // cleaning
        dockerClientInstance.removeNetwork(networkId);
    }

    @Test
    public void shouldNotCreateNetworkSinceDockerCmdException() {
        useCorruptedDockerClient();
        assertThat(dockerClientInstance.createNetwork(getRandomString())).isEmpty();
    }

    // getNetworkId

    @Test
    public void shouldGetNetworkId() {
        String networkName = getRandomString();
        String networkId = dockerClientInstance.createNetwork(networkName);
        assertThat(dockerClientInstance.getNetworkId(networkName)).isEqualTo(networkId);
        // cleaning
        dockerClientInstance.removeNetwork(networkId);
    }

    @Test
    public void shouldNotGetNetworkIdSinceEmptyName() {
        assertThat(dockerClientInstance.getNetworkId("")).isEmpty();
    }

    @Test
    public void shouldNotGetNetworkIdSinceDockerCmdException() {
        useCorruptedDockerClient();
        assertThat(dockerClientInstance.getNetworkId(getRandomString())).isEmpty();
    }

    // isNetworkPresent

    @Test
    public void shouldFindNetworkPresent() {
        String networkName = getRandomString();
        dockerClientInstance.createNetwork(networkName);
        assertThat(dockerClientInstance.isNetworkPresent(networkName)).isTrue();
        dockerClientInstance.removeNetwork(networkName);
    }

    @Test
    public void shouldNotFineNetworkPresentSinceEmptyName() {
        assertThat(dockerClientInstance.isNetworkPresent("")).isFalse();
        
    }

    @Test
    public void shouldNotFineNetworkPresentSinceDockerCmdException() {
        useCorruptedDockerClient();
        assertThat(dockerClientInstance.isNetworkPresent(getRandomString())).isFalse();
        
    }

    // removeNetwork

    @Test
    public void shouldRemoveNetwork() {
        String networkName = getRandomString();
        dockerClientInstance.createNetwork(networkName);
        assertThat(dockerClientInstance.isNetworkPresent(networkName)).isTrue();
        assertThat(dockerClientInstance.removeNetwork(networkName)).isTrue();
        assertThat(dockerClientInstance.isNetworkPresent(networkName)).isFalse();
    }

    @Test
    public void shouldNotRemoveNetworkSinceEmptyId() {
        assertThat(dockerClientInstance.removeNetwork("")).isFalse();
    }

    @Test
    public void shouldNotRemoveNetworkSinceDockerCmdException() {
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
    public void shouldFindImagePresent() {
        dockerClientInstance.pullImage(ALPINE_LATEST);
        assertThat(dockerClientInstance.isImagePresent(ALPINE_LATEST)).isTrue();
        dockerClientInstance.removeImage(ALPINE_LATEST);
    }

    @Test
    public void shouldNotFindImagePresent() {
        assertThat(dockerClientInstance.isImagePresent(getRandomString())).isFalse();
    }

    @Test
    public void shouldNotFindImagePresentSinceEmptyName() {
        assertThat(dockerClientInstance.isImagePresent("")).isFalse();
    }


    @Test
    public void shouldNotFindImagePresentSinceDockerCmdException() {
        useCorruptedDockerClient();
        assertThat(dockerClientInstance.isImagePresent("")).isFalse();
    }

    // pull image

    @Test
    public void shouldPullImage() {
        assertThat(dockerClientInstance.pullImage(ALPINE_LATEST)).isTrue();
        assertThat(dockerClientInstance.isImagePresent(ALPINE_LATEST)).isTrue();
        dockerClientInstance.removeImage(ALPINE_LATEST);
    }

    @Test
    public void shouldNotPullImageSinceNoTag() {
        assertThat(dockerClientInstance.pullImage("alpine")).isFalse();
    }

    @Test
    public void shouldNotPullImageSinceEmptyImageName() {
        assertThat(dockerClientInstance.pullImage("")).isFalse();
    }

    @Test
    public void shouldNotPullImageSinceEmptyNameButPresentTag() {
        assertThat(dockerClientInstance.pullImage(":latest")).isFalse();
    }

    @Test
    public void shouldNotPullImageSincePresentNameButEmptyTag() {
        assertThat(dockerClientInstance.pullImage("blabla:")).isFalse();
    }

    @Test
    public void shouldNotPullImageSinceWrongName() {
        assertThat(dockerClientInstance.pullImage(BLABLA_LATEST)).isFalse();
    }

    @Test
    public void shouldNotPullImageSinceWrongTag() {
        assertThat(dockerClientInstance.pullImage(ALPINE_BLABLA)).isFalse();
    }

    @Test
    public void shouldNotPullImageSinceDockerCmdException() {
        useCorruptedDockerClient();
        assertThat(dockerClientInstance.pullImage(getRandomString())).isFalse();
    }

    /**
     * Following test will only occur if dockerhubPassword envvar is present
     */
    @Test
    public void shouldPullPrivateImage() {
        String username = getEnvValue(DOCKERHUB_USERNAME_ENV_NAME);
        String password = getEnvValue(DOCKERHUB_PASSWORD_ENV_NAME);
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            System.out.println("No dockerhub username or password found, will" +
                    " abort shouldPullPrivateImage test");
            return;
        }
        // Get an authenticated docker client
        DockerClientInstance authClientInstance = DockerClientFactory.getDockerClientInstance(username, password);
        // clean to avoid previous tests collisions
        authClientInstance.removeImage(PRIVATE_IMAGE_NAME);
        // pull image and check
        assertThat(dockerClientInstance.pullImage(PRIVATE_IMAGE_NAME)).isTrue();
        // clean
        dockerClientInstance.removeImage(PRIVATE_IMAGE_NAME);
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
    public void shouldGetImageId() {
        dockerClientInstance.pullImage(ALPINE_LATEST);
        assertThat(dockerClientInstance.getImageId(ALPINE_LATEST)).isNotEmpty();
    }

    @Test
    public void shouldGetImageIdWithDockerIoClassicImage() {
        String image = DOCKER_IO_CLASSIC_IMAGE;
        dockerClientInstance.pullImage(image);
        assertThat(dockerClientInstance.getImageId(image)).isNotEmpty();
    }

    @Test
    public void shouldGetImageIdWithShortClassicImage() {
        String image = SHORT_CLASSIC_IMAGE;
        dockerClientInstance.pullImage(image);
        assertThat(dockerClientInstance.getImageId(image)).isNotEmpty();
    }

    @Test
    public void shouldGetImageIdWithDockerIoLibraryImage() {
        String image = DOCKER_IO_LIBRARY_IMAGE;
        dockerClientInstance.pullImage(image);
        assertThat(dockerClientInstance.getImageId(image)).isNotEmpty();
    }

    @Test
    public void shouldGetImageIdWithShortLibraryImage() {
        String image = SHORT_LIBRARY_IMAGE;
        dockerClientInstance.pullImage(image);
        assertThat(dockerClientInstance.getImageId(image)).isNotEmpty();
    }

    @Test
    public void shouldGetImageIdWithVeryShortLibraryImage() {
        String image = VERY_SHORT_LIBRARY_IMAGE;
        dockerClientInstance.pullImage(image);
        assertThat(dockerClientInstance.getImageId(image)).isNotEmpty();
    }

    @Test
    public void shouldGetImageIdWithClassicDockerComImage() {
        String image = DOCKER_COM_CLASSIC_IMAGE;
        dockerClientInstance.pullImage(image);
        String imageId = dockerClientInstance.getImageId(image);
        assertThat(imageId).isNotEmpty();
    }

    @Test
    public void shouldNotGetImageIdSinceEmptyName() {
        assertThat(dockerClientInstance.getImageId("")).isEmpty();
    }

    @Test
    public void shouldNotGetImageId() {
        assertThat(dockerClientInstance.getImageId(BLABLA_LATEST)).isEmpty();
    }

    @Test
    public void shouldNotGetImageIdSinceDockerCmdException() {
        useCorruptedDockerClient();
        assertThat(dockerClientInstance.getImageId(getRandomString())).isEmpty();
    }

    // sanitizeImageName

    @Test
    public void shouldGetSanitizedImageWithDockerIoClassicImage() {
        assertThat(dockerClientInstance.sanitizeImageName(DOCKER_IO_CLASSIC_IMAGE))
                .isEqualTo("alpine/socat:latest");
    }

    @Test
    public void shouldGetSanitizedImageWithDockerIoLibraryImage() {
        assertThat(dockerClientInstance.sanitizeImageName(DOCKER_IO_LIBRARY_IMAGE))
                .isEqualTo("alpine:latest");
    }

    @Test
    public void shouldGetSanitizedImageWithShortLibraryImage() {
        assertThat(dockerClientInstance.sanitizeImageName(SHORT_LIBRARY_IMAGE))
                .isEqualTo("alpine:latest");
    }

    @Test
    public void shouldGetSanitizedImageIfShortNameLibraryName() {
        assertThat(dockerClientInstance.sanitizeImageName(VERY_SHORT_LIBRARY_IMAGE))
                .isEqualTo("alpine:latest");
    }

    @Test
    public void shouldDoNothingTOSanitizeImageWithDockerComClassicImage() {
        assertThat(dockerClientInstance.sanitizeImageName(DOCKER_COM_CLASSIC_IMAGE))
                .isEqualTo(DOCKER_COM_CLASSIC_IMAGE);
    }

    @Test
    public void shouldDoNothingForSanitizedImage() {
        String image = "nexus.iex.ec/some-app:latest";
        assertThat(dockerClientInstance.sanitizeImageName(image))
                .isEqualTo(image);
    }

    // Remove image

    @Test
    public void shouldRemoveImage() {
        dockerClientInstance.pullImage(DOCKER_IO_CLASSIC_IMAGE);
        assertThat(dockerClientInstance.removeImage(DOCKER_IO_CLASSIC_IMAGE)).isTrue();
    }

    @Test
    public void shouldRemoveImageByIdSinceEmptyName() {
        assertThat(dockerClientInstance.removeImage("")).isFalse();
    }

    @Test
    public void shouldNotRemoveImageByIdSinceDockerCmdException() {
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
    public void shouldRunSuccessfullyAndWaitForContainerToFinish() {
        DockerRunRequest dockerRunRequest = getDefaultDockerRunRequest(false);
        dockerRunRequest.setMaxExecutionTime(5000); // 5s
        String msg = "Hello world!";
        dockerRunRequest.setCmd("sh -c 'sleep 2 && echo " + msg + "'");
        String containerName = dockerRunRequest.getContainerName();

        DockerRunResponse dockerRunResponse =
                dockerClientInstance.run(dockerRunRequest);

        assertThat(dockerRunResponse).isNotNull();
        assertThat(dockerRunResponse.isSuccessful()).isTrue();
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
    public void shouldRunSuccessfullyAndNotWaitForTimeout() {
        DockerRunRequest dockerRunRequest = getDefaultDockerRunRequest(false);
        dockerRunRequest.setMaxExecutionTime(0); // detached mode // can be -1
        dockerRunRequest.setCmd("sh -c 'sleep 30'");
        String containerName = dockerRunRequest.getContainerName();

        DockerRunResponse dockerRunResponse =
                dockerClientInstance.run(dockerRunRequest);

        assertThat(dockerRunResponse).isNotNull();
        assertThat(dockerRunResponse.isSuccessful()).isTrue();
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
    public void shouldRunAndReturnFailureInStderrSinceBadCmd() {
        DockerRunRequest dockerRunRequest = getDefaultDockerRunRequest(false);
        dockerRunRequest.setMaxExecutionTime(5000); // 5s
        dockerRunRequest.setCmd("sh -c 'someBadCmd'");
        String containerName = dockerRunRequest.getContainerName();

        DockerRunResponse dockerRunResponse =
                dockerClientInstance.run(dockerRunRequest);

        System.out.println(dockerRunResponse);
        assertThat(dockerRunResponse).isNotNull();
        assertThat(dockerRunResponse.isSuccessful()).isFalse();
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
    public void shouldRunAndReturnFailureAndLogsSinceTimeout() {
        DockerRunRequest dockerRunRequest = getDefaultDockerRunRequest(false);
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
        assertThat(dockerRunResponse.isSuccessful()).isFalse();
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
    public void shouldReturnFailureSinceCantCreateContainer() {
        DockerRunRequest dockerRunRequest = getDefaultDockerRunRequest(false);
        dockerRunRequest.setMaxExecutionTime(5000); // 5s
        String msg = "Hello world!";
        dockerRunRequest.setCmd("sh -c 'sleep 2 && echo " + msg + "'");
        String containerName = dockerRunRequest.getContainerName();
        doReturn("").when(dockerClientInstance).createContainer(dockerRunRequest);

        DockerRunResponse dockerRunResponse =
                dockerClientInstance.run(dockerRunRequest);

        System.out.println(dockerRunResponse);
        assertThat(dockerRunResponse).isNotNull();
        assertThat(dockerRunResponse.isSuccessful()).isFalse();
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
    public void shouldReturnFailureSinceCantStartContainer() {
        DockerRunRequest dockerRunRequest = getDefaultDockerRunRequest(false);
        dockerRunRequest.setMaxExecutionTime(5000); // 5s
        String msg = "Hello world!";
        dockerRunRequest.setCmd("sh -c 'sleep 2 && echo " + msg + "'");
        String containerName = dockerRunRequest.getContainerName();
        doReturn(false).when(dockerClientInstance).startContainer(containerName);

        DockerRunResponse dockerRunResponse =
                dockerClientInstance.run(dockerRunRequest);

        System.out.println(dockerRunResponse);
        assertThat(dockerRunResponse).isNotNull();
        assertThat(dockerRunResponse.isSuccessful()).isFalse();
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
    public void shouldReturnFailureSinceCantStopContainer() {
        DockerRunRequest dockerRunRequest = getDefaultDockerRunRequest(false);
        dockerRunRequest.setMaxExecutionTime(5000); // 5s
        String msg = "Hello world!";
        dockerRunRequest.setCmd("sh -c 'sleep 10 && echo " + msg + "'");
        String containerName = dockerRunRequest.getContainerName();
        doReturn(false).when(dockerClientInstance).stopContainer(containerName);

        DockerRunResponse dockerRunResponse =
                dockerClientInstance.run(dockerRunRequest);

        System.out.println(dockerRunResponse);
        assertThat(dockerRunResponse).isNotNull();
        assertThat(dockerRunResponse.isSuccessful()).isFalse();
        assertThat(dockerRunResponse.getContainerExitCode()).isEqualTo(-1);
        assertThat(dockerRunResponse.getStdout()).isEmpty();
        assertThat(dockerRunResponse.getStderr()).isEmpty();
        verify(dockerClientInstance).createContainer(dockerRunRequest);
        verify(dockerClientInstance).startContainer(containerName);
        verify(dockerClientInstance)
                .waitContainerUntilExitOrTimeout(eq(containerName), any());
        verify(dockerClientInstance).stopContainer(containerName);
        verify(dockerClientInstance, never()).getContainerLogs(containerName);
        verify(dockerClientInstance, never()).removeContainer(containerName);
        // clean
        dockerClientInstance.stopAndRemoveContainer(containerName);
    }

    @Test
    public void shouldReturnFailureAndLogsSinceCantRemoveContainer() {
        DockerRunRequest dockerRunRequest = getDefaultDockerRunRequest(false);
        dockerRunRequest.setMaxExecutionTime(5000); // 5s
        String msg = "Hello world!";
        dockerRunRequest.setCmd("sh -c 'sleep 2 && echo " + msg + "'");
        String containerName = dockerRunRequest.getContainerName();
        doReturn(false).when(dockerClientInstance).removeContainer(containerName);

        DockerRunResponse dockerRunResponse =
                dockerClientInstance.run(dockerRunRequest);

        System.out.println(dockerRunResponse);
        assertThat(dockerRunResponse).isNotNull();
        assertThat(dockerRunResponse.isSuccessful()).isFalse();
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
    public void shouldCreateContainer() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
        dockerClientInstance.pullImage(request.getImageUri());
        String containerId = dockerClientInstance.createContainer(request);
        assertThat(containerId).isNotEmpty();
        // cleaning
        dockerClientInstance.removeContainer(request.getContainerName());
        dockerClientInstance.removeImage(request.getImageUri());
    }

    @Test
    public void shouldNotCreateContainerSinceNoRequest() {
        assertThat(dockerClientInstance.createContainer(null)).isEmpty();
    }

    @Test
    public void shouldNotCreateContainerSinceEmptyContainerName() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
        request.setContainerName("");
        assertThat(dockerClientInstance.createContainer(request)).isEmpty();
    }

    @Test
    public void shouldNotCreateContainerSinceEmptyImageUri() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
        request.setImageUri("");
        assertThat(dockerClientInstance.createContainer(request)).isEmpty();
    }

    @Test
    public void shouldNotCreateContainerSinceDockerCmdException() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
        when(dockerClientInstance.getClient())
                .thenCallRealMethod() // isContainerPresent
                .thenCallRealMethod() // isNetworkPresent
                .thenCallRealMethod() // createNetwork
                .thenReturn(corruptedClient);
        assertThat(dockerClientInstance.createContainer(request)).isEmpty();
    }

    @Test
    public void shouldCreateContainerAndRemoveExistingDuplicate() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
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
    public void shouldNotCreateContainerSinceDuplicateIsPresent() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
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
    public void shouldBuildHostConfigFromRunRequest() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);

        HostConfig hostConfig =
                dockerClientInstance.buildHostConfigFromRunRequest(request);
        assertThat(hostConfig.getNetworkMode())
                .isEqualTo(DOCKER_NETWORK);
        assertThat((hostConfig.getBinds()[0].getPath()))
                .isEqualTo(IexecFileHelper.SLASH_IEXEC_IN);
        assertThat((hostConfig.getBinds()[0].getVolume().getPath()))
                .isEqualTo(IexecFileHelper.SLASH_IEXEC_OUT);
        assertThat(hostConfig.getDevices()).isNull();
    }

    @Test
    public void shouldBuildHostConfigWithDeviceFromRunRequest() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
        request.setDevices(new ArrayList<>());
        request.getDevices().add(new Device("", DEVICE_PATH_IN_CONTAINER, DEVICE_PATH_ON_HOST));

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
    public void shouldBuildHostConfigWithSgxDeviceFromRunRequest() {
        DockerRunRequest request = getDefaultDockerRunRequest(true);

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
                .isEqualTo(SgxUtils.SGX_DEVICE_PATH);
        assertThat(hostConfig.getDevices()[0].getPathOnHost())
                .isEqualTo(SgxUtils.SGX_DEVICE_PATH);
    }

    @Test
    public void shouldNotbuildHostConfigFromRunRequestSinceNoRequest() {
        HostConfig hostConfig =
                dockerClientInstance.buildHostConfigFromRunRequest(null);
        assertThat(hostConfig).isNull();
    }

    @Test
    public void shouldbuildCreateContainerCmdFromRunRequest() {
        CreateContainerCmd createContainerCmd = dockerClientInstance.getClient()
                .createContainerCmd("repo/image:tag");
        DockerRunRequest request = getDefaultDockerRunRequest(false);
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
    public void shouldbuildCreateContainerCmdFromRunRequestWithFullParams() {
        CreateContainerCmd createContainerCmd = dockerClientInstance.getClient()
                .createContainerCmd("repo/image:tag");
        DockerRunRequest request = getDefaultDockerRunRequest(false);

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
    public void shouldNotbuildCreateContainerCmdFromRunRequestSinceNoRequest() {
        Optional<CreateContainerCmd> actualCreateContainerCmd =
                dockerClientInstance.buildCreateContainerCmdFromRunRequest(
                        getDefaultDockerRunRequest(false),
                        null);
        assertThat(actualCreateContainerCmd).isEmpty();
    }

    @Test
    public void shouldNotbuildCreateContainerCmdFromRunRequestSinceNoCreateContainerCmd() {
        Optional<CreateContainerCmd> actualCreateContainerCmd =
                dockerClientInstance.buildCreateContainerCmdFromRunRequest(
                        null,
                        dockerClientInstance.getClient()
                                .createContainerCmd("repo/image:tag")
                        );
        assertThat(actualCreateContainerCmd).isEmpty();
    }

    // getContainerName

    @Test
    public void shouldGetContainerName() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
        String containerId = dockerClientInstance.createContainer(request);

        assertThat(dockerClientInstance.getContainerName(containerId))
                .isEqualTo(request.getContainerName());

        // cleaning
        dockerClientInstance.removeContainer(request.getContainerName());
    }

    @Test
    public void shouldNotGetContainerNameSinceEmptyId() {
        assertThat(dockerClientInstance.getContainerName("")).isEmpty();
    }

    @Test
    public void shouldNotGetContainerNameSinceNoContainer() {
        assertThat(dockerClientInstance.getContainerName(getRandomString())).isEmpty();
    }

    @Test
    public void shouldNotGetContainerNameSinceDockerCmdException() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
        String containerId = dockerClientInstance.createContainer(request);

        useCorruptedDockerClient();
        assertThat(dockerClientInstance.getContainerName(containerId)).isEmpty();

        // cleaning
        useRealDockerClient();
        dockerClientInstance.removeContainer(request.getContainerName());
    }

    // getContainerId

    @Test
    public void shouldGetContainerId() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
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
    public void shouldNotGetContainerIdSinceEmptyId() {
        assertThat(dockerClientInstance.getContainerId("")).isEmpty();
    }

    @Test
    public void shouldNotGetContainerIdSinceDockerCmdException() {
        useCorruptedDockerClient();
        assertThat(dockerClientInstance.getContainerId(getRandomString())).isEmpty();
    }

    // getContainerStatus

    @Test
    public void shouldGetContainerStatus() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
        pullImageIfNecessary();
        dockerClientInstance.createContainer(request);

        assertThat(dockerClientInstance.getContainerStatus(request.getContainerName()))
                .isEqualTo(DockerClientInstance.CREATED_STATUS);

        // cleaning
        dockerClientInstance.removeContainer(request.getContainerName());
    }

    @Test
    public void shouldNotGetContainerStatusSinceEmptyId() {
        assertThat(dockerClientInstance.getContainerStatus("")).isEmpty();
    }

    @Test
    public void shouldNotGetContainerStatusSinceDockerCmdException() {
        useCorruptedDockerClient();
        assertThat(dockerClientInstance.getContainerStatus(getRandomString())).isEmpty();
    }

    // start container

    @Test
    public void shouldStartContainer() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
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
    public void shouldNotStartContainerNameSinceEmptyId() {
        assertThat(dockerClientInstance.startContainer("")).isFalse();
    }

    @Test
    public void shouldNotStartContainerSinceDockerCmdException() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
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

    @Test
    public void shouldTimeoutAfterWaitContainerUntilExitOrTimeout() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 30 && echo Hello from Docker alpine!'");
        pullImageIfNecessary();
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(containerName);
        assertThat(dockerClientInstance.getContainerStatus(containerName))
                .isEqualTo(DockerClientInstance.RUNNING_STATUS);
        Date before = new Date();
        int exitCode = dockerClientInstance.waitContainerUntilExitOrTimeout(containerName,
                Instant.now().plusSeconds(5));
        assertThat(dockerClientInstance.getContainerStatus(containerName))
                .isEqualTo(DockerClientInstance.RUNNING_STATUS);
        assertThat(exitCode).isEqualTo(-1);
        assertThat(new Date().getTime() - before.getTime()).isGreaterThan(1000);
        // cleaning
        dockerClientInstance.stopContainer(containerName);
        dockerClientInstance.removeContainer(containerName);
    }

    @Test
    public void shouldWaitContainerUntilExitOrTimeoutSinceExited() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
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
    public void shouldGetContainerExitCode() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
        dockerClientInstance.createContainer(request);
        int exitCode = dockerClientInstance
                .getContainerExitCode(request.getContainerName());
        assertThat(exitCode).isEqualTo(0);
    }

    @Test
    public void shouldNotGetContainerExitCodeSinceDockerCmdException() {
        useCorruptedDockerClient();
        assertThat(dockerClientInstance.getContainerExitCode(getRandomString()))
                .isEqualTo(-1);
    }

    // getContainerLogs

    @Test
    public void shouldGetContainerLogsSinceStdout() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
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
    public void shouldGetContainerLogsSinceStderr() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
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
    public void shouldNotGetContainerLogsSinceEmptyId() {
        assertThat(dockerClientInstance.getContainerLogs("")).isEmpty();
    }

    @Test
    public void shouldNotGetContainerLogsSinceDockerCmdException() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
        dockerClientInstance.createContainer(request);
        when(dockerClientInstance.getClient())
                .thenCallRealMethod() // isContainerPresent
                .thenReturn(corruptedClient);
        assertThat(dockerClientInstance.getContainerLogs(request.getContainerName()))
                .isEmpty();
    }

    // stopContainer

    @Test
    public void shouldStopContainer() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 10 && echo Hello from Docker alpine!'");
        pullImageIfNecessary();
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(containerName);

        assertThat(dockerClientInstance.getContainerStatus(containerName))
                .isEqualTo(DockerClientInstance.RUNNING_STATUS);
        assertThat(dockerClientInstance.stopContainer(containerName)).isTrue();
        assertThat(dockerClientInstance.getContainerStatus(containerName))
                .isEqualTo(DockerClientInstance.EXITED_STATUS);

        // cleaning
        dockerClientInstance.removeContainer(containerName);
    }

    @Test
    public void shouldNotStopContainerSinceEmptyId() {
        assertThat(dockerClientInstance.stopContainer("")).isFalse();
    }

    @Test
    public void shouldNotStopContainerSinceDockerCmdException() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
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
    public void shouldRemoveContainer() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 10 && echo Hello from Docker alpine!'");
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(containerName);
        dockerClientInstance.stopContainer(containerName);

        assertThat(dockerClientInstance.removeContainer(containerName)).isTrue();
    }

    @Test
    public void shouldNotRemoveContainerSinceEmptyId() {
        assertThat(dockerClientInstance.removeContainer("")).isFalse();
    }

    @Test
    public void shouldNotRemoveContainerSinceRunning() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
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
    public void shouldNotRemoveContainerSinceDockerCmdException() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
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
    public void shouldExecuteCommandInContainer() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 10'");
        String msg = "Hello from Docker alpine!";
        String cmd = "echo " + msg;
        pullImageIfNecessary();
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(containerName);

        Optional<DockerLogs> logs = 
                dockerClientInstance.exec(containerName, "sh", "-c", cmd);
        assertThat(logs.get().getStdout().trim()).isEqualTo(msg);
        // clean
        dockerClientInstance.stopAndRemoveContainer(containerName);
    }

    @Test
    public void shouldNotExecuteCommandSinceContainerNotFound() {
        // no container created
        Optional<DockerLogs> logs = 
                dockerClientInstance.exec(getRandomString(), "sh", "-c", "ls");
        assertThat(logs).isEmpty();
    }

    @Test
    public void shouldNotExecuteCommandSinceDockerException() {
        DockerRunRequest request = getDefaultDockerRunRequest(false);
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
        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
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
