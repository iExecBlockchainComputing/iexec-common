package com.iexec.common.replicate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplicateStatusChange {

    private Date date;
    private ReplicateStatus status;
    private ReplicateStatusModifier modifier;

    public ReplicateStatusChange(ReplicateStatus status, ReplicateStatusModifier modifier) {
        this.date = new Date();
        this.status = status;
        this.modifier = modifier;
    }
}
