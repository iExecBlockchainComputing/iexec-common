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

package com.iexec.common.sdk.cli;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.iexec.common.docker.client.DockerClientFactory;
import com.iexec.common.docker.client.DockerLogs;
import com.iexec.common.sdk.cli.input.CliInput;
import com.iexec.common.sdk.cli.output.CliOutput;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class IexecCli {

    private static final String IEXEC_CLI = "iexec-cli";
    private final int chainId;
    private final String walletPassword;

    public IexecCli(int chainId, String walletPassword) {
        this.chainId = chainId;
        this.walletPassword = walletPassword;
    }

    /*
     *
     * WARN using iexec-cli infurwhatever key
     *
     */
    public <T> T run(String cmd, Class<? extends CliOutput<T>> iexecCliResponseClass) {
        if (cmd.isEmpty()) {
            log.error("Failed to runIexecCmd (empty cmd) [cmd:{}]", cmd);
            return null;
        }

        cmd = String.format("%s --chain %s --wallet-file wallet.json --keystoredir /wallet --password %s --raw",
                cmd, chainId, walletPassword);

        Optional<DockerLogs> iexecCliResponse = DockerClientFactory.get().exec(IEXEC_CLI, cmd);

        if (iexecCliResponse.isEmpty()) {
            log.error("Failed to run (iexec-cli exec failed) [cmd:{}]", cmd);
            return null;
        }
        String stdout = iexecCliResponse.get().getStdout();
        String stderr = iexecCliResponse.get().getStderr();

        if (stderr != null && !stderr.isEmpty()){
            log.error("Failed to run (found stderr) [cmd:{}, stdout:{}, stderr:{}]", cmd, stdout, stderr);
            return null;
        }

        CliOutput<T> cliOutput;
        try {
            cliOutput = new JsonMapper().readValue(stdout, iexecCliResponseClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("Failed to run (empty response) [cmd:{}]", cmd);
            return null;
        }

        if (!cliOutput.isOk()) {
            log.error("Failed to run (not ok response) [cmd:{}]", cmd);
            return null;
        }

        return cliOutput.getBody();
    }

    public boolean copyInputToHomeDir(CliInput cliInput, String fileName) {
        String cliInputJsonString;
        try {
            cliInputJsonString = new ObjectMapper().writeValueAsString(cliInput);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("Failed to copyCliInputToHomeDir (writeValueAsString)");
            return false;
        }
        DockerClientFactory.get().exec(IEXEC_CLI, "echo '" + cliInputJsonString + "' > ~/" + fileName);
        return true;
    }
}
