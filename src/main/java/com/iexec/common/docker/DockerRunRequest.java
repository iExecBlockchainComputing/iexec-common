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
import com.iexec.common.sgx.SgxDriverMode;
import com.iexec.common.utils.ArgsUtils;
import com.iexec.common.utils.SgxUtils;
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
    private SgxDriverMode sgxDriverMode;
    private String dockerNetwork;
    private String workingDir;
    private boolean shouldDisplayLogs;
    private List<Device> devices;

    public String getStringArgsCmd() {
        return this.cmd;
    }

    public String[] getArrayArgsCmd() {
        return ArgsUtils.stringArgsToArrayArgs(this.cmd);
    }

    /**
     * Gets a copy of the binds list.
     * @return A copy of defined binds or an empty list
     */
    public List<String> getBinds() {
        return binds != null ? new ArrayList<>(binds) : List.of();
    }

    /**
     * Gets a copy of the devices list.
     * @return A copy of defined devices or an empty list
     */
    public List<Device> getDevices() {
        return devices != null ? new ArrayList<>(devices) : List.of();
    }

    public SgxDriverMode getSgxDriverMode() {
        return sgxDriverMode != null ? sgxDriverMode : SgxDriverMode.NONE;
    }

    // override builder's sgxDriverMode() & devices() methods
    public static class DockerRunRequestBuilder { 
        private SgxDriverMode sgxDriverMode;
        private List<Device> devices;

        /**
         * Add an SGX device when isSgx is true.
         * 
         * @param sgxDriverMode SGX driver mode
         * @return
         */
        public DockerRunRequestBuilder sgxDriverMode(SgxDriverMode sgxDriverMode) {
            this.sgxDriverMode = sgxDriverMode;
            if (!SgxDriverMode.isDriverModeNotNone(sgxDriverMode)) {
                return this;
            }
            if (this.devices == null) {
                this.devices = new ArrayList<>();
            }

            for (String deviceName : sgxDriverMode.getDevices()) {
                Device sgxDevice = new Device(
                        SgxUtils.SGX_CGROUP_PERMISSIONS,
                        "/dev/" + deviceName,
                        "/dev/" + deviceName);
                this.devices.add(sgxDevice);
            }
            return this;
        }

        /**
         * Add new elements without replacing
         * the existing list.
         * 
         * @param devices
         * @return
         */
        public DockerRunRequestBuilder devices(List<Device> devices) {
            if (this.devices == null) {
                this.devices = new ArrayList<>();
            }
            this.devices.addAll(devices);
            return this;
        }
    }
}
