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

    public static final String CREATED_STATUS = "created";
    public static final String RUNNING_STATUS = "running";
    public static final String RESTARTING_STATUS = "restarting";
    public static final String EXITED_STATUS = "exited";

    private DockerClient client;

    DockerClientInstance() {
        this.client = createClient("", "");
    }

    DockerClientInstance(String username, String password) {
        this.client = createClient(username, password);
    }

    public DockerClient getClient() {
        return this.client;
    }

    /**
     * Docker volume
     * 
     */

    public synchronized boolean createVolume(String volumeName) {
        if (StringUtils.isBlank(volumeName)) {
            log.error("Invalid docker volume name [name:{}]", volumeName);
            return false;
        }
        if (isVolumePresent(volumeName)) {
            log.info("Docker volume already present [name:{}]", volumeName);
            return true;
        }
        try (CreateVolumeCmd createVolumeCmd = getClient().createVolumeCmd()) {
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
        try (ListVolumesCmd listVolumesCmd = getClient().listVolumesCmd()) {
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

    public synchronized boolean removeVolume(String volumeName) {
        if (StringUtils.isBlank(volumeName)) {
            return false;
        }
        if (!isVolumePresent(volumeName)) {
            log.info("No docker volume to remove [name:{}]", volumeName);
            return false;
        }
        try (RemoveVolumeCmd removeVolumeCmd = getClient().removeVolumeCmd(volumeName)) {
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

    public synchronized String createNetwork(String networkName) {
        if (StringUtils.isBlank(networkName)) {
            log.error("Invalid docker network name [name:{}]", networkName);
            return "";
        }
        if (isNetworkPresent(networkName)) {
            log.info("Docker network already present [name:{}]", networkName);
            return getNetworkId(networkName);
        }
        try (CreateNetworkCmd networkCmd = getClient().createNetworkCmd()) {
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
            log.error("Invalid docker network name [name:{}]", networkName);
            return "";
        }
        try (ListNetworksCmd listNetworksCmd = getClient().listNetworksCmd()) {
            return listNetworksCmd
                    .withNameFilter(networkName)
                    .exec()
                    .stream()
                    .filter(network -> StringUtils.isNotBlank(network.getName()))
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

    public synchronized boolean removeNetwork(String networkName) {
        if (StringUtils.isBlank(networkName)) {
            log.error("Invalid docker network name [name:{}]", networkName);
            return false;
        }
        if (!isNetworkPresent(networkName)) {
            log.info("No docker network to remove [name:{}]", networkName);
            return false;
        }
        try (RemoveNetworkCmd removeNetworkCmd =
                     getClient().removeNetworkCmd(networkName)) {
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

    /**
     * Pull docker image and timeout after 1 minute.
     * 
     * @param imageName
     * @return true if image is pulled successfully,
     * false otherwise.
     */
    public boolean pullImage(String imageName) {
        if (StringUtils.isBlank(imageName)) {
            log.error("Invalid docker image name [name:{}]", imageName);
            return false;
        }
        NameParser.ReposTag repoAndTag = NameParser.parseRepositoryTag(imageName);
        if (StringUtils.isBlank(repoAndTag.repos)
                || StringUtils.isBlank(repoAndTag.tag)) {
            log.error("Error parsing docker image name [name:{}, repo:{}, tag:{}]",
                    imageName, repoAndTag.repos, repoAndTag.tag);
            return false;
        }
        try (PullImageCmd pullImageCmd =
                    getClient().pullImageCmd(repoAndTag.repos)) {
            log.info("Pulling docker image [name:{}]", imageName);
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
            log.error("Invalid docker image name [name:{}]", imageName);
            return "";
        }
        String sanitizedImageName = sanitizeImageName(imageName);

        try (ListImagesCmd listImagesCmd = getClient().listImagesCmd()) {
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
            return "";
        }
    }

    /**
     * Remove "docker.io" and "library" from image name.
     * @param image
     * @return
     */
    public String sanitizeImageName(String image) {
        List<String> regexList = Arrays.asList( // order matters
                "docker.io/library/(.*)", // docker.io/library/alpine:latest
                "library/(.*)", // library/alpine:latest
                "docker.io/(.*)"); // docker.io/repo/image:latest

        for (String regex : regexList) {
            Matcher m = Pattern.compile(regex).matcher(image);
            if (m.find()) {
                return m.group(1);
            }
        }
        return image;
    }

    public boolean isImagePresent(String imageName) {
        return !getImageId(imageName).isEmpty();
    }

    public synchronized boolean removeImage(String imageName) {
        if (StringUtils.isBlank(imageName)) {
            // TODO throw new IllegalArgumentException("Image name cannot be blank");
            log.error("Docker image name cannot be blank");
            return false;
        }
        if (!isImagePresent(imageName)) {
            log.info("No docker image to remove [name:{}]", imageName);
            return false;
        }
        try (RemoveImageCmd removeImageCmd =
                    getClient().removeImageCmd(imageName)) {
            removeImageCmd.exec();
            log.info("Removed docker image [name:{}]", imageName);
            return true;
        } catch (Exception e) {
            log.error("Error removing docker image [name:{}]", imageName, e);
            return false;
        }
    }

    /**
     * Docker container
     * 
     */

    /**
     * Run a docker container with the specified config.
     * If maxExecutionTime is 0, the container will run
     * in detached mode, thus, we return immediately without
     * waiting for it to exit.
     * <p>
     * If the needed docker image is not available locally,
     * it will be downloaded but will not be removed since
     * it could be attached to other containers.
     * @param dockerRunRequest config of the run
     * @return a response with metadata and success or failure
     * status.
     */
    public DockerRunResponse run(DockerRunRequest dockerRunRequest) {
        log.info("Running docker container [name:{}, image:{}, cmd:{}]",
                dockerRunRequest.getContainerName(), dockerRunRequest.getImageUri(),
                dockerRunRequest.getArrayArgsCmd());
        DockerRunResponse dockerRunResponse = DockerRunResponse.builder()
                .isSuccessful(false)
                .build();
        if (!pullImage(dockerRunRequest.getImageUri())) {
            return dockerRunResponse;
        }
        // TODO choose to remove duplicate containers or not
        String containerId = createContainer(dockerRunRequest);
        if (containerId.isEmpty()) {
            return dockerRunResponse;
        }
        String containerName = dockerRunRequest.getContainerName();
        if (!startContainer(containerName)) {
            removeContainer(containerName);
            return dockerRunResponse;
        }
        if (dockerRunRequest.getMaxExecutionTime() == 0) {
            // container will run until self-exited or explicitly-stopped
            dockerRunResponse.setSuccessful(true);
            return dockerRunResponse;
        }
        Instant timeoutDate = Instant.now()
                .plusMillis(dockerRunRequest.getMaxExecutionTime());
        Long exitCode = waitContainerUntilExitOrTimeout(containerName, timeoutDate);
        dockerRunResponse.setContainerExitCode(exitCode);
        boolean isTimeout = exitCode == null;
        boolean isSuccessful = exitCode == 0;
        if (isTimeout && !stopContainer(containerName)) {
            return dockerRunResponse;
        }
        getContainerLogs(containerName).ifPresent(containerLogs -> {
            dockerRunResponse.setDockerLogs(containerLogs);
        });
        if (!removeContainer(containerName)) {
            return dockerRunResponse;
        }
        dockerRunResponse.setSuccessful(isSuccessful);
        return dockerRunResponse;
    }

    public boolean stopAndRemoveContainer(String containerName) {
        return stopContainer(containerName)
                && removeContainer(containerName);
    }

    /**
     * Create docker container and remove existing
     * duplicate if found.
     * 
     * @param dockerRunRequest
     * @return created container id or an empty
     * string if a problem occurs
     */
    public String createContainer(DockerRunRequest dockerRunRequest) {
        return createContainer(dockerRunRequest, true);
    }

    /**
     * Create docker container and choose whether to
     * remove existing duplicate container (if found)
     * or not.
     * 
     * @param dockerRunRequest
     * @param removeDuplicate
     * @return newly created container id if removeDuplicate is
     * true, otherwise an empty string.
     */
    public synchronized String createContainer(DockerRunRequest dockerRunRequest, boolean removeDuplicate) {
        if (dockerRunRequest == null
                || StringUtils.isBlank(dockerRunRequest.getImageUri())
                || StringUtils.isBlank(dockerRunRequest.getContainerName())) {
            return "";
        }
        String containerName = dockerRunRequest.getContainerName();
        String oldContainerId = getContainerId(containerName);
        // clean duplicate if present
        if (StringUtils.isNotBlank(oldContainerId)) {
            log.info("Found duplicate container [name:{}, oldContainerId:{}, removeDuplicate:{}]",
                    containerName, oldContainerId, removeDuplicate);
            if (!removeDuplicate) {
                return "";
            }
            stopContainer(containerName);
            removeContainer(containerName);
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
        if (dockerRunRequest.getDevices() != null) {
            dockerRunRequest.getDevices().forEach(device ->
                    hostConfig.withDevices(Device.parse(device))
            );
        }
        return hostConfig;
    }

    public boolean isContainerPresent(String containerName) {
        return !getContainerId(containerName).isEmpty();
    }

    public String getContainerName(String containerId) {
        if (StringUtils.isBlank(containerId)) {
            // TODO throw IllegalArgumentException
            log.error("Invalid docker container id [id:{}]", containerId);
            return "";
        }
        try (InspectContainerCmd inspectContainerCmd =
                     getClient().inspectContainerCmd(containerId)) {
            String name = inspectContainerCmd.exec().getName();
            // docker-java returns '/<container_id>' instead of '<container_id>'
            return name != null ? name.replace("/", "") : "";
        } catch (Exception e) {
            log.error("Error getting docker container name [id:{}]", containerId, e);
            return "";
        }
    }

    public String getContainerId(String containerName) {
        if (StringUtils.isBlank(containerName)) {
            log.error("Invalid docker container name [name:{}]", containerName);
            return "";
        }
        try (ListContainersCmd listContainersCmd = getClient().listContainersCmd()) {
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
                    getClient().inspectContainerCmd(containerName)) {
            return inspectContainerCmd.exec()
                    .getState()
                    .getStatus();
        } catch (Exception e) {
            log.error("Error getting docker container status [name:{}]", containerName, e);
        }
        return "";
    }

    public synchronized boolean startContainer(String containerName) {
        if (StringUtils.isBlank(containerName)) {
            return false;
        }
        try (StartContainerCmd startContainerCmd =
                    getClient().startContainerCmd(containerName)) {
            startContainerCmd.exec();
            log.info("Started docker container [name:{}]", containerName);
            return true;
        } catch (Exception e) {
            log.error("Error starting docker container [name:{}]", containerName, e);
            return false;
        }
    }

    /**
     * Waits for full execution of a container (and stops waiting after a
     * particular date)
     * @param containerName name of the container to wait for
     * @param timeoutDate waiting is aborted once this date is reached
     * @return container's exit code (when relevant)
     */
    public Long waitContainerUntilExitOrTimeout(
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
            if (seconds % 60 == 0) { // don't display logs too often
                log.info("Container is running [name:{}]", containerName);
            }
            WaitUtils.sleep(1);
            isExited = getContainerStatus(containerName).equals(EXITED_STATUS);
            isTimeout = Instant.now().isAfter(timeoutDate);
            seconds++;
        }
        if (isExited) {
            Long containerExitCode = getContainerExitCode(containerName);
            log.info("Container exited by itself [name:{}, exitCode:{}]",
                    containerName, containerExitCode);
            return containerExitCode;
        }
        log.warn("Container reached timeout [name:{}]", containerName);
        return null;
    }

    public Long getContainerExitCode(String containerName) {
        if (StringUtils.isBlank(containerName)) {
            // TODO throw new IllegalArgumentException("Container name cannot be blank");
            log.error("Invalid docker container name [name:{}]", containerName);
            return null;
        }
        try (InspectContainerCmd inspectContainerCmd =
                     getClient().inspectContainerCmd(containerName)) {
            return inspectContainerCmd.exec()
                    .getState()
                    .getExitCodeLong();
        } catch (Exception e) {
            log.error("Error getting container exit code [name:{}]", containerName, e);
            return null;
        }
    }

    public Optional<DockerLogs> getContainerLogs(String containerName) {
        if (StringUtils.isBlank(containerName)) {
            log.error("Invalid docker container name [name:{}]", containerName);
            return Optional.empty();
        }
        if (!isContainerPresent(containerName)) {
            log.error("Cannot get logs of inexistent docker container [name:{}]", containerName);
            return Optional.empty();
        }
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();
        try (LogContainerCmd logContainerCmd =
                    getClient().logContainerCmd(containerName)) {
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

    public synchronized boolean stopContainer(String containerName) {
        if (StringUtils.isBlank(containerName)) {
            log.info("Invalid docker container name [name:{}]", containerName);
            return false;
        }
        if (!isContainerPresent(containerName)) {
            log.error("No docker container to stop [name:{}]", containerName);
            return false;
        }
        List<String> statusesToStop = Arrays.asList(RESTARTING_STATUS, RUNNING_STATUS);
        if (!statusesToStop.contains(getContainerStatus(containerName))) {
            return true;
        }
        try (StopContainerCmd stopContainerCmd =
                    getClient().stopContainerCmd(containerName)) {
            stopContainerCmd.exec();
            log.info("Stopped docker container [name:{}]", containerName);
            return true;
        } catch (Exception e) {
            log.error("Error stopping docker container [name:{}]", containerName, e);
            return false;
        }
    }

    public synchronized boolean removeContainer(String containerName) {
        if (StringUtils.isBlank(containerName)) {
            log.error("Invalid docker container name [name:{}]", containerName);
            return false;
        }
        if (!isContainerPresent(containerName)) {
            log.info("No docker container to remove [name:{}]", containerName);
            return false;
        }
        try (RemoveContainerCmd removeContainerCmd =
                    getClient().removeContainerCmd(containerName)) {
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
        ExecCreateCmdResponse execCreateCmdResponse = getClient().execCreateCmd(containerName)
                .withAttachStderr(true)
                .withAttachStdout(true)
                .withCmd(cmd)
                .exec();
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();
        // run 'docker exec' command
        try (ExecStartCmd execStartCmd = getClient().execStartCmd(execCreateCmdResponse.getId())) {
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
