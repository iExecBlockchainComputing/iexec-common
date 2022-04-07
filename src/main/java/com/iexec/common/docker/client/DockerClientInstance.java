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
import com.github.dockerjava.api.exception.DockerException;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.core.NameParser;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.iexec.common.docker.DockerLogs;
import com.iexec.common.docker.DockerRunRequest;
import com.iexec.common.docker.DockerRunResponse;
import com.iexec.common.utils.ArgsUtils;
import com.iexec.common.utils.WaitUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@SuppressWarnings("deprecation") // ExecStartResultCallback
public class DockerClientInstance {

    // default docker registry address
    public static final String DEFAULT_DOCKER_REGISTRY = "docker.io";

    public static final String CREATED_STATUS = "created";
    public static final String RUNNING_STATUS = "running";
    public static final String RESTARTING_STATUS = "restarting";
    public static final String EXITED_STATUS = "exited";

    private final DockerClient client;

    /**
     * Create a new unauthenticated Docker client instance with the default Docker registry
     * {@link DockerClientInstance#DEFAULT_DOCKER_REGISTRY}.
     */
    DockerClientInstance() {
        this.client = createClient(DEFAULT_DOCKER_REGISTRY, "", "");
    }

    /**
     * Create a new unauthenticated Docker client instance with the specified Docker registry
     * address.
     * 
     * @param registryAddress
     * @throws IllegalArgumentException if registry address is blank
     */
    DockerClientInstance(String registryAddress) {
        if (StringUtils.isBlank(registryAddress)) {
            throw new IllegalArgumentException("Docker registry address must not be blank");
        }
        this.client = createClient(registryAddress, "", "");
    }

