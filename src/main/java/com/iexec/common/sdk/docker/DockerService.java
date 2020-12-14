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

package com.iexec.common.sdk.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import kotlin.Pair;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;

@Slf4j
public class DockerService {

    private static String[] toDockerCmd(String cmd) {
        if (cmd.isEmpty()) {
            return new String[0];
        }
        return cmd.split(" ");
    }

    public Pair<String, String> exec(String containerName, String cmd) { // iexec --help
        String containerId = getContainerId(containerName);

        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();

        try {
            getDockerClient()
                    .execStartCmd(getDockerClient().execCreateCmd(containerId).withAttachStdout(true)
                            .withAttachStderr(true).withCmd("sh", "-c", cmd).exec().getId())
                    .exec(new ExecStartResultCallback(stdout, stderr))
                    .awaitCompletion();
        } catch (Exception e) {
            log.error("`docker exec` failed [containerName:{}, e:{}, stderr:{}]", containerName, e, stderr.toString());
            return null;
        }
        return new Pair<>(stdout.toString(), stderr.toString());
    }

    private DockerClient getDockerClient() {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .build();
        return DockerClientImpl.getInstance(config, httpClient);
    }

    private String getContainerId(String containerName) {
        List<Container> containers = getDockerClient().listContainersCmd()
                .withStatusFilter(Collections.singletonList("running"))
                .withNameFilter(Collections.singleton(containerName))
                .exec();

        if (containers.isEmpty()) {
            return "";
        }

        return containers.get(0).getId();
    }

}
