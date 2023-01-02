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
import com.iexec.common.docker.DockerRunRequest;
import com.iexec.common.sgx.SgxDriverMode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.doReturn;

class DockerExecTests extends AbstractDockerTests {

    @BeforeAll
    static void beforeAll() {
        new DockerClientInstance().pullImage(ALPINE_LATEST);
    }

    //region exec
    @Test
    void shouldExecuteCommandInContainerWithStdout() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 10'");
        String msg = "Hello from Docker alpine!";
        String cmd = "echo " + msg;
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(containerName);
        Optional<DockerLogs> logs = dockerClientInstance.exec(containerName, "sh", "-c", cmd);
        assertThat(logs).isPresent();
        assertThat(logs.get().getStdout().trim()).isEqualTo(msg);
        assertThat(logs.get().getStderr().trim()).isEmpty();
        dockerClientInstance.stopAndRemoveContainer(containerName);
    }

    @Test
    void shouldExecuteCommandInContainerWithStderr() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 10'");
        String msg = "Hello from Docker alpine!";
        String cmd = "echo " + msg + " >&2";
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(containerName);
        Optional<DockerLogs> logs = dockerClientInstance.exec(containerName, "sh", "-c", cmd);
        assertThat(logs).isPresent();
        assertThat(logs.get().getStdout().trim()).isEmpty();
        assertThat(logs.get().getStderr().trim()).isEqualTo(msg);
        dockerClientInstance.stopAndRemoveContainer(containerName);
    }

    @Test
    void shouldNotExecuteCommandSinceEmptyContainerName() {
        assertThat(dockerClientInstance.exec("", "sh", "-c", "ls")).isEmpty();
    }

    @Test
    void shouldNotExecuteCommandSinceContainerNotFound() {
        String containerName = getRandomString();
        Optional<DockerLogs> logs =
                dockerClientInstance.exec(containerName, "sh", "-c", "ls");
        assertThat(logs).isEmpty();
    }

    @Test
    void shouldNotExecuteCommandSinceDockerException() {
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 10'");
        String msg = "Hello from Docker alpine!";
        String cmd = "echo " + msg;
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(containerName);
        doReturn(true).when(corruptClientInstance).isContainerPresent(containerName);
        assertThat(corruptClientInstance.exec(containerName, "sh", "-c", cmd)).isEmpty();
        dockerClientInstance.stopAndRemoveContainer(containerName);
    }
    //endregion

    @Test
    void shouldInterruptThreadOnInterruptedException() {
        //TODO retrieve and validate exec method output
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        DockerRunRequest request = getDefaultDockerRunRequest(SgxDriverMode.NONE);
        String containerName = request.getContainerName();
        request.setCmd("sh -c 'sleep 30'");
        String msg = "Hello from Docker alpine!";
        String cmd = "sleep 10 && echo " + msg + " >&2";
        dockerClientInstance.createContainer(request);
        dockerClientInstance.startContainer(containerName);
        Future<Optional<DockerLogs>> future = threadPool.submit(() -> dockerClientInstance.exec(containerName, "sh", "-c", cmd));
        await().atMost(1, TimeUnit.SECONDS).until(() -> threadPool.getActiveCount() == 1);
        future.cancel(true);
        assertThat(future.isCancelled()).isTrue();
        assertThatThrownBy(future::get).isInstanceOf(CancellationException.class);
        dockerClientInstance.stopAndRemoveContainer(containerName);
    }

}
