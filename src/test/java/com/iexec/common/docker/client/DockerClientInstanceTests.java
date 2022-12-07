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
import com.iexec.common.docker.DockerRunRequest;
import com.iexec.common.sgx.SgxDriverMode;
import com.iexec.common.utils.ArgsUtils;
import com.iexec.common.utils.IexecFileHelper;
import com.iexec.common.utils.SgxUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

// Disabling these tests as they are not UT.
// They interact with the outer world and the CI/CD doesn't like that.
@Disabled
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
    void shouldGetAuthenticatedClientWithDockerIoRegistry() throws Exception {
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

    //region isImagePresent
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
    //endregion

    //region pullImage
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
    //endregion

    private String getEnvValue(String envVarName) {
        return System.getenv(envVarName) != null ?
                //Intellij envvar injection
                System.getenv(envVarName) :
                //gradle test -DdockerhubPassword=xxx
                System.getProperty(envVarName);
    }

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
        useCorruptedDockerClient();
        assertThat(dockerClientInstance.getImageId(getRandomString())).isEmpty();
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
