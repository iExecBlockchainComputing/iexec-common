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
import com.github.dockerjava.api.model.AuthConfig;

import java.util.HashMap;
import java.util.Map;

public abstract class DockerClientFactory {

    private static Map<String, DockerClientInstance> clientsMap = new HashMap<>();

    /**
     * Get an unauthenticated Docker client instance.
     * 
     * @return unauthenticated client
     */
    public static synchronized DockerClientInstance getDockerClientInstance() {
        return createInstance("", "", "");
    }

    /**
     * Get a Docker client instance that is authenticated against the default docker
     * registry {@link AuthConfig#DEFAULT_SERVER_ADDRESS}
     * ({@code https://index.docker.io/v1/}) using the provided credentials.
     * 
     * @param username
     * @param password
     * @throws DockerException if the authentication fails
     */
    public static synchronized DockerClientInstance getDockerClientInstance(
            String username, String password) throws DockerException {
        return createInstance("", username, password);
    }

    /**
     * Get a Docker client that is authenticated against a specific registry with the
     * provided credentials.
     * 
     * @param registryUrl e.g. {@code https://index.docker.io/v1/, https://nexus.iex.ec,
     *                          docker.io, nexus.iex.ec}
     * @param username
     * @param password
     * @throws DockerException if the authentication fails
     */
    public static synchronized DockerClientInstance getDockerClientInstance(
            String registryUrl, String username, String password) throws DockerException {
        return createInstance(registryUrl, username, password);
    }

    /**
     * Used at least in unit tests.
     */
    static void purgeClients() {
        clientsMap.clear();
    }

    private static DockerClientInstance createInstance(
            String registryUrl, String username, String password) {
        String id = getClientIdentifier(registryUrl, username);
        if (clientsMap.get(id) == null) {
            clientsMap.put(id, new DockerClientInstance(registryUrl, username, password));
        }
        return clientsMap.get(id);
    }

    private static String getClientIdentifier(String registryUrl, String username) {
        return registryUrl + username;
    }
}
