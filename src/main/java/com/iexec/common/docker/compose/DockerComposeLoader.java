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

package com.iexec.common.docker.compose;

import com.palantir.docker.compose.DockerComposeExtension;
import com.palantir.docker.compose.connection.Container;
import com.palantir.docker.compose.connection.DockerMachine;
import com.palantir.docker.compose.connection.DockerMachine.LocalBuilder;
import com.palantir.docker.compose.connection.DockerPort;
import com.palantir.docker.compose.connection.waiting.HealthCheck;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.Duration;

import java.util.Map;
import java.util.function.Function;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class DockerComposeLoader {

    private String composeFilePath;
    private String mainServiceName;
    private int mainServicePort;
    private int timeoutSeconds;
    private Map<String, String> dockerMachineEnv;

    /**
     * Load docker compose and wait for the main
     * service to respond on the provided port.
     * 
     * @return extension referencing the loaded compose
     */

    public DockerComposeExtension load() {
        if (StringUtils.isBlank(composeFilePath)) {
            throw new IllegalArgumentException("Invalid compose file path");
        }
        if (StringUtils.isBlank(mainServiceName)) {
            throw new IllegalArgumentException("Invalid main service name");
        }
        if (mainServicePort <= 0) {
            throw new IllegalArgumentException("Invalid main service port");
        }
        if (timeoutSeconds <= 0) {
            throw new IllegalArgumentException("Invalid timeout");
        }
        DockerComposeExtension.Builder builder = DockerComposeExtension.builder()
                .file(composeFilePath)
                .removeConflictingContainersOnStartup(true)
                .pullOnStartup(true)
                // .saveLogsTo(path) TODO
                .waitingForService(
                        mainServiceName,
                        httpHealthCheck(mainServicePort),
                        Duration.standardSeconds(timeoutSeconds)
                );
        if (dockerMachineEnv != null) {
            LocalBuilder dockerMachineBuilder = DockerMachine.localMachine();
            dockerMachineEnv.forEach((key, value) -> dockerMachineBuilder
                    .withAdditionalEnvironmentVariable(key, value));
            builder.machine(dockerMachineBuilder.build());
        }
        
        return builder.build();
    }

    private static HealthCheck<Container> httpHealthCheck(int portNumber) {
        Function<DockerPort, String> urlFunction =
                (port) -> port.inFormat("http://$HOST:$EXTERNAL_PORT");
        return HealthChecks.toRespondOverHttp(portNumber, urlFunction);
    }
}
