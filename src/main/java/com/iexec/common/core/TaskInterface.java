package com.iexec.common.core;


import com.iexec.common.replicate.ReplicateModel;
import com.iexec.common.replicate.ReplicateStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@RequestMapping
public interface TaskInterface {

    @RequestMapping(method = RequestMethod.GET, path = "/tasks/available")
    ResponseEntity<ReplicateModel> getReplicate(@RequestParam(name = "workerName") String workerName);

    @RequestMapping(method = RequestMethod.POST, path = "/tasks/{taskId}/replicates/updateStatus")
    ResponseEntity<ReplicateModel> updateReplicateStatus(@PathVariable(name = "taskId") String taskId,
                                                   @RequestParam(name = "replicateStatus") ReplicateStatus replicateStatus,
                                                   @RequestParam(name = "workerName") String workerName);
}