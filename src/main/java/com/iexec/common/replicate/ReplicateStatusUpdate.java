package com.iexec.common.replicate;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private ReplicateStatusDetails details;
    @JsonIgnore
    private boolean isSuccess;                  // inferred automatically from the status

    public ReplicateStatusUpdate(ReplicateStatus status) {
        this(status, null, null);
    }

    public ReplicateStatusUpdate(ReplicateStatus status, ReplicateStatusCause cause) {
        this(status, null, new ReplicateStatusDetails(cause));
    }

    public ReplicateStatusUpdate(ReplicateStatus status, ReplicateStatusModifier modifier) {
       this(status, modifier, null);
    }

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

    public ReplicateStatusDetails getDetailsWithoutStdout() {
        if (details == null || details.getStdout() == null) {
            return details;
        }
        ReplicateStatusDetails detailsWithoutStdout = new ReplicateStatusDetails(details);
        detailsWithoutStdout.setStdout(null);
        return detailsWithoutStdout;
    }

    public static ReplicateStatusUpdate poolManagerRequest(ReplicateStatus status) {
        return new ReplicateStatusUpdate(status, POOL_MANAGER);
    }

    public static ReplicateStatusUpdate poolManagerRequest(ReplicateStatus status,
                                                           ReplicateStatusCause cause) {
        ReplicateStatusDetails details = new ReplicateStatusDetails(cause);
        return new ReplicateStatusUpdate(status, WORKER, details);
    }

    public static ReplicateStatusUpdate poolManagerRequest(ReplicateStatus status,
                                                           ReplicateStatusDetails details) {
        return new ReplicateStatusUpdate(status, POOL_MANAGER, details);
    }
}