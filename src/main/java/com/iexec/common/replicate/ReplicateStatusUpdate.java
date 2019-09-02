package com.iexec.common.replicate;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.iexec.common.replicate.ReplicateStatusModifier.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplicateStatusUpdate {

    private ReplicateStatus status;
    private ReplicateStatusModifier modifier;
    private Date date;                          // defined by the core
    private boolean isSuccess;                  // inferred automatically from the status
    private ReplicateStatusDetails details;

    public ReplicateStatusUpdate(ReplicateStatus status,
                                 ReplicateStatusModifier modifier,
                                 ReplicateStatusDetails details) {
        this.status = status;
        this.modifier = modifier;
        this.details = details;
        this.isSuccess = ReplicateStatus.isSuccess(status);

        if (modifier != null && modifier.equals(POOL_MANAGER)) {
            this.date = new Date();
        }
    }

    public ReplicateStatusUpdate(ReplicateStatus status,
                                 ReplicateStatusModifier modifier) {
       this(status, modifier, null);
    }

    public static ReplicateStatusUpdate workerRequest(ReplicateStatus status) {
        return new ReplicateStatusUpdate(status, WORKER);
    }

    public static ReplicateStatusUpdate workerRequest(ReplicateStatus status,
                                                      ReplicateStatusCause cause) {
        ReplicateStatusDetails details = ReplicateStatusDetails.builder().cause(cause).build();
        return new ReplicateStatusUpdate(status, WORKER, details);
    }

    public static ReplicateStatusUpdate workerRequest(ReplicateStatus status,
                                                      ReplicateStatusDetails details) {
        return new ReplicateStatusUpdate(status, WORKER, details);
    }

    public static ReplicateStatusUpdate poolManagerRequest(ReplicateStatus status) {
        return new ReplicateStatusUpdate(status, POOL_MANAGER);
    }

    public static ReplicateStatusUpdate poolManagerRequest(ReplicateStatus status,
                                                           ReplicateStatusCause cause) {
        ReplicateStatusDetails details = ReplicateStatusDetails.builder().cause(cause).build();
        return new ReplicateStatusUpdate(status, WORKER, details);
    }

    public static ReplicateStatusUpdate poolManagerRequest(ReplicateStatus status,
                                                           ReplicateStatusDetails details) {
        return new ReplicateStatusUpdate(status, POOL_MANAGER, details);
    }
}