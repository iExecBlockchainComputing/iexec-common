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
    private Date date;
    private boolean isSuccess;
    private ReplicateStatusDetails details;

    public ReplicateStatusUpdate(ReplicateStatus status,
                                 ReplicateStatusModifier modifier,
                                 ReplicateStatusDetails details) {
        this(status, modifier);
        this.details = details;
    }

    public ReplicateStatusUpdate(ReplicateStatus status,
                                 ReplicateStatusModifier modifier) {
        this.status = status;
        this.modifier = modifier;
        this.date = new Date();
        this.isSuccess = ReplicateStatus.isSuccess(status);
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