    /**
     * Create a new authenticated Docker client instance. The created client will be
     * authenticated against the provided registry.
     * 
     * @param registryAddress e.g. {@code https://index.docker.io/v1/, https://nexus.iex.ec,
     *                          docker.io, nexus.iex.ec}
     * @param username
     * @param password
     */
    DockerClientInstance(String registryAddress, String username, String password) {
        if (StringUtils.isBlank(registryAddress)) {
            throw new IllegalArgumentException("Docker registry address must not be blank");
        }
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("Docker registry username must not be blank");
        }
        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("Docker registry password must not be blank");
        }
        this.client = createClient(registryAddress, username, password);
    }

    public DockerClient getClient() {
        return this.client;
    }

    /*
     * Docker volume
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

    /*
     * Docker network
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

    /*
     * Docker image
     */

    /**
     * Pull docker image and timeout after 1 minute.
     * 
     * @param imageName Name of the image to pull
     * @return true if image is pulled successfully,
     * false otherwise.
     */
    public boolean pullImage(String imageName) {
        return pullImage(imageName, Duration.of(1, ChronoUnit.MINUTES));
    }

    /**
     * Pull docker image and timeout after given duration.
     *
     * @param imageName Name of the image to pull
     * @param timeout Duration to wait before timeout
     * @return true if image is pulled successfully,
     * false otherwise.
     */
    public boolean pullImage(String imageName, Duration timeout) {
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
            boolean isPulledBeforeTimeout = pullImageCmd
                    .withTag(repoAndTag.tag)
                    .exec(new PullImageResultCallback() {})
                    .awaitCompletion(timeout.toSeconds(), TimeUnit.SECONDS);
            if (!isPulledBeforeTimeout){
                log.error("Docker image has not been pulled (timeout) [name:{}, timeout:{}s]",
                        imageName, timeout.toSeconds());
                return false;
            }
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

    /*
     * Docker container
     */

    /**
     * Run a docker container with the specified config.
     * If maxExecutionTime is les or equal to 0, the container
     * will run in detached mode, thus, we return immediately
     * without waiting for it to exit.
     * 
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
                .containerExitCode(-1)
                .build();
        String containerName = dockerRunRequest.getContainerName();
        // TODO choose to remove duplicate containers or not
        if (createContainer(dockerRunRequest).isEmpty()) {
            log.error("Failed to create container for docker run [name:{}]", containerName);
            return dockerRunResponse;
        }
        if (!startContainer(containerName)) {
            log.error("Failed to start container for docker run [name:{}]", containerName);
            removeContainer(containerName);
            return dockerRunResponse;
        }
        if (dockerRunRequest.getMaxExecutionTime() <= 0) {
            // container will run until self-exited or explicitly-stopped
            log.info("Docker container will run in detached mode [name:{}]", containerName);
            dockerRunResponse.setSuccessful(true);
            return dockerRunResponse;
        }
        Instant timeoutDate = Instant.now()
                .plusMillis(dockerRunRequest.getMaxExecutionTime());
        int exitCode = waitContainerUntilExitOrTimeout(containerName, timeoutDate);
        dockerRunResponse.setContainerExitCode(exitCode);
        boolean isTimeout = exitCode == -1;
        boolean isSuccessful = !isTimeout && exitCode == 0L;
        if (isTimeout && !stopContainer(containerName)) {
            log.error("Failed to force-stop container after timeout [name:{}]", containerName);
            return dockerRunResponse;
        }
        getContainerLogs(containerName).ifPresent(dockerRunResponse::setDockerLogs);
        if (!removeContainer(containerName)) {
            log.error("Failed to remove container after run [name:{}]", containerName);
            return dockerRunResponse;
        }
        log.info("Finished running docker container [name:{}, isSuccessful:{}]",
                containerName, isSuccessful);
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
            log.error("Invalid docker run request [dockerRunRequest:{}]", dockerRunRequest);
            return "";
        }
        String containerName = dockerRunRequest.getContainerName();
        // clean duplicate if present
        if (isContainerPresent(containerName)) {
            log.info("Found duplicate container [name:{}, oldContainerId:{}, removeDuplicate:{}]",
                    containerName, getContainerId(containerName), removeDuplicate);
            if (!removeDuplicate) {
                return "";
            }
            stopContainer(containerName);
            removeContainer(containerName);
        }
        // create network if needed
        String networkName = dockerRunRequest.getDockerNetwork();
        if (StringUtils.isNotBlank(networkName)
                && StringUtils.isBlank(createNetwork(networkName))) {
            log.error("Failed to create network for the container [name:{}, networkName:{}]",
                    containerName, networkName);
            return "";
        }
        // create container
        try (CreateContainerCmd createContainerCmd = getClient()
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
        final String dockerNetworkName = dockerRunRequest.getDockerNetwork();
        if (StringUtils.isNotBlank(dockerNetworkName)) {
            hostConfig.withNetworkMode(dockerNetworkName);
        }
        final List<String> binds = dockerRunRequest.getBinds();
        if (!binds.isEmpty()) {
            hostConfig.withBinds(Binds.fromPrimitive(binds.toArray(new String[0])));
        }
        final List<Device> devices = dockerRunRequest.getDevices();
        hostConfig.withDevices(devices);
        return hostConfig;
    }

    public boolean isContainerPresent(String containerName) {
        return !getContainerId(containerName).isEmpty();
    }

    /**
     * Check if a container is active. The container is considered active
     * it is in one of the statuses {@code running} or {@code restarting}.
     * 
     * @param containerName
     * @return true if the container is in one of the active statuses,
     *         false otherwise.
     */
    public boolean isContainerActive(String containerName) {
        String currentContainerStatus = getContainerStatus(containerName);
        return List.of(RUNNING_STATUS, RESTARTING_STATUS)
                .contains(currentContainerStatus);
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
    public int waitContainerUntilExitOrTimeout(
            String containerName,
            Instant timeoutDate
    ) {
        if (StringUtils.isBlank(containerName)) {
            // TODO throw new IllegalArgumentException("Container name cannot be blank");
            log.error("Container name cannot be blank [name:{}]", containerName);
            return -1;
        }
        if (timeoutDate == null) {
            // TODO throw new IllegalArgumentException("Timeout date cannot be null");
            log.error("Timeout date cannot be null");
            return -1;
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
        if (!isExited) {
            log.warn("Container reached timeout [name:{}]", containerName);
            return -1;    
        }
        int containerExitCode = getContainerExitCode(containerName);
        log.info("Container exited by itself [name:{}, exitCode:{}]",
                containerName, containerExitCode);
        return containerExitCode;
    }

    public int getContainerExitCode(String containerName) {
        if (StringUtils.isBlank(containerName)) {
            // TODO throw new IllegalArgumentException("Container name cannot be blank");
            log.error("Invalid docker container name [name:{}]", containerName);
            return -1;
        }
        try (InspectContainerCmd inspectContainerCmd =
                     getClient().inspectContainerCmd(containerName)) {
            return inspectContainerCmd.exec()
                    .getState()
                    .getExitCodeLong().intValue();
        } catch (Exception e) {
            log.error("Error getting container exit code [name:{}]", containerName, e);
            return -1;
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
            log.error("Error getting docker container logs [name:{}]", containerName, e);
            return Optional.empty();
        }
        return Optional.of(DockerLogs.builder()
                .stdout(stdout.toString())
                .stderr(stderr.toString())
                .build());
    }

    /**
     * Stop a running docker container.
     * 
     * @param containerName name of the container to stop
     * @return true if the container was successfully stopped or its status
     * is not "running" or "restarting", false otherwise.
     */
    public synchronized boolean stopContainer(String containerName) {
        if (StringUtils.isEmpty(containerName)) {
            log.info("Invalid docker container name [name:{}]", containerName);
            return false;
        }
        if (!isContainerPresent(containerName)) {
            log.error("No docker container to stop [name:{}]", containerName);
            return false;
        }
        if (!isContainerActive(containerName)) {
            return true;
        }
        try (StopContainerCmd stopContainerCmd =
                    getClient().stopContainerCmd(containerName)) {
            stopContainerCmd
                    .withTimeout(0) // don't wait
                    .exec();
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
        if (!isContainerPresent(containerName)) {
            log.error("Cannot run docker exec since container not found [name:{}]",
                    containerName);
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
            log.error("Error running docker exec command [name:{}, cmd:{}]",
                    containerName, cmd, e);
            return Optional.empty();
        }
        return Optional.of(DockerLogs.builder()
                .stdout(stdout.toString())
                .stderr(stderr.toString())
                .build());
    }

    /*
     * Docker client
     */

    /**
     * Build a new docker client instance. If credentials are provided, an authentication
     * attempt is made to the specified registry.
     * 
     * @param registryAddress
     * @param username
     * @param password
     * @return an authenticated docker client if credentials are provided
     * @throws IllegalArgumentException if registry address is blank
     * @throws DockerException if authentication fails
     */
    private static DockerClient createClient(String registryAddress, String username,
            String password) throws DockerException, IllegalArgumentException {
        if (StringUtils.isBlank(registryAddress)) {
            throw new IllegalArgumentException("Registry address must not be blank");
        }
        boolean shouldAuthenticate = StringUtils.isNotBlank(username)
                && StringUtils.isNotBlank(password);
        DefaultDockerClientConfig.Builder configBuilder =
                DefaultDockerClientConfig.createDefaultConfigBuilder()
                        .withDockerTlsVerify(false)
                        .withRegistryUrl(registryAddress);
        if (shouldAuthenticate) {
            configBuilder.withRegistryUsername(username)
                    .withRegistryPassword(password);
        }
        DefaultDockerClientConfig config = configBuilder.build();
        DockerHttpClient httpClient = new ZerodepDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .build();
        DockerClient dockerClient = DockerClientImpl.getInstance(config, httpClient);
        if (shouldAuthenticate) {
            dockerClient.authCmd().exec();
            log.info("Authenticated Docker client registry [registry:{}, username:{}]",
                    registryAddress, username);
        }
        return dockerClient;
    }

    /**
     * Parse Docker image name and its registry address. If no registry is specified
     * the default Docker registry {@link DockerClientInstance#DEFAULT_DOCKER_REGISTRY}
     * is returned.
     * <p>
     * e.g.:
     * host.xyz/image:tag           - host.xyz
     * username/image:tag           - docker.io
     * docker.io/username/image:tag - docker.io
     *
     * @param imageName name of the docker image
     * @return registry address
     */
    public static String parseRegistryAddress(String imageName) {
        NameParser.ReposTag reposTag = NameParser.parseRepositoryTag(imageName);
        NameParser.HostnameReposName hostnameReposName = NameParser.resolveRepositoryName(reposTag.repos);
        String registry = hostnameReposName.hostname;
        return AuthConfig.DEFAULT_SERVER_ADDRESS.equals(registry)
                // to be consistent, we use common default address
                // everywhere for the default DockerHub registry
                ? DockerClientInstance.DEFAULT_DOCKER_REGISTRY
                : registry;
    }

}
