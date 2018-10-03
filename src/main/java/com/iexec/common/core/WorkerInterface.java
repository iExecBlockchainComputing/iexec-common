package com.iexec.common.core;


import com.iexec.common.config.PublicConfiguration;
import com.iexec.common.config.WorkerConfigurationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping
public interface WorkerInterface {

    @RequestMapping(method = RequestMethod.GET, path = "/coreversion")
    ResponseEntity<String> getCoreVersion();

    @RequestMapping(method = RequestMethod.GET, path = "/workers/config")
    ResponseEntity<PublicConfiguration> getPublicConfiguration();

    @RequestMapping(method = RequestMethod.POST, path = "/workers/ping")
    ResponseEntity ping(@RequestParam(name = "workerName") String workerName);

    @RequestMapping(method = RequestMethod.POST, path = "/workers/register")
    ResponseEntity registerWorker(@RequestBody WorkerConfigurationModel model);

}