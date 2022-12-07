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

import com.iexec.common.docker.DockerLogs;
import com.iexec.common.docker.DockerRunFinalStatus;
import com.iexec.common.docker.DockerRunRequest;
import com.iexec.common.docker.DockerRunResponse;
import com.iexec.common.sgx.SgxDriverMode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Slf4j
class DockerContainerTests extends AbstractDockerTests {

    @BeforeAll
    static void beforeAll() {
        new DockerClientInstance().pullImage(ALPINE_LATEST);
    }

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        log.info(">>> {}", testInfo.getDisplayName());
    }

    @AfterEach
    void afterEach(TestInfo testInfo) {
        log.info(">>> {}", testInfo.getDisplayName());
    }

    //region run
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
        doCallRealMethod().when(dockerClientInstance).stopContainer(containerName);
        dockerClientInstance.stopAndRemoveContainer(containerName);
    }

    // strange
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
        doCallRealMethod().when(dockerClientInstance).removeContainer(containerName);
        dockerClientInstance.stopAndRemoveContainer(containerName);
    }
    //endregion

    //region createContainer
    @Test
    void shouldCreateContainer() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerId = dockerClientInstance.createContainer(request);
        assertThat(containerId).isNotEmpty();
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
        String containerName = request.getContainerName();
        doReturn(false).when(corruptClientInstance).isContainerPresent(containerName);
        doReturn(DOCKER_NETWORK).when(corruptClientInstance).createNetwork(DOCKER_NETWORK);
        assertThat(corruptClientInstance.createContainer(request)).isEmpty();
    }

    @Test
    void shouldCreateContainerAndRemoveExistingDuplicate() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        // create first container
        String container1Id = dockerClientInstance.createContainer(request);
        // create second container with same name (should replace previous one)
        String container2Id = dockerClientInstance.createContainer(request);
        assertThat(container2Id)
                .isNotEmpty()
                .isNotEqualTo(container1Id);
        dockerClientInstance.removeContainer(containerName);
    }

    @Test
    void shouldNotCreateContainerSinceDuplicateIsPresent() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        // create first container
        String container1Id = dockerClientInstance.createContainer(request);
        // create second container with same name (should not replace previous one)
        String container2Id = dockerClientInstance.createContainer(request, false);
        assertThat(container1Id).isNotEmpty();
        assertThat(container2Id).isEmpty();
        dockerClientInstance.removeContainer(containerName);
    }
    //endregion

    //region isContainerPresent
    @Test
    void shouldIsContainerPresentBeTrue() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        dockerClientInstance.createContainer(request);
        assertThat(dockerClientInstance.isContainerPresent(containerName)).isTrue();
        dockerClientInstance.removeContainer(containerName);
    }
    //endregion

    //region isContainerActive
    @Test
    void shouldIsContainerActiveBeTrue() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        request.setCmd("sh -c 'sleep 10 && echo Hello from Docker alpine!'");
        dockerClientInstance.createContainer(request);
        String containerName = request.getContainerName();
        assertThat(dockerClientInstance.startContainer(containerName)).isTrue();
        assertThat(dockerClientInstance.isContainerActive(containerName)).isTrue();
        dockerClientInstance.stopAndRemoveContainer(containerName);
    }

    @Test
    void shouldIsContainerActiveBeFalseSinceContainerIsNotRunning() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        dockerClientInstance.createContainer(request);
        assertThat(dockerClientInstance.isContainerActive(containerName)).isFalse();
        dockerClientInstance.removeContainer(request.getContainerName());
    }
    //endregion

    //region getContainerName
    @Test
    void shouldGetContainerName() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerId = dockerClientInstance.createContainer(request);
        String containerName = request.getContainerName();
        assertThat(dockerClientInstance.getContainerName(containerId)).isEqualTo(containerName);
        dockerClientInstance.removeContainer(containerName);
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
        String expectedId = dockerClientInstance.createContainer(request);

        String containerId =
                dockerClientInstance.getContainerId(request.getContainerName());
        assertThat(containerId)
                .isNotEmpty()
                .isEqualTo(expectedId);

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
    void shouldTimeoutAfterWaitContainerUntilExitOrTimeout() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 30 && echo Hello from Docker alpine!'");
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
        String containerName = request.getContainerName();
        dockerClientInstance.createContainer(request);
        int exitCode = dockerClientInstance
                .getContainerExitCode(containerName);
        assertThat(exitCode).isZero();
        dockerClientInstance.removeContainer(containerName);
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

    //region getContainerLogs
    @Test
    void shouldGetContainerLogsSinceStdout() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'echo Hello from Docker alpine!'");
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(containerName);
        Optional<DockerLogs> containerLogs = dockerClientInstance.getContainerLogs(containerName);
        assertThat(containerLogs).isPresent();
        assertThat(containerLogs.get().getStdout()).contains("Hello from Docker alpine!");
        assertThat(containerLogs.get().getStderr()).isEmpty();
        dockerClientInstance.stopAndRemoveContainer(containerName);
    }

    @Test
    void shouldGetContainerLogsSinceStderr() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'echo Hello from Docker alpine! >&2'");
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(containerName);

        Optional<DockerLogs> containerLogs =
                dockerClientInstance.getContainerLogs(containerName);
        assertThat(containerLogs).isPresent();
        assertThat(containerLogs.get().getStdout()).isEmpty();
        assertThat(containerLogs.get().getStderr()).contains("Hello from Docker alpine!");
        dockerClientInstance.stopAndRemoveContainer(containerName);
    }

    @Test
    void shouldNotGetContainerLogsSinceEmptyId() {
        assertThat(dockerClientInstance.getContainerLogs("")).isEmpty();
    }

    @Test
    void shouldNotGetContainerSinceNoContainer() {
        assertThat(dockerClientInstance.getContainerLogs(getRandomString())).isEmpty();
    }

    @Test
    void shouldNotGetContainerLogsSinceDockerCmdException() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        dockerClientInstance.createContainer(request);
        doReturn(true).when(corruptClientInstance).isContainerPresent(containerName);
        assertThat(corruptClientInstance.getContainerLogs(containerName)).isEmpty();
        dockerClientInstance.removeContainer(containerName);
    }
    //endregion

    //region stopContainer
    @Test
    void shouldStopContainer() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 10 && echo Hello from Docker alpine!'");
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(containerName);
        assertThat(dockerClientInstance.getContainerStatus(containerName))
                .isEqualTo(DockerClientInstance.RUNNING_STATUS);

        assertThat(dockerClientInstance.stopContainer(containerName)).isTrue();
        assertThat(dockerClientInstance.getContainerStatus(containerName))
                .isEqualTo(DockerClientInstance.EXITED_STATUS);
        verify(dockerClientInstance, atLeastOnce()).isContainerPresent(containerName);
        verify(dockerClientInstance).isContainerActive(containerName);
        dockerClientInstance.removeContainer(containerName);
    }

    @Test
    void shouldReturnTrueWhenContainerIsNotActive() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 10 && echo Hello from Docker alpine!'");
        dockerClientInstance.createContainer(request);
        assertThat(dockerClientInstance.getContainerStatus(containerName))
                .isEqualTo(DockerClientInstance.CREATED_STATUS);
        assertThat(dockerClientInstance.stopContainer(containerName)).isTrue();
        verify(dockerClientInstance, times(2)).isContainerPresent(containerName);
        verify(dockerClientInstance).isContainerActive(containerName);
        dockerClientInstance.removeContainer(containerName);
    }

    @Test
    void shouldNotStopContainerSinceEmptyId() {
        assertThat(dockerClientInstance.stopContainer("")).isFalse();
    }

    @Test
    void shouldNotStopContainerSinceNotFound() {
        String containerName = "not-found";
        assertThat(dockerClientInstance.stopContainer(containerName)).isFalse();
        verify(dockerClientInstance).isContainerPresent(containerName);
        verify(dockerClientInstance, never()).isContainerActive(containerName);
    }

    @Test
    void shouldNotStopContainerSinceDockerCmdException() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 10 && echo Hello from Docker alpine!'");
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(containerName);
        doReturn(true).when(corruptClientInstance).isContainerPresent(containerName);
        doReturn(true).when(corruptClientInstance).isContainerActive(containerName);
        assertThat(corruptClientInstance.stopContainer(containerName)).isFalse();
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
    void shouldNotRemoveContainerSinceNoContainer() {
        assertThat(dockerClientInstance.removeContainer(getRandomString())).isFalse();
    }

    @Test
    void shouldNotRemoveContainerSinceRunning() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 5 && echo Hello from Docker alpine!'");
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(containerName);

        assertThat(dockerClientInstance.getContainerStatus(containerName))
                .isEqualTo(DockerClientInstance.RUNNING_STATUS);
        assertThat(dockerClientInstance.removeContainer(containerName)).isFalse();
        dockerClientInstance.stopAndRemoveContainer(containerName);
    }

    @Test
    void shouldNotRemoveContainerSinceDockerCmdException() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 5 && echo Hello from Docker alpine!'");
        dockerClientInstance.createContainer(request);
        doReturn(true).when(corruptClientInstance).isContainerPresent(containerName);
        assertThat(corruptClientInstance.removeContainer(containerName)).isFalse();
        dockerClientInstance.removeContainer(containerName);
    }
    //endregion

}
