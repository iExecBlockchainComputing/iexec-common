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
import com.iexec.common.docker.DockerLogs;
import com.iexec.common.docker.DockerRunRequest;
import com.iexec.common.docker.client.DockerClientFactory;
import com.iexec.common.docker.client.DockerClientInstance;
import com.iexec.common.sdk.broker.BrokerOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.util.Optional;

@Slf4j
public class IexecCli {

    private static final String IEXEC_CLI = "iexec-cli";
    private static final String IEXEC_SDK_DOCKER_URI = "iexechub/iexec-sdk:7.0.2";
    private static final String WORKING_DIR = "/home/node";
    private static final int DEV_CHAIN_ID = 17;
    private static final String DEV_CHAIN_HUB = "0xBF6B2B07e47326B7c8bfCb4A5460bef9f0Fd2002";

    private static final DockerClientInstance dockerClient = DockerClientFactory.getDockerClientInstance();

    /**
     * Match order onchain by running "iexec order fill"
     * in an iExec SDK docker image. For local chain, the
     * container is connected to 
     * 
     * /!\ WARN using iexec-cli infurwhatever key
     * 
     * @param chainId
     * @param brokerOrder
     * @param walletContent
     * @param walletPassword
     * @return
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
                .dockerNetwork("host") // for local chain
                .maxExecutionTime(-1) // run in detached mode
                .build();
        dockerClient.run(request);
        // create iExec files in container
        dockerClient.exec(IEXEC_CLI, "sh", "-c", getIexecInitCommand());
        // change dev chain id in chain.json
        // TODO change this hack
        if (chainId == DEV_CHAIN_ID) {
            setupLocalChainConfig();
        }
        // copy wallet in container
        dockerClient.exec(IEXEC_CLI, "sh", "-c", getCopyWalletCommand(walletContent));
        Optional<DockerLogs> walletCheckLogs = dockerClient.exec(IEXEC_CLI, "sh", "-c", "ls -l | grep wallet.json");
        if (walletCheckLogs.isEmpty() || StringUtils.isBlank(walletCheckLogs.get().getStdout())) {
            log.error("Error copying wallet for match order");
            dockerClient.stopAndRemoveContainer(IEXEC_CLI);
            return "";
        }
        // copy orders in container
        dockerClient.exec(IEXEC_CLI, "sh", "-c", getCopyOrdersCommand(chainId, brokerOrder));
        Optional<DockerLogs> ordersCheckLogs = dockerClient.exec(IEXEC_CLI, "sh", "-c", "ls -l | grep orders.json");
        if (ordersCheckLogs.isEmpty() || StringUtils.isBlank(ordersCheckLogs.get().getStdout())) {
            log.error("Error copying orders for match order");
            dockerClient.stopAndRemoveContainer(IEXEC_CLI);
            return "";
        }
        // run match order command
        Optional<DockerLogs> matchOrderLogs = dockerClient.exec(IEXEC_CLI, "sh", "-c", getMatchOrdersCommand(chainId, walletPassword));
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

    private static String getIexecInitCommand() {
        return "iexec init --skip-wallet";
    }

    private static void setupLocalChainConfig() {
        Optional<DockerLogs> catLogs =
                dockerClient.exec(IEXEC_CLI, "sh", "-c", "cat chain.json");
        if (catLogs.isEmpty()
                || StringUtils.isBlank(catLogs.get().getStdout())) {
            throw new RuntimeException("Cannot setup local chain");
        }
        JSONObject chainDotJson = new JSONObject(catLogs.get().getStdout());
        // dev chain
        chainDotJson.getJSONObject("chains").getJSONObject("dev").put("id", String.valueOf(DEV_CHAIN_ID));
        chainDotJson.getJSONObject("chains").getJSONObject("dev").put("hub", DEV_CHAIN_HUB);
        chainDotJson.getJSONObject("chains").getJSONObject("dev").put("sms", "http://localhost:15000");
        // sidechain
        JSONObject sidechain = new JSONObject();
        sidechain.put("id", String.valueOf(DEV_CHAIN_ID));
        sidechain.put("host", "http://localhost:8545");
        sidechain.put("sms", "http://localhost:15000");
        sidechain.put("resultProxy", "http://localhost:18089");
        sidechain.put("hub", DEV_CHAIN_HUB);
        sidechain.put("native", true);
        chainDotJson.getJSONObject("chains").put("sidechain", sidechain);
        Optional<DockerLogs> setChainLogs =
                dockerClient.exec(IEXEC_CLI, "sh", "-c", "echo '" + chainDotJson + "' > chain.json");
        if (setChainLogs.isEmpty()
                || StringUtils.isNotBlank(setChainLogs.get().getStderr())) {
            throw new RuntimeException("Cannot setup local chain");
        }
    }

    private static String getCopyWalletCommand(String walletContent) {
        return "echo '" + walletContent + "' > wallet.json";
    }

    private static String getCopyOrdersCommand(int chainId, BrokerOrder brokerOrder) {
        try {
            BrokerOrderCliInput cliInput = new BrokerOrderCliInput(chainId, brokerOrder);
            String cliInputJsonString = new ObjectMapper().writeValueAsString(cliInput);
            return "echo '" + cliInputJsonString + "' > orders.json";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("Failed to copyCliInputToHomeDir (writeValueAsString)");
            return "";
        }
    }

    private static String getMatchOrdersCommand(int chainId, String walletPassword) { 
        return String.format(
                "iexec order fill --skip-request-check --chain %s " +
                "--wallet-file wallet.json --keystoredir %s --password %s --raw",
                chainId, WORKING_DIR, walletPassword);
    }
}
