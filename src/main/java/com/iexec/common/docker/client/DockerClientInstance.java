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
import com.iexec.common.docker.DockerLogs;
import com.iexec.common.docker.DockerRunRequest;
import com.iexec.common.docker.DockerRunResponse;
import com.iexec.common.utils.ArgsUtils;
import com.iexec.common.utils.WaitUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@SuppressWarnings("deprecation") // ExecStartResultCallback
public class DockerClientInstance {

    private static final String RUNNING_STATUS = "running";
    private static final String RESTARTING_STATUS = "restarting";
    private static final String EXITED_STATUS = "exited";

    @Getter
    private DockerClient client;

    DockerClientInstance() {
        this.client = createClient("", "");
    }

    DockerClientInstance(String username, String password) {
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
        try (CreateVolumeCmd createVolumeCmd = this.client.createVolumeCmd()) {
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
        return getVolume(volumeName).isPresent();
    }

    public Optional<InspectVolumeResponse> getVolume(String volumeName) {
        if (StringUtils.isBlank(volumeName)) {
            return Optional.empty();
        }
        try (ListVolumesCmd listVolumesCmd = this.client.listVolumesCmd()) {
            List<InspectVolumeResponse> volumes = listVolumesCmd
                    .withFilter("name", Collections.singletonList(volumeName))
                    .exec()
                    .getVolumes();
            List<InspectVolumeResponse> filtered = volumes.stream()
                    .filter(volume -> volumeName.equals(volume.getName()))
                    .collect(Collectors.toList());
            return filtered.stream().findFirst();
        } catch (Exception e) {
            log.error("Error getting docker volume [name:{}]", volumeName, e);
            return Optional.empty();
        }
    }

    public boolean removeVolume(String volumeName) {
        if (StringUtils.isBlank(volumeName)) {
            return false;
        }
        try (RemoveVolumeCmd removeVolumeCmd = this.client.removeVolumeCmd(volumeName)) {
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
        try (CreateNetworkCmd networkCmd = this.client.createNetworkCmd()) {
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
        try (ListNetworksCmd listNetworksCmd = this.client.listNetworksCmd()) {
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
                     this.client.removeNetworkCmd(networkName)) {
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

    public boolean isImagePresent(String image) {
        return getImageId(image).isEmpty();
    }

    /**
     * Pull docker image and timeout after 1 minute.
     * 
     * @param imageName
     * @return true if image is pulled successfully,
     * false otherwise.
     */
    public boolean pullImage(String imageName) {
        if (StringUtils.isBlank(imageName)) {
            return false;
        }
        NameParser.ReposTag repoAndTag = NameParser.parseRepositoryTag(imageName);
        if (StringUtils.isBlank(repoAndTag.repos)
                || StringUtils.isBlank(repoAndTag.tag)) {
            return false;
        }
        try (PullImageCmd pullImageCmd =
                    this.client.pullImageCmd(repoAndTag.repos)) {
            pullImageCmd
                    .withTag(repoAndTag.tag)
                    .exec(new PullImageResultCallback() {})
                    .awaitCompletion(1, TimeUnit.MINUTES);
            log.info("Pulled docker image [name:{}]", imageName);
            return true;
        } catch (Exception e) {
            log.error("Error pulling docker image [name:{}]", imageName, e);
            return false;
        }
    }

    public String getImageId(String imageName) {
        if (StringUtils.isBlank(imageName)) {
            return "";
        }
        String sanitizedImageName = sanitizeImageName(imageName);

        try (ListImagesCmd listImagesCmd = this.client.listImagesCmd()) {
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
            log.error("Error getting docker image id [name:{}]", imageName, e);
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

    // TODO remove image by name to avoid conflict error
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
                     this.client.removeImageCmd(imageId)) {
            removeImageCmd.exec();
            return true;
        } catch (Exception e) {
            log.error("Error removing docker image by id [id:{}]", imageId, e);
            return false;
        }
    }

    /**
     * Docker container
     * 
     */

    public DockerRunResponse run(DockerRunRequest dockerRunRequest) {
        DockerRunResponse dockerRunResponse = DockerRunResponse.builder()
                .isSuccessful(false)
                .build();
        if (!pullImage(dockerRunRequest.getImageUri())) {
            return dockerRunResponse;
        }
        String containerId = createContainer(dockerRunRequest);
        if (containerId.isEmpty()) {
            return dockerRunResponse;
        }
        if (!startContainer(containerId)) {
            removeContainer(containerId);
            return dockerRunResponse;
        }
        if (dockerRunRequest.getMaxExecutionTime() < 0) {
            // container will run until self-exited or explicitly-stopped
            dockerRunResponse.setSuccessful(true);
            return dockerRunResponse;
        }
        Instant timeoutDate = Instant.now()
                .plusMillis(dockerRunRequest.getMaxExecutionTime());
        waitContainerUntilExitOrTimeout(containerId, timeoutDate);
        if (!stopContainer(containerId)) {
            return dockerRunResponse;
        }
        getContainerLogs(containerId).ifPresent(containerLogs -> {
            dockerRunResponse.setDockerLogs(containerLogs);
        });
        if (!removeContainer(containerId)) {
            return dockerRunResponse;
        }
        dockerRunResponse.setSuccessful(true);
        return dockerRunResponse;
    }

    public boolean stopAndRemoveContainer(String containerName) {
        return stopContainer(containerName)
                && removeContainer(containerName);
    }

    public String createContainer(DockerRunRequest dockerRunRequest) {
        if (dockerRunRequest == null
                || StringUtils.isBlank(dockerRunRequest.getImageUri())
                || StringUtils.isBlank(dockerRunRequest.getContainerName())) {
            return "";
        }
        String containerName = dockerRunRequest.getContainerName();
        String oldContainerId = getContainerId(containerName);
        // clean duplicate if present
        if (!StringUtils.isBlank(oldContainerId)) {
            log.info("Found duplicate container [name:{}, oldContainerId:{}]",
                    containerName, oldContainerId);
            stopContainer(oldContainerId);
            removeContainer(oldContainerId);
        }
        // create network if needed
        String network = dockerRunRequest.getDockerNetwork();
        if (StringUtils.isNotBlank(network)
                && StringUtils.isBlank(createNetwork(network))) {
            return "";
        }
        // create container
        try (CreateContainerCmd createContainerCmd = client
                .createContainerCmd(dockerRunRequest.getImageUri())) {
            String containerId =
                    buildCreateContainerCmdFromRunRequest(dockerRunRequest, createContainerCmd)
                            .map(CreateContainerCmd::exec)
                            .map(CreateContainerResponse::getId)
                            .orElse("");
            if (StringUtils.isNotBlank(containerId)) {
                log.info("Created docker container [name:{}, id:{}]",
                        containerName, containerId);
            }
            return containerId;
        } catch (Exception e) {
            log.error("Error creating docker container [name:{}]", containerName, e);
            return "";
        }
    }

    /**
     * Params of the DockerRunRequest need to be passed to the CreateContainerCmd
     * when creating a container
     *
     * @param dockerRunRequest contains information for creating container
     * @return a templated HostConfig
     */
    public Optional<CreateContainerCmd> buildCreateContainerCmdFromRunRequest(
            DockerRunRequest dockerRunRequest,
            CreateContainerCmd createContainerCmd
    ) {
        if (dockerRunRequest == null || createContainerCmd == null) {
            return Optional.empty();
        }
        createContainerCmd
                .withName(dockerRunRequest.getContainerName())
                .withHostConfig(buildHostConfigFromRunRequest(dockerRunRequest));
        if (StringUtils.isNotBlank(dockerRunRequest.getCmd())) {
            createContainerCmd.withCmd(
                    ArgsUtils.stringArgsToArrayArgs(dockerRunRequest.getCmd()));
        }
        // here the entrypoint can be an empty string
        // to override the default behavior
        if (dockerRunRequest.getEntrypoint() != null) {
            createContainerCmd.withEntrypoint(
                    ArgsUtils.stringArgsToArrayArgs(dockerRunRequest.getEntrypoint()));
        }
        if (dockerRunRequest.getEnv() != null && !dockerRunRequest.getEnv().isEmpty()) {
            createContainerCmd.withEnv(dockerRunRequest.getEnv());
        }
        if (dockerRunRequest.getContainerPort() > 0) {
            createContainerCmd.withExposedPorts(
                    new ExposedPort(dockerRunRequest.getContainerPort()));
        }
        if (dockerRunRequest.getWorkingDir() != null) {
            createContainerCmd.withWorkingDir(dockerRunRequest.getWorkingDir());
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
        HostConfig hostConfig = HostConfig.newHostConfig();
        if (StringUtils.isNotBlank(dockerRunRequest.getDockerNetwork())) {
            hostConfig.withNetworkMode(dockerRunRequest.getDockerNetwork());
        }
        if (dockerRunRequest.getBinds() != null && !dockerRunRequest.getBinds().isEmpty()) {
            hostConfig.withBinds(Binds.fromPrimitive(
                    dockerRunRequest.getBinds().toArray(new String[0])));
        }
        return hostConfig;
    }

    // public String getContainerName(String containerId) {
    //     if (StringUtils.isBlank(containerId)) {
    //         return "";
    //     }
    //     try (InspectContainerCmd inspectContainerCmd =
    //                  this.client.inspectContainerCmd(containerId)) {
    //         String name = inspectContainerCmd.exec().getName();
    //         // docker-java returns '/<container_id>' instead of '<container_id>'
    //         return name != null ? name.replace("/", "") : "";
    //     } catch (Exception e) {
    //         log.error("Error getting docker container name [id:{}]", containerId, e);
    //         return "";
    //     }
    // }

    public String getContainerId(String containerName) {
        if (StringUtils.isBlank(containerName)) {
            return "";
        }
        try (ListContainersCmd listContainersCmd = this.client.listContainersCmd()) {
            return listContainersCmd
                    .withShowAll(true)
                    .withNameFilter(Collections.singleton(containerName))
                    .exec()
                    .stream()
                    .findFirst()
                    .map(Container::getId)
                    .orElse("");
        } catch (Exception e) {
            log.error("Error getting docker container id [name:{}]", containerName, e);
            return "";
        }
    }

    public String getContainerStatus(String containerName) {
        if (StringUtils.isBlank(containerName)) {
            return "";
        }
        try (InspectContainerCmd inspectContainerCmd =
                    this.client.inspectContainerCmd(containerName)) {
            return inspectContainerCmd.exec()
                    .getState()
                    .getStatus();
        } catch (Exception e) {
            log.error("Error getting docker container status [name:{}]", containerName, e);
        }
        return "";
    }

    public boolean startContainer(String containerName) {
        if (StringUtils.isBlank(containerName)) {
            return false;
        }
        try (StartContainerCmd startContainerCmd =
                    this.client.startContainerCmd(containerName)) {
            startContainerCmd.exec();
            log.info("Started docker container [name:{}]", containerName);
            return true;
        } catch (Exception e) {
            log.error("Error starting docker container [name:{}]", containerName, e);
            return false;
        }
    }

    /**
     * Wait for a container to exit or timeout.
     * @param containerName
     * @param timeoutDate
     * @return true if the container exited before
     * the timeout, false if not.
     */
    public boolean waitContainerUntilExitOrTimeout(
            String containerName,
            Instant timeoutDate
    ) {
        if (StringUtils.isBlank(containerName)) {
            throw new IllegalArgumentException("Container name cannot be blank");
        }
        if (timeoutDate == null) {
            throw new IllegalArgumentException("Timeout date cannot be null");
        }
        boolean isExited = false;
        boolean isTimeout = false;
        int seconds = 0;
        while (!isExited && !isTimeout) {
            if (seconds % 2 == 0) { // don't display logs too often
                log.info("Container is running [name:{}]", containerName);
            }
            WaitUtils.sleep(1);
            isExited = getContainerStatus(containerName).equals(EXITED_STATUS);
            isTimeout = Instant.now().isAfter(timeoutDate);
            seconds++;
        }
        if (isTimeout) {
            log.warn("Container reached timeout [name:{}]", containerName);
        }
        return !isTimeout;
    }

    public Optional<DockerLogs> getContainerLogs(String containerName) {
        if (StringUtils.isBlank(containerName)) {
            return Optional.empty();
        }
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();
        try (LogContainerCmd logContainerCmd =
                    this.client.logContainerCmd(containerName)) {
            logContainerCmd
                    .withStdOut(true)
                    .withStdErr(true)
                    .exec(new ExecStartResultCallback(stdout, stderr))
                    .awaitCompletion();
        } catch (Exception e) {
            log.error("Error getting docker container logs [name:{}]", containerName);
            return Optional.empty();
        }
        return Optional.of(DockerLogs.builder()
                .stdout(stdout.toString())
                .stderr(stderr.toString())
                .build());
    }

    public boolean stopContainer(String containerName) {
        if (StringUtils.isBlank(containerName)) {
            return false;
        }
        List<String> statusesToStop = Arrays.asList(RESTARTING_STATUS, RUNNING_STATUS);
        if (!statusesToStop.contains(getContainerStatus(containerName))) {
            return true;
        }
        try (StopContainerCmd stopContainerCmd =
                    this.client.stopContainerCmd(containerName)) {
            stopContainerCmd.exec();
            log.info("Stopped docker container [name:{}]", containerName);
            return true;
        } catch (Exception e) {
            log.error("Error stopping docker container [name:{}]", containerName, e);
            return false;
        }
    }

    public boolean removeContainer(String containerName) {
        if (StringUtils.isBlank(containerName)) {
            return false;
        }
        try (RemoveContainerCmd removeContainerCmd =
                    this.client.removeContainerCmd(containerName)) {
            removeContainerCmd.exec();
            log.info("Removed docker container [name:{}]", containerName);
            return true;
        } catch (Exception e) {
            log.error("Error removing docker container [name:{}]", containerName, e);
            return false;
        }
    }

    public Optional<DockerLogs> exec(String containerName, String... cmd) {
        if (StringUtils.isBlank(containerName)) {
            return Optional.empty();
        }
        // create 'docker exec' command
        ExecCreateCmdResponse execCreateCmdResponse = this.client.execCreateCmd(containerName)
                .withAttachStderr(true)
                .withAttachStdout(true)
                .withCmd(cmd)
                .exec();
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();
        // run 'docker exec' command
        try (ExecStartCmd execStartCmd = this.client.execStartCmd(execCreateCmdResponse.getId())) {
            execStartCmd
                    .exec(new ExecStartResultCallback(stdout, stderr))
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
}
