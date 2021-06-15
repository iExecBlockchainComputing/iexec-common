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

import com.github.dockerjava.api.exception.DockerException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public abstract class DockerClientFactory {

    private static Map<String, DockerClientInstance> clientsMap = new HashMap<>();

    /**
     * Get an unauthenticated Docker client instance connected to the default Docker
     * registry {@link DockerClientInstance#DEFAULT_DOCKER_REGISTRY}.
     * 
     * @return unauthenticated client
     */
    public static synchronized DockerClientInstance getDockerClientInstance() {
        try {
            return getOrCreateInstance(DockerClientInstance.DEFAULT_DOCKER_REGISTRY, "", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get an unauthenticated Docker client instance connected to the provided registry.
     * 
     * @return unauthenticated client
     */
    public static synchronized DockerClientInstance
            getDockerClientInstance(String registryAddress) {
        try {
            return getOrCreateInstance(registryAddress, "", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get a Docker client that is authenticated against a specific registry with the
     * provided credentials. The default docker.io registry can be specified using
     * {@link DockerClientInstance#DEFAULT_DOCKER_REGISTRY}.
     * 
     * @param registryUrl e.g. {@code https://index.docker.io/v1/, https://nexus.iex.ec,
     *                          docker.io, nexus.iex.ec}
     * @param username
     * @param password
     * @throws DockerException if the authentication fails
     */
    public static synchronized DockerClientInstance getDockerClientInstance(
            String registryUrl, String username, String password) throws Exception {
        return getOrCreateInstance(registryUrl, username, password);
    }

    /**
     * Used at least in unit tests.
     */
    static void purgeClients() {
        clientsMap.clear();
    }

    private static DockerClientInstance getOrCreateInstance(
            String registryUrl, String username, String password) throws Exception {
        String id = getClientIdentifier(registryUrl, username);
        if (clientsMap.get(id) == null) {
            boolean shouldAuthenticate = StringUtils.isNotBlank(username)
                    && StringUtils.isNotBlank(password);
            DockerClientInstance instance = shouldAuthenticate
                ? new DockerClientInstance(registryUrl, username, password)
                : new DockerClientInstance(registryUrl);
            clientsMap.put(id, instance);
        }
        return clientsMap.get(id);
    }

    private static String getClientIdentifier(String registryUrl, String username) {
        return registryUrl + username;
    }
}
