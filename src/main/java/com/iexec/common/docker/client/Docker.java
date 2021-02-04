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
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.core.NameParser;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.iexec.common.utils.ArgsUtils;
import com.iexec.common.utils.WaitUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Docker {

    static final String INTERNAL_DOCKER_NETWORK = "iexec-dataset-api-net";

    private DockerClient client;

    public Docker() {
        this.client = createClient("", "");
    }

    public Docker(String username, String password) {
        this.client = createClient(username, password);
    }

    /**
     * Docker volume
     * 
     */

    public boolean createVolume(String volumeName) {
        if (StringUtils.isBlank(volumeName)) {
            log.error("Invalid docker volume name [name:{}]", volumeName);
            return false;
        }
        if (isVolumePresent(volumeName)) {
            log.info("Docker volume already present [name:{}]", volumeName);
            return true;
        }
        try (CreateVolumeCmd createVolumeCmd = client.createVolumeCmd()) {
            String name = createVolumeCmd
                    .withName(volumeName)
                    .exec()
                    .getName();
            if (name == null || !name.equals(volumeName)) {
                return false;
            }
            log.info("Created docker volume [name:{}]", volumeName);
            return true;
        } catch (Exception e) {
            log.error("Error creating docker volume [name:{}]", volumeName, e);
            return false;
        }
    }

    public boolean isVolumePresent(String volumeName) {
        if (StringUtils.isBlank(volumeName)) {
            return false;
        }
        try (ListVolumesCmd listVolumesCmd = client.listVolumesCmd()) {
            ListVolumesResponse response = listVolumesCmd
                    .withDanglingFilter(true)
                    .withFilter("name", Collections.singletonList(volumeName))
                    .exec();
            log.info("{}", response);
            return response.getVolumes()
                    .stream()
                    .map(InspectVolumeResponse::getName)
                    .anyMatch(name -> name.equals(volumeName));
        } catch (Exception e) {
            log.error("Error checking docker volume [name:{}]", volumeName, e);
            return false;
        }
    }

    public boolean removeVolume(String volumeName) {
        if (StringUtils.isBlank(volumeName)) {
            return false;
        }
        try (RemoveVolumeCmd removeVolumeCmd = client.removeVolumeCmd(volumeName)) {
            removeVolumeCmd.exec();
            log.info("Removed docker volume [name:{}]", volumeName);
            return true;
        } catch (Exception e) {
            log.error("Error removing docker volume [name:{}]", volumeName, e);
            return false;
        }
    }

    /**
     * Docker network
     * 
     */

    public String createNetwork(String networkName) {
        if (StringUtils.isBlank(networkName)) {
            log.error("Invalid docker network name [name:{}]", networkName);
            return "";
        }
        if (isNetworkPresent(networkName)) {
            log.info("Docker network already present [name:{}]", networkName);
            return getNetworkId(networkName);
        }
        try (CreateNetworkCmd networkCmd = client.createNetworkCmd()) {
            String id = networkCmd
                    .withName(networkName)
                    .withDriver("bridge")
                    .exec()
                    .getId();
            if (id == null) {
                return "";
            }
            log.info("Created docker network [name:{}]", networkName);
            return id;
        } catch (Exception e) {
            log.error("Error creating docker network [name:{}]", networkName, e);
            return "";
        }
    }

    public String getNetworkId(String networkName) {
        if (StringUtils.isBlank(networkName)) {
            return "";
        }
        try (ListNetworksCmd listNetworksCmd = client.listNetworksCmd()) {
            return listNetworksCmd
                    .withNameFilter(networkName)
                    .exec()
                    .stream()
                    .filter(network -> !StringUtils.isBlank(network.getName()))
                    .filter(network -> network.getName().equals(networkName))
                    .map(Network::getId)
                    .findFirst()
                    .orElse("");
        } catch (Exception e) {
            log.error("Error getting network id [name:{}]", networkName, e);
            return "";
        }
    }

    public boolean isNetworkPresent(String networkName) {
        return !getNetworkId(networkName).isEmpty();
    }

    public boolean removeNetwork(String networkName) {
        if (StringUtils.isBlank(networkName)) {
            return false;
        }
        try (RemoveNetworkCmd removeNetworkCmd =
                     client.removeNetworkCmd(networkName)) {
            removeNetworkCmd.exec();
            log.info("Removed docker network [name:{}]", networkName);
            return true;
        } catch (Exception e) {
            log.error("Error removing docker network [name:{}]", networkName, e);
            return false;
        }
    }

    /**
     * Docker image
     * 
     */

    public boolean pullImage(String imageName) {
        if (StringUtils.isBlank(imageName)) {
            return false;
        }
        NameParser.ReposTag repoAndTag = NameParser.parseRepositoryTag(imageName);
        if (!StringUtils.isBlank(repoAndTag.repos)
                || !StringUtils.isBlank(repoAndTag.tag)) {
            return false;
        }
        DockerClient dockerClient = client;
        try (PullImageCmd pullImageCmd =
                     dockerClient.pullImageCmd(repoAndTag.repos)) {
            pullImageCmd
                    .withTag(repoAndTag.tag)
                    .exec(new PullImageResultCallback() {
                    })
                    .awaitCompletion(1, TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            logError("pull image", imageName, "", e);
        }
        return false;
    }

    public String getImageId(String imageName) {
        if (StringUtils.isBlank(imageName)) {
            return "";
        }
        String sanitizedImageName = sanitizeImageName(imageName);

        try (ListImagesCmd listImagesCmd = client.listImagesCmd()) {
            return listImagesCmd
                    .withDanglingFilter(false)
                    .withImageNameFilter(sanitizedImageName)
                    .exec()
                    .stream()
                    .filter(image -> image.getRepoTags() != null && image.getRepoTags().length > 0)
                    .filter(image -> Arrays.asList(image.getRepoTags()).contains(sanitizedImageName))
                    .map(Image::getId)
                    .findFirst()
                    .orElse("");
        } catch (Exception e) {
            logError("get image id", sanitizedImageName, "", e);
        }
        return "";
    }

    public String sanitizeImageName(String image) {
        List<String> regexList = Arrays.asList( // order matters
                "docker.io/library/(.*)", // docker.io/library/alpine:latest
                "library/(.*)", // library/alpine:latest
                "docker.io/(.*)", // docker.io/repo/image:latest
                "docker.com/(.*)"); // registry.hub.docker.com/repo/image:latest (deprecated)

        for (String regex : regexList) {
            Matcher m = Pattern.compile(regex).matcher(image);
            if (m.find()) {
                return m.group(1);
            }
        }
        return image;
    }

    public boolean removeImage(String imageName) {
        return removeImageById(getImageId(imageName));
    }

    /*
    * If same image has been pulled with multiple repository base names:
    *
    * - docker.io/repo/image:latest
    * and
    * - registry.hub.docker.com/repo/image:latest (e.g)
    *
    * It will return conflict:
    * unable to delete <imageId> (must be forced) - image is referenced in multiple repositories
    * */
    boolean removeImageById(String imageId) {
        if (StringUtils.isBlank(imageId)) {
            return false;
        }
        try (RemoveImageCmd removeImageCmd =
                     client.removeImageCmd(imageId)) {
            removeImageCmd.exec();
            return true;
        } catch (Exception e) {
            logError("remove image", imageId, imageId, e);
        }
        return false;
    }

    /**
     * Docker container
     * 
     */

    public String createContainer(DockerRunRequest dockerRunRequest) {
        if (dockerRunRequest == null) {
            return "";
        }
        String containerName = dockerRunRequest.getContainerName();
        if (StringUtils.isBlank(containerName)) {
            return "";
        }

        String oldContainerId = getContainerId(containerName);
        if (!StringUtils.isBlank(oldContainerId)) {
            logInfo("Container duplicate found",
                    containerName, oldContainerId);
            stopContainer(oldContainerId);
            removeContainer(oldContainerId);
        }

        if (StringUtils.isBlank(getNetworkId(INTERNAL_DOCKER_NETWORK))
                && StringUtils.isBlank(createNetwork(INTERNAL_DOCKER_NETWORK))) {
            return "";
        }

        if (StringUtils.isBlank(dockerRunRequest.getImageUri())) {
            return "";
        }
        try (CreateContainerCmd createContainerCmd = client
                .createContainerCmd(dockerRunRequest.getImageUri())) {
            return buildCreateContainerCmdFromRunRequest(dockerRunRequest, createContainerCmd)
                    .map(CreateContainerCmd::exec)
                    .map(CreateContainerResponse::getId)
                    .orElse("");
        } catch (Exception e) {
            logError("create container", containerName, "", e);
        }
        return "";
    }

    /**
     * Params of the DockerRunRequest need to be passed to the CreateContainerCmd
     * when creating a container
     *
     * @param dockerRunRequest contains information for creating container
     * @return a templated HostConfig
     */
    public Optional<CreateContainerCmd> buildCreateContainerCmdFromRunRequest(DockerRunRequest dockerRunRequest,
                                                                       CreateContainerCmd createContainerCmd) {
        if (dockerRunRequest == null || createContainerCmd == null) {
            return Optional.empty();
        }
        createContainerCmd
                .withName(dockerRunRequest.getContainerName())
                .withHostConfig(buildHostConfigFromRunRequest(dockerRunRequest));

        if (!StringUtils.isBlank(dockerRunRequest.getCmd())) {
            createContainerCmd.withCmd(
                    ArgsUtils.stringArgsToArrayArgs(dockerRunRequest.getCmd()));
        }
        if (dockerRunRequest.getEnv() != null && !dockerRunRequest.getEnv().isEmpty()) {
            createContainerCmd.withEnv(dockerRunRequest.getEnv());
        }
        if (dockerRunRequest.getContainerPort() > 0) {
            createContainerCmd.withExposedPorts(
                    new ExposedPort(dockerRunRequest.getContainerPort()));
        }
        return Optional.of(createContainerCmd);
    }

    /**
     * Some params of the DockerRunRequest need to be passed to the HostConfig
     * instead of the CreateContainerCmd
     *
     * @param dockerRunRequest contains information for setting up host
     *                         when creating a container
     * @return a templated HostConfig
     */
    public HostConfig buildHostConfigFromRunRequest(DockerRunRequest dockerRunRequest) {
        if (dockerRunRequest == null) {
            return null;
        }
        HostConfig hostConfig = HostConfig.newHostConfig()
                .withNetworkMode(INTERNAL_DOCKER_NETWORK);

        if (dockerRunRequest.getBinds() != null && !dockerRunRequest.getBinds().isEmpty()) {
            hostConfig.withBinds(Binds.fromPrimitive(
                    dockerRunRequest.getBinds().toArray(new String[0])));
        }

        return hostConfig;
    }

    public String getContainerName(String containerId) {
        if (StringUtils.isBlank(containerId)) {
            return "";
        }
        try (InspectContainerCmd inspectContainerCmd =
                     client.inspectContainerCmd(containerId)) {
            String name = inspectContainerCmd.exec().getName();
            // docker-java returns '/<container_id>' instead of '<container_id>'
            return name != null ? name.replace("/", "") : "";
        } catch (Exception e) {
            logError("get container name", "", containerId, e);
        }
        return "";
    }

    public String getContainerId(String containerName) {
        if (StringUtils.isBlank(containerName)) {
            return "";
        }
        try (ListContainersCmd listContainersCmd = client.listContainersCmd()) {
            return listContainersCmd
                    .withShowAll(true)
                    .withNameFilter(Collections.singleton(containerName))
                    .exec()
                    .stream()
                    .findFirst()
                    .map(Container::getId)
                    .orElse("");
        } catch (Exception e) {
            logError("get container id", containerName, "", e);
        }
        return "";
    }

    public String getContainerStatus(String containerId) {
        if (StringUtils.isBlank(containerId)) {
            return "";
        }
        try (InspectContainerCmd inspectContainerCmd =
                     client.inspectContainerCmd(containerId)) {
            return inspectContainerCmd.exec()
                    .getState()
                    .getStatus();
        } catch (Exception e) {
            logError("get container status",
                    getContainerName(containerId), containerId, e);
        }
        return "";
    }

    public boolean startContainer(String containerId) {
        if (StringUtils.isBlank(containerId)) {
            return false;
        }
        try (StartContainerCmd startContainerCmd =
                     client.startContainerCmd(containerId)) {
            startContainerCmd.exec();
            return true;
        } catch (Exception e) {
            logError("start container",
                    getContainerName(containerId), containerId, e);
        }
        return false;
    }

    public void waitContainerUntilExitOrTimeout(String containerId,
                                                Date executionTimeoutDate) {
        if (StringUtils.isBlank(containerId)) {
            return;
        }
        boolean isExited = false;
        boolean isTimeout = false;
        int seconds = 0;
        String containerName = getContainerName(containerId);
        while (!isExited && !isTimeout) {
            if (seconds % 60 == 0) { //don't display logs too often
                logInfo("Still running", containerName, containerId);
            }

            WaitUtils.sleep(1);
            isExited = getContainerStatus(containerId).equals("exited");
            isTimeout = new Date().after(executionTimeoutDate);
            seconds++;
        }

        if (isTimeout) {
            log.warn("Container reached timeout, stopping [containerId:{}, " +
                            "containerName:{}]",
                    containerName, containerId);
        }
    }

    public Optional<DockerLogs> getContainerLogs(String containerId) {
        if (StringUtils.isBlank(containerId)) {
            return Optional.empty();
        }
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();

        try (LogContainerCmd logContainerCmd =
                     client.logContainerCmd(containerId)) {
            logContainerCmd
                    .withStdOut(true)
                    .withStdErr(true)
                    .exec(new ExecStartResultCallback(stdout, stderr))
                    .awaitCompletion();
        } catch (Exception e) {
            logError("get docker logs",
                    getContainerName(containerId), containerId, e);
            return Optional.empty();
        }
        return Optional.of(DockerLogs.builder()
                .stdout(stdout.toString())
                .stderr(stderr.toString())
                .build());
    }

    public boolean stopContainer(String containerId) {
        if (StringUtils.isBlank(containerId)) {
            return false;
        }
        List<String> statusesToStop = Arrays.asList("restarting", "running");
        if (!statusesToStop.contains(getContainerStatus(containerId))) {
            return true;
        }
        try (StopContainerCmd stopContainerCmd =
                     client.stopContainerCmd(containerId)) {
            stopContainerCmd.exec();
            return true;
        } catch (Exception e) {
            logError("stop container",
                    getContainerName(containerId), containerId, e);
        }
        return false;
    }

    public boolean removeContainer(String containerId) {
        if (StringUtils.isBlank(containerId)) {
            return false;
        }
        try (RemoveContainerCmd removeContainerCmd =
                     client.removeContainerCmd(containerId)) {
            removeContainerCmd.exec();
            return true;
        } catch (Exception e) {
            logError("remove container", "", containerId, e);
        }
        return false;
    }

    public Optional<DockerLogs> exec(String containerName, String... cmd) {
        String containerId = getContainerId(containerName);
        if (containerId.isEmpty()) {
            return Optional.empty();
        }
        // create 'docker exec' command
        ExecCreateCmdResponse execCreateCmdResponse = client.execCreateCmd(containerId)
                .withAttachStderr(true)
                .withAttachStdout(true)
                .withCmd(cmd)
                .exec();
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();
        ExecStartResultCallback execStartResultCallback = new ExecStartResultCallback(stdout, stderr);
        // run 'docker exec' command
        try (ExecStartCmd execStartCmd = client.execStartCmd(execCreateCmdResponse.getId())) {
            execStartCmd
                    .exec(execStartResultCallback)
                    .awaitCompletion();
        } catch (Exception e) {
            log.error("Error running docker exec command [containerName:{}, cmd:{}]",
                    containerName, cmd, e);
            return Optional.empty();
        }
        return Optional.of(DockerLogs.builder()
                .stdout(stdout.toString())
                .stderr(stderr.toString())
                .build());
    }

    /**
     * Docker client
     * 
     */
    private DockerClient createClient(String username, String password) {
        DefaultDockerClientConfig.Builder configBuilder =
                DefaultDockerClientConfig.createDefaultConfigBuilder()
                        .withDockerTlsVerify(false);

        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            configBuilder.withRegistryUsername(username)
                    .withRegistryPassword(password);
        }

        DefaultDockerClientConfig config = configBuilder.build();

        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .build();
        return DockerClientImpl.getInstance(config, httpClient);
    }

    private void logInfo(String infoMessage, String name, String id) {
        log.info("{} [name:'{}', id:'{}']", infoMessage, name, id);
    }

    private void logError(String failureContext, String name, String id,
                          Exception exception) {
        log.error("Failed to {} [name:'{}', id:'{}']",
                failureContext, name, id, exception);
    }
}
