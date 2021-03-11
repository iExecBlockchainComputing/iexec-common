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

package com.iexec.common.docker;

import com.github.dockerjava.api.model.Device;
import com.iexec.common.utils.ArgsUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DockerRunRequest {

    private String chainTaskId;
    private String containerName;
    private int containerPort;
    private String imageUri;
    private String entrypoint;
    private String cmd;
    private List<String> env;
    private List<String> binds;
    private long maxExecutionTime;
    private String dockerNetwork;
    private String workingDir;
    private boolean shouldDisplayLogs;

    private final List<Device> devices = new ArrayList<>();

    public DockerRunRequest device(
            String cGroupPermissions,
            String pathInContainer,
            String pathOnHost) {
        Device device = new Device(cGroupPermissions, pathInContainer, pathOnHost);
        return device(device);
    }

    public DockerRunRequest device(Device device) {
        this.devices.add(device);
        return this;
    }

    public String getStringArgsCmd() {
        return this.cmd;
    }

    public String[] getArrayArgsCmd() {
        return ArgsUtils.stringArgsToArrayArgs(this.cmd);
    }
}
