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
import com.iexec.common.docker.client.DockerClientInstance;
import com.iexec.common.docker.client.DockerLogs;
import com.iexec.common.docker.client.DockerRunRequest;
import com.iexec.common.sdk.broker.BrokerOrder;
import com.iexec.common.utils.FileHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
public class IexecCli {

    private static final String IEXEC_CLI = "iexec-cli";
    private static final String IEXEC_SDK_DOCKER_URI = "iexechub/iexec-sdk:5.0.0";
    private static final String WORKING_DIR = "/home/node";

    private static final DockerClientInstance dockerClient = DockerClientFactory.get();

    /*
     * WARN using iexec-cli infurwhatever key
     *
     */
    public static synchronized String runIexecFillCommand(
            int chainId,
            BrokerOrder brokerOrder,
            String walletContent,
            String walletPassword
    ) {
        // start SDK container
        DockerRunRequest request = DockerRunRequest.builder()
                .containerName(IEXEC_CLI)
                .imageUri(IEXEC_SDK_DOCKER_URI)
                .workingDir(WORKING_DIR)
                .entrypoint("tail -f")
                .maxExecutionTime(-1) // run in detached mode
                .build();
        dockerClient.run(request);
        // create iExec files in container
        dockerClient.exec(IEXEC_CLI, getIexecInitCommand());
        // change dev chain id in chain.json
        dockerClient.exec(IEXEC_CLI,"sh", "-c", "sed -i 's/65535/17/' chain.json");
        // copy wallet in container
        dockerClient.exec(IEXEC_CLI, getCopyWalletCommand(walletContent));
        Optional<DockerLogs> walletCheckLogs = dockerClient.exec(IEXEC_CLI, "sh", "-c", "ls -l | grep wallet.json");
        if (walletCheckLogs.isEmpty() || StringUtils.isBlank(walletCheckLogs.get().getStdout())) {
            log.error("Error copying wallet for match order");
            dockerClient.stopAndRemoveContainer(IEXEC_CLI);
            return "";
        }
        // copy orders in container
        dockerClient.exec(IEXEC_CLI, getCopyOrdersCommand(chainId, brokerOrder));
        Optional<DockerLogs> ordersCheckLogs = dockerClient.exec(IEXEC_CLI, "sh", "-c", "ls -l | grep orders.json");
        if (ordersCheckLogs.isEmpty() || StringUtils.isBlank(ordersCheckLogs.get().getStdout())) {
            log.error("Error copying orders for match order");
            dockerClient.stopAndRemoveContainer(IEXEC_CLI);
            return "";
        }
        // run match order command
        Optional<DockerLogs> matchOrderLogs = dockerClient.exec(IEXEC_CLI, getMatchOrdersCommand(chainId, walletPassword));
        if (matchOrderLogs.isEmpty()) {
            log.error("Failed to match orders");
            dockerClient.stopAndRemoveContainer(IEXEC_CLI);
            return "";
        }
        String stdout = matchOrderLogs.get().getStdout();
        String stderr = matchOrderLogs.get().getStderr();
        // check stderr
        if (stderr != null && !stderr.isEmpty()) {
            log.error("Failed to run (found stderr) [stdout:{}, stderr:{}]",
                    stdout, stderr);
            dockerClient.stopAndRemoveContainer(IEXEC_CLI);
            return "";
        }
        // parse logs
        try {
            String dealId = "";
            FillOrdersCliOutput cliOutput = new JsonMapper()
                    .readValue(stdout, FillOrdersCliOutput.class);
            if (cliOutput.isOk()) {
                dealId = cliOutput.getDealid();
            } else {
                log.error("Failed to run (not ok response)");
            }
            dockerClient.stopAndRemoveContainer(IEXEC_CLI);
            return dealId;
        } catch (JsonProcessingException e) {
            log.error("Failed to run (empty response)", e);
            dockerClient.stopAndRemoveContainer(IEXEC_CLI);
            return "";
        }
    }

    private static String[] getIexecInitCommand() {
        return "iexec init --skip-wallet".split(" ");
    }

    private static String[] getCopyWalletCommand(String walletContent) {
        return new String[]{"sh", "-c", "echo '" + walletContent + "' > wallet.json"};
    }

    private static String[] getCopyOrdersCommand(int chainId, BrokerOrder brokerOrder) {
        try {
            BrokerOrderCliInput cliInput = new BrokerOrderCliInput(chainId, brokerOrder);
            String cliInputJsonString = new ObjectMapper().writeValueAsString(cliInput);
            return new String[]{"sh", "-c", "echo '" + cliInputJsonString + "' > orders.json"};
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("Failed to copyCliInputToHomeDir (writeValueAsString)");
            return new String[0];
        }
    }

    private static String[] getMatchOrdersCommand(int chainId, String walletPassword) { 
        return String.format(
                "iexec order fill --skip-request-check --chain %s " +
                "--wallet-file wallet.json --keystoredir %s --password %s --raw",
                chainId, WORKING_DIR, walletPassword)
                .split(" ");
    }
}